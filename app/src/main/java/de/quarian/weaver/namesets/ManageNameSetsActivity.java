package de.quarian.weaver.namesets;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;

public class ManageNameSetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_name_sets);
        setTitle(R.string.activity_title_manage_name_sets);
    }
}
