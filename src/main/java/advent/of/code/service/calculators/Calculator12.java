package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Calculator12 implements TaskCalculator {
    @Override
    public String calculate1(List<String> lines) {
        int sum = 0;
        for (var line : lines) {
            var code = line.split(" ")[0];
            var expectedNumbers = Arrays.stream(line.split(" ")[1].split(",")).map(Integer::parseInt).toList();
            long n = code.chars().filter(c -> c == '?').count();
            for (int i = 0; i < Math.pow(2, n); i++) {
                String sequence = "";
                int index = 0;
                int j = i;
                for (char c : code.toCharArray()) {
                    if (c == '?') {
                        sequence += ((j >> index++) & 1) == 1 ? '#' : '.';
                    } else {
                        sequence += c;
                    }
                }
                sum += containsCorrectSeqeunce(sequence, expectedNumbers) ? 1 : 0;
            }

        }
        return "" + sum;
    }

    private boolean containsCorrectSeqeunce(String sequence, List<Integer> expectedNumbers) {
        var bits = Arrays.stream(sequence.split("\\.+")).filter(s -> !s.isEmpty()).toList();

        if (bits.size() != expectedNumbers.size())
            return false;

        for (int i = 0; i < bits.size(); i++) {
            if (bits.get(i).length() != expectedNumbers.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }

    @Override
    public int getId() {
        return 12;
    }
}
