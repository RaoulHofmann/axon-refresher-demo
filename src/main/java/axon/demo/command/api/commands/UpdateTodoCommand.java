package axon.demo.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class UpdateTodoCommand {

    @TargetAggregateIdentifier
    private final String todoId;
    private final String title;
    private final String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateTodoCommand that)) return false;
        return Objects.equals(todoId, that.todoId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoId, title, description);
    }

    @Override
    public String toString() {
        return "UpdateTodoCommand{" +
                "todoId='" + todoId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
