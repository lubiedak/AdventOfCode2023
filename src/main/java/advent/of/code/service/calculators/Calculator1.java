package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Calculator1 implements TaskCalculator {

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
        for(String line : lines){
            sum += calculateLine(line);
        }
        return "" + sum;
    }

    @Override
    public String calculate2(List<String> lines) {
        return null;
    }

    private int calculateLine(String line) {
        char firstDigit = ' ';
        char secondDigit = ' ';
        boolean foundFirstDigit = false;
        for (char c : line.toCharArray()) {
            if (Character.isDigit(c)) {
                if (!foundFirstDigit) {
                    firstDigit = c;
                    secondDigit = c;
                    foundFirstDigit = true;
                } else {
                    secondDigit = c;
                }
            }
        }
        return Integer.parseInt("" + firstDigit + secondDigit);
    }

}
