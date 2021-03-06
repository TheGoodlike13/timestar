package com.superum.api.v2.grouping;

import com.superum.api.v2.group.GroupNotFoundException;
import com.superum.api.v2.student.StudentNotFoundException;
import com.superum.exception.DatabaseException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Responsible for handling commands for grouping students into groups
 *
 * Uses a pseudo-CQRS model, where commands only mutate the state, but do not return any values, with the exception
 * of cases where a portion of the state is generated by the back end in a non deterministic way which is difficult
 * if not impossible for the front end to read immediately after;
 * Primary example - when the database generates a primary key number value using auto-increment;
 * Counter example - when the database generates a timestamp of create/update (unimportant metadata);
 * </pre>
 */
@Service
public interface ValidGroupingCommandService {

    /**
     * <pre>
     * Creates a grouping record
     *
     * partitionId separates different app partitions (please refer to the API file or PartitionController)
     * </pre>
     * @throws GroupNotFoundException if group with given id doesn't exist
     * @throws StudentNotFoundException if student for any given id doesn't exist
     * @throws DuplicateGroupingException if this group already has a grouping record
     * @throws DatabaseException if database error occurred
     * @throws DataAccessException if an unexpected database error occurred
     */
    void create(ValidGroupingDTO validGroupingDTO, int partitionId);

    /**
     * <pre>
     * Updates a grouping record
     *
     * partitionId separates different app partitions (please refer to the API file or PartitionController)
     * </pre>
     * @throws GroupNotFoundException if group with given id doesn't exist
     * @throws StudentNotFoundException if student for any given id doesn't exist
     * @throws GroupingNotFoundException if this group doesn't already have a grouping record
     * @throws DatabaseException if database error occurred
     * @throws DataAccessException if an unexpected database error occurred
     */
    void update(ValidGroupingDTO validGroupingDTO, int partitionId);

    /**
     * <pre>
     * Deletes all grouping records for a group with given id
     *
     * partitionId separates different app partitions (please refer to the API file or PartitionController)
     * </pre>
     * @throws GroupNotFoundException if group with given id doesn't exist
     * @throws GroupingNotFoundException if this group doesn't have a grouping record
     * @throws DatabaseException if database error occurred
     * @throws DataAccessException if an unexpected database error occurred
     */
    void deleteForGroup(int groupId, int partitionId);

    /**
     * <pre>
     * Deletes all grouping records for a student with given id
     *
     * partitionId separates different app partitions (please refer to the API file or PartitionController)
     * </pre>
     * @throws StudentNotFoundException if student with given id doesn't exist
     * @throws GroupingNotFoundException if this student doesn't have a grouping record
     * @throws DatabaseException if database error occurred
     * @throws DataAccessException if an unexpected database error occurred
     */
    void deleteForStudent(int studentId, int partitionId);

}
