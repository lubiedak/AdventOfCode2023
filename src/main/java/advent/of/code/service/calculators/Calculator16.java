package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static advent.of.code.service.calculators.Calculator16.D.*;
import static advent.of.code.service.calculators.CalculatorUtils.readSchema;

@Component
public class Calculator16 implements TaskCalculator {

    static Map<D, Integer> directions = Map.of(RIGHT, 1, LEFT, 2, UP, 4, DOWN, 8);

    @Override
    public String calculate1(List<String> lines) {
        var schema = readSchema(lines);
        return "" + calculate(schema, new BeamStream(new Position(0, 0), RIGHT));
    }

    @Override
    public String calculate2(List<String> lines) {
        var schema = readSchema(lines);
        int sum = 0;
        int maxCol = schema[0].length - 1;
        int maxRow = schema.length - 1;
        for (int row = 0; row < schema.length; row++) {
            sum = Math.max(sum, calculate(schema, new BeamStream(new Position(row, 0), RIGHT)));
            sum = Math.max(sum, calculate(schema, new BeamStream(new Position(row, maxCol), LEFT)));
        }

        for (int col = 0; col < schema[0].length; col++) {
            sum = Math.max(sum, calculate(schema, new BeamStream(new Position(0, col), DOWN)));
            sum = Math.max(sum, calculate(schema, new BeamStream(new Position(maxRow, col), UP)));
        }

        return "" + sum;
    }

    private int calculate(char[][] schema, BeamStream startingBeam) {
        int height = schema.length;
        int width = schema[0].length;

        int[][] schemaMarked = new int[height][width];
        List<BeamStream> beams = new ArrayList<>();
        beams.add(startingBeam);

        while (!beams.isEmpty()) {
            // We mark that given field was visited from this direction using '|=' operator
            beams.forEach(beam -> schemaMarked[beam.position.row][beam.position.col] |= directions.get(beam.direction));
            List<BeamStream> newBeams = new ArrayList<>();
            beams.forEach(interactWithAMirror(schema, newBeams));
            beams.addAll(newBeams);
            beams.removeIf(b -> b.isFinished(height, width) || b.beenThere(schemaMarked) > 0);

            beams.forEach(BeamStream::next);
        }
        return sumEnergizedCells(schemaMarked);
    }

    private static int sumEnergizedCells(int[][] schemaMarked) {
        int sum = 0;
        for (int[] row : schemaMarked) {
            for (int col = 0; col < schemaMarked[0].length; col++) {
                if (row[col] != 0) {
                    sum++;
                }
            }
        }
        return sum;
    }

    private static Consumer<BeamStream> interactWithAMirror(char[][] schema, List<BeamStream> newBeams) {
        return beam -> {
            int row = beam.position.row;
            int col = beam.position.col;
            var symbol = schema[row][col];
            if (symbol == '/' || symbol == '\\') {
                beam.directionUpdate(symbol);
            }
            if (symbol == '|' && (beam.direction == RIGHT || beam.direction == LEFT)) {
                beam.setDirection(UP);
                newBeams.add(new BeamStream(new Position(row, col), DOWN));
            }
            if (symbol == '-' && (beam.direction == UP || beam.direction == DOWN)) {
                beam.setDirection(RIGHT);
                newBeams.add(new BeamStream(new Position(row, col), LEFT));
            }
        };
    }

    static class BeamStream {
        Position position;
        D direction;

        BeamStream(Position position, D direction) {
            this.position = position;
            this.direction = direction;
        }

        void next() {
            position = position.next(direction);
        }

        void directionUpdate(char symbol) {
            if (symbol == '/') {
                direction = switch (direction) {
                    case UP -> RIGHT;
                    case DOWN -> LEFT;
                    case LEFT -> DOWN;
                    case RIGHT -> UP;
                };
            }
            if (symbol == '\\') {
                direction = switch (direction) {
                    case UP -> LEFT;
                    case DOWN -> RIGHT;
                    case LEFT -> UP;
                    case RIGHT -> DOWN;
                };
            }
        }

        void setDirection(D direction) {
            this.direction = direction;
        }

        boolean isFinished(int height, int width) {
            return (direction == RIGHT && position.col >= width - 1)
                    || (direction == LEFT && position.col <= 0)
                    || (direction == UP && position.row <= 0)
                    || (direction == DOWN && position.row >= height - 1);
        }

        int beenThere(int[][] schema) {
            int row = position.row;
            int col = position.col;
            int nextMarker = switch (direction) {
                case UP -> schema[row - 1][col];
                case DOWN -> schema[row + 1][col];
                case LEFT -> schema[row][col - 1];
                case RIGHT -> schema[row][col + 1];
            };
            return nextMarker & directions.get(direction);
        }
    }

    record Position(int row, int col) {

        Position next(D direction) {
            return switch (direction) {
                case UP -> new Position(row - 1, col);
                case DOWN -> new Position(row + 1, col);
                case LEFT -> new Position(row, col - 1);
                case RIGHT -> new Position(row, col + 1);
            };
        }
    }

    enum D {UP, DOWN, LEFT, RIGHT}

    @Override
    public int getId() {
        return 16;
    }
}
