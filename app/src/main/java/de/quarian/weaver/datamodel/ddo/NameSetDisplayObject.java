package de.quarian.weaver.datamodel.ddo;

import androidx.annotation.NonNull;

public class NameSetDisplayObject implements Comparable<NameSetDisplayObject> {

    private boolean isSelected;

    private String nameSetName;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(final boolean selected) {
        isSelected = selected;
    }

    public String getNameSetName() {
        return nameSetName;
    }

    public void setNameSetName(@NonNull final String nameSetName) {
        this.nameSetName = nameSetName;
    }

    @Override
    public int compareTo(@NonNull final NameSetDisplayObject nameSetDisplayObject) {
        return nameSetName.compareTo(nameSetDisplayObject.nameSetName);
    }
}
