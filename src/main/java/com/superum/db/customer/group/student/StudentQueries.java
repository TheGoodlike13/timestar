package com.superum.db.customer.group.student;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface StudentQueries {

	List<Student> readAllForCustomer(int customerId);
	
	List<Student> readAllForLesson(long lessonId);
	
}
