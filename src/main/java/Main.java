import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {

        String filePath = "src/main/resources/poker.txt";
        AtomicInteger player1Wins = new AtomicInteger();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            br.lines().forEach(line -> {
                List<Card> cards = Arrays.stream(line.split(" "))
                    .map(card -> new Card(card.charAt(0), card.charAt(1)))
                    .toList();

                Hand player1Hand = Hand.createHandFromString(cards.subList(0, 5).toString());
                Hand player2Hand = Hand.createHandFromString(cards.subList(5, 10).toString());

                HandComparator comparator = new HandComparator();
                int result = comparator.compare(player1Hand, player2Hand);

                if (result > 0) {
                    player1Wins.getAndIncrement();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Player 1 wins: " + player1Wins + " hands.");

    }
}
