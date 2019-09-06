package de.bruenni.rideprediction.activityservice.infrastructure.domain;

import javax.json.bind.annotation.JsonbProperty;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Contains unique id of the entity.
 *
 * @author Oliver Brüntje
 */
public class Identifiable<T> {

    protected static final String FIELD_ID = "id";

    @JsonbProperty(value = FIELD_ID)
    private T id;

    public Identifiable(T id) {
        this.id = notNull(id, "id may not be null");
    }

    public T getId() {
        return id;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Identifiable))
            return false;

        Identifiable<?> that = (Identifiable<?>)o;

        return id.equals(that.id);
    }

    @Override public int hashCode() {
        return id.hashCode();
    }

    @Override public String toString() {
        return "Identifiable{" +
                "id=" + id +
                '}';
    }
}
