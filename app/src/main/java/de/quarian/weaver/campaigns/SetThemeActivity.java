package de.quarian.weaver.campaigns;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;

public class SetThemeActivity extends AppCompatActivity {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_theme);
        setTitle(R.string.activity_title_set_theme);
    }
}
