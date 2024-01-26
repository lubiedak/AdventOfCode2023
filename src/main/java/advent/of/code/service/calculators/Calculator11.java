package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static advent.of.code.service.calculators.CalculatorUtils.readSchema;

@Component
public class Calculator11 implements TaskCalculator {
    
    @Override
    public String calculate1(List<String> lines) {
        var schema = readSchema(lines);
        var rows = findExpandedRows(lines);
        var columns = findExpandedColumns(schema);
        var positions = getGalaxiesPositions(schema);

        long sum = 0;
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                sum += distance(positions.get(i), positions.get(j), rows, columns, 2);
            }
        }
        return "" + sum;
    }

    @Override
    public String calculate2(List<String> lines) {
        var schema = readSchema(lines);
        var rows = findExpandedRows(lines);
        var columns = findExpandedColumns(schema);
        var positions = getGalaxiesPositions(schema);

        long sum = 0;
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                sum += distance(positions.get(i), positions.get(j), rows, columns, 1000000);
            }
        }
        return "" + sum;
    }

    long distance(Position p1, Position p2, List<Integer> rows, List<Integer> columns, int coefficient) {
        int firstColumn = Math.min(p1.h, p2.h);
        int lastColumn = Math.max(p1.h, p2.h);
        int firstRow = Math.min(p1.v, p2.v);
        int lastRow = Math.max(p1.v, p2.v);
        long sum = 0;
        for (int row = firstRow; row < lastRow; row++) {
            sum += rows.contains(row) ? coefficient : 1;
        }

        for (int col = firstColumn; col < lastColumn; col++) {
            sum += columns.contains(col) ? coefficient : 1;
        }
        return sum;
    }

    private List<Integer> findExpandedRows(List<String> lines) {
        List<Integer> rows = new ArrayList<>();
        int i = 0;
        for (var line : lines) {
            if (!line.contains("#")) {
                rows.add(i);
            }
            i++;
        }
        return rows;
    }

    private List<Integer> findExpandedColumns(char[][] schema) {
        List<Integer> columnsToExpand = new ArrayList<>();
        for (int col = 0; col < schema[0].length; col++) {
            boolean containsGalaxy = false;
            for (int row = 0; row < schema.length; row++) {
                if (schema[row][col] == '#') {
                    containsGalaxy = true;
                }

            }
            if (!containsGalaxy) {
                columnsToExpand.add(col);
            }
        }
        return columnsToExpand;
    }

    private List<Position> getGalaxiesPositions(char[][] schema) {
        List<Position> positions = new ArrayList<>();
        for (int row = 0; row < schema.length; row++) {
            for (int col = 0; col < schema[0].length; col++) {
                if (schema[row][col] == '#') {
                    positions.add(new Position(row, col));
                }
            }
        }
        return positions;
    }

    @Override
    public int getId() {
        return 11;
    }

    record Position(int v, int h) {
    }
}
