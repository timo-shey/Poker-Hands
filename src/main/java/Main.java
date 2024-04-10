import game.Poker;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        Poker game = new Poker();
        int player1Wins = game.countPlayer1Wins();

        System.out.println("Player 1 wins: " + player1Wins);
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
