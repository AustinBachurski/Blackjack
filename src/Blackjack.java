// Here we import the ArrayList collection used to hold our cards.
import java.util.ArrayList;

// Here we import the Collections utility so we can use the `shuffle` method.
import java.util.Collections;

// This is the main class that holds game state and logic.
public class Blackjack {
    // Declaring some constants so our intent is clear later.
    private static final int DECK_SIZE = 52;
    private static final int DEALER_MINIMUM = 17;

    // Declaring all the variables we'll need for the game state.
    private final GameStats stats = new GameStats();
    private final ArrayList<Card> deck = new ArrayList<>();
    private Hand dealerHand;
    private Hand playerHand;
    private Face dealerFace;
    private FaceUpOption showCardFace;

    // This array holds all the "faces" the dealer can make
    // depending on the game state.  The leading white space is
    // needed to center the dealer at the card table.
    private final String[] faces = new String[]{
            "                         ┌-( ͡° ͜ʖ ͡°)-┐ Your move.",
            "                          ┌( ಠ_ಠ)┐ Less than 17, gotta hit...",
            "                          ¯\\_(ツ)_/¯ Draw, lame...",
            "                          (ง ͠° ͟ل͜ ͡°)ง You lose!.",
            "                           (╯°o°)ᕗ You busted!  Dealer wins.",
            "                          (ノಠ益ಠ)ノ彡┻━┻ Bah!  You win.",
    };

    // This enum is used to index into the faces array above.
    private enum Face {
        NORMAL,
        DEALER_HIT,
        DRAW,
        DEALER_WINS,
        PLAYER_BUST,
        DEALER_LOST,
    }

    // Starting a new game round shuffles the deck and
    // deals two cards to each player.  We set the dealer's
    // face to NORMAL, and set the display option to show
    // only the dealer's first card face up.
    private void dealNewGame() {
        shuffleDeck();
        dealerHand = new Hand(deck.removeLast(), deck.removeLast());
        playerHand = new Hand(deck.removeLast(), deck.removeLast());
        dealerFace = Face.NORMAL;
        showCardFace = FaceUpOption.ONE;
    }

    // This function holds the gameplay logic.
    private void gameLoop() {
        // Here we loop until the player decides to "stay".
        // Inside the loop we draw cards from the deck, adding
        // them to the player's hand and checking for a
        // "bust" condition.
        while (true) {
            ConsoleIO.drawCardTable(this);

            if (ConsoleIO.playerHits())
            {
                playerHand.addCard(deck.removeLast());

                if (playerHand.busted())
                {
                    ++stats.dealerWins;
                    dealerFace = Face.PLAYER_BUST;
                    roundEnd();
                    return;
                }
            } else {
                break;
            }
        }

        // After the player chooses to stay, the dealer will
        // flip their cards face up.  So we draw the card
        // table to the screen again.
        showCardFace = FaceUpOption.ALL;
        ConsoleIO.drawCardTable(this);

        // This loop sums the values of the dealer's cards and
        // draws additional cards until the sum is greater
        // than 16 as required by the Blackjack rules.
        while (dealerHand.calculateValue() < DEALER_MINIMUM)
        {
            dealerFace = Face.DEALER_HIT;
            ConsoleIO.drawCardTable(this);

            dealerHand.addCard(deck.removeLast());
            ConsoleIO.pressEnter();
        }

        // After the dealer has drawn all the required cards
        // we check the dealer's hand for a "bust" condition.
        if (dealerHand.busted()) {
            ++stats.playerWins;
            dealerFace = Face.DEALER_LOST;
            roundEnd();
            return;
        }

        // Here we compare the value of the dealer's hand to that of
        // the player.  The higher value wins, if the values are the
        // same, a draw occurs.
        if (dealerHand.calculateValue() > playerHand.calculateValue()) {
            ++stats.dealerWins;
            dealerFace = Face.DEALER_WINS;
            roundEnd();
        } else if (dealerHand.calculateValue() < playerHand.calculateValue()) {
            ++stats.playerWins;
            dealerFace = Face.DEALER_LOST;
            roundEnd();
        } else {
            ++stats.draws;
            dealerFace = Face.DRAW;
            roundEnd();
        }
    }

    // When a round ends, we increment the gamesPlayed counter, and
    // redraw the screen to show the results of the round.
    private void roundEnd() {
        ++stats.gamesPlayed;
        ConsoleIO.drawCardTable(this);
    }

    // We simulate shuffling the deck by simply creating a new set of cards,
    // this avoids having to "collect" the cards back up off the table.
    // Once the deck is created, confirm that the deck contains 52 cards.
    // Then we call the built-in `shuffle` method on the deck object
    // randomizing the order of the cards.
    private void shuffleDeck() {
        for (int suit = CardSuit.CLUBS.ordinal(); suit <= CardSuit.HEARTS.ordinal(); ++suit) {
            for (int face = CardFace.ACE.ordinal(); face <= CardFace.KING.ordinal(); ++face) {
                deck.add(new Card(CardFace.values()[face], CardSuit.values()[suit]));
            }
        }

        assert(deck.size() == DECK_SIZE);
        Collections.shuffle(deck);
    }

    // This is the "constructor" for the Blackjack object.
    // This is how we create the object, and the body of the method
    // contains the initialization routine for the object.
    public Blackjack() {
        dealNewGame();
    }

    // This flag tells the display whether to show all the dealer cards
    // face up, or only the first card.
    public enum FaceUpOption {
        ALL,
        ONE,
    }

    // This method returns the state of the previously mentioned flag.
    public FaceUpOption cardsToShow() {
        return showCardFace;
    }

    // This method returns the current dealer face.
    public String getDealerFace() {
        return faces[dealerFace.ordinal()];
    }

    // This method returns the dealer's hand of cards.
    public Hand getDealerHand() {
        return dealerHand;
    }

    // This method returns the player's hand of cards.
    public Hand getPlayerHand() {
        return playerHand;
    }

    // This method starts the game, in the loop, we play the game,
    // once a round ends we display the value of each hand to make
    // it easier for the player to understand the outcome of the game.
    // We ask the user if they want to play again, if they do, the
    // loop continues, if not, we display the stats for the game and
    // exit the program.
    public void play() {
        while (true) {
            gameLoop();
            ConsoleIO.displayRoundScore(playerHand.calculateValue(), dealerHand.calculateValue());

            if (ConsoleIO.playAgain()) {
                dealNewGame();
            } else {
                break;
            }
        }

        ConsoleIO.displayEndOfGameStats(stats);
    }
}
