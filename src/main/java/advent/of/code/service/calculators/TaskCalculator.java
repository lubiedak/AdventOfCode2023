package advent.of.code.service.calculators;

import java.util.List;

public interface TaskCalculator {

    String calculate1(List<String> lines);
    String calculate2(List<String> lines);

    int getId();
}
