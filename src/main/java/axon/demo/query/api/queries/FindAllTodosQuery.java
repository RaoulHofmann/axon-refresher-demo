package axon.demo.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class FindAllTodosQuery {

    private final boolean includeCompleted;
    private final boolean includeDeleted;

    public FindAllTodosQuery() {
        this(true, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FindAllTodosQuery that)) return false;
        return includeCompleted == that.includeCompleted &&
                includeDeleted == that.includeDeleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(includeCompleted, includeDeleted);
    }

    @Override
    public String toString() {
        return "FindAllTodosQuery{" +
                "includeCompleted=" + includeCompleted +
                ", includeDeleted=" + includeDeleted +
                '}';
    }
}
