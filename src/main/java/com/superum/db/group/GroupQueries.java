package com.superum.db.group;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface GroupQueries {

	List<Group> readAllForCustomer(int customerId, int partitionId);
	
	List<Group> readAllForCustomerAndTeacher(int customerId, int teacherId, int partitionId);
	
}
