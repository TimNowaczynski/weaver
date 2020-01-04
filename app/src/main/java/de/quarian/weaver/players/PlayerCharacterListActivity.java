package de.quarian.weaver.players;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.quarian.weaver.R;

public class PlayerCharacterListActivity extends AppCompatActivity implements View.OnClickListener {

    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_character_list);
        findViewById(R.id.player_character_list_pick_color).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.player_character_list_pick_color) {
            showColorPicker();
        }
    }

    private void showColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(this, 255, 0, 0, 0);
        colorPicker.enableAutoClose();
        colorPicker.show();
        colorPicker.setCallback((@ColorInt int colorInt) -> {
            final TextView example = findViewById(R.id.player_character_list_character_text_example);
            example.setTextColor(colorInt);
        });
    }

    /*
    TODO: provide an option to delete chars and maybe repick colors
     */
}
