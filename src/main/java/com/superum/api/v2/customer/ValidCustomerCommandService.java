package com.superum.api.v2.customer;

import com.superum.exception.DatabaseException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Responsible for handling customer commands
 *
 * Uses a pseudo-CQRS model, where commands only mutate the state, but do not return any values, with the exception
 * of cases where a portion of the state is generated by the back end in a non deterministic way which is difficult
 * if not impossible for the front end to read immediately after;
 * Primary example - when the database generates a primary key number value using auto-increment;
 * Counter example - when the database generates a timestamp of create/update (unimportant metadata);
 * </pre>
 */
@Service
public interface ValidCustomerCommandService {

    /**
     * <pre>
     * Creates a new customer
     *
     * The id field must not be set, but all the other mandatory fields must be set
     *
     * partitionId separates different app partitions (please refer to the API file or PartitionController)
     * </pre>
     * @return the created customer with id field now set
     *
     * @throws InvalidCustomerException if id field was set or a mandatory field was not set
     * @throws DatabaseException if database error occurred
     * @throws DataAccessException if an unexpected database error occurred
     */
    ValidCustomerDTO create(ValidCustomerDTO validCustomerDTO, int partitionId);

    /**
     * <pre>
     * Updates an existing customer
     *
     * The id field must be set, and at least one more field must be set
     *
     * partitionId separates different app partitions (please refer to the API file or PartitionController)
     * </pre>
     * @throws InvalidCustomerException if id field was not set, or no other fields were set
     * @throws CustomerNotFoundException if a customer with specified id does not exist
     * @throws DatabaseException if database error occurred
     * @throws DataAccessException if an unexpected database error occurred
     */
    void update(ValidCustomerDTO validCustomerDTO, int partitionId);

    /**
     * <pre>
     * Deletes a customer with specified id
     *
     * partitionId separates different app partitions (please refer to the API file or PartitionController)
     * </pre>
     * @throws CustomerNotFoundException if no customer with this id exists
     * @throws UnsafeCustomerDeleteException if this customer cannot be deleted due to being used in other objects
     * @throws DatabaseException if database error occurred
     * @throws DataAccessException if an unexpected database error occurred
     */
    void delete(int customerId, int partitionId);

}