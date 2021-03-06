package com.superum.api.v3.account;

import com.google.common.base.MoreObjects;
import com.superum.api.v1.account.AccountType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Objects;

public class Account {

    public Account create() {
        String hashedPassword = passwordEncoder.encode(password);
        accountRepository.create(id, username, accountType.name(), hashedPassword, createdAt.toEpochMilli(), updatedAt.toEpochMilli());
        return this;
    }

    public void sendCredentials(String email, String partitionName) {
        accountEmailSender.sendEmailWithCredentials(email, partitionName, password);
    }

    // CONSTRUCTORS

    public static Account valueOf(Integer id, String username, AccountType accountType, String password,
                                  Instant createdAt, Instant updatedAt, AccountRepository accountRepository,
                                  AccountEmailSender accountEmailSender, PasswordEncoder passwordEncoder) {
        return new Account(id, username, accountType, password, createdAt, updatedAt, accountRepository,
                accountEmailSender, passwordEncoder);
    }

    private Account(Integer id, String username, AccountType accountType, String password, Instant createdAt,
                   Instant updatedAt, AccountRepository accountRepository, AccountEmailSender accountEmailSender,
                    PasswordEncoder passwordEncoder) {
        this.id = id;
        this.username = username;
        this.accountType = accountType;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.accountRepository = accountRepository;
        this.accountEmailSender = accountEmailSender;
        this.passwordEncoder = passwordEncoder;
    }

    // PRIVATE

    private final Integer id;
    private final String username;
    private final AccountType accountType;
    private final String password;
    private final Instant createdAt;
    private final Instant updatedAt;

    private final AccountRepository accountRepository;
    private final AccountEmailSender accountEmailSender;
    private final PasswordEncoder passwordEncoder;

    // OBJECT OVERRIDES

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("accountType", accountType)
                .add("password", "[PROTECTED]")
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(username, account.username) &&
                Objects.equals(accountType, account.accountType) &&
                Objects.equals(password, account.password) &&
                Objects.equals(createdAt, account.createdAt) &&
                Objects.equals(updatedAt, account.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, accountType, password, createdAt, updatedAt);
    }

}
