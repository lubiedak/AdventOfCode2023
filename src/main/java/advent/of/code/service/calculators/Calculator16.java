package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static advent.of.code.service.calculators.CalculatorUtils.readSchema;

@Component
public class Calculator16 implements TaskCalculator {

    @Override
    public String calculate1(List<String> lines) {
        var schema = readSchema(lines);
        int height = schema.length;
        int width = schema[0].length;

        var schemaMarked = schema;

        List<BeamStream> beams = new ArrayList<>();
        beams.add(new BeamStream(new Position(0, 0), D.RIGHT));
        schemaMarked[0][0] = '#';
        while (!areAllFinished(beams, height, width)) {
            beams.forEach(BeamStream::next);
            beams.forEach(beam -> schemaMarked[beam.position.h][beam.position.w] = '#');
            beams.forEach(beam -> {
                    var symbol = schema[beam.position.h][beam.position.w];
                    }
            );
        }


        return "Not implemented";
    }

    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }


    boolean areAllFinished(List<BeamStream> beams, int height, int width) {
        return beams.stream().allMatch(b -> b.isFinished(height, width));
    }

    class BeamStream {
        Position position;
        D direction;
        Position prev;
        D from;

        BeamStream(Position position, D direction) {
            this.position = position;
            this.direction = direction;
        }

        void next() {
            prev = new Position(position.h, position.w);
            position = position.next(direction);
            from = position.cameFrom(prev);
        }

        boolean isFinished(int height, int width) {
            return direction == D.RIGHT && position.w >= width - 1
                    || direction == D.LEFT && position.w <= 0
                    || direction == D.UP && position.h <= 0
                    || direction == D.DOWN && position.h >= height - 1;
        }
    }

    record Position(int h, int w) {
        D cameFrom(Position previous) {
            if (previous.h == h && previous.w - w == 1)
                return D.RIGHT;
            if (previous.h == h && previous.w - w == -1)
                return D.LEFT;
            if (previous.h - h == 1 && previous.w == w)
                return D.DOWN;
            if (previous.h - h == -1 && previous.w == w)
                return D.UP;
            return D.WRONG;
        }

        Position next(D direction) {
            return switch (direction) {
                case UP -> new Position(h - 1, w);
                case DOWN -> new Position(h + 1, w);
                case LEFT -> new Position(h, w - 1);
                case RIGHT -> new Position(h, w + 1);
                case WRONG -> null;
            };
        }
    }

    enum D {UP, DOWN, LEFT, RIGHT, WRONG}

    ;

    @Override
    public int getId() {
        return 16;
    }
}
