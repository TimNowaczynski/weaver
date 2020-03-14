package de.quarian.weaver.dev;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.di.DependencyInjector;

public class DeveloperFunctionsActivity extends AppCompatActivity {

    public static class ActivityDependencies {

        @Inject
        public DemoDataSetInjector demoDataSetInjector;

        @Inject
        public WeaverDB weaverDB;

    }

    public final ActivityDependencies activityDependencies = new ActivityDependencies();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_functions);
        setTitle(R.string.activity_title_developer_functions);
        DependencyInjector.get().injectDependencies(this);
        setUpSetDBDemoStateButton();
    }

    private void setUpSetDBDemoStateButton() {
        findViewById(R.id.developer_functions_set_db_demo_state_button).setOnClickListener((view) -> {
            activityDependencies.demoDataSetInjector.setDemoState(activityDependencies.weaverDB);
            setResult(RESULT_OK);
            finish();
        });
    }
}
