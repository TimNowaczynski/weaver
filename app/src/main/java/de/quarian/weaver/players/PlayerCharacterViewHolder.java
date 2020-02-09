package de.quarian.weaver.players;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.PlayerCharacter;

public class PlayerCharacterViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;
    private final ImageView deleteButton;
    private Runnable deletePlayerCharacterRunnable;

    public PlayerCharacterViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.player_character_list_name_text);
        deleteButton = itemView.findViewById(R.id.player_character_list_delete_character);
    }

    public void setDeletePlayerCharacterRunnable(@NonNull Runnable deletePlayerCharacterRunnable) {
        this.deletePlayerCharacterRunnable = deletePlayerCharacterRunnable;
    }

    public void setPlayerCharacter(final PlayerCharacter playerCharacter) {
        final int textColor = Color.argb(
                playerCharacter.characterHighlightColorA,
                playerCharacter.characterHighlightColorR,
                playerCharacter.characterHighlightColorG,
                playerCharacter.characterHighlightColorB);

        textView.setTextColor(textColor);
        textView.setText(String.format("%s (%s)", playerCharacter.playerCharacterName, playerCharacter.playerName));
        deleteButton.setOnClickListener((view) -> deletePlayerCharacterRunnable.run());

        if (playerCharacter.playerCharacterAvatar == null) {
            textView.setCompoundDrawables(null, null, null, null);
        } else {
            /* TODO: extend with player character avatar,
                but then we also need an interface element to add those.
                Best way might be to have it clearly optional via a
                button in the table row (list view item).
             */
        }
    }
}
