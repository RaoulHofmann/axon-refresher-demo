package axon.demo.infrastructure.repository;

import axon.demo.infrastructure.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    Optional<TodoEntity> findByTodoId(String todoId);

    Optional<TodoEntity> findByTodoIdAndDeletedFalse(String todoId);

    List<TodoEntity> findByDeletedFalse();

    List<TodoEntity> findByCompletedFalse();

    List<TodoEntity> findByCompletedFalseAndDeletedFalse();

    void deleteByTodoId(String todoId);
}