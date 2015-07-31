package com.superum.db.lesson.table.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.superum.utils.ObjectUtils;
import com.superum.utils.StringUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TotalLessonData {

	// PUBLIC API

	@JsonProperty("count")
	public int getCount() {
		return count;
	}
	
	@JsonProperty("duration")
	public int getDuration() {
		return duration;
	}
	
	@JsonProperty("cost")
	public BigDecimal getCost() {
		return cost;
	}
	
	// OBJECT OVERRIDES

	@Override
	public String toString() {
		return "TotalLessonData" + StringUtils.toString(
				"Count: " + count,
				"Duration: " + duration,
				"Cost: " + cost);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof TotalLessonData))
			return false;

		TotalLessonData other = (TotalLessonData) o;

		return this.count == other.count
				&& this.duration == other.duration
				&& Objects.equals(this.cost, other.cost);
	}

	@Override
	public int hashCode() {
        return ObjectUtils.hash(count, duration, cost);
	}

	// CONSTRUCTORS

	@JsonCreator
	public TotalLessonData(@JsonProperty("count") int count,
						   @JsonProperty("duration") int duration,
						   @JsonProperty("cost") BigDecimal cost) {
		this.count = count;
		this.duration = duration;
		this.cost = cost;
	}

	// PRIVATE
	
	private final int count;
	private final int duration;
	
	@NotNull
	private final BigDecimal cost;

}
