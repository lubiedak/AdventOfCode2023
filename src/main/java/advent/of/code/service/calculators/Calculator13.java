package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Calculator13 implements TaskCalculator {
    @Override
    public String calculate1(List<String> lines) {
        List<String> currentBlock = new ArrayList<>();
        int sum = 0;
        for (var line : lines) {
            if (!line.isEmpty()) {
                currentBlock.add(line);
            } else {
                int rowSymmetry = findReflection(currentBlock);
                currentBlock = transpose(currentBlock);
                int columnSymmetry = findReflection(currentBlock);
                sum += 100 * rowSymmetry + columnSymmetry;
                currentBlock = new ArrayList<>();
            }
        }

        return "" + sum;
    }

    private List<String> transpose(List<String> currentBlock) {
        List<String> transposedBlock = new ArrayList<>();
        for (int i = 0; i < currentBlock.get(0).length(); i++) {
            var transposed = "";
            for (var line : currentBlock) {
                transposed += line.charAt(i);
            }
            transposedBlock.add(transposed);
        }
        return transposedBlock;
    }

    private int findReflection(List<String> currentBlock) {
        for (int i = 0; i < currentBlock.size() - 1; i++) {
            if (currentBlock.get(i).equals(currentBlock.get(i + 1))) {
                var equals = true;
                var begin = i - 1;
                var end = i + 2;
                while (begin >= 0 && end < currentBlock.size() && equals) {
                    equals = currentBlock.get(begin--).equals(currentBlock.get(end++));
                }
                if (equals) {
                    return i + 1;
                }
            }
        }
        return 0;
    }

    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }

    @Override
    public int getId() {
        return 13;
    }
}
