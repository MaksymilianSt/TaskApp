package TaskApp.demo.model.projection;

import TaskApp.demo.model.Task;
import TaskApp.demo.model.TaskGroup;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {
    private String description ;
    private Set<GroupTaskWriteModel> tasks;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(Set<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;
    }
    public TaskGroup toGroup(){
            var result = new TaskGroup();
            result.setDescription(this.description);
            result.setTasks(
                    tasks.stream()
                            .map(GroupTaskWriteModel::toTask)
                            .collect(Collectors.toSet())
            );
            return result;
    }
}
