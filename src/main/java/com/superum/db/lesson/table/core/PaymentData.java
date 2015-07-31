package com.superum.db.lesson.table.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.superum.utils.ObjectUtils;
import com.superum.utils.StringUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentData {

	// PUBLIC API

	@JsonProperty("paymentDate")
	public Date getPaymentDate() {
		return paymentDate;
	}
	
	@JsonProperty("cost")
	public BigDecimal getCost() {
		return cost;
	}
	
	// OBJECT OVERRIDES

	@Override
	public String toString() {
		return "PaymentData" + StringUtils.toString(
				"Payment date: " + paymentDate,
				"Cost: " + cost);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof PaymentData))
			return false;

		PaymentData other = (PaymentData) o;

		return Objects.equals(this.paymentDate, other.paymentDate)
				&& Objects.equals(this.cost, other.cost);
	}

	@Override
	public int hashCode() {
		return ObjectUtils.hash(paymentDate, cost);
	}

	// CONSTRUCTORS

	@JsonCreator
	public PaymentData(@JsonProperty("paymentDate") Date paymentDate,
					   @JsonProperty("cost") BigDecimal cost) {
		this.paymentDate = paymentDate;
		this.cost = cost;
	}

	// PRIVATE
	
	@NotNull
	private final Date paymentDate;
	
	@NotNull
	private final BigDecimal cost;

}
