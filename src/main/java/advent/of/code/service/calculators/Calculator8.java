package advent.of.code.service.calculators;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Calculator8 implements TaskCalculator {

    @Override
    public String calculate1(List<String> lines) {
        String cordsSequence = lines.get(0);
        return "" + findDistanceFromAToB(lines, cordsSequence, "AAA", "ZZZ");
    }

    @Override
    public String calculate2(List<String> lines) {
        String cordsSequence = lines.get(0);
        var map = readCoords(lines);
        long sum = 0;
        String[] currentPoints = map.keySet().stream().filter(s -> s.toCharArray()[2] == 'A').toArray(String[]::new);

        long current = findDistanceFromAToAnyWithLasLetterB(lines, cordsSequence, currentPoints[0], 'Z');

        for (int i = 1; i < currentPoints.length; i++) {
            long next = findDistanceFromAToAnyWithLasLetterB(lines, cordsSequence, currentPoints[i], 'Z');
            current = lcm(current, next);
        }

        System.out.println(current);
        return "\n" + current;
    }

    private Map<String, Coords> readCoords(List<String> lines) {
        var map = new HashMap<String, Coords>();
        lines.stream().skip(1).forEach(line -> {
            var inputData = line.split("\\s= ");
            var coords = inputData[1].split(",");
            map.put(inputData[0], new Coords(coords[0].substring(1), coords[1].substring(1, 4)));
        });
        return map;
    }

    private int findDistanceFromAToB(List<String> lines, String path, String a, String b) {
        var map = readCoords(lines);
        boolean found = false;
        int sum = 0;

        String currentPoint = a;
        while (!found) {
            for (char c : path.toCharArray()) {
                currentPoint = map.get(currentPoint).next(c);
                sum++;
                if (currentPoint.equals(b)) {
                    found = true;
                    break;
                }
            }
        }
        return sum;
    }

    private long findDistanceFromAToAnyWithLasLetterB(List<String> lines, String path, String a, char b) {
        var map = readCoords(lines);
        boolean found = false;
        long sum = 0;

        String currentPoint = a;
        while (!found) {
            for (char c : path.toCharArray()) {
                currentPoint = map.get(currentPoint).next(c);
                sum++;
                if (currentPoint.toCharArray()[2] == b) {
                    found = true;
                    break;
                }
            }
        }
        return sum;
    }

    private long gcd(long a, long b) {
        long remainder = a % b;
        return remainder == 0 ? b : gcd(b, remainder);
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    @Override
    public int getId() {
        return 8;
    }

    record Coords(String L, String R) {
        public String next(char side) {
            return side == 'R' ? R : L;
        }
    }
}
