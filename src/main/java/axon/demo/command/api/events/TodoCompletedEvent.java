package axon.demo.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class TodoCompletedEvent {

    private final String todoId;
    private final Instant completedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoCompletedEvent that)) return false;
        return Objects.equals(todoId, that.todoId) &&
                Objects.equals(completedAt, that.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoId, completedAt);
    }

    @Override
    public String toString() {
        return "TodoCompletedEvent{" +
                "todoId='" + todoId + '\'' +
                ", completedAt=" + completedAt +
                '}';
    }
}
