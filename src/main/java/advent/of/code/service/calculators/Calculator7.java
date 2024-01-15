package advent.of.code.service.calculators;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Calculator7 implements TaskCalculator {

    private final static String CARDS = "AKQT98765432J";
    private final static ArrayList<String> HAND_TYPE = new ArrayList<>(Arrays.asList("abcde", "aabcd", "aabbc", "aaabc", "aaabb", "aaaab", "aaaaa"));

    @Override
    public String calculate1(List<String> lines) {
        var cards = new ArrayList<Hand>();
        for (String line : lines) {
            var hand = line.split("\\s+")[0];
            int bet = Integer.parseInt(line.split("\\s+")[1]);
            var sorted = sortCards(hand);
            var maped = mapHand(hand);
            cards.add(new Hand(hand, sorted, maped, handStrength(sorted), bet));
        }
        return "" + sumBets(cards);
    }

    @Override
    public String calculate2(List<String> lines) {
        var cards = new ArrayList<Hand>();
        for (String line : lines) {
            var hand = line.split("\\s+")[0];
            int bet = Integer.parseInt(line.split("\\s+")[1]);
            var sorted = sortCards2(hand);
            var maped = mapHand2(hand);
            cards.add(new Hand(hand, sorted, maped, handStrength(sorted), bet));
        }
        return "" + sumBets(cards);
    }

    private long sumBets(ArrayList<Hand> cards) {
        cards.sort(Comparator.comparingInt(Hand::rank).thenComparing(Hand::cardsMaped, Comparator.naturalOrder()));
        long sum = 0;
        for (int i = 0; i < cards.size(); i++) {
            sum += cards.get(i).bet() * (i + 1);
        }
        return sum;
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

    private String mapHand(String hand) {
        hand = hand.replace('A', 'Z');
        hand = hand.replace('K', 'Y');
        hand = hand.replace('Q', 'X');
        hand = hand.replace('J', 'W');
        return hand;
    }

    private String mapHand2(String hand) {
        hand = mapHand(hand);
        hand = hand.replace('W', '1');
        return hand;
    }

    private String sortCards(String hand) {
        Character[] handArray = hand.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        Map<Character, Integer> map = new TreeMap<>();

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

    private String sortCards2(String hand) {
        Character[] handArray = hand.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        Map<Character, Integer> map = new TreeMap<>();
        var maxChar = Arrays.stream(handArray).min(Comparator.comparingInt(CARDS::indexOf));
        Character maxValue = 'J';
        int maxVal = 1;
        for (var card : handArray) {
            if (map.containsKey(card)) {
                map.put(card, map.get(card) + 1);
                if (map.get(card) > maxVal && card != 'J') {
                    maxVal = map.get(card);
                    maxValue = card;
                }
            } else {
                map.put(card, 1);
            }
        }

        if (map.containsKey('J') && map.size() > 1) {
            if (maxValue != 'J') {
                map.put(maxValue, map.get(maxValue) + map.get('J'));
            } else {
                map.put(maxChar.get(), map.get(maxChar.get()) + map.get('J'));

            }
            map.remove('J');
        }

        return map.entrySet().stream().sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .map(e -> IntStream.range(0, e.getValue())
                        .mapToObj(index -> "" + e.getKey())
                        .collect(Collectors.joining())).map(String::valueOf).collect(Collectors.joining());
    }

    @Override
    public int getId() {
        return 7;
    }

    record Hand(String cards, String sorted, String cardsMaped, int rank, int bet) {
    }
}

