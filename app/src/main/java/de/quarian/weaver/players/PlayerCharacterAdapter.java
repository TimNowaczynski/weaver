package de.quarian.weaver.players;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.R;
import de.quarian.weaver.database.PlayerCharacterDAO;
import de.quarian.weaver.datamodel.PlayerCharacter;

public class PlayerCharacterAdapter extends RecyclerView.Adapter<PlayerCharacterViewHolder> {

    private final Activity activity;
    private final PlayerCharacterDAO playerCharacterDAO;
    private final List<PlayerCharacter> playerCharacters = new ArrayList<>();

    public PlayerCharacterAdapter(@NonNull Activity activity, @NonNull final PlayerCharacterDAO playerCharacterDAO) {
        this.activity = activity;
        this.playerCharacterDAO = playerCharacterDAO;
    }

    public void refreshPlayerCharacters(@NonNull final List<PlayerCharacter> playerCharacters) {
        this.playerCharacters.clear();
        this.playerCharacters.addAll(playerCharacters);
    }

    @NonNull
    @Override
    public PlayerCharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.player_character_list_item, parent, false);
        return new PlayerCharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerCharacterViewHolder viewHolder, int position) {
        final PlayerCharacter playerCharacter = playerCharacters.get(position);
        viewHolder.setDeletePlayerCharacterRunnable(() -> AsyncTask.execute(() -> {
            playerCharacters.remove(playerCharacter);
            playerCharacterDAO.deletePlayerCharacter(playerCharacter);
            activity.runOnUiThread(() -> this.notifyItemRemoved(position));
            activity.setResult(Activity.RESULT_OK);
        }));
        viewHolder.setPlayerCharacter(playerCharacter);
    }

    @Override
    public int getItemCount() {
        return playerCharacters.size();
    }
}
