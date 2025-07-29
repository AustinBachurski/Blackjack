// Here we import the ArrayList collection used to hold our cards.
import java.util.ArrayList;

// This class holds your "Hand" of cards.
public class Hand {
    // Defining some constants, an ace is worth 1 at it's base but
    // may also be worth 11.  The ACE_HIGH_VALUE number is added
    // to the base value in the event it is used as 11.
    private static final int ACE_HIGH_VALUE = 10;

    // If a hand is worth more than 21, the holder busts.
    private static final int TWENTY_ONE = 21;

    // These constants are used for drawing the cards and the table as ascii art.
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

    // This is our collection of cards.
    private final ArrayList<Card> cards = new ArrayList<>();

    // Card art is held in an array, this value contains the number of
    // rows in the array so that we can draw cards side by side.
    private final int cardArtIndices;

    // This is the "constructor" for a Hand object.
    // This is how we create the object, and the body of the method
    // contains the initialization routine for the object.
    public Hand(Card firstCard, Card secondCard)
    {
        // Add the two cards passed in as arguments to our hand of cards.
        cards.add(firstCard);
        cards.add(secondCard);

        // Use the first card to provide indices for drawing the ascii art.
        cardArtIndices = firstCard.getArtIndices();
    }

    // This method passes a card into the cards collection,
    // adding it to our hand of cards.
    public void addCard(Card card) {
        cards.add(card);
    }

    // This method returns the string representation of all of our
    // cards, shown face up.
    public String allCardsFaceUp() {
        // Java strings are "copy on write" - which is memory inefficient
        // when gluing many string components together.  So we use a
        // StringBuilder object to do it efficiently.
        StringBuilder sb = new StringBuilder();

        // The cardArdIndices are used to index into the array
        // of strings that represents the card art.  We access
        // the index of each card in our hand and add it to the
        // String Builder object.
        for (int i = 0; i < cardArtIndices; ++i) {
            for (Card card : cards) {
                sb.append(card.getArtFaceUp()[i]).append(' ');
            }
            // For each loop through the cards, we add a newline character.
            sb.append('\n');
        }
        // We only need the newline during the loops, this is the end of the
        // ascii art string, so we remove the newline character before returning
        // the string.
        sb.deleteCharAt(sb.length() - 1);

        // Done assembling the ascii art, convert the string builder to a
        // proper String object and return.
        return sb.toString();
    }

    // This method checks for a "bust" condition.
    public boolean busted() {
       return calculateValue() > TWENTY_ONE;
    }

    // This method calculates the numeric value of our hand of cards.
    public int calculateValue() {
        // Accumulators are declared.
        int total = 0;
        int aces = 0;

        // We loop through every card in our hand, retrieving the face
        // value for the card, adding it to our total accumulator.
        // We also check to see if a card is an Ace if so, we
        // increment the aces accumulator for later calculations.
        for (Card card : cards) {
            total += card.getFace().value();

            if (card.getFace() == CardFace.ACE) {
                ++aces;
            }
        }

        // If we don't have any aces in our hand, we're done, return the total.
        if (aces == 0) {
            return total;
        }

        // If we to have aces, we initialize another accumulator
        // with the current total.
        int runningTotal = total;

        // Here we loop for each ace we have in our hand, if we have
        // 21, don't bother with additional math, return the accumulator value.
        // Otherwise, we compare the value of the accumulator, if it's less than 21
        // we count one of our aces as 11 instead of 1.  If we're still under 21, we
        // update our running total with the ace as 11.  This loop repeats until
        // we either hit 21, exceed 21, or run out of aces.  Then we return
        // the updated accumulator value.
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

    // This method assembles the ascii art string of the card table with the dealer's cards.
    public String tableCards(Blackjack.FaceUpOption option ) {
        // As before, we use a String Builder.
        StringBuilder sb = new StringBuilder();

        // This variable stores the length of the string before adding the
        // dealer's cards to the ascii art.  We use this to determine how much
        // whitespace to fill the table in with later.
        int rowStart = 0;

        // Dealer side of the table.
        sb.append("       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        // We once again loop over the indices of the card art array.
        for (int i = 0; i < cardArtIndices; ++i) {
            // Add the left side of the card table to the ascii art.
            sb.append(TABLE_SIDES[i]);

            // Record the length of the string at this point.
            rowStart = sb.length();

            // Flag for displaying all or one of the dealer's cards.
            boolean firstCard = true;

            // Loop through each card in the hand, retrieving the ascii art
            // for the card.  The first iteration will always show the first card.
            for (Card card : cards) {
                if (firstCard) {
                    sb.append(card.getArtFaceUp()[i]).append(' ');

                    // Set the firstCard flag based on the enum flag in the game object.
                    if (option == Blackjack.FaceUpOption.ONE) {
                        firstCard = false;
                    }

                // If the flag is not set, we draw the rest of the cards face down.
                } else {
                    sb.append(card.getArtFaceDown()[i]).append(' ');
                }
            }

            // Here we use the rowStart measurement we took earlier to draw the table.
            // The table is a fixed width, but the amount of whitespace we need to
            // draw will change based on the amount of cards we draw.  We subtract
            // the previously recorded length from the current length, then subtract
            // that value from the known width of the table.  This tells us how much
            // whitespace to add to the art.
            sb.append(" ".repeat(TABLE_WIDTH - (sb.length() - rowStart)));
            sb.append(TABLE_SIDES[i + OTHER_SIDE]);
            sb.append('\n');
        }

        // Player side of the table.
        sb.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        // These are supposed to be table legs...
        sb.append("||     ||                                                         ||     ||\n");
        sb.append("||                                                                       ||\n");
        sb.append("||                                                                       ||\n");

        // Done assembling the ascii art, convert the string builder to a
        // proper String object and return.
        return sb.toString();
    }
}
