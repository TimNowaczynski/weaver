package de.quarian.weaver.campaigns;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;

public class CampaignActivity extends AppCompatActivity {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";
    public static String EXTRA_MODE = "extra.mode";
    public static int REQUEST_CODE_MODIFY_CAMPAIGNS = -1;

    private static int NO_VALID_CAMPAIGN_ID = -2;

    private Mode mode;
    private int campaignID;

    public enum Mode {
        VIEW, EDIT, NEW
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        determineID();
        determineMode();
        setUpListeners();
    }

    private void determineID() {
        campaignID = getIntent().getIntExtra(EXTRA_CAMPAIGN_ID, NO_VALID_CAMPAIGN_ID);
        if (campaignID == NO_VALID_CAMPAIGN_ID) {
            //TODO: show dialog (closing the activity) and log error
        }
    }

    private void determineMode() {
        final String modeString = getIntent().getStringExtra(EXTRA_MODE);
        mode = Mode.valueOf(modeString);
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

    private void setUpListeners() {
        if (mode != Mode.VIEW) {
            setUpSetThemeButton();
        }
    }

    private void setUpSetThemeButton() {
        final View setThemeButton = findViewById(R.id.set_theme_button);
        setThemeButton.setOnClickListener((view) -> NavigationController.getInstance().setTheme(this, this.campaignID));
    }
}
