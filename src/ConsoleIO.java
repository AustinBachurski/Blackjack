import java.util.Scanner;

public final class ConsoleIO {
    private ConsoleIO() { }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void displayEndOfGameStats(GameStats stats) {
        clearScreen();
        System.out.println("You played " + stats.gamesPlayed + (stats.gamesPlayed == 1 ?" game." : " games."));
        System.out.println("You won " + stats.playerWins + (stats.playerWins == 1 ? " time." : " times."));
        System.out.println("You lost " + stats.dealerWins + (stats.dealerWins == 1 ? " time." : " times."));
        System.out.println("There "
                + (stats.draws == 1 ? "was " : "were ") + stats.draws + (stats.draws == 1 ? " draw." : " draws."));
        System.out.println("( ━☞´◔‿ゝ◔`)━☞ GG");
    }

    public static void drawCardTable(Game game) {
        clearScreen();
        System.out.println(game.getDealerFace());
        System.out.println(game.getDealerHand().tableCards(game.cardsToShow()));
        System.out.println(game.getPlayerHand().allCardsFaceUp());
    }

    public static boolean playAgain() {
        Scanner stdin = new Scanner(System.in);

        while(true) {
            System.out.println("Play again (y/n)? ");
            String input = stdin.nextLine();

            if (input.length() == 1) {
                if (input.equals("y")) {
                    return true;
                }
                if (input.equals("n")) {
                    return false;
                }
            }
            System.out.println("Just tell me, y or n... ");
        }
    }

    public static boolean playerHits() {
        Scanner stdin = new Scanner(System.in);

        System.out.print("Would you like to hit(h), or stay(s)? ");

        while (true){
            String input = stdin.nextLine();

            if (input.length() == 1) {
                if (input.charAt(0) == 'h') {
                    return true;
                }
                else if (input.charAt(0) == 's') {
                    return false;
                }
            }

            System.out.print("No complaining, hit(h) or stay(s)? ");
        }
    }

    public static void pressEnter() {
        System.out.print("Press enter to continue...");
        Scanner stdin = new Scanner(System.in);
        stdin.nextLine();
    }

}
