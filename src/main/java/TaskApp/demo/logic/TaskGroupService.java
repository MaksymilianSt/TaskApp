package TaskApp.demo.logic;

import TaskApp.demo.model.TaskGroup;
import TaskApp.demo.model.TaskGroupRepository;
import TaskApp.demo.model.TaskRepository;
import TaskApp.demo.model.projection.GroupReadModel;
import TaskApp.demo.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }
    public List<GroupReadModel> readAll(){
        return repository.findAll().stream()
                .map(taskGroup ->  new GroupReadModel(taskGroup))
                .collect(Collectors.toList());
    }
    public void toggleGroup(int groupId ){
        if(taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
            throw new IllegalStateException("group has undone tasks . Do all the tasks first");
        }
       TaskGroup result = repository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("Task group with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
