package advent.of.code.service.calculators;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Calculator2 implements TaskCalculator {

    private static final Map<String, Integer> limitsForBalls = Map.of("red", 12, "green", 13, "blue", 14);

    @Override
    public String calculate1(List<String> lines) {
        int sum = 0;
        boolean impossibleGame;
        for (var line : lines) {
            var id = readGameId(line);
            impossibleGame = false;
            for (String game : readGames(line)) {
                var balls = parseGameSet(game);
                impossibleGame = areBallsExceeded(balls);
                if (impossibleGame) break;
            }
            if (!impossibleGame)
                sum += id;
        }
        return "" + sum;
    }

    @Override
    public String calculate2(List<String> lines) {
        int sum = 0;
        for (var line : lines) {
            var balls = findMinimumNumberOfBalls(readGames(line));
            sum += balls.values().stream().reduce(1, (a, b) -> a * b);
        }
        return "" + sum;
    }

    @Override
    public int getId() {
        return 2;
    }

    private int readGameId(String line) {
        return Integer.parseInt(line.split(": ")[0].replace("Game ", ""));
    }

    private String[] readGames(String line) {
        return line.split(": ")[1].split("; ");
    }

    private boolean areBallsExceeded(Map<String, Integer> balls) {
        for (var ball : balls.entrySet()) {
            if (ball.getValue() > limitsForBalls.get(ball.getKey())) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Integer> findMinimumNumberOfBalls(String[] games) {
        Map<String, Integer> minimumNumberOfBalls = new HashMap<>();
        for (var game : games) {
            var gameSet = parseGameSet(game);
            for (var ball : gameSet.entrySet()) {
                if (!minimumNumberOfBalls.containsKey(ball.getKey())) {
                    minimumNumberOfBalls.put(ball.getKey(), ball.getValue());
                } else {
                    int currentMinimum = minimumNumberOfBalls.get(ball.getKey());
                    if (currentMinimum < ball.getValue())
                        minimumNumberOfBalls.put(ball.getKey(), ball.getValue());
                }
            }
        }
        return minimumNumberOfBalls;
    }

    private Map<String, Integer> parseGameSet(String gameSet) {
        String[] balls = gameSet.split(", ");
        if (balls.length > 3) {
            throw new RuntimeException("Too many balls");
        }

        return Stream.of(balls).collect(Collectors.toMap(b -> b.split(" ")[1], b -> Integer.parseInt(b.split(" ")[0])));
    }

    record GameSet(int red, int green, int blue) {
    }

    record Game(int id, List<GameSet> games) {
    }
}
