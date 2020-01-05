package de.quarian.weaver.campaigns;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;

public class CampaignEditorActivity extends AppCompatActivity {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";
    public static String EXTRA_MODE = "extra.mode";

    private static int INVALID_CAMPAIGN_ID = -2;

    private Mode mode;
    private int campaignID;

    public enum Mode {
        EDIT, NEW
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        determineMode();
        setUpListeners();
    }

    private void determineMode() {
        final String modeString = getIntent().getStringExtra(EXTRA_MODE);
        mode = Mode.valueOf(modeString);
        if(mode == Mode.NEW) {
            setTitle(R.string.activity_title_add_campaign_screen);
            setContentView(R.layout.activity_edit_campaign);
        } else {
            //TODO: init with values (don't forget the title here as well)
            determineID();
            setTitle(R.string.activity_title_edit_campaign_screen);
            setContentView(R.layout.activity_edit_campaign);
        }
    }

    private void determineID() {
        campaignID = getIntent().getIntExtra(EXTRA_CAMPAIGN_ID, INVALID_CAMPAIGN_ID);
        if (campaignID == INVALID_CAMPAIGN_ID) {
            //TODO: show dialog (closing the activity) and log error
        }
    }

    private void setUpListeners() {
        setUpSetThemeButton();
        setUpConfigureNameSetsButton();
    }

    private void setUpSetThemeButton() {
        final View setThemeButton = findViewById(R.id.set_theme_button_dummy);
        setThemeButton.setOnClickListener((view) -> NavigationController.getInstance().setTheme(this, this.campaignID));
    }

    private void setUpConfigureNameSetsButton() {
        final View setThemeButton = findViewById(R.id.configure_name_sets_button_dummy);
        setThemeButton.setOnClickListener((view) -> NavigationController.getInstance().configureNameSets(this, this.campaignID));
    }
}
