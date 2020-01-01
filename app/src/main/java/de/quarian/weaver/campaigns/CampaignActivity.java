package de.quarian.weaver.campaigns;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;

public class CampaignActivity extends AppCompatActivity {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";
    public static String EXTRA_MODE = "extra.mode";
    public static int REQUEST_CODE_MODIFY_CAMPAIGNS = -1;

    public enum Mode {
        VIEW, EDIT, NEW
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        determineMode();
    }

    private void determineMode() {
        final String modeString = getIntent().getStringExtra(EXTRA_MODE);
        final Mode mode = Mode.valueOf(modeString);
        if (mode == Mode.VIEW) {
            //TODO: Put campaign name into title as well
            setTitle(R.string.activity_title_view_campaign_screen);
            setContentView(R.layout.activity_view_campaign);
        } else if(mode == Mode.NEW) {
            setTitle(R.string.activity_title_add_campaign_screen);
            setContentView(R.layout.activity_edit_campaign);
        } else {
            //TODO: init with values (don't forget the title here as well)
            setTitle(R.string.activity_title_edit_campaign_screen);
            setContentView(R.layout.activity_edit_campaign);
        }
    }

}
