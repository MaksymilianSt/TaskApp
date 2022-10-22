package TaskApp.demo.controller;

import TaskApp.demo.model.SqlTaskRepository;
import TaskApp.demo.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    private final SqlTaskRepository repository;
    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(SqlTaskRepository repository) {
        this.repository = repository;
    }
    @GetMapping(value = "/tasks",params = {"!sort", "!page","!size"})
    ResponseEntity<List< Task>> readAllTasks(){
        logger.warn("exposing all tasks !");
        return ResponseEntity.ok(repository.findAll());
    }
    @GetMapping(value = "/tasks")
    ResponseEntity<?> readAllTasks(Pageable page){
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
}
