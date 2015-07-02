package com.superum.db.lesson.table;

import java.sql.Date;

import org.springframework.stereotype.Repository;

import com.superum.db.lesson.table.core.PaymentData;
import com.superum.db.lesson.table.core.TeacherLessonData;

@Repository
public interface LessonTableQueries {

	TeacherLessonData readForTeacherAndCustomer(int teacherId, int customerId, Date start, Date end);
	
	PaymentData countPriceForTeacher(int teacherId);
	
	PaymentData countPriceForCustomer(int customerId);
	
}