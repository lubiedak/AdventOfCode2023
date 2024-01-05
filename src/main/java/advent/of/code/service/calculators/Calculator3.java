package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Calculator3 implements TaskCalculator {

    // could be more flexible for dimension size
    private static final int SIZE = 140;
    private static final String symbols = "!@#$%&*-_+='/";
    private final char[][] engineSchema = new char[SIZE][SIZE];

    @Override
    public String calculate1(List<String> lines) {
        for (int i = 0; i < SIZE; i++) {
            engineSchema[i] = lines.get(i).toCharArray();
        }
        int sum = 0;
        for (int i = 0; i < SIZE; i++) {
            String number = "";
            for (int j = 0; j < SIZE; j++) {
                int numberToAdd = 0;
                if (Character.isDigit(engineSchema[i][j])) {
                    number += engineSchema[i][j];
                } else if (!number.isEmpty()) {
                    numberToAdd = Integer.parseInt(number);
                    if (checkIfSpecialCharacterInSurrounding(i, j - number.length(), number.length(), engineSchema[i])) {
                        System.out.println(numberToAdd);
                        sum += numberToAdd;
                    }
                    number = "";
                }
            }
        }
        return "" + sum;
    }

    private boolean checkIfSpecialCharacterInSurrounding(int i, int j, int length, char[] line) {
        int startingRow = Math.max(0, j - 1);
        int lastRow = Math.min(SIZE - 1, j + length);

        if (i > 0) {
            for (int a = startingRow; a <= lastRow; a++) {
                if (symbols.contains("" + engineSchema[i - 1][a])) {
                    return true;
                }
            }
        }
        if (i < SIZE - 1) {
            for (int a = startingRow; a <= lastRow; a++) {
                if (symbols.contains("" + engineSchema[i + 1][a])) {
                    return true;
                }
            }
        }
        if (symbols.contains("" + engineSchema[i][startingRow])) {
            return true;
        }

        return symbols.contains("" + engineSchema[i][lastRow]);
    }

    @Override
    public String calculate2(List<String> lines) {
        return null;
    }

    @Override
    public int getId() {
        return 3;
    }
}
