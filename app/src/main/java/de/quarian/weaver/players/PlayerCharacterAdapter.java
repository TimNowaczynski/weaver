package de.quarian.weaver.players;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.R;
import de.quarian.weaver.WeaverLayoutInflater;
import de.quarian.weaver.database.PlayerCharacterDAO;
import de.quarian.weaver.datamodel.PlayerCharacter;

public class PlayerCharacterAdapter extends RecyclerView.Adapter<PlayerCharacterViewHolder> {

    private final Activity activity;
    private final PlayerCharacterDAO playerCharacterDAO;
    private final WeaverLayoutInflater weaverLayoutInflater;
    private final List<PlayerCharacter> playerCharacters = new ArrayList<>();

    public PlayerCharacterAdapter(@NonNull Activity activity,
                                  @NonNull final PlayerCharacterDAO playerCharacterDAO,
                                  @NonNull final WeaverLayoutInflater weaverLayoutInflater) {
        this.activity = activity;
        this.playerCharacterDAO = playerCharacterDAO;
        this.weaverLayoutInflater = weaverLayoutInflater;
    }

    public void refreshPlayerCharacters(@NonNull final List<PlayerCharacter> playerCharacters) {
        this.playerCharacters.clear();
        this.playerCharacters.addAll(playerCharacters);
    }

    @NonNull
    @Override
    public PlayerCharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = weaverLayoutInflater.inflate(R.layout.player_character_list_item, parent);
        return new PlayerCharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerCharacterViewHolder viewHolder, int position) {
        final PlayerCharacter playerCharacter = playerCharacters.get(position);
        viewHolder.setDeletePlayerCharacterRunnable(() -> AsyncTask.execute(() -> deletePlayerCharacter(playerCharacter)));
        viewHolder.setPlayerCharacter(playerCharacter);
    }

    @VisibleForTesting
    protected void deletePlayerCharacter(final PlayerCharacter playerCharacter) {
        final int position = playerCharacters.indexOf(playerCharacter);
        playerCharacters.remove(playerCharacter);
        playerCharacterDAO.deletePlayerCharacter(playerCharacter);
        activity.runOnUiThread(() -> this.notifyItemRemoved(position));
        activity.setResult(Activity.RESULT_OK);
    }

    @Override
    public int getItemCount() {
        return playerCharacters.size();
    }

    @VisibleForTesting
    protected List<PlayerCharacter> getPlayerCharacters() {
        return playerCharacters;
    }
}
