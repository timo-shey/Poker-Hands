import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@ToString
@AllArgsConstructor
public class Hand {
    private List<Card> cards;

    public static Hand createHandFromString(String handString) {
        List<Card> cards = IntStream.range(0, handString.length() / 3)
            .mapToObj(i -> new Card(handString.charAt(i * 3), handString.charAt(i * 3 + 1)))
            .collect(Collectors.toList());
        return new Hand(cards);
    }

}
