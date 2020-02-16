package de.quarian.weaver.players;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.ActivityPreconditionErrorHandler;
import de.quarian.weaver.R;
import de.quarian.weaver.Utils;
import de.quarian.weaver.database.CampaignDAO;
import de.quarian.weaver.database.PlayerCharacterDAO;
import de.quarian.weaver.database.WeaverDB;
import de.quarian.weaver.datamodel.Campaign;
import de.quarian.weaver.datamodel.PlayerCharacter;
import de.quarian.weaver.di.ActivityModule;
import de.quarian.weaver.di.ApplicationModule;
import de.quarian.weaver.di.DaggerActivityComponent;

// TODO: extend themed activity
// TODO: avatars? not sure if that's a must for players
public class PlayerCharacterListActivity extends AppCompatActivity {

    public static class ActivityDependencies {

        @Inject
        @Nullable
        public ActivityPreconditionErrorHandler errorHandler;

    }

    private static final long INVALID_CAMPAIGN_ID = -1L;
    public static String EXTRA_CAMPAIGN_ID = "extra.campaignId";

    private final ActivityDependencies activityDependencies = new ActivityDependencies();

    @Inject
    public WeaverDB weaverDB;

    private PlayerCharacterDAO playerCharacterDAO;
    private PlayerCharacterAdapter playerCharacterAdapter;
    private long campaignId = INVALID_CAMPAIGN_ID;
    private long roleplayingSystemId = -1L;
    private int highlightColor = Color.WHITE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_character_list);
        injectDependencies();

        final boolean requirements = requireCampaignId();
        if (requirements) {
            readCampaign();
            setUpList();
        }
    }

    private void injectDependencies() {
        DaggerActivityComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this.activityDependencies);
        playerCharacterDAO = weaverDB.playerCharacterDAO();
    }

    private boolean requireCampaignId() {
        if (activityDependencies.errorHandler != null) {
            campaignId = getIntent().getLongExtra(EXTRA_CAMPAIGN_ID, INVALID_CAMPAIGN_ID);
            return activityDependencies.errorHandler.requireOrFinish(() -> campaignId != INVALID_CAMPAIGN_ID, R.string.activity_player_character_list_invalid_id_error_title, R.string.activity_player_character_list_invalid_id_error_text);
        } else {
            finish(); // Should basically never happen
            return false;
        }
    }

    private void readCampaign() {
        final CampaignDAO campaignDAO = weaverDB.campaignDAO();
        AsyncTask.execute(() -> {
            final Campaign campaign = campaignDAO.readCampaignByID(this.campaignId);
            this.roleplayingSystemId = campaign.roleplayingSystemId;
        });
    }

    private void setUpList() {
        final RecyclerView playerCharacterList = findViewById(R.id.player_character_list);
        playerCharacterList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        playerCharacterAdapter = new PlayerCharacterAdapter(this, playerCharacterDAO);

        AsyncTask.execute(() -> {
            final List<PlayerCharacter> playerCharacters = playerCharacterDAO.readPlayerCharactersForCampaign(campaignId);
            runOnUiThread(() -> {
                playerCharacterList.setAdapter(playerCharacterAdapter);
                playerCharacterAdapter.refreshPlayerCharacters(playerCharacters);
            });
        });
    }

    public void onShowColorPickerClicked(View view) {
        final ColorPicker colorPicker = new ColorPicker(this, 255, 255, 255, 255);
        colorPicker.enableAutoClose();
        colorPicker.show();
        colorPicker.setCallback((@ColorInt int colorInt) -> {
            this.highlightColor = colorInt;
            final TextView example = findViewById(R.id.player_character_list_character_text_hint);
            example.setTextColor(colorInt);
        });
    }

    public void onAddPlayerCharacterClicked(View view) {
        final TextInputEditText characterName = findViewById(R.id.player_character_list_character_name);
        final TextInputEditText playerName = findViewById(R.id.player_character_list_player_name);
        final PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.campaignId = campaignId;
        playerCharacter.roleplayingSystemId = this.roleplayingSystemId;
        playerCharacter.playerCharacterName = String.valueOf(characterName.getText());
        playerCharacter.playerName = String.valueOf(playerName.getText());

        final int[] colorARGB = Utils.toColorARGB(this.highlightColor);
        playerCharacter.characterHighlightColorA = colorARGB[0];
        playerCharacter.characterHighlightColorR = colorARGB[1];
        playerCharacter.characterHighlightColorG = colorARGB[2];
        playerCharacter.characterHighlightColorB = colorARGB[3];

        AsyncTask.execute(() -> {
            final boolean requiredInputFields = requireInputFields(characterName, playerName);
            if (requiredInputFields) {
                playerCharacterDAO.createPlayerCharacter(playerCharacter);
                final List<PlayerCharacter> playerCharacters = playerCharacterDAO.readPlayerCharactersForCampaign(campaignId);
                playerCharacterAdapter.refreshPlayerCharacters(playerCharacters);
                runOnUiThread(() -> {
                    playerCharacterAdapter.notifyDataSetChanged();
                    characterName.setText("");
                    playerName.setText("");
                    setResult(RESULT_OK);
                });
            }
        });
    }

    private boolean requireInputFields(final TextInputEditText... editTexts) {
        boolean inputIsValid = true;
        for (final TextInputEditText editText : editTexts) {
            final String text = String.valueOf(editText.getText());
            if (text.trim().length() == 0) {
                markInvalidInput(editText);
                inputIsValid = false;
            } else {
                runOnUiThread(() -> editText.setError(null));
            }
        }
        return inputIsValid;
    }

    private void markInvalidInput(@NonNull final TextInputEditText editText) {
        final Resources resources = getResources();
        final String htmlPreset = "<font color='red'>%s</font>";
        final String error = resources.getString(R.string.generic_error_required_field);
        final String htmlError = String.format(htmlPreset, error);
        final Spanned errorSpanned;
        if (Build.VERSION.SDK_INT >= 24) {
            errorSpanned = Html.fromHtml(htmlError, 0);
        } else {
            errorSpanned = Html.fromHtml(htmlError);
        }
        runOnUiThread(() -> editText.setError(errorSpanned));
    }

    /*
    TODO: provide an option to delete chars and maybe re-pick colors
     */
}
