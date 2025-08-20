package axon.demo.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class TodoUpdatedEvent {

    private final String todoId;
    private final String title;
    private final String description;
    private final Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoUpdatedEvent that)) return false;
        return Objects.equals(todoId, that.todoId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoId, title, description, updatedAt);
    }

    @Override
    public String toString() {
        return "TodoUpdatedEvent{" +
                "todoId='" + todoId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
