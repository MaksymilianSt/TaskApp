package TaskApp.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SqlTaskRepository extends JpaRepository<Task ,Integer> {

    List<Task> findByDone(@Param("state") boolean done);
}