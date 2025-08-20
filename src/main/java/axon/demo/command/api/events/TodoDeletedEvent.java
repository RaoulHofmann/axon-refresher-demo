package axon.demo.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class TodoDeletedEvent {

    private final String todoId;
    private final Instant deletedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoDeletedEvent that)) return false;
        return Objects.equals(todoId, that.todoId) &&
                Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoId, deletedAt);
    }

    @Override
    public String toString() {
        return "TodoDeletedEvent{" +
                "todoId='" + todoId + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
