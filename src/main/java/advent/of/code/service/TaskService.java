package advent.of.code.service;

import advent.of.code.service.calculators.Calculator1;
import lombok.RequiredArgsConstructor;
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

    private final Calculator1 calculator1;

    public String task1(MultipartFile file) {
        return calculator1.calculate1(readLines(file));
    }

    private List<String> readLines(MultipartFile file){
        try {
            return new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
