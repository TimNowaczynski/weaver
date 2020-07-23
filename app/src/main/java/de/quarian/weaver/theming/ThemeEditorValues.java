package de.quarian.weaver.theming;

import android.graphics.Color;

import androidx.annotation.Nullable;
import de.quarian.weaver.datamodel.Theme;

public class ThemeEditorValues {

    private final int initialActionColor;
    private final int initialScreenBackgroundColor;
    private final int initialBackgroundTextColor;
    private final int initialItemBackgroundColor;
    private final int initialItemTextColor;

    private int actionColor;
    private int screenBackgroundColor;
    private int backgroundTextColor;
    private int itemBackgroundColor;
    private int itemTextColor;

    public ThemeEditorValues(@Nullable final Theme theme) {
        if (theme != null) {
            this.initialActionColor = Color.argb(theme.actionColorA, theme.actionColorR, theme.actionColorG, theme.actionColorB);
            this.initialScreenBackgroundColor = Color.argb(theme.screenBackgroundColorA, theme.screenBackgroundColorR, theme.screenBackgroundColorG, theme.screenBackgroundColorB);
            this.initialBackgroundTextColor = Color.argb(theme.backgroundFontColorA, theme.backgroundFontColorR, theme.backgroundFontColorG, theme.backgroundFontColorB);
            this.initialItemBackgroundColor = Color.argb(theme.itemBackgroundColorA, theme.itemBackgroundColorR, theme.itemBackgroundColorG, theme.itemBackgroundColorB);
            this.initialItemTextColor = Color.argb(theme.itemFontColorA, theme.itemFontColorR, theme.itemFontColorG, theme.itemFontColorB);
        } else {
            this.initialActionColor = Color.BLACK;
            this.initialScreenBackgroundColor = Color.BLACK;
            this.initialBackgroundTextColor = Color.BLACK;
            this.initialItemBackgroundColor = Color.BLACK;
            this.initialItemTextColor = Color.BLACK;
        }

        this.actionColor = this.initialActionColor;
        this.screenBackgroundColor = this.initialScreenBackgroundColor;
        this.backgroundTextColor = this.initialBackgroundTextColor;
        this.itemBackgroundColor = this.initialItemBackgroundColor;
        this.itemTextColor = this.initialItemTextColor;
    }

    public boolean hasPendingChanges() {
        return this.actionColor != this.initialActionColor ||
                this.screenBackgroundColor != this.initialScreenBackgroundColor ||
                this.backgroundTextColor != this.initialBackgroundTextColor ||
                this.itemBackgroundColor != this.initialItemBackgroundColor ||
                this.itemTextColor != this.initialItemTextColor;
    }

    public int getActionColor() {
        return actionColor;
    }

    public void setActionColor(int actionColor) {
        this.actionColor = actionColor;
    }

    public int getScreenBackgroundColor() {
        return screenBackgroundColor;
    }

    public void setScreenBackgroundColor(int screenBackgroundColor) {
        this.screenBackgroundColor = screenBackgroundColor;
    }

    public int getBackgroundTextColor() {
        return backgroundTextColor;
    }

    public void setBackgroundTextColor(int backgroundTextColor) {
        this.backgroundTextColor = backgroundTextColor;
    }

    public int getItemBackgroundColor() {
        return itemBackgroundColor;
    }

    public void setItemBackgroundColor(int itemBackgroundColor) {
        this.itemBackgroundColor = itemBackgroundColor;
    }

    public int getItemTextColor() {
        return itemTextColor;
    }

    public void setItemTextColor(int itemTextColor) {
        this.itemTextColor = itemTextColor;
    }
}
