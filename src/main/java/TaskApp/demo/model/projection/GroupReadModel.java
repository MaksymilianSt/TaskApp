package TaskApp.demo.model.projection;


import TaskApp.demo.model.TaskGroup;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {
    private String description;
    /**
     * Deadline from the latest task in group.
     */
    private LocalDateTime deadline;
    private Set<GroupTaskReadModel> tasks;

    public GroupReadModel(TaskGroup group){
        this.description = group.getDescription();

        group.getTasks().stream()
                .map(task -> task.getDeadline())
                .max(LocalDateTime::compareTo)
                .ifPresent(localDateTime -> this.deadline = localDateTime);

        tasks = group.getTasks().stream()
                .map(task ->  new GroupTaskReadModel(task))
                .collect(Collectors.toSet());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<GroupTaskReadModel> getTasks() {
        return tasks;
    }

    public void setTasks(Set<GroupTaskReadModel> tasks) {
        this.tasks = tasks;
    }
}
