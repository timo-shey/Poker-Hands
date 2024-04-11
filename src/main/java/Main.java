import game.Poker;

public class Main {

    public static void main(String[] args) {

        Poker game = new Poker();
        int player1Wins = game.countPlayer1Wins();

        System.out.println("Player 1 wins: " + player1Wins);
    }
}
