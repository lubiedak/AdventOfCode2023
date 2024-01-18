package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Calculator10 implements TaskCalculator {

    private static final Position NORTH = new Position(-1, 0);
    private static final Position SOUTH = new Position(1, 0);
    private static final Position EAST = new Position(0, 1);
    private static final Position WEST = new Position(0, -1);

    private static final Map<Character, Positions> PIPES = Map.of('|', new Positions(NORTH, SOUTH),
            '-', new Positions(EAST, WEST),
            'L', new Positions(NORTH, EAST),
            'J', new Positions(NORTH, WEST),
            '7', new Positions(SOUTH, WEST),
            'F', new Positions(SOUTH, EAST));

    @Override

    public String calculate1(List<String> lines) {
        char[][] schema = readSchema(lines);
        var startingPosition = positionOf(schema, 'S');
        var next = firstPosition(schema, startingPosition);
        int i = 1;
        var pipe = schema[next.v][next.h];
        var previous = startingPosition;
        var current = next;
        while(pipe != 'S'){
            next = current.add(getNext(pipe, previous));
            previous = next.previous(current);
            current = next;
            pipe = schema[current.v][current.h];
            i++;
        }


        return "\n" + i/2;
    }

    @Override
    public String calculate2(List<String> lines) {
        return "Not implemented";
    }

    private Position getNext(char pipe, Position previous){
        return PIPES.get(pipe).getNext(previous);
    }

    private Position firstPosition(char[][] schema, Position startingPosition) {
        for (var position : List.of(NORTH, SOUTH, EAST, WEST)) {
            var next = startingPosition.add(position);
            var previous = startingPosition.previous(next);
            if(PIPES.get(schema[next.v][next.h]).contains(previous)){
                return next;
            }
        }
        return null;
    }

    private char[][] readSchema(List<String> lines) {
        char[][] schema = new char[lines.size()][];
        int i = 0;
        for (var line : lines) {
            schema[i++] = line.toCharArray();
        }
        return schema;
    }

    private Position positionOf(char[][] schema, char c) {
        for (int i = 0; i < schema.length; ++i) {
            for (int j = 0; j < schema[i].length; ++j) {
                if (schema[i][j] == c) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    @Override
    public int getId() {
        return 10;
    }

    record Position(int v, int h) {
        Position add(Position p) {
            return new Position(v + p.v, h + p.h);
        }

        Position previous(Position p) {
            return new Position(p.v - v, p.h - h);
        }
    }

    record Positions(Position in, Position out) {
        boolean contains(Position p) {
            return p.equals(in) || p.equals(out);
        }

        Position getNext(Position previous){
            return in.equals(previous) ? out : in;
        }
    }
}
