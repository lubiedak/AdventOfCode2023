package advent.of.code.service;

import advent.of.code.service.calculators.Calculator1;
import advent.of.code.service.calculators.Calculator2;
import advent.of.code.service.calculators.TaskCalculator;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskService {

    private final List<TaskCalculator> calculators;

    public String process(MultipartFile file, int id, int subId) throws ExecutionControl.NotImplementedException {
        var calculator = calculators.stream().filter(tc -> tc.getId() == id).findFirst().orElseThrow(() -> new ExecutionControl.NotImplementedException("Task not implemented"));
        if (subId == 1) {
            return calculator.calculate1(readLines(file));
        }
        return calculator.calculate2(readLines(file));
    }

    private List<String> readLines(MultipartFile file) {
        try {
            return new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
