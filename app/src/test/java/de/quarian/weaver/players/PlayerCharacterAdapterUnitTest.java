package de.quarian.weaver.players;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import de.quarian.weaver.R;
import de.quarian.weaver.Utils;
import de.quarian.weaver.database.PlayerCharacterDAO;
import de.quarian.weaver.datamodel.PlayerCharacter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerCharacterAdapterUnitTest {

    @Mock
    public Activity activity;

    @Mock
    public PlayerCharacterDAO playerCharacterDAO;

    private PlayerCharacterAdapter playerCharacterAdapter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        playerCharacterAdapter = new PlayerCharacterAdapter(activity, playerCharacterDAO);
    }

    @Test
    public void testRefreshListOfPlayerCharacters() {
        final List<PlayerCharacter> playerCharacters = playerCharacterAdapter.getPlayerCharacters();
        assertThat(playerCharacters.size(), is(0));

        final PlayerCharacter playerCharacter = mock(PlayerCharacter.class);
        final List<PlayerCharacter> newContent = new ArrayList<>(1);
        newContent.add(playerCharacter);
        newContent.add(playerCharacter);

        playerCharacterAdapter.refreshPlayerCharacters(newContent);
        assertThat(playerCharacters.size(), is(2));

        newContent.remove(1);
        playerCharacterAdapter.refreshPlayerCharacters(newContent);
        assertThat(playerCharacters.size(), is(1)); // <- Should be 1, not 3
    }

    @Test
    public void testBindViewHolder() {
        final PlayerCharacter playerCharacter = mock(PlayerCharacter.class);
        final List<PlayerCharacter> playerCharacters = new ArrayList<>(1);
        playerCharacters.add(playerCharacter);
        playerCharacterAdapter.refreshPlayerCharacters(playerCharacters);

        final View rootView = mock(View.class);
        final View textView = mock(TextView.class);
        final View imageView = mock(ImageView.class);
        when(rootView.findViewById(R.id.player_character_list_name_text)).thenReturn(textView);
        when(rootView.findViewById(R.id.player_character_list_delete_character)).thenReturn(imageView);

        final Utils.ColorConverter colorConverter = mock(Utils.ColorConverter.class);
        final PlayerCharacterViewHolder viewHolder = new PlayerCharacterViewHolder(rootView, colorConverter);
        playerCharacterAdapter.bindViewHolder(viewHolder, 0);
    }

    @Test
    public void deletePlayerCharacter() {
        final PlayerCharacter playerCharacter = mock(PlayerCharacter.class);
        playerCharacterAdapter.deletePlayerCharacter(playerCharacter);
        verify(playerCharacterDAO).deletePlayerCharacter(playerCharacter);

        final ArgumentCaptor<Runnable> argumentCaptor = ArgumentCaptor.forClass(Runnable.class);
        verify(activity).runOnUiThread(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), notNullValue());

        verify(activity).setResult(Activity.RESULT_OK);
    }
}
