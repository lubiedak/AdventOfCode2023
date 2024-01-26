package advent.of.code.service.calculators;

import java.util.List;

public class CalculatorUtils {
    public static char[][] readSchema(List<String> lines) {
        char[][] schema = new char[lines.size()][];
        int i = 0;
        for (var line : lines) {
            schema[i++] = line.toCharArray();
        }
        return schema;
    }
}
