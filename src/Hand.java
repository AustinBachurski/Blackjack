import java.util.ArrayList;

public class Hand {
    private static final int ACE_HIGH_VALUE = 10;
    private static final int TWENTY_ONE = 21;
    private static final int TABLE_WIDTH = 60;
    private static final int OTHER_SIDE = 7;
    private static final String[] TABLE_SIDES = new String[] {
            "      / ",
            "     /  ",
            "    /   ",
            "   /    ",
            "  /     ",
            " /      ",
            "/       ",
            "\\",
            " \\",
            "  \\",
            "   \\",
            "    \\",
            "     \\",
            "      \\",
    };
    private final ArrayList<Card> cards = new ArrayList<>();
    private final int cardArtIndices;

    public Hand(Card firstCard, Card secondCard)
    {
        cards.add(firstCard);
        cards.add(secondCard);
        cardArtIndices = firstCard.getArtIndices();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public String allCardsFaceUp() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cardArtIndices; ++i) {
            for (Card card : cards) {
                sb.append(card.getArtFaceUp()[i]).append(' ');
            }
            sb.append('\n');
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public boolean busted() {
       return calculateValue() > TWENTY_ONE;
    }

    public int calculateValue() {
        int total = 0;
        int aces = 0;

        for (Card card : cards) {
            total += card.getFace().value();

            if (card.getFace() == CardFace.ACE) {
                ++aces;
            }
        }

        if (aces == 0) {
            return total;
        }

        int runningTotal = total;

        for (int i = 0; i < aces; ++i) {
            if (runningTotal == TWENTY_ONE) {
                return runningTotal;
            }

            if (runningTotal < TWENTY_ONE && runningTotal + ACE_HIGH_VALUE <= TWENTY_ONE) {
                runningTotal += ACE_HIGH_VALUE;
            }
        }
        return runningTotal;
    }

    public String tableCards(Game.FaceUpOption option ) {
        StringBuilder sb = new StringBuilder();

        int rowStart = 0;

        // Dealer side of the table.
        sb.append("       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        for (int i = 0; i < cardArtIndices; ++i) {
            sb.append(TABLE_SIDES[i]);
            rowStart = sb.length();

            boolean firstCard = true;
            for (Card card : cards) {
                if (firstCard) {
                    sb.append(card.getArtFaceUp()[i]).append(' ');
                    if (option == Game.FaceUpOption.ONE) {
                        firstCard = false;
                    }
                } else {
                    sb.append(card.getArtFaceDown()[i]).append(' ');
                }
            }
            sb.append(" ".repeat(TABLE_WIDTH - (sb.length() - rowStart)));
            sb.append(TABLE_SIDES[i + OTHER_SIDE]);
            sb.append('\n');
        }

        // Player side of the table.
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        sb.append("||     ||                                                         ||     ||\n");
        sb.append("||                                                                       ||\n");
        sb.append("||                                                                       ||\n");
        return sb.toString();
    }
}
