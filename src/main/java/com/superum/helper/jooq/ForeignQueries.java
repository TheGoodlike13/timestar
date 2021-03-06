package com.superum.helper.jooq;

/**
 * Contains methods for queries on other tables
 * @param <ID> foreign key type
 */
public interface ForeignQueries<ID> {

    /**
     * @return true if a given value is used as a foreign key; false otherwise
     * @throws NullPointerException if id is null
     */
    boolean isUsed(ID id);

}
