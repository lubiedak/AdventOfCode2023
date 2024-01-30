package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static advent.of.code.service.calculators.CalculatorUtils.readSchemaDigits;

import com.zaxxer.sparsebits.SparseBitSet;

@Component
public class Calculator17 implements TaskCalculator {

    private final static Map<Character, String> possible = Map.of('v', "v<>", '^', "^<>", '>', ">^v", '<', "<^v");

    @Override
    public String calculate1(List<String> lines) {
        var schema = readSchemaDigits(lines);
        var length = schema[0].length;
        var minDistances = new int[schema.length][length];

        Map<String, Position> paths = createFirstPaths(schema, length, minDistances);

        Optional<Position> lastPos = Optional.empty();
        int i = 0;
        while (!paths.isEmpty() && i < 3 * length) {
            i++;
            Map<String, Position> newPaths = new LinkedHashMap<>();
            for (var path : paths.entrySet()) {
                String key = path.getKey();
                char last = key.charAt(key.length() - 1);
                var options = possible.get(last);
                if (key.length() >= 3) {
                    String last3 = key.substring(key.length() - 3);
                    if (last3.chars().allMatch(c -> last3.charAt(0) == c))
                        options = options.substring(1);
                }

                for (var direction : options.toCharArray()) {
                    var currentPosition = path.getValue();
                    var newPosition = newPosition(schema, direction, currentPosition);
                    if (newPosition != null) {
                        var minDistance = minDistances[newPosition.row][newPosition.col];
                        if (minDistance == 0 || minDistance >= newPosition.value) {
                            newPaths.put(key + direction, newPosition);
                        }
                        if (minDistance == 0 || minDistance > newPosition.value) {
                            minDistances[newPosition.row][newPosition.col] = newPosition.value;
                        }

                    }
                }
            }
            lastPos = paths.values().stream().filter(p -> p.row == schema.length - 1 && p.col == schema[0].length - 1)
                    .min(Comparator.comparingInt(p -> p.value));

            var minDistance = newPaths.entrySet().stream().map(e -> e.getValue().toEnd(length, schema[0].length)).min(Integer::compareTo).get();

            paths = newPaths.entrySet().stream()
                    .filter(e -> e.getValue().toEnd(length, schema[0].length) < minDistance + 3)
                    .sorted(Map.Entry.comparingByValue(Comparator.comparingInt(Position::value)))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        return "" + lastPos.get().value;
    }

    private static Map<String, Position> createFirstPaths(byte[][] schema, int length, int[][] minDistances) {
        Map<String, Position> paths = new LinkedHashMap<>();
        var bits1 = new SparseBitSet(schema.length * length);
        bits1.set(0);
        bits1.set(1);
        var bits2 = new SparseBitSet(schema.length * length);
        bits2.set(0);
        bits2.set(length);


        paths.put(">", new Position(0, 1, schema[0][1], bits1));
        paths.put("v", new Position(1, 0, schema[1][0], bits2));
        minDistances[0][1] = schema[0][1];
        minDistances[1][0] = schema[1][0];
        return paths;
    }

    private Position newPosition(byte[][] schema, char direction, Position position) {
        int col = position.col + colPosition(direction);
        int row = position.row + rowPosition(direction);
        if (row >= 0 && col >= 0 && row < schema.length && col < schema[0].length) {
            var wasVisited = position.visited.get(row * schema[0].length + col);
            if (wasVisited) {
                return null;
            } else {
                var bits = position.visited.clone();
                bits.set(schema[0].length * row + col);
                return new Position(row, col, position.value + schema[row][col], bits);
            }
        }
        return null;
    }

    private int colPosition(char c) {
        return switch (c) {
            case '<' -> -1;
            case '>' -> 1;
            default -> 0;
        };
    }

    private int rowPosition(char c) {
        return switch (c) {
            case '^' -> -1;
            case 'v' -> 1;
            default -> 0;
        };
    }

    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }

    @Override
    public int getId() {
        return 17;
    }

    record Position(int row, int col, int value, SparseBitSet visited) {
        int toEnd(int rows, int cols) {
            return rows + cols - row - col;
        }
    }
}
