package de.quarian.weaver.characters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;

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
        final Intent intent = new Intent(getBaseContext(), CharacterEditorActivity.class);
        intent.putExtra(CharacterEditorActivity.EXTRA_CHARACTER_ID, CharacterEditorActivity.Mode.NEW);
        startActivityForResult(intent, CharacterEditorActivity.REQUEST_CODE_MODIFY_CHARACTERS);
    }

    public void onViewCharacterClicked(View view) {
        final Intent intent = new Intent(getBaseContext(), CharacterViewActivity.class);
        startActivity(intent);
    }

    //TODO: Edit option for list items

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CharacterEditorActivity.REQUEST_CODE_MODIFY_CHARACTERS && resultCode == RESULT_OK) {
            //TODO: refresh content
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
