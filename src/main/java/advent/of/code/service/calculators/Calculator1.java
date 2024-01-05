package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Calculator1 implements TaskCalculator {

    private final static Digit EMPTY = new Digit(0, 0);
    private final static Digit MAX = new Digit(0, Integer.MAX_VALUE);

    private final Comparator<Digit> digitsComparator = (d1, d2) -> d1.position > d2.position ? 1 : -1;

    private static final Map<String, Integer> digits = Map.of("one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9);

    @Override
    public String calculate1(List<String> lines) {
        int sum = 0;
        for (String line : lines) {
            sum += calculateDigits(line).asInt();
        }
        return "" + sum;
    }

    @Override
    public String calculate2(List<String> lines) {
        int sum = 0;
        for(String line : lines){
            Digits numberedDigits = calculateDigits(line);
            Digits letteredDigits = calculateTextDigits(line);
            Digits combined = new Digits(Collections.min(Arrays.asList(numberedDigits.first, letteredDigits.first), digitsComparator),
                    Collections.max(Arrays.asList(numberedDigits.second, letteredDigits.second), digitsComparator));
            sum += combined.asInt();
        }

        return "" + sum;
    }

    @Override
    public int getId() {
        return 1;
    }

    private Digits calculateDigits(String line) {
        Digit first = EMPTY;
        Digit second = EMPTY;

        boolean foundFirstDigit = false;
        int position = 0;
        for (char c : line.toCharArray()) {
            if (Character.isDigit(c)) {
                if (!foundFirstDigit) {
                    first = new Digit(Character.getNumericValue(c), position);
                    second = new Digit(Character.getNumericValue(c), position);
                    foundFirstDigit = true;
                } else {
                    second = new Digit(Character.getNumericValue(c), position);
                }
            }
            position++;
        }
        return new Digits(first, second);
    }

    private Digits calculateTextDigits(String line) {
        return new Digits(getMinFromLine(line), getMaxFromLine(line));
    }

    private Digit getMinFromLine(String line){
        return digits.entrySet().stream().map(e -> {
            int position = line.indexOf(e.getKey());
            if (position != -1) {
                return new Digit(e.getValue(), position);
            }
            return MAX;
        }).min(digitsComparator).get();
    }

    private Digit getMaxFromLine(String line){
        return digits.entrySet().stream().map(e -> {
            int position = line.lastIndexOf(e.getKey());
            if (position != -1) {
                return new Digit(e.getValue(), position);
            }
            return EMPTY;
        }).max(digitsComparator).get();
    }

    record Digit(int digit, int position) {
    }

    record Digits(Digit first, Digit second) {
        int asInt() {
            return 10 * first.digit + second.digit;
        }
    }

}
