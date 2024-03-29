package TaskApp.demo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    Optional<Task> findById(Integer id);
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);
    Task save(Task entity);
    Page<Task> findAll(Pageable page);
    boolean existsById(Integer id);
    List<Task> findByDone( boolean done);


}
