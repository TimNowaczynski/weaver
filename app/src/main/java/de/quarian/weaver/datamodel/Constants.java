package de.quarian.weaver.datamodel;

public class Constants {

    /**
     * For last names we will possibly use {@link Gender}.ANY, not sure
     * if there is something like a last name restricted tp a certain gender
     */
    public enum Gender {

        MALE(0),
        FEMALE(1),
        UNISEX(2);

        final int value;

        Gender(final int value) {
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
}
