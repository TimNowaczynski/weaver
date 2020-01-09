package de.quarian.weaver.datamodel;

public class Constants {

    public enum NamePosition {

        FIRST(0),
        LAST(1),
        ANY(2);

        final int value;

        NamePosition(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
