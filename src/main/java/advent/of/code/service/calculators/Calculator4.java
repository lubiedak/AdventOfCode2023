package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class Calculator4 implements TaskCalculator {
    @Override
    public String calculate1(List<String> lines) {
        int sum = 0;
        for (var line : lines) {
            var cardNumbers = line.split("\\s\\|\\s");
            var winningNumbers = new java.util.ArrayList<>(List.of(cardNumbers[0].split(": +")[1].split(" +")));
            int howManyHaveWon = 0;
            for (var cardNumber : cardNumbers[1].split(" +")) {
                if (winningNumbers.contains(cardNumber)) {
                    howManyHaveWon++;
                }
            }
            if (howManyHaveWon > 0) {
                sum += (int)Math.pow(2, howManyHaveWon - 1);
            }
        }
        return "" + sum;
    }

    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }

    @Override
    public int getId() {
        return 4;
    }
}
