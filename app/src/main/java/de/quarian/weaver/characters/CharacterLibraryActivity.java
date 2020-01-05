package de.quarian.weaver.characters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;
import de.quarian.weaver.RequestCodes;

public class CharacterLibraryActivity extends AppCompatActivity {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_library);
        setTitle(R.string.activity_title_character_library);
        //TODO: Override title with campaign name
    }

    // Listeners

    public void onAddCharacterClicked(View view) {
        NavigationController.getInstance().addNewCharacter(this);
    }

    public void onViewCharacterClicked(View view) {
        NavigationController.getInstance().viewCharacter(this);
    }

    //TODO: Edit option for list items

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodes.MODIFY_CHARACTERS && resultCode == RESULT_OK) {
            //TODO: refresh content
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
