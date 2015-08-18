package com.superum.helper.jooq;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.TableField;

import java.util.Arrays;
import java.util.List;

public class ForeignQueriesImpl<ID> implements ForeignQueries<ID> {

    /**
     * @return true if a record with foreign key value id exists in given partition; false otherwise
     *
     * @throws NullPointerException if key is null
     */
    @Override
    public boolean isUsed(ID id) {
        if (id == null)
            throw new IllegalArgumentException("Foreign key value cannot be null");

        return sql.fetchExists(
                sql.selectOne()
                        .from(tables())
                        .where(foreignKeys.stream()
                                .map(key -> key.eq(id))
                                .reduce(Condition::or)
                                .orElseThrow(() -> new AssertionError("At least one field should have been provided to the checker!"))));
    }

    // CONSTRUCTORS

    @SafeVarargs
    public ForeignQueriesImpl(DSLContext sql, TableField<?, ID>... foreignKeys) {
        this.sql = sql;
        this.foreignKeys = Arrays.asList(foreignKeys);
    }

    // PRIVATE

    private final DSLContext sql;
    private final List<TableField<?, ID>> foreignKeys;

    private Table[] tables() {
        return foreignKeys.stream()
                .map(TableField::getTable)
                .toArray(Table[]::new);
    }

}