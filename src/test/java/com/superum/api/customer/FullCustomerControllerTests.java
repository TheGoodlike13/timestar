package com.superum.api.customer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.superum.utils.FakeUtils;
import env.IntegrationTestEnvironment;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.superum.utils.FakeFieldUtils.fakeName;
import static com.superum.utils.FakeFieldUtils.fakePhone;
import static com.superum.utils.FakeUtils.makeFakeFullCustomer;
import static com.superum.utils.FakeUtils.makeSomeFakes;
import static com.superum.utils.JsonUtils.*;
import static com.superum.utils.MockMvcUtils.fromResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TransactionConfiguration(defaultRollback = true)
@Transactional
public class FullCustomerControllerTests extends IntegrationTestEnvironment {

    @Test
    public void insertingCustomerWithoutId_shouldCreateNewCustomer() throws Exception {
        ValidCustomerDTO customer = makeFakeFullCustomer(CUSTOMER_SEED).withoutId();

        MvcResult result = mockMvc.perform(put("/timestar/api/v2/customer/")
                    .contentType(APPLICATION_JSON_UTF8)
                    .header("Authorization", authHeader())
                    .content(convertObjectToJsonBytes(customer)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();

        ValidCustomerDTO returnedCustomer = fromResponse(result, ValidCustomerDTO.class);
        int customerId = returnedCustomer.getId();
        customer = customer.withId(customerId);

        assertEquals("The returned customer should be equal to original customer, except for id field; ", returnedCustomer, customer);

        ValidCustomerDTO customerFromDB = databaseHelper.readFullCustomerFromDb(customerId);

        assertEquals("The customer in the database should be equal to the returned customer; ", customerFromDB, returnedCustomer);
    }

    @Test
    public void readingCustomerWithValidId_shouldReturnACustomer() throws Exception {
        ValidCustomerDTO insertedCustomer = databaseHelper.insertFullCustomerIntoDb(makeFakeFullCustomer(CUSTOMER_SEED).withoutId());
        int customerId = insertedCustomer.getId();

        MvcResult result = mockMvc.perform(get("/timestar/api/v2/customer/{customerId}", customerId)
                    .contentType(APPLICATION_JSON_UTF8)
                    .header("Authorization", authHeader()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();

        ValidCustomerDTO returnedCustomer = fromResponse(result, ValidCustomerDTO.class);

        assertEquals("The read customer should be equal to original customer; ", returnedCustomer, insertedCustomer);

        ValidCustomerDTO customerFromDB = databaseHelper.readFullCustomerFromDb(customerId);

        assertEquals("The customer in the database should be equal to the returned customer; ", customerFromDB, returnedCustomer);
    }

    @Test
    public void updatingCustomerWithValidData_shouldReturnOldCustomer() throws Exception {
        ValidCustomerDTO insertedCustomer = databaseHelper.insertFullCustomerIntoDb(makeFakeFullCustomer(CUSTOMER_SEED).withoutId());
        int customerId = insertedCustomer.getId();

        ValidCustomerDTO updatedCustomer = makeFakeFullCustomer(CUSTOMER_SEED + 1).withId(customerId);

        mockMvc.perform(post("/timestar/api/v2/customer")
                .contentType(APPLICATION_JSON_UTF8)
                .header("Authorization", authHeader())
                .content(convertObjectToJsonBytes(updatedCustomer)))
                .andDo(print())
                .andExpect(status().isOk());

        ValidCustomerDTO customerFromDB = databaseHelper.readFullCustomerFromDb(customerId);

        assertEquals("The customer in the database should be equal to the updated customer; ", customerFromDB, updatedCustomer);
    }

    @Test
    public void updatingPartialCustomerWithValidData_shouldReturnOldCustomer() throws Exception {
        ValidCustomerDTO insertedCustomer = databaseHelper.insertFullCustomerIntoDb(makeFakeFullCustomer(CUSTOMER_SEED).withoutId());
        int customerId = insertedCustomer.getId();

        ValidCustomerDTO partialUpdatedCustomer = makeFakeFullCustomer(customerId, null,
                fakeName(CUSTOMER_SEED + 1), fakePhone(CUSTOMER_SEED + 1),
                null, null, null);

        mockMvc.perform(post("/timestar/api/v2/customer/")
                .contentType(APPLICATION_JSON_UTF8)
                .header("Authorization", authHeader())
                .content(convertObjectToJsonBytes(partialUpdatedCustomer)))
                .andDo(print())
                .andExpect(status().isOk());

        ValidCustomerDTO customerFromDB = databaseHelper.readFullCustomerFromDb(customerId);
        ValidCustomerDTO updatedCustomer = makeFakeFullCustomer(customerId, insertedCustomer.getStartDate(),
                partialUpdatedCustomer.getName(), partialUpdatedCustomer.getPhone(),
                insertedCustomer.getWebsite(), insertedCustomer.getPicture(), insertedCustomer.getComment());

        assertEquals("The customer in the database should be equal to the updated customer; ", customerFromDB, updatedCustomer);
    }

    @Test
    public void updatingCustomerWithInvalidId_shouldReturnBadReques() throws Exception {
        ValidCustomerDTO validCustomer = makeFakeFullCustomer(CUSTOMER_SEED);

        String json = convertObjectToString(validCustomer);
        String invalidJson = replace(json, "id", -1);

        mockMvc.perform(post("/timestar/api/v2/customer")
                .contentType(APPLICATION_JSON_UTF8)
                .header("Authorization", authHeader())
                .content(invalidJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deletingCustomerWithValidId_shouldReturnDeletedCustomer() throws Exception {
        ValidCustomerDTO insertedCustomer = databaseHelper.insertFullCustomerIntoDb(makeFakeFullCustomer(CUSTOMER_SEED).withoutId());
        int customerId = insertedCustomer.getId();

        mockMvc.perform(delete("/timestar/api/v2/customer/{customerId}", customerId)
                .contentType(APPLICATION_JSON_UTF8)
                .header("Authorization", authHeader()))
                .andDo(print())
                .andExpect(status().isOk());

        ValidCustomerDTO customerFromDB = databaseHelper.readFullCustomerFromDb(customerId);

        assertNull("The customer from the database should be equal null, since it was deleted; ", customerFromDB);
    }

    @Test
    public void readingCustomerForTeacherWithValidId_shouldReturnListOfCustomers() throws Exception {
        // THIS TEST NEEDS TO BE REWRITTEN DUE TO SCHEMA CHANGES
        /*
        ValidCustomerDTO insertedCustomer = databaseHelper.insertFullCustomerIntoDb(makeFakeFullCustomer(CUSTOMER_SEED).withoutId());
        int customerId = insertedCustomer.getId();
        List<ValidCustomerDTO> validCustomers = Collections.singletonList(insertedCustomer);

        Teacher insertedTeacher = databaseHelper.insertTeacherIntoDb(makeFakeTeacher(0));
        int teacherId = insertedTeacher.getId();

        Group insertedGroup = databaseHelper.insertGroupIntoDb(new Group(0, teacherId, "GroupZ"));
        int groupId = insertedGroup.getId();

        databaseHelper.insertStudentIntoDb(new Student(0, groupId, customerId, "djsdasuodhnsauo@DUOSAODusa.asdbiuasd", "blegh"));

        MvcResult result = mockMvc.perform(get("/timestar/api/v2/customer/for/teacher/{teacherId}", teacherId)
                .contentType(APPLICATION_JSON_UTF8)
                .header("Authorization", authHeader()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();

        List<ValidCustomerDTO> returnedCustomers = fromResponse(result, LIST_OF_CUSTOMERS);

        assertEquals("The read customers should be equal to original customers; ", returnedCustomers, validCustomers);

        ValidCustomerDTO customerFromDB = databaseHelper.readFullCustomerFromDb(customerId);
        List<ValidCustomerDTO> customersFromDb = Collections.singletonList(customerFromDB);

        assertEquals("The customers in the database should be equal to the read customers; ", customersFromDb, returnedCustomers);*/
    }

    @Test
    public void readingAllCustomers_shouldReturnListOfCustomers() throws Exception {
        List<ValidCustomerDTO> allCustomers = makeSomeFakes(2, FakeUtils::makeFakeFullCustomer).stream()
                .map(ValidCustomerDTO::withoutId)
                .map(databaseHelper::insertFullCustomerIntoDb)
                .collect(Collectors.toList());

        MvcResult result = mockMvc.perform(get("/timestar/api/v2/customer")
                .contentType(APPLICATION_JSON_UTF8)
                .header("Authorization", authHeader()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();

        List<ValidCustomerDTO> returnedCustomers = fromResponse(result, LIST_OF_CUSTOMERS);

        assertEquals("The read customers should be equal to original customers; ", returnedCustomers, allCustomers);

        List<ValidCustomerDTO> customersFromDb = allCustomers.stream()
                .mapToInt(ValidCustomerDTO::getId)
                .mapToObj(databaseHelper::readFullCustomerFromDb)
                .collect(Collectors.toList());

        assertEquals("The customers in the database should be equal to the read customers; ", customersFromDb, returnedCustomers);
    }

    @Test
    public void countingCustomersForTeacherWithValidId_shouldReturnCount() throws Exception {
        // THIS TEST NEEDS TO BE REWRITTEN DUE TO SCHEMA CHANGES
        /*
        ValidCustomerDTO insertedCustomer = databaseHelper.insertFullCustomerIntoDb(makeFakeFullCustomer(CUSTOMER_SEED).withoutId());
        int customerId = insertedCustomer.getId();
        List<ValidCustomerDTO> validCustomers = Collections.singletonList(insertedCustomer);

        Teacher insertedTeacher = databaseHelper.insertTeacherIntoDb(defaultTeacher());
        int teacherId = insertedTeacher.getId();

        Group insertedGroup = databaseHelper.insertGroupIntoDb(new Group(0, teacherId, "GroupZ"));
        int groupId = insertedGroup.getId();

        databaseHelper.insertStudentIntoDb(new Student(0, groupId, customerId, "djsdasuodhnsauo@DUOSAODusa.asdbiuasd", "blegh"));

        MvcResult result = mockMvc.perform(get("/timestar/api/v2/customer/for/teacher/{teacherId}/count", teacherId)
                .contentType(APPLICATION_JSON_UTF8)
                .header("Authorization", authHeader()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();

        int returnedCount = fromResponse(result, Integer.class);

        assertEquals("The amount of read customers should be equal to original amount of customers; ", returnedCount, validCustomers.size());

        ValidCustomerDTO customerFromDB = databaseHelper.readFullCustomerFromDb(customerId);
        List<ValidCustomerDTO> customersFromDb = Collections.singletonList(customerFromDB);

        assertEquals("The amount of customers from the database should be equal to the amount of read customers; ", customersFromDb.size(), returnedCount);*/
    }

    @Test
    public void countingAllCustomers_shouldReturnCount() throws Exception {
        List<ValidCustomerDTO> allCustomers = makeSomeFakes(2, FakeUtils::makeFakeFullCustomer).stream()
                .map(ValidCustomerDTO::withoutId)
                .map(databaseHelper::insertFullCustomerIntoDb)
                .collect(Collectors.toList());

        MvcResult result = mockMvc.perform(get("/timestar/api/v2/customer/count")
                .contentType(APPLICATION_JSON_UTF8)
                .header("Authorization", authHeader()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();

        int returnedCount = fromResponse(result, Integer.class);

        assertEquals("The amount of read customers should be equal to original amount of customers; ", returnedCount, allCustomers.size());

        long amountOfCustomersInDb = allCustomers.stream()
                .mapToInt(ValidCustomerDTO::getId)
                .mapToObj(databaseHelper::readFullCustomerFromDb)
                .filter(customer -> customer != null)
                .count();

        assertEquals("The amount of customers in the database should be equal to the amount of read customers; ", amountOfCustomersInDb, returnedCount);
    }

    // PRIVATE

    private static final TypeReference<List<ValidCustomerDTO>> LIST_OF_CUSTOMERS = new TypeReference<List<ValidCustomerDTO>>() {};
    private static final int CUSTOMER_SEED = 1;

}
