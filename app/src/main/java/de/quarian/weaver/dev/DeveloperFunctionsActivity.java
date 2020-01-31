package de.quarian.weaver.dev;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.di.ApplicationComponent;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerApplicationComponent;

public class DeveloperFunctionsActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_REFRESH_CONTENT = 1;

    @Inject
    public DemoDataSetInjector demoDataSetInjector;

    @Inject
    public WeaverDB weaverDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_functions);
        setTitle(R.string.activity_title_developer_functions);

        final ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
        applicationComponent.inject(this);
        setUpSetDBDemoStateButton();
    }

    private void setUpSetDBDemoStateButton() {
        findViewById(R.id.developer_functions_set_db_demo_state_button).setOnClickListener((view) -> {
            demoDataSetInjector.setDemoState(weaverDB);
            setResult(RESULT_OK);
            finish();
        });
    }
}
