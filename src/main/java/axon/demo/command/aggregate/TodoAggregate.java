package axon.demo.command.aggregate;

import axon.demo.command.api.commands.CompleteTodoCommand;
import axon.demo.command.api.commands.CreateTodoCommand;
import axon.demo.command.api.commands.DeleteTodoCommand;
import axon.demo.command.api.commands.UpdateTodoCommand;
import axon.demo.command.api.events.TodoCompletedEvent;
import axon.demo.command.api.events.TodoCreatedEvent;
import axon.demo.command.api.events.TodoDeletedEvent;
import axon.demo.command.api.events.TodoUpdatedEvent;
import axon.demo.web.exception.TodoNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;

@Aggregate
@Getter
@NoArgsConstructor
public class TodoAggregate {

    @AggregateIdentifier
    private String todoId;
    private String title;
    private String description;
    private boolean completed;
    private boolean deleted;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant completedAt;

    @CommandHandler
    public TodoAggregate(CreateTodoCommand command) {
        if (command.getTitle() == null || command.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Todo title cannot be empty");
        }

        AggregateLifecycle.apply(new TodoCreatedEvent(
                command.getTodoId(),
                command.getTitle(),
                command.getDescription(),
                Instant.now()
        ));
    }

    @CommandHandler
    public void handle(UpdateTodoCommand command) {
        if (deleted) {
            throw new TodoNotFoundException("Cannot update deleted todo: " + command.getTodoId());
        }

        if (command.getTitle() == null || command.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Todo title cannot be empty");
        }

        if (!command.getTitle().equals(this.title) ||
                !java.util.Objects.equals(command.getDescription(), this.description)) {

            AggregateLifecycle.apply(new TodoUpdatedEvent(
                    command.getTodoId(),
                    command.getTitle(),
                    command.getDescription(),
                    Instant.now()
            ));
        }
    }

    @CommandHandler
    public void handle(CompleteTodoCommand command) {
        if (deleted) {
            throw new TodoNotFoundException("Cannot complete deleted todo: " + command.getTodoId());
        }

        if (completed) {
            return;
        }

        AggregateLifecycle.apply(new TodoCompletedEvent(
                command.getTodoId(),
                Instant.now()
        ));
    }

    @CommandHandler
    public void handle(DeleteTodoCommand command) {
        if (deleted) {
            return;
        }

        AggregateLifecycle.apply(new TodoDeletedEvent(
                command.getTodoId(),
                Instant.now()
        ));
    }

    @EventSourcingHandler
    public void on(TodoCreatedEvent event) {
        this.todoId = event.getTodoId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.createdAt = event.getCreatedAt();
        this.completed = false;
        this.deleted = false;
    }

    @EventSourcingHandler
    public void on(TodoUpdatedEvent event) {
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.updatedAt = event.getUpdatedAt();
    }

    @EventSourcingHandler
    public void on(TodoCompletedEvent event) {
        this.completed = true;
        this.completedAt = event.getCompletedAt();
    }

    @EventSourcingHandler
    public void on(TodoDeletedEvent event) {
        this.deleted = true;
    }
}
