package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Calculator9 implements TaskCalculator {

    @Override
    public String calculate1(List<String> lines) {
        return "\n" + calculate(lines, false);
    }

    @Override
    public String calculate2(List<String> lines) {
        return "\n" + calculate(lines, true);
    }

    private long calculate(List<String> lines, boolean predictFirst){
        long sum = 0;
        for (var line : lines) {
            Long[][] sequences = new Long[100][];
            Long[] sequence = Arrays.stream(line.split(" ")).map(Long::parseLong).toArray(Long[]::new);

            int seqNumber = 0;
            do {
                sequences[seqNumber] = sequence;
                var newSequence = new Long[sequence.length - 1];
                for (int i = 1; i < sequence.length; i++) {
                    newSequence[i - 1] = sequence[i] - sequence[i - 1];
                }
                sequence = newSequence;
                seqNumber++;
            } while (!allAreZeros(sequence));
            sum += predictFirst ? predictFirst(sequences, seqNumber) : predictLast(sequences, seqNumber);
        }

        return sum;
    }

    private boolean allAreZeros(Long[] sequence) {
        for (var el : sequence) {
            if (el != 0) {
                return false;
            }
        }
        return true;
    }

    private long predictLast(Long[][] sequences, int last) {
        long predicted = sequences[last-1][0];
        for (int i = last - 2; i >= 0; i--) {
            predicted = predicted + sequences[i][sequences[i].length - 1];
        }
        return predicted;
    }

    private long predictFirst(Long[][] sequences, int last) {
        long predicted = sequences[last-1][0];
        for (int i = last - 2; i >= 0; i--) {
            predicted =  sequences[i][0] - predicted;
        }
        return predicted;
    }

    @Override
    public int getId() {
        return 9;
    }
}
