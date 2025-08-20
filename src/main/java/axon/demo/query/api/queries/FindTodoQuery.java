package axon.demo.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class FindTodoQuery {

    private final String todoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FindTodoQuery that)) return false;
        return Objects.equals(todoId, that.todoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoId);
    }

    @Override
    public String toString() {
        return "FindTodoQuery{" +
                "todoId='" + todoId + '\'' +
                '}';
    }
}
