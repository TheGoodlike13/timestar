package com.superum.config;

import com.superum.helper.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import timestar_v2.tables.records.*;

import static timestar_v2.Tables.*;

@Configuration
@Lazy
public class DefaultSqlConfig {

    @Autowired
    PersistenceContext persistenceContext;

    // COMMANDS

    @Bean
    public DefaultCommands<TeacherRecord, Integer> defaultTeacherCommands() {
        return new DefaultCommandsImpl<>(persistenceContext.dsl(), TEACHER, TEACHER.ID, TEACHER.PARTITION_ID);
    }

    @Bean
    public DefaultCommands<CustomerRecord, Integer> defaultCustomerCommands() {
        return new DefaultCommandsImpl<>(persistenceContext.dsl(), CUSTOMER, CUSTOMER.ID, CUSTOMER.PARTITION_ID);
    }

    @Bean
    public DefaultCommands<GroupOfStudentsRecord, Integer> defaultGroupCommands() {
        return new DefaultCommandsImpl<>(persistenceContext.dsl(), GROUP_OF_STUDENTS, GROUP_OF_STUDENTS.ID, GROUP_OF_STUDENTS.PARTITION_ID);
    }

    @Bean
    public DefaultCommands<LessonRecord, Long> defaultLessonCommands() {
        return new DefaultCommandsImpl<>(persistenceContext.dsl(), LESSON, LESSON.ID, LESSON.PARTITION_ID);
    }

    @Bean
    public DefaultCommands<StudentRecord, Integer> defaultStudentCommands() {
        return new DefaultCommandsImpl<>(persistenceContext.dsl(), STUDENT, STUDENT.ID, STUDENT.PARTITION_ID);
    }

    // QUERIES

    @Bean
    public DefaultQueries<TeacherRecord, Integer> defaultTeacherQueries() {
        return new DefaultQueriesImpl<>(persistenceContext.dsl(), TEACHER, TEACHER.ID, TEACHER.PARTITION_ID);
    }

    @Bean
    public DefaultQueries<CustomerRecord, Integer> defaultCustomerQueries() {
        return new DefaultQueriesImpl<>(persistenceContext.dsl(), CUSTOMER, CUSTOMER.ID, CUSTOMER.PARTITION_ID);
    }

    @Bean
    public DefaultQueries<GroupOfStudentsRecord, Integer> defaultGroupQueries() {
        return new DefaultQueriesImpl<>(persistenceContext.dsl(), GROUP_OF_STUDENTS, GROUP_OF_STUDENTS.ID, GROUP_OF_STUDENTS.PARTITION_ID);
    }

    @Bean
    public DefaultQueries<LessonRecord, Long> defaultLessonQueries() {
        return new DefaultQueriesImpl<>(persistenceContext.dsl(), LESSON, LESSON.ID, LESSON.PARTITION_ID);
    }

    @Bean
    public DefaultQueries<StudentRecord, Integer> defaultStudentQueries() {
        return new DefaultQueriesImpl<>(persistenceContext.dsl(), STUDENT, STUDENT.ID, STUDENT.PARTITION_ID);
    }

    // FOREIGN

    @Bean
    public ForeignQueries<Integer> foreignTeacherQueries() {
        //noinspection Convert2Diamond
        return new ForeignQueriesImpl<Integer>(persistenceContext.dsl(), GROUP_OF_STUDENTS.TEACHER_ID, LESSON.TEACHER_ID);
    }

    @Bean
    public ForeignQueries<Integer> foreignCustomerQueries() {
        //noinspection Convert2Diamond
        return new ForeignQueriesImpl<Integer>(persistenceContext.dsl(), STUDENT.CUSTOMER_ID, GROUP_OF_STUDENTS.CUSTOMER_ID);
    }

    @Bean
    public ForeignQueries<Integer> foreignGroupQueries() {
        //noinspection Convert2Diamond
        return new ForeignQueriesImpl<Integer>(persistenceContext.dsl(), STUDENTS_IN_GROUPS.GROUP_ID, LESSON.GROUP_ID);
    }

    @Bean
    public ForeignQueries<Long> foreignLessonQueries() {
        //noinspection Convert2Diamond
        return new ForeignQueriesImpl<Long>(persistenceContext.dsl(), LESSON_ATTENDANCE.LESSON_ID);
    }

    @Bean
    public ForeignQueries<Integer> foreignStudentQueries() {
        //noinspection Convert2Diamond
        return new ForeignQueriesImpl<Integer>(persistenceContext.dsl(), LESSON_ATTENDANCE.STUDENT_ID, STUDENTS_IN_GROUPS.STUDENT_ID);
    }

    // MANY

    @Bean
    public CommandsForMany<Long, Integer> lessonAttendanceCommands() {
        return new CommandsForManyImpl<>(persistenceContext.dsl(), LESSON_ATTENDANCE,
                LESSON_ATTENDANCE.LESSON_ID, LESSON_ATTENDANCE.STUDENT_ID, LESSON_ATTENDANCE.PARTITION_ID);
    }

    @Bean
    public CommandsForMany<Integer, Integer> groupingCommands() {
        return new CommandsForManyImpl<>(persistenceContext.dsl(), STUDENTS_IN_GROUPS,
                STUDENTS_IN_GROUPS.GROUP_ID, STUDENTS_IN_GROUPS.STUDENT_ID, STUDENTS_IN_GROUPS.PARTITION_ID);
    }

    // QUERIES MANY

    @Bean
    public QueriesForMany<Long, Integer> defaultLessonAttendanceQueries() {
        return new QueriesForManyImpl<>(persistenceContext.dsl(), LESSON_ATTENDANCE,
                LESSON_ATTENDANCE.LESSON_ID, LESSON_ATTENDANCE.STUDENT_ID, LESSON_ATTENDANCE.PARTITION_ID);
    }

    @Bean
    public QueriesForMany<Integer, Integer> defaultGroupingQueries() {
        return new QueriesForManyImpl<>(persistenceContext.dsl(), STUDENTS_IN_GROUPS,
                STUDENTS_IN_GROUPS.GROUP_ID, STUDENTS_IN_GROUPS.STUDENT_ID, STUDENTS_IN_GROUPS.PARTITION_ID);
    }

}
