package de.quarian.weaver.datamodel;

public class Constants {

    /**
     * For last names we will possibly use {@link NameGender}.UNISEX, not sure
     * if there is something like a last name restricted tp a certain gender
     */
    public enum NameGender {

        MALE(0),
        FEMALE(1),
        UNISEX(2);

        final int value;

        NameGender(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum NamePosition {

        FIRST(0),
        LAST(1);

        final int value;

        NamePosition(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum CharacterGender {

        MALE(0),
        FEMALE(1),
        OTHER(2);

        final int value;

        CharacterGender(final int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
