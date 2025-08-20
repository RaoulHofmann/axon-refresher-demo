package axon.demo.command.handler;

import axon.demo.infrastructure.entity.TodoEntity;
import axon.demo.infrastructure.repository.TodoRepository;
import axon.demo.query.api.queries.FindAllTodosQuery;
import axon.demo.query.api.queries.FindTodoQuery;
import axon.demo.web.exception.TodoNotFoundException;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TodoQueryHandler {

    private final TodoRepository repository;

    @QueryHandler
    public TodoEntity handle(FindTodoQuery query) {
        return repository.findByTodoIdAndDeletedFalse(query.getTodoId())
                .orElseThrow(() -> new TodoNotFoundException("Todo not found: " + query.getTodoId()));
    }

    @QueryHandler
    public List<TodoEntity> handle(FindAllTodosQuery query) {
        if (!query.isIncludeCompleted() && !query.isIncludeDeleted()) {
            return repository.findByCompletedFalseAndDeletedFalse();
        } else if (!query.isIncludeDeleted()) {
            return repository.findByDeletedFalse();
        } else if (!query.isIncludeCompleted()) {
            return repository.findByCompletedFalse();
        }
        return repository.findAll();
    }
}
