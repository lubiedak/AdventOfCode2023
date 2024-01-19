package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Calculator11 implements TaskCalculator {
    @Override
    public String calculate1(List<String> lines) {
        var schema = expandSchema(lines);
        var positions = getGalaxiesPositions(schema);
        int sum = 0;
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                sum += positions.get(i).distance(positions.get(j));
            }
        }
        return "" + sum;
    }

    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }


    private char[][] expandSchema(List<String> lines) {
        var schema = rowsToExpand(lines);
        return columnsToExpand(schema);
    }

    private char[][] rowsToExpand(List<String> lines) {
        int totalRows = lines.size();
        for (var line : lines) {
            totalRows += line.contains("#") ? 0 : 1;
        }

        char[][] schema = new char[totalRows][];
        int i = 0;
        for (var line : lines) {
            if (!line.contains("#")) {
                schema[i++] = line.toCharArray();
            }
            schema[i++] = line.toCharArray();
        }
        return schema;
    }

    private char[][] columnsToExpand(char[][] schema) {
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
        var newSchema = new char[schema.length][];

        for (int row = 0; row < schema.length; row++) {
            int i = 0;
            newSchema[row] = new char[schema[0].length + columnsToExpand.size()];
            for (int col = 0; col < schema[0].length; col++) {
                if (columnsToExpand.contains(col)) {
                    newSchema[row][col + i++] = '.';
                }
                newSchema[row][col + i] = schema[row][col];
            }
        }
        return newSchema;
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
        int distance(Position p) {
            return Math.abs(p.h - h) + Math.abs(p.v - v);
        }
    }
}
