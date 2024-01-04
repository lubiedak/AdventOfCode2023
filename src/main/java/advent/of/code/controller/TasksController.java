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

    @PostMapping("/{id}/{subId}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable int id, @PathVariable int subId) {
        if(subId == 1) {
            return taskService.task1(file);
        }
        if(subId==2){
            return taskService.task2(file);
        }
        return "0";
    }
}
