package de.quarian.weaver.characters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;

public class CharacterEventActivity extends AppCompatActivity {

    public static int REQUEST_CODE_ADD_EVENT = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setTitle(R.string.activity_title_character_add_event);
    }
}
