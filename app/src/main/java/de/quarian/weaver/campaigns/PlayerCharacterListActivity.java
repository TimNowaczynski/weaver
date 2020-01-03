package de.quarian.weaver.campaigns;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;

public class PlayerCharacterListActivity extends AppCompatActivity {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_character_list);
    }

    /*
    TODO: Well, everything. But remember: Select a highlight color,
    TODO: provide an option to delete chars, repick colors
     */
}
