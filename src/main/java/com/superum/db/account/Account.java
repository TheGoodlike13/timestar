package com.superum.db.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.InstantSerializer;
import com.superum.helper.utils.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.Instant;
import org.jooq.Record;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

import static com.superum.db.generated.timestar.Tables.ACCOUNT;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	// PUBLIC API

	@JsonProperty("id")
	public int getId() {
		return id;
	}
	
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}
	
	@JsonProperty("accountType")
	public String getAccountType() {
		return accountType == null ? null : accountType.name();
	}

	@JsonProperty("createdAt")
	@JsonSerialize(using = InstantSerializer.class)
    public Instant getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("updatedAt")
    @JsonSerialize(using = InstantSerializer.class)
    public Instant getUpdatedAt() {
        return updatedAt;
    }
	
	@JsonIgnore
	public String getPassword() {
		return StringUtils.toStr(password);
	}
	
	@JsonIgnore
	public void erasePassword() {
		StringUtils.erase(password);
		password = null;
	}
	
	// OBJECT OVERRIDES

	@Override
	public String toString() {
		return "Account" + StringUtils.toString(
				"Account ID: " + id,
				"Username: " + username,
				"Account Type: " + accountType,
				"Password: [PROTECTED]",
                "Created at: " + createdAt,
                "Updated at: " + updatedAt);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Account))
			return false;

		Account other = (Account) o;

		return this.id == other.id
				&& Objects.equals(this.username, other.username)
				&& Objects.equals(this.accountType, other.accountType)
				&& Arrays.equals(this.password, other.password);
	}

	@Override
	public int hashCode() {
        return Objects.hash(id, username, accountType, password);
	}

	// CONSTRUCTORS

	@JsonCreator
	public Account(@JsonProperty("id") int id,
				   @JsonProperty("username") String username,
				   @JsonProperty("accountType") String accountType,
				   @JsonProperty("password") char[] password) {
		this(id, username, accountType, password, null, null);
	}

    public Account(int id, String username, String accountType, char[] password, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.username = username;
        this.accountType = accountType == null ? null : AccountType.valueOf(accountType);
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
	
	public static Account valueOf(Record accountRecord) {
		if (accountRecord == null)
			return null;
		
		int id = accountRecord.getValue(ACCOUNT.ID);
		String username = accountRecord.getValue(ACCOUNT.USERNAME);
		String accountType = accountRecord.getValue(ACCOUNT.ACCOUNT_TYPE);
		char[] password = accountRecord.getValue(ACCOUNT.PASSWORD).toCharArray();

		long createdTimestamp = accountRecord.getValue(ACCOUNT.CREATED_AT);
		Instant createdAt = new Instant(createdTimestamp);
		long updatedTimestamp = accountRecord.getValue(ACCOUNT.UPDATED_AT);
		Instant updatedAt = new Instant(updatedTimestamp);
		return new Account(id, username, accountType, password, createdAt, updatedAt);
	}

    public static Builder builder() {
        return new Builder();
    }

    public static IdStep stepBuilder() {
        return new Builder();
    }

	// PRIVATE
	
	private final int id;
	
	@NotNull
	private final String username;
	
	private final AccountType accountType;
	
	@NotEmpty
	private char[] password;

    private final Instant createdAt;
    private final Instant updatedAt;

    // GENERATED

	public interface IdStep {
		UsernameStep id(int id);
	}

	public interface UsernameStep {
		AccountTypeStep username(String username);
	}

	public interface AccountTypeStep {
		PasswordStep accountType(String accountType);
	}

	public interface PasswordStep {
        BuildStep password(char... password);
	}

	public interface BuildStep {
        BuildStep createdAt(Instant createdAt);
        BuildStep createdAt(long createdAt);
		BuildStep updatedAt(Instant updatedAt);
        BuildStep updatedAt(long updatedAt);
		Account build();
	}

	public static class Builder implements IdStep, UsernameStep, AccountTypeStep, PasswordStep, BuildStep {
		private int id;
		private String username;
		private String accountType;
		private char[] password;

		private Instant createdAt;
		private Instant updatedAt;

		private Builder() {}

		@Override
		public Builder id(int id) {
			this.id = id;
			return this;
		}

		@Override
		public Builder username(String username) {
			this.username = username;
			return this;
		}

		@Override
		public Builder accountType(String accountType) {
			this.accountType = accountType;
			return this;
		}

		@Override
		public Builder password(char... password) {
			this.password = password;
			return this;
		}

		@Override
		public Builder createdAt(Instant createdAt) {
			this.createdAt = createdAt;
			return this;
		}

        @Override
        public Builder createdAt(long createdAt) {
            this.createdAt = new Instant(createdAt);
            return this;
        }

        @Override
		public Builder updatedAt(Instant updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

        @Override
        public Builder updatedAt(long updatedAt) {
            this.updatedAt = new Instant(updatedAt);
            return this;
        }

        @Override
		public Account build() {
			return new Account(id, username, accountType, password, createdAt, updatedAt);
		}
	}
}
