package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Calculator14 implements TaskCalculator {

    private final static char LOAD = 'O';
    private final static char SPACE = '.';

    @Override
    public String calculate1(List<String> lines) {
        var schema = readSchema(lines);
        long then = System.currentTimeMillis();
        schema = rollNorth(schema);

        System.out.println(System.currentTimeMillis() - then);
        int i = 1;
        long sum = 0;
        for (int row = schema.length - 1; row >= 0; row--) {
            sum += (i++) * (new String(schema[row]).chars().filter(c -> c == LOAD).count());
        }
        return "" + sum;
    }

    private char[][] rollNorth(char[][] schema) {
        for (int col = 0; col < schema[0].length; col++) {
            for (int row = 0; row < schema.length; row++) {
                if (schema[row][col] == LOAD) {
                    int currentRow = row;
                    while (canGoUp(schema, currentRow, col)) {
                        schema = swapNorth(schema, currentRow--, col);
                    }
                }
            }
        }
        return schema;
    }

    private boolean canGoUp(char[][] schema, int row, int col) {
        int oneUp = row - 1;
        return oneUp >= 0 && schema[oneUp][col] == SPACE;
    }


    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }

    private char[][] swapNorth(char[][] schema, int h, int w) {
        char temp = schema[h - 1][w];
        schema[h - 1][w] = schema[h][w];
        schema[h][w] = temp;
        return schema;
    }

    private char[][] readSchema(List<String> lines) {
        char[][] schema = new char[lines.size()][];
        int i = 0;
        for (var line : lines) {
            schema[i++] = line.toCharArray();
        }
        return schema;
    }

    @Override
    public int getId() {
        return 14;
    }
}
