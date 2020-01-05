package de.quarian.weaver.characters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;

public class CharacterEditorActivity extends AppCompatActivity {

    public static String EXTRA_MODE = "extra.mode";

    public static String EXTRA_CHARACTER_ID = "extra.characterID";

    public enum Mode {
        NEW, EDIT
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_edit);
        determineMode();
    }

    private void determineMode() {
        final Mode mode = (Mode) getIntent().getSerializableExtra(EXTRA_MODE);
        configureTitle(mode);
    }

    private void configureTitle(@NonNull Mode mode) {
        //TODO: Override title with character name if in edit mode
        if (mode == Mode.EDIT) {
            setTitle(R.string.activity_title_character_library_edit);
        } else {
            setTitle(R.string.activity_title_character_library_add);
        }
    }
}
