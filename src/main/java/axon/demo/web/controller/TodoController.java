package axon.demo.web.controller;

import axon.demo.command.api.commands.CreateTodoCommand;
import axon.demo.infrastructure.entity.TodoEntity;
import axon.demo.query.api.queries.FindAllTodosQuery;
import axon.demo.web.dto.TodoRequest;
import axon.demo.web.dto.TodoResponse;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TodoController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @GetMapping("/v1/todos")
    public CompletableFuture<ResponseEntity<List<TodoResponse>>> getAllTodos(
            @RequestParam(defaultValue = "true") boolean includeCompleted,
            @RequestParam(defaultValue = "false") boolean includeDeleted) {

        FindAllTodosQuery query = new FindAllTodosQuery(includeCompleted, includeDeleted);

        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(TodoEntity.class))
                .thenApply(entities -> {
                    List<TodoResponse> responses = entities.stream()
                            .map(TodoResponse::from)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(responses);
                });
    }

    @PostMapping("/v1/todos")
    public CompletableFuture<ResponseEntity<Object>> createTodo(@RequestBody TodoRequest todoRequest) {
        CreateTodoCommand command = new CreateTodoCommand(todoRequest.getTodoId(), todoRequest.getTitle(), todoRequest.getDescription());
        return commandGateway.send(command);
    }
}
