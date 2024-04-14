import game.Poker;
import hand.HandService;
import hand.HandServiceImpl;

/**
 * This class serves as the entry point for the Poker game.
 * It initializes the game with a HandService implementation and calculates the number of wins for Player 1.
 */
public class Main {

    public static void main(String[] args) {

        HandService handService = new HandServiceImpl();
        Poker game = new Poker(handService);
        int player1Wins = game.countPlayer1Wins();

        System.out.println("Player 1 wins: " + player1Wins);
    }
}
