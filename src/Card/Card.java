package Card;

public class Card {
    CardFace _face;    // ╭, ╮, ╰, ╯
    CardSuite _suite;  // ♣, ♠, ♦, ♥

    Card(CardFace face, CardSuite suite) {
        _face = face;
        _suite = suite;
    }
    String[][] representation = new String[][]{
            { "╭-------╮" },
            { "| " + _suite.str() + "     |" },
            { "|   " + _face.str() + "   |" },
            { "|     " + _suite.str() + "|" },
            { "╰-------╯" },
    };

}
