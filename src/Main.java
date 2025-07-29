// Main object for the program.
public class Main {
    // Program entry point - we create a game object that holds
    // game information.  Then call the `play()` method of
    // said object to start the game.
    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.play();
    }
}
