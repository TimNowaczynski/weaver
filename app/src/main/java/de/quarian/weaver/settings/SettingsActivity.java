package de.quarian.weaver.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.activity_title_settings_screen);
    }

    //TODO: Configure Automatic Asset Cleanup
    /*
     * TODO: (Create an activity which can be reached from within this one)
     *  ^-- scratch that, there is already such an activity
     *  and can be reached from the campaign list activity
     *  which shows an overview of all assets in two tabs, one for each
     *  scheduled and unscheduled items.
     *  It should be possible to invoke this with an intent parameter
     *  which filters the list by campaign id (we might need another DB change
     *  for this).
     *  In the latter case it shall be reached through a hint in the campaign screen.
     *  "x items are scheduled to delete, review now"
     */

    //TODO: Spread the love (rate, donate)
}
