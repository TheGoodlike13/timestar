package com.superum.helper.fields.core;

import com.google.common.collect.ImmutableList;
import com.superum.helper.utils.EqualsUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.superum.helper.utils.TimeUtils.fromString;

/**
 * <pre>
 * NamedField intended for use with non-primitives
 * </pre>
 */
public class SimpleField<T> extends NamedField<T> {

    @Override
    public boolean isSet() {
        return value != null;
    }

    @Override
    public T getValue() {
        return value;
    }

    // OBJECT OVERRIDES

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof SimpleField))
            return false;

        SimpleField other = (SimpleField) o;

        return this.equalsName(other) && Objects.equals(this.value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, value);
    }

    // CONSTRUCTORS

    public static <T> SimpleField<T> empty(String fieldName, Mandatory mandatory) {
        return new SimpleField<>(fieldName, null, mandatory);
    }

    public static SimpleField<BigDecimal> valueOf(String fieldName, BigDecimal value, Mandatory mandatory) {
        if (value == null)
            return empty(fieldName, mandatory);

        return new BigDecimalField(fieldName, value, mandatory);
    }

    /**
     * @deprecated Please use org.joda.time.Instant instead!
     */
    @Deprecated
    public static SimpleField<Date> valueOf(String fieldName, Date value, Mandatory mandatory) throws ParseException {
        if (value == null)
            return empty(fieldName, mandatory);

        if (value instanceof java.sql.Date)
            value = fromString(value.toString());

        @SuppressWarnings("deprecation")
        SimpleField<Date> field = new JavaUtilDateField(fieldName, value, mandatory);
        return field;
    }

    public static <T> SimpleField<T> valueOf(String fieldName, T value, Mandatory mandatory) {
        if (value == null)
            return empty(fieldName, mandatory);

        return new SimpleField<>(fieldName, value, mandatory);
    }

    /**
     * <pre>
     * This version ensures that the list inside the field will remain immutable (as long as the original list
     * is not messed with)
     * </pre>
     */
    public static <T> SimpleField<List<T>> immutableValueOf(String fieldName, List<T> value, Mandatory mandatory) {
        if (value == null)
            return empty(fieldName, mandatory);

        return new SimpleField<>(fieldName, ImmutableList.copyOf(value), mandatory);
    }

    protected SimpleField(String fieldName, T value, Mandatory mandatory) {
        super(fieldName, mandatory);
        this.value = value;
    }

    // PROTECTED

    protected final T value;

    // EXTENDING CLASSES FOR SPECIAL CASES

    /**
     * <pre>
     * Specialized NamedField, intended to be used with java.math.BigDecimal
     *
     * This Field will use compareTo() method to check for equality of the Fields, which will ignore things such as scale
     * </pre>
     */
    private static class BigDecimalField extends SimpleField<BigDecimal> {

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof BigDecimalField))
                return false;

            BigDecimalField other = (BigDecimalField) o;

            return EqualsUtils.equalsJavaMathBigDecimal(this.value, other.value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : Double.valueOf(value.doubleValue()).hashCode();
        }

        // CONSTRUCTORS

        protected BigDecimalField(String fieldName, BigDecimal value, Mandatory mandatory) {
            super(fieldName, value, mandatory);
        }

    }

    /**
     * <pre>
     * Specialized NamedField, intended to be used with java.sql.Date
     *
     * This Field will check for String equality between java.sql.Date instances;
     * normal equality can fail due to timezone differences
     * </pre>
     * @deprecated java.sql.Date should not be used with Spring/Jackson according to their documentation,
     *               therefore this class is now marked as deprecated. AVOID!
     */
    @Deprecated
    private static class JavaSqlDateField extends SimpleField<java.sql.Date> {

        @Override
        @SuppressWarnings("deprecation")
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof JavaSqlDateField))
                return false;

            JavaSqlDateField other = (JavaSqlDateField) o;

            return EqualsUtils.equalsJavaSqlDate(this.value, other.value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.toString().hashCode();
        }

        // CONSTRUCTORS

        protected JavaSqlDateField(String fieldName, java.sql.Date value, Mandatory mandatory) {
            super(fieldName, value, mandatory);
        }

    }

    /**
     * <pre>
     * Specialized NamedField, intended to be used with java.util.Date
     *
     * This Field will check for day equality, rather than comparing the entire Date (i.e. hours, minutes, etc)
     * </pre>
     * @deprecated Please use org.joda.time.Instant instead!
     */
    @Deprecated
    private static class JavaUtilDateField extends SimpleField<Date> {

        @Override
        @SuppressWarnings("deprecation")
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof JavaUtilDateField))
                return false;

            JavaUtilDateField other = (JavaUtilDateField) o;

            return EqualsUtils.equalsJavaUtilDate(this.value, other.value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.toString().hashCode();
        }

        // CONSTRUCTORS

        protected JavaUtilDateField(String fieldName, java.util.Date value, Mandatory mandatory) {
            super(fieldName, value, mandatory);
        }

    }

}