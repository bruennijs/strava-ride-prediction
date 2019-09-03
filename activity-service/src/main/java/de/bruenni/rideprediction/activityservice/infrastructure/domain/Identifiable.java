package de.bruenni.rideprediction.activityservice.infrastructure.domain;

import javax.json.bind.annotation.JsonbProperty;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Contains unique id of the entity.
 *
 * @author Oliver Brüntje
 */
public class Identifiable<T> {

    @JsonbProperty(value = "id")
    private T id;

    public Identifiable(T id) {
        this.id = notNull(id, "id may not be null");
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
