package de.quarian.weaver.test;

import android.os.Bundle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import de.quarian.weaver.R;
import de.quarian.weaver.theming.ThemePreviewFragment;

public class FragmentTestHostActivity extends AppCompatActivity {

    public static final String EXTRA_FRAGMENT_CLASS = "test.extras.fragmentClassToTest";

    private ThemePreviewFragment fragmentUnderTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test_host);

        try {
            final String fragmentClassName = getIntent().getStringExtra(EXTRA_FRAGMENT_CLASS);
            @SuppressWarnings("unchecked")
            final Class<ThemePreviewFragment> fragmentClass = (Class<ThemePreviewFragment>) getClassLoader().loadClass(fragmentClassName);
            final Constructor<ThemePreviewFragment> constructor = fragmentClass.getConstructor();

            fragmentUnderTest = constructor.newInstance();
            setContentView(R.layout.activity_fragment_test_host);

            final FragmentManager supportFragmentManager = getSupportFragmentManager();
            supportFragmentManager.beginTransaction()
                    .add(R.id.test_activity_fragment_container, fragmentUnderTest)
                    .commitAllowingStateLoss();

        } catch (ClassNotFoundException |
                NoSuchMethodException |
                IllegalAccessException |
                InstantiationException |
                InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    public Fragment getFragmentUnderTestOrFail() {
        if (fragmentUnderTest == null) {
            throw new RuntimeException("Could not initialize fragment");
        }
        return fragmentUnderTest;
    }
}
