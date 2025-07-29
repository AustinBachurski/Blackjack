// Here we import the Scanner utility to read input from the terminal.
import java.util.Scanner;

// This class is used to print output to and read input from
// the computer's terminal console.
//
// It may be worth noting that my IDE complains that I'm leaking
// resources by not closing the Scanner objects in the display
// methods.  From what I'm reading you don't want to call `close`
// on System.in or terminal input will fail to be read.  You only
// want to close it on program termination.  At program termination,
// the operating system is going to reclaim all the resources
// anyway, so I didn't bother.  I didn't figure the game would ever
// be played long enough for potential resource leaks to be a problem.
// I almost put the Scanner object in the class and instantiated it
// in the Blackjack object, but I wanted to keep the IO separate from
// the game logic.  I'm not sure if this was the correct decision...
public final class ConsoleIO {
    // A private constructor is used since we never need to construct
    // a ConsoleIO object, it's effectively used as a static
    // class, though Java has no concept of this.
    private ConsoleIO() { }

    // This method clears the terminal screen by sending special
    // characters to the terminal, `flush()` ensures the special
    // characters are sent immediately.
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // This method displays the game stats when the player is done
    // playing the game.  Ternary statements are used to show
    // descriptive words in singular or plural form depending
    // on the value they're describing.
    public static void displayEndOfGameStats(GameStats stats) {
        clearScreen();
        System.out.println("You played " + stats.gamesPlayed + (stats.gamesPlayed == 1 ?" game." : " games."));
        System.out.println("You won " + stats.playerWins + (stats.playerWins == 1 ? " time." : " times."));
        System.out.println("You lost " + stats.dealerWins + (stats.dealerWins == 1 ? " time." : " times."));
        System.out.println("There "
                + (stats.draws == 1 ? "was " : "were ") + stats.draws + (stats.draws == 1 ? " draw." : " draws."));
        System.out.println("( ━☞´◔‿ゝ◔`)━☞ GG");
    }

    // This method displays the value of each hand at the end of a round.
    public static void displayRoundScore(int playerHandValue, int dealerHandValue) {
        System.out.println("Dealer Hand was worth: " + dealerHandValue);
        System.out.println("Your Hand was worth: " + playerHandValue);
    }

    // This method is used to draw the ascii art for the game, we clear the screen,
    // space everything down by one row for aesthetics, draw the dealer face, dealer
    // cards and the card table, and finally, the player's cards.
    public static void drawCardTable(Blackjack game) {
        clearScreen();
        System.out.println();
        System.out.println(game.getDealerFace());
        System.out.println(game.getDealerHand().tableCards(game.cardsToShow()));
        System.out.println(game.getPlayerHand().allCardsFaceUp());
    }

    // This method returns the player's decision as to whether
    // they'd like to play again or not.
    public static boolean playAgain() {
        // The `Scanner` object is used to read input from the terminal.
        Scanner stdin = new Scanner(System.in);

        // Here we loop until the player gives us a valid 'y' or 'n' answer
        // to whether they'd like to play again.
        while(true) {
            System.out.println("Play again (y/n)? ");
            String input = stdin.nextLine();

            if (input.length() == 1) {
                if (Character.toLowerCase(input.charAt(0)) == 'y') {
                    return true;
                }
                if (Character.toLowerCase(input.charAt(0)) == 'n') {
                    return false;
                }
            }

            System.out.println("Just tell me, y or n... ");
        }
    }

    // This method returns the player's decision to "hit" or not.
    public static boolean playerHits() {
        Scanner stdin = new Scanner(System.in);

        System.out.print("Would you like to hit(h), or stay(s)? ");

        // Here we loop until the player gives us a valid 'h' or 's' answer
        // to whether they'd like to hit or stay.
        while (true){
            String input = stdin.nextLine();

            if (input.length() == 1) {
                if (Character.toLowerCase(input.charAt(0)) == 'h') {
                    return true;
                }
                else if (Character.toLowerCase(input.charAt(0)) == 's') {
                    return false;
                }
            }

            System.out.print("No complaining, hit(h) or stay(s)? ");
        }
    }

    // This method is used to pause the game loop until the user
    // presses enter.  We do this strictly to show the dealer
    // informing us that they need to draw an additional card.
    public static void pressEnter() {
        System.out.print("Press enter to continue...");
        Scanner stdin = new Scanner(System.in);
        stdin.nextLine();
    }

}
