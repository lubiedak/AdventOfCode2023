package advent.of.code.controller;

import advent.of.code.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TasksController {

    private final TaskService taskService;

    @PostMapping("/{id}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable String id) {
        return taskService.task1(file);
    }
}
