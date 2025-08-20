package axon.demo.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class CompleteTodoCommand {

    @TargetAggregateIdentifier
    private final String todoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompleteTodoCommand that)) return false;
        return Objects.equals(todoId, that.todoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoId);
    }

    @Override
    public String toString() {
        return "CompleteTodoCommand{" +
                "todoId='" + todoId + '\'' +
                '}';
    }
}
