package de.quarian.weaver.players;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;
import de.quarian.weaver.R;
import de.quarian.weaver.Utils;
import de.quarian.weaver.datamodel.PlayerCharacter;

public class PlayerCharacterViewHolder extends RecyclerView.ViewHolder {

    private final Utils.ColorConverter colorConverter;
    private final TextView textView;
    private final ImageView deleteButton;
    private Runnable deletePlayerCharacterRunnable;

    public PlayerCharacterViewHolder(@NonNull View itemView) {
        this(itemView, new Utils.ColorConverter());
    }

    @VisibleForTesting
    public PlayerCharacterViewHolder(@NonNull View itemView, @NonNull Utils.ColorConverter colorConverter) {
        super(itemView);
        textView = itemView.findViewById(R.id.player_character_list_name_text);
        deleteButton = itemView.findViewById(R.id.player_character_list_delete_character);
        this.colorConverter = colorConverter;
    }

    public void setDeletePlayerCharacterRunnable(@NonNull Runnable deletePlayerCharacterRunnable) {
        this.deletePlayerCharacterRunnable = deletePlayerCharacterRunnable;
    }

    public void setPlayerCharacter(final PlayerCharacter playerCharacter) {
        final int textColor = colorConverter.toColorInt(playerCharacter.characterHighlightColorA,
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
