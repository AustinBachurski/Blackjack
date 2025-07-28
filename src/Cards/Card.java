package Cards;

public class Card {
    public String[] _art;
    CardFace _face;    // ╭, ╮, ╰, ╯
    CardSuite _suite;  // ♣, ♠, ♦, ♥

    public Card(CardFace face, CardSuite suite) {
        _art = new String[]{
        "╭-------╮",
        "| " + suite.str() + "     |",
        "|   " + face.str() + "  |",
        "|     " + suite.str() + " |",
        "╰-------╯",
        };
        _face = face;
        _suite = suite;

    }

}
