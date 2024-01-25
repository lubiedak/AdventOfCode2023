package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Calculator13 implements TaskCalculator {
    @Override
    public String calculate1(List<String> lines) {
        return "" + sumRefflectionsWithDiff(lines, 0);
    }


    @Override
    public String calculate2(List<String> lines) {
        return "" + sumRefflectionsWithDiff(lines, 1);
    }

    private int sumRefflectionsWithDiff(List<String> lines, int maxDiff) {
        List<String> currentBlock = new ArrayList<>();
        int sum = 0;
        for (var line : lines) {
            if (!line.isEmpty()) {
                currentBlock.add(line);
            } else {
                int rowSymmetry = findReflection(currentBlock, maxDiff);
                int columnSymmetry = 0;
                if (rowSymmetry == 0) {
                    currentBlock = transpose(currentBlock);
                    columnSymmetry = findReflection(currentBlock, maxDiff);
                }
                sum += 100 * rowSymmetry + columnSymmetry;
                currentBlock = new ArrayList<>();
            }
        }
        return sum;
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

    private int findReflection(List<String> currentBlock, int maxDiff) {
        for (int i = 0; i < currentBlock.size() - 1; i++) {
            int diff = 0;
            var begin = i;
            var end = i + 1;
            while (begin >= 0 && end < currentBlock.size() && diff <= maxDiff) {
                diff += differsBy(currentBlock.get(begin--), currentBlock.get(end++));
            }
            if (diff == maxDiff) {
                return i + 1;
            }
        }
        return 0;
    }

    private int differsBy(String s1, String s2) {
        var chars1 = s1.toCharArray();
        var chars2 = s2.toCharArray();
        int diffSum = 0;
        for (int i = 0; i < chars1.length; i++) {
            diffSum += chars1[i] == chars2[i] ? 0 : 1;
        }
        return diffSum;
    }


    @Override
    public int getId() {
        return 13;
    }
}
