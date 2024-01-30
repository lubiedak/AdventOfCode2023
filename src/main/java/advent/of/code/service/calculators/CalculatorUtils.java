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

    public static byte[][] readSchemaDigits(List<String> lines) {
        byte[][] schema = new byte[lines.size()][lines.get(0).length()];
        int i = 0;
        for (var line : lines) {
            for (int j = 0; j < line.length(); j++) {
                schema[i][j] = Byte.parseByte("" + line.charAt(j));
            }
            i++;
        }
        return schema;
    }
}
