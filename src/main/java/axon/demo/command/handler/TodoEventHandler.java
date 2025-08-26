package axon.demo.command.handler;

import axon.demo.command.api.events.TodoCompletedEvent;
import axon.demo.command.api.events.TodoCreatedEvent;
import axon.demo.command.api.events.TodoDeletedEvent;
import axon.demo.command.api.events.TodoUpdatedEvent;
import axon.demo.infrastructure.entity.TodoEntity;
import axon.demo.infrastructure.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodoEventHandler {

    private final TodoRepository todoRepository;

    @EventHandler
    public void on(TodoCreatedEvent event) {
        TodoEntity todo = TodoEntity.builder()
                .todoId(event.getTodoId())
                .title(event.getTitle())
                .description(event.getDescription())
                .createdAt(event.getCreatedAt())
                .completed(false)
                .deleted(false)
                .build();

        todoRepository.save(todo);
    }

    @EventHandler
    public void on(TodoUpdatedEvent event) {
        TodoEntity todo = todoRepository.findById(event.getTodoId()).orElseThrow();
        todo.setTitle(event.getTitle());
        todo.setDescription(event.getDescription());
        todo.setUpdatedAt(event.getUpdatedAt());
        todoRepository.save(todo);
    }

    @EventHandler
    public void on(TodoCompletedEvent event) {
        TodoEntity todo = todoRepository.findById(event.getTodoId()).orElseThrow();
        todo.setCompleted(true);
        todo.setCompletedAt(event.getCompletedAt());
        todoRepository.save(todo);
    }

    @EventHandler
    public void on(TodoDeletedEvent event) {
        TodoEntity todo = todoRepository.findById(event.getTodoId()).orElseThrow();
        todo.setDeleted(true);
        todoRepository.save(todo);
    }
}
