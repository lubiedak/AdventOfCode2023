package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Calculator6 implements TaskCalculator {

    @Override
    public String calculate1(List<String> lines) {
        var times = lines.get(0).split(":\\s+")[1].split("\\s+");
        var distances = lines.get(1).split(":\\s+")[1].split("\\s+");
        long optionsToBeat = 1;
        for (int i = 0; i < times.length; i++) {
            int totalTime = Integer.parseInt(times[i]);
            int distanceToBeat = Integer.parseInt(distances[i]);
            optionsToBeat *= calculateAllPossibleRecordBeats(totalTime, distanceToBeat);
        }

        return "" + optionsToBeat;
    }

    @Override
    public String calculate2(List<String> lines) {
        long totalTime = Long.parseLong(lines.get(0).split(":\\s+")[1].replace(" ", ""));
        long distanceToBeat = Long.parseLong(lines.get(1).split(":\\s+")[1].replace(" ", ""));
        return "" + calculateAllPossibleRecordBeats(totalTime, distanceToBeat);
    }

    private long calculateAllPossibleRecordBeats(long totalTime, long distanceToBeat){
        long sumOfBeats = 0;
        for (long t = 1; t < totalTime; t++) {
            long distanceMade = t * (totalTime - t); //could be optimized because function is symetrical.
            sumOfBeats += distanceMade > distanceToBeat ? 1 : 0;
        }
        return sumOfBeats;
    }

    @Override
    public int getId() {
        return 6;
    }


}
