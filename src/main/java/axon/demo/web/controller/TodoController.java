package axon.demo.web.controller;

import axon.demo.infrastructure.entity.TodoEntity;
import axon.demo.query.api.queries.FindAllTodosQuery;
import axon.demo.web.dto.TodoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Builder
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

}
