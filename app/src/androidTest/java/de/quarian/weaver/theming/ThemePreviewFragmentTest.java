package de.quarian.weaver.theming;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.fragment.app.FragmentManager;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import de.quarian.weaver.R;
import de.quarian.weaver.datamodel.ddo.ThemeDisplayObject;
import de.quarian.weaver.test.FragmentTestHostActivity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ThemePreviewFragmentTest {

    @Rule
    public ActivityTestRule<FragmentTestHostActivity> activityTestRule = new ActivityTestRule<>(FragmentTestHostActivity.class, false, false);

    @Before
    public void setUp() {
        final Intent startIntent = new Intent();
        startIntent.putExtra(FragmentTestHostActivity.EXTRA_FRAGMENT_CLASS, ThemePreviewFragment.class.getName());
        activityTestRule.launchActivity(startIntent);
    }

    @Test
    public void testElementsArePresent() {
        final View view = getPreviewView();

        // So we can't really look up views by hints (I think), so I go just for IDs here
        assertThat(view.findViewById(R.id.fragment_theme_preview_background), notNullValue());
        assertThat(view.findViewById(R.id.fragment_theme_preview_background_text), notNullValue());
        assertThat(view.findViewById(R.id.fragment_theme_preview_button), notNullValue());
        assertThat(view.findViewById(R.id.fragment_theme_preview_item), notNullValue());
        assertThat(view.findViewById(R.id.fragment_theme_preview_item_text), notNullValue());
    }

    @NotNull
    private View getPreviewView() {
        final FragmentManager supportFragmentManager = activityTestRule.getActivity().getSupportFragmentManager();
        final ThemePreviewFragment themePreviewFragment = (ThemePreviewFragment) supportFragmentManager.getFragments().get(0);
        assertThat(themePreviewFragment, notNullValue());

        final View view = themePreviewFragment.getView();
        assertThat(view, notNullValue());
        return view;
    }

    @Test
    public void testActionColorCanBeSet() throws Exception {
        final ThemePreviewFragment themePreviewFragment = getThemePreviewFragment();
        final ThemeDisplayObject themeDisplayObject = themePreviewFragment.getThemeDisplayObject();
        themeDisplayObject.actionColor = Color.BLACK;
        themePreviewFragment.refreshContent();
        themeDisplayObject.refresh();

        // Wait for Async Task doing the refresh
        Thread.sleep(100L);

        final View view = getPreviewView();
        final View actionView = view.findViewById(R.id.fragment_theme_preview_button);
        final GradientDrawable background = (GradientDrawable) actionView.getBackground();
        assertThat(background.getColor(), notNullValue());
        assertThat(background.getColor().getDefaultColor(), is(Color.BLACK));
    }

    @NotNull
    private ThemePreviewFragment getThemePreviewFragment() {
        final FragmentTestHostActivity fragmentTestHostActivity = activityTestRule.getActivity();
        return (ThemePreviewFragment) fragmentTestHostActivity.getFragmentUnderTestOrFail();
    }

    @Test
    public void testBackgroundTextColorCanBeSet() throws Exception {
        final ThemePreviewFragment themePreviewFragment = getThemePreviewFragment();
        final ThemeDisplayObject themeDisplayObject = themePreviewFragment.getThemeDisplayObject();
        themeDisplayObject.backgroundTextColor = Color.BLACK;
        themePreviewFragment.refreshContent();

        // Wait for Async Task doing the refresh
        Thread.sleep(100L);

        final View view = getPreviewView();
        final TextView backgroundTextView = view.findViewById(R.id.fragment_theme_preview_background_text);
        assertThat(backgroundTextView.getTextColors().getDefaultColor(), is(Color.BLACK));
    }

    @Test
    public void testItemTextColorCanBeSet() throws Exception {
        final ThemePreviewFragment themePreviewFragment = getThemePreviewFragment();
        final ThemeDisplayObject themeDisplayObject = themePreviewFragment.getThemeDisplayObject();
        themeDisplayObject.itemTextColor = Color.BLACK;
        themePreviewFragment.refreshContent();

        // Wait for Async Task doing the refresh
        Thread.sleep(100L);

        final View view = getPreviewView();
        final TextView itemTextView = view.findViewById(R.id.fragment_theme_preview_item_text);
        assertThat(itemTextView.getTextColors().getDefaultColor(), is(Color.BLACK));
    }

    @Test
    public void testItemSecondaryTextColorCanBeSet() throws Exception {
        final ThemePreviewFragment themePreviewFragment = getThemePreviewFragment();
        final ThemeDisplayObject themeDisplayObject = themePreviewFragment.getThemeDisplayObject();
        themeDisplayObject.itemTextSecondaryColor = Color.argb(255, 255, 0, 0);
        themePreviewFragment.refreshContent();

        // Wait for Async Task doing the refresh
        Thread.sleep(100L);

        final View view = getPreviewView();
        final TextView itemTextView = view.findViewById(R.id.fragment_theme_preview_item_text_secondary);

        final int currentTextColor = itemTextView.getCurrentTextColor();
        assertThat(currentTextColor, is(Color.RED));
    }

    @Test
    public void testBackgroundColorCanBeSet() throws Exception {
        final ThemePreviewFragment themePreviewFragment = getThemePreviewFragment();
        final ThemeDisplayObject themeDisplayObject = themePreviewFragment.getThemeDisplayObject();
        themeDisplayObject.backgroundColor = Color.BLACK;
        themePreviewFragment.refreshContent();

        // Wait for Async Task doing the refresh
        Thread.sleep(100L);

        final View view = getPreviewView();
        final View backgroundView = view.findViewById(R.id.fragment_theme_preview_background);
        final ColorDrawable background = (ColorDrawable) backgroundView.getBackground();
        assertThat(background.getColor(), is(Color.BLACK));
    }

    @Test
    public void testItemColorCanBeSet() throws Exception {
        final ThemePreviewFragment themePreviewFragment = getThemePreviewFragment();
        themePreviewFragment.setItemColor(Color.BLACK);

        // Wait for Async Task doing the refresh
        Thread.sleep(100L);

        final View view = getPreviewView();
        final View backgroundView = view.findViewById(R.id.fragment_theme_preview_item);

        final GradientDrawable background = (GradientDrawable) backgroundView.getBackground();
        assertThat(background.getColor(), notNullValue());
        assertThat(background.getColor().getDefaultColor(), is(Color.BLACK));
    }
}
