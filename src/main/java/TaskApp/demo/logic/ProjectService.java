package TaskApp.demo.logic;

import TaskApp.demo.TaskConfigurationProperties;
import TaskApp.demo.model.*;
import TaskApp.demo.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    public ProjectService(ProjectRepository projectRepository, TaskGroupRepository taskGroupRepository, TaskConfigurationProperties config) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }
    public List<Project> readAll(){
       return projectRepository.findAll();
    }
    public Project save(Project toSave){
        return projectRepository.save(toSave);
    }
    public GroupReadModel createGroup(LocalDateTime deadline ,int projectId ){
        if(!config.getTemplate().isAllowMultipleTasks() &&
                taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId))
            throw new IllegalStateException("Only one undone group from project is allowed");
      TaskGroup result =  projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(project.getSteps().stream()
                                    .map(step -> new Task(

                                                step.getDescription(),
                                                deadline.plusDays(step.getDaysToDeadline())
                                                          )
                                         ).collect(Collectors.toSet())
                            );
                    targetGroup.setProject(project);
                    return taskGroupRepository.save(targetGroup);
                }).orElseThrow(()->new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(result);
    }
}

