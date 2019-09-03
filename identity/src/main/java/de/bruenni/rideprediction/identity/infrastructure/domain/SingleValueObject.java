package de.bruenni.rideprediction.identity.infrastructure.domain;

import java.io.Serializable;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Abstracts value objects.
 *
 * @author Oliver Brüntje
 */
public abstract class SingleValueObject<V extends Comparable<? super V>>
        implements Serializable, Comparable<SingleValueObject<V>> {

    public static final String DEFAULT_COLUMN_NAME = "value";

    private V value;

    protected SingleValueObject() {
        // required for proxying
    }

    public SingleValueObject(final V initialValue) {
        value = validateAndNormalize(initialValue);
    }

    /**
     * Validates and normalizes the constructor parameter of this simple value object. This implementation checks the
     * value to be non-null. Subclasses may override this method to alter validation and normalization.
     *
     * @param v The constructor value.
     * @return The validated and normalized value.
     */
    protected V validateAndNormalize(final V v) {
        return notNull(v, "value must not be null");
    }

    /**
     * Returns the simple value of this value object. May be overridden by
     *
     * @return the simple value
     */
    public V getValue() {
        return value;
    }

    public String toString() {
        return getValue().toString();
    }

    public int hashCode() {
        return getValue().hashCode();
    }

    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || !(object.getClass().isAssignableFrom(getClass())
                || getClass().isAssignableFrom(object.getClass()))) {
            return false;
        }
        SingleValueObject<V> valueObject = (SingleValueObject<V>) object;
        return valueObject.getValue().equals(getValue());
    }

    @Override
    public int compareTo(final SingleValueObject<V> object) {
        return getValue().compareTo(object.getValue());
    }
}

