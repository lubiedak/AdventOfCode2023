package advent.of.code.controller;

import advent.of.code.service.TaskService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class TasksController {

    private final TaskService taskService;

    @PostMapping("/{id}/{subId}")
    public String processTask(@RequestParam("file") MultipartFile file, @PathVariable int id, @PathVariable int subId)
            throws ExecutionControl.NotImplementedException {
        return taskService.process(file, id, subId);
    }
}
