package com.superum.api.v1.partition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;
import org.jooq.Record;

import java.util.Objects;

import static timestar_v2.Tables.PARTITIONS;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Partition {

	// PUBLIC API
	
	@JsonProperty("id") 
	public int getId() {
		return id;
	}
	
	@JsonProperty("name") 
	public String getName() {
		return name;
	}

	// CONSTRUCTORS

	@JsonCreator
	public Partition(@JsonProperty("id") int id, 
					 @JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
	}
	
	public static Partition valueOf(Record partitionRecord) {
		if (partitionRecord == null)
			return null;
		
		int id = partitionRecord.getValue(PARTITIONS.ID);
		String name = partitionRecord.getValue(PARTITIONS.NAME);
		return new Partition(id, name);
	}

	// PRIVATE
	
	private final int id;
	
	@NotEmpty
	private final String name;

	// OBJECT OVERRIDES

	@Override
	public String toString() {
		return MoreObjects.toStringHelper("Partition")
				.add("Partition id", id)
				.add("Name", name)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Partition)) return false;
		Partition partition = (Partition) o;
		return Objects.equals(id, partition.id) &&
				Objects.equals(name, partition.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

}
