package com.superum.api.v3.account.impl;

import com.superum.api.v3.account.AccountRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static timestar_v2.Tables.ACCOUNT;

@Repository
@Transactional
public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public int create(Integer id, String username, String password, String accountType, long createdAt, long updatedAt) {
        return sql.insertInto(ACCOUNT)
                .set(ACCOUNT.ID, id)
                .set(ACCOUNT.USERNAME, username)
                .set(ACCOUNT.PASSWORD, password)
                .set(ACCOUNT.ACCOUNT_TYPE, accountType)
                .set(ACCOUNT.CREATED_AT, createdAt)
                .set(ACCOUNT.UPDATED_AT, updatedAt)
                .execute();
    }

    @Override
    public int updateUsername(String originalUsername, String newUsername, long updatedAt) {
        return sql.update(ACCOUNT)
                .set(ACCOUNT.USERNAME, newUsername)
                .set(ACCOUNT.UPDATED_AT, updatedAt)
                .where(ACCOUNT.USERNAME.eq(originalUsername))
                .execute();
    }

    @Override
    public int deleteAccount(int id, String accountType) {
        return sql.deleteFrom(ACCOUNT)
                .where(ACCOUNT.ID.eq(id).and(ACCOUNT.ACCOUNT_TYPE.eq(accountType)))
                .execute();
    }

    // CONSTRUCTORS

    @Autowired
    public AccountRepositoryImpl(DSLContext sql) {
        this.sql = sql;
    }

    // PRIVATE

    private final DSLContext sql;

}
