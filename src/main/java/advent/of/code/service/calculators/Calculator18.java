package advent.of.code.service.calculators;

import com.zaxxer.sparsebits.SparseBitSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class Calculator18 implements TaskCalculator {

    private static final int MARK = 1;

    @Override
    public String calculate1(List<String> lines) {
        int col = 0;
        int row = 0;
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0, 0));
        int minRow = 0, minCol = 0, maxRow = 0, maxCol = 0;
        for (var line : lines) {
            var orders = line.split(" ");
            int by = Integer.parseInt(orders[1]);
            int lastCol = col, lastRow = row;
            switch (orders[0]) {
                case "L" -> col -= by;
                case "R" -> col += by;
                case "U" -> row -= by;
                case "D" -> row += by;
            }
            if (lastCol != col) {
                int finalRow = row;
                IntStream.rangeClosed(Math.min(lastCol, col), Math.max(col, lastCol)).forEach(c -> positions.add(new Position(finalRow, c)));
            }

            if (lastRow != row) {
                int finalCol = col;
                IntStream.rangeClosed(Math.min(lastRow, row), Math.max(row, lastRow)).forEach(r -> positions.add(new Position(r, finalCol)));
            }

            if (minRow == 0 || minRow > row)
                minRow = row;
            if (minCol == 0 || minCol > col)
                minCol = col;
            if (maxRow < row)
                maxRow = row;
            if (maxCol < col)
                maxCol = col;
        }

        var movedPositions = new ArrayList<Position>();
        int[][] table = new int[maxRow - minRow + 1][maxCol - minCol + 1];
        SparseBitSet[] table2 = new SparseBitSet[maxRow - minRow + 1];
        int finalMinRow = minRow;
        int finalMinCol = minCol;
        int finalMaxCol = maxCol;
        positions.forEach(p -> {
                    var moved = new Position(p.row - finalMinRow, p.col - finalMinCol);
                    movedPositions.add(moved);
                    table[moved.row][moved.col] = MARK;
                    if (table2[moved.row] == null)
                        table2[moved.row] = new SparseBitSet(finalMaxCol - finalMinCol + 1);
                    table2[moved.row].set(moved.col);
                }
        );
        int capacity = countCapacity(table);
        fill(1, 3, table);

        return "" + capacity;
    }

    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }

    @Override
    public int getId() {
        return 18;
    }

    void fill(int row, int col, int[][] table) {
        if (table[row][col] != MARK) {
            table[row][col] = MARK;
            if (row > 0)
                fill(row - 1, col, table);
            if (col > 0)
                fill(row, col - 1, table);
            if (row < table.length - 1)
                fill(row + 1, col, table);
            if (col < table[0].length - 1)
                fill(row, col + 1, table);
        }
    }

    int countCapacity(int[][] table) {
        int sum = 0;
        for (var r : table) {
            int begin = -1;
            int end = -1;
            boolean firstBorder = false;
            for (int i = 0; i < r.length; i++) {
                if (r[i] == 1 && end == -1) {
                    begin = i;
                    sum++;
                } else if (begin != -1 && r[i] == 0) {

                    for (int j = i; j < r.length; j++) {
                        if (r[j] == MARK && !firstBorder) {
                            firstBorder = true;
                        }
                        if (firstBorder && r[j] == 0 || j == r.length - 1) {
                            end = j;
                            i = j;
                            firstBorder = false;
                            break;
                        }
                    }
                } else if (begin == r.length - 1) {
                    sum += 1 + begin;
                }
                if (r[i] == 1 && begin != -1 && end != -1) {
                    sum += end - begin;
                    begin = -1;
                    end = -1;
                    firstBorder = false;
                }
            }

        }
        return sum;
    }

    record Position(int row, int col) {
    }
}
