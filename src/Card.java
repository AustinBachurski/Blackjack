public class Card {
    private final String[] artFaceDown;
    private final String[] artFaceUp;
    private final CardFace face;

    public Card(CardFace face, CardSuit suit) {
        this.artFaceDown = new String[]{
                "╭-------╮",
                "| \\ | / |",
                "|  \\|/  |",
                "|---X---|",
                "|  /|\\  |",
                "| / | \\ |",
                "╰-------╯",
        };

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

    public String[] getArtFaceDown() {
        return artFaceDown;
    }

    public String[] getArtFaceUp() {
        return artFaceUp;
    }

    public int getArtIndices() {
        return artFaceUp.length;
    }

    public CardFace getFace() {
        return face;
    }
}
