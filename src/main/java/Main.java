public class Main {

    public static void main(String[] args) {

        String card1 = "2D";
        String card2 = "2H";
        String card3 = "2S";
        String card4 = "2C";
        String card5 = "7D";

        String card6 = "2D";
        String card7 = "2H";
        String card8 = "2S";
        String card9 = "5C";
        String card10 = "7D";

        Hand player1Hand = Hand.createHandFromString(card1 + " " + card2 + " " + card3 + " " + card4 + " " + card5);
        Hand player2Hand = Hand.createHandFromString(card6 + " " + card7 + " " + card8 + " " + card9 + " " + card10);

        HandComparator handComparator = new HandComparator();
        int result = handComparator.compare(player1Hand, player2Hand);

        if (result > 0) {
            System.out.println("Player 1 wins");
        } else if (result < 0) {
            System.out.println("Player 2 wins");
        } else {
            System.out.println("Draw");
        }


    }
}
