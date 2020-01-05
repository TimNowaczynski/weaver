package de.quarian.weaver.characters;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.quarian.weaver.NavigationController;
import de.quarian.weaver.R;

public class CharacterViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_view);
        setUpToolbar();
        //TODO: Override title with character name
        findViewById(R.id.character_view_add_event).setOnClickListener((view) -> addEvent());
    }

    private void setUpToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.activity_title_character_library_view);
    }

    private void addEvent() {
        final Intent intent = new Intent(getBaseContext(), CharacterEventActivity.class);
        startActivityForResult(intent, CharacterEventActivity.REQUEST_CODE_ADD_EVENT);
    }

    // Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_character_view_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.menu_item_edit_character) {
            NavigationController.getInstance().editCharacter(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == CharacterEditorActivity.REQUEST_CODE_MODIFY_CHARACTERS) {
                //TODO: refresh view
            }
            if (requestCode == CharacterEventActivity.REQUEST_CODE_ADD_EVENT) {
                //TODO: refresh view
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
