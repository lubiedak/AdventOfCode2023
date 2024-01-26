package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;
import java.util.*;

import static advent.of.code.service.calculators.CalculatorUtils.readSchema;

@Component
public class Calculator14 implements TaskCalculator {

    private final static char LOAD = 'O';
    private final static char SPACE = '.';

    @Override
    public String calculate1(List<String> lines) {
        var schema = readSchema(lines);
        schema = rollNorth(schema);

        return "" + sumLoad(schema);
    }

    @Override
    public String calculate2(List<String> lines) {
        var schema = readSchema(lines);

        /*
        We assume that after couple hundreds roll we go into some cycle. So we do many rolls, and then start to discover a sequence.
        For bigger problems it may fail. Also it may fail if 2 consecutive sumLoads are exactly the same.
         */
        int SKIPPED = 1500;
        String seqence = "";
        for (int i = 0; i < 5000; i++) {
            schema = rollNorth(schema);
            schema = rollWest(schema);
            schema = rollSouth(schema);
            schema = rollEast(schema);
            if (i >= SKIPPED) {
                int sum = sumLoad(schema);
                seqence += sum + " ";
                var mid = seqence.length() / 2;
                if (seqence.substring(0, mid).equals(seqence.substring(mid))) {
                    break;
                }
            }
        }

        var realSequence = seqence.substring(seqence.length() / 2).split(" ");
        int index = (1000000000 - SKIPPED - 1) % realSequence.length;
        return realSequence[index];
    }

    private int sumLoad(char[][] schema) {
        int i = 1;
        int sum = 0;
        for (int row = schema.length - 1; row >= 0; row--) {
            sum += (i++) * (new String(schema[row]).chars().filter(c -> c == LOAD).count());
        }
        return sum;
    }

    private char[][] rollNorth(char[][] schema) {
        for (int col = 0; col < schema[0].length; col++) {
            for (int row = 0; row < schema.length; row++) {
                if (schema[row][col] == LOAD) {
                    int currentRow = row;
                    while (canGo(schema, currentRow - 1, col)) {
                        schema = swap(schema, currentRow, col, --currentRow, col);
                    }
                }
            }
        }
        return schema;
    }

    private char[][] rollSouth(char[][] schema) {
        for (int col = 0; col < schema[0].length; col++) {
            for (int row = schema.length - 1; row >= 0; row--) {
                if (schema[row][col] == LOAD) {
                    int currentRow = row;
                    while (canGo(schema, currentRow + 1, col)) {
                        schema = swap(schema, currentRow, col, ++currentRow, col);
                    }
                }
            }
        }
        return schema;
    }

    private char[][] rollEast(char[][] schema) {
        for (int row = 0; row < schema.length; row++) {
            for (int col = schema[0].length - 1; col >= 0; col--) {
                if (schema[row][col] == LOAD) {
                    int currentCol = col;
                    while (canGo(schema, row, currentCol + 1)) {
                        schema = swap(schema, row, currentCol, row, ++currentCol);
                    }
                }
            }
        }
        return schema;
    }

    private char[][] rollWest(char[][] schema) {
        for (int row = 0; row < schema.length; row++) {
            for (int col = 0; col < schema[0].length; col++) {
                if (schema[row][col] == LOAD) {
                    int currentCol = col;
                    while (canGo(schema, row, currentCol - 1)) {
                        schema = swap(schema, row, currentCol, row, --currentCol);
                    }
                }
            }
        }
        return schema;
    }

    private boolean canGo(char[][] schema, int row, int col) {
        return row >= 0 && row < schema.length && col >= 0 && col < schema[0].length && schema[row][col] == SPACE;
    }

    private char[][] swap(char[][] schema, int h, int w, int h1, int w1) {
        char temp = schema[h1][w1];
        schema[h1][w1] = schema[h][w];
        schema[h][w] = temp;
        return schema;
    }

    @Override
    public int getId() {
        return 14;
    }
}
