import game.Poker;
import hand.HandService;
import hand.HandServiceImpl;

public class Main {

    public static void main(String[] args) {

        HandService handService = new HandServiceImpl();
        Poker game = new Poker(handService);
        int player1Wins = game.countPlayer1Wins();

        System.out.println("Player 1 wins: " + player1Wins);
    }
}
