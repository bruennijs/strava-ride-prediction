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
}
