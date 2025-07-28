import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private static final int DECK_SIZE = 52;
    private static final int DEALER_MINIMUM = 17;
    private final GameStats stats = new GameStats();
    private final ArrayList<Card> deck = new ArrayList<>();
    private Hand dealerHand;
    private Hand playerHand;
    private Face dealerFace;
    private FaceUpOption showCardFace;

    private final String[] faces = new String[]{
            "                         ┌-( ͡° ͜ʖ ͡°)-┐ Your move.",
            "                          ┌( ಠ_ಠ)┐ Less than 17, gotta hit...",
            "                          ¯\\_(ツ)_/¯ Draw, lame...",
            "                          (ง ͠° ͟ل͜ ͡°)ง You lose!.",
            "                           (╯°o°)ᕗ You busted!  Dealer wins.",
            "                          (ノಠ益ಠ)ノ彡┻━┻ Bah!  You win.",
    };

    private enum Face {
        NORMAL,
        DEALER_HIT,
        DRAW,
        DEALER_WINS,
        PLAYER_BUST,
        DEALER_LOST,
    }

    private void dealNewGame() {
        shuffleDeck();
        dealerHand = new Hand(deck.removeLast(), deck.removeLast());
        playerHand = new Hand(deck.removeLast(), deck.removeLast());
        dealerFace = Face.NORMAL;
        showCardFace = FaceUpOption.ONE;
    }

    private void gameLoop() {
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

        showCardFace = FaceUpOption.ALL;
        ConsoleIO.drawCardTable(this);

        while (dealerHand.calculateValue() < DEALER_MINIMUM)
        {
            dealerFace = Face.DEALER_HIT;
            ConsoleIO.drawCardTable(this);

            dealerHand.addCard(deck.removeLast());
            ConsoleIO.pressEnter();
        }

        if (dealerHand.busted()) {
            ++stats.playerWins;
            dealerFace = Face.DEALER_LOST;
            roundEnd();
            return; // Error was here.
        }

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
        }
    }

    private void roundEnd() {
        ++stats.gamesPlayed;
        ConsoleIO.drawCardTable(this);
    }

    private void shuffleDeck() {
        for (int suit = CardSuit.CLUBS.ordinal(); suit <= CardSuit.HEARTS.ordinal(); ++suit) {
            for (int face = CardFace.ACE.ordinal(); face <= CardFace.KING.ordinal(); ++face) {
                deck.add(new Card(CardFace.values()[face], CardSuit.values()[suit]));
            }
        }

        assert(deck.size() == DECK_SIZE);
        Collections.shuffle(deck);
    }


    public Game() {
        dealNewGame();
    }

    public enum FaceUpOption {
        ALL,
        ONE,
    }

    public FaceUpOption cardsToShow() {
        return showCardFace;
    }

    public String getDealerFace() {
        return faces[dealerFace.ordinal()];
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public void play() {
        do {
            gameLoop();
            dealNewGame();
        } while(ConsoleIO.playAgain());

        ConsoleIO.displayEndOfGameStats(stats);
    }
}
