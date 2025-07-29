// This class represents a playing card, it contains the
// ascii artwork as well as the face value that is
// used for the value calculation.
public class Card {
    private final String[] artFaceDown = new String[]{
            "╭-------╮",
            "| \\ | / |",
            "|  \\|/  |",
            "|---X---|",
            "|  /|\\  |",
            "| / | \\ |",
            "╰-------╯",
    };
    private final String[] artFaceUp;
    private final CardFace face;

    // This is the "constructor" for the Card object.
    // This is how we create the object, we use the passed
    // in face and suit to set the face up ascii art, then
    // set the face value for later calculations.
    public Card(CardFace face, CardSuit suit) {
        this.artFaceUp = new String[]{
        "╭-------╮",
        "| " + suit.str() + "     |",
        "|       |",
        "|   " + face.str() + "  |",
        "|       |",
        "|     " + suit.str() + " |",
        "╰-------╯",
        };

        this.face = face;
    }

    // This method returns the ascii art string for a face down card.
    public String[] getArtFaceDown() {
        return artFaceDown;
    }

    // This method returns the ascii art string for a face up card.
    public String[] getArtFaceUp() {
        return artFaceUp;
    }

    // This method returns the number of indices in the ascii art array.
    public int getArtIndices() {
        return artFaceUp.length;
    }

    // This method returns the face value of the card.
    public CardFace getFace() {
        return face;
    }
}
