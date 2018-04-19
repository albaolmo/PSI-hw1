package server;

/**
 * @author Alba Olmo
 *
 */

public enum Orientation {
    NORTH("^"),
    EAST(">"),
    SOUTH("v"),
    WEST("<"){
    };

    private final String text;

    Orientation(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    
}
