package com.superum.fields.primitives;

import com.superum.fields.Mandatory;
import com.superum.fields.NamedField;

public class IntField extends NamedField<Integer> {

    @Override
    public boolean isSet() {
        return value != 0;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public int intValue() {
        return value;
    }

    // OBJECT OVERRIDES

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof IntField))
            return false;

        IntField other = (IntField) o;

        return this.value == other.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    // CONSTRUCTORS

    public IntField(String fieldName, int value, Mandatory mandatory) {
        super(fieldName, mandatory);
        this.value = value;
    }

    // PRIVATE

    private final int value;

}
