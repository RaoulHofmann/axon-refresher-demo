package axon.demo.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class TodoCreatedEvent {

    private final String todoId;
    private final String title;
    private final String description;
    private final Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoCreatedEvent that)) return false;
        return Objects.equals(todoId, that.todoId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoId, title, description, createdAt);
    }

    @Override
    public String toString() {
        return "TodoCreatedEvent{" +
                "todoId='" + todoId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
