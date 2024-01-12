package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Calculator7 implements TaskCalculator {

    private final static String CARDS = "AKQJT98765432";
    private final static ArrayList<String> HAND_TYPE = new ArrayList<>(Arrays.asList("abcde", "aabcd", "aabbc", "aaabc", "aaabb", "aaaab", "aaaaa"));

    @Override
    public String calculate1(List<String> lines) {
        var sorted = sortCards("AQ223", CARDS);
        int rank = handStrength(sorted);

        return

                "Not implemented";
    }

    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }

    private int handStrength(String hand) {
        String usedCards = "";
        char currentChar = 'a' - 1;
        String handMapped = "";
        for (char c : hand.toCharArray()) {
            if (!usedCards.contains("" + c)) {
                currentChar++;
            }
            handMapped += currentChar;
            usedCards += c;

        }
        return HAND_TYPE.indexOf(handMapped);
    }

    private String sortCards(String hand) {
        Character[] handArray =
                hand.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        Map<Character, Integer> map = new TreeMap<>(Comparator.comparingInt(CARDS::indexOf));

        for (var card : handArray) {
            if (map.containsKey(card)) {
                map.put(card, map.get(card) + 1);
            } else {
                map.put(card, 1);
            }
        }

        return map.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .map(e -> IntStream.range(0, e.getValue()).mapToObj(index -> "" + e.getKey()).collect(Collectors.joining()))
                .map(String::valueOf)
                .collect(Collectors.joining());

    }

    @Override
    public int getId() {
        return 7;
    }

}

