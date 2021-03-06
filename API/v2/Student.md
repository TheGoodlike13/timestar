# Student API

[Back to APIv2](./APIv2.md#api-v2)

## Relevant classes

[ValidStudentDTO](../../src/main/java/com/superum/api/v2/student/ValidStudentDTO.java)

### Commands

#### Create
```
    POST  /student
    BODY  ValidStudentDTO
    RET   ValidStudentDTO
```

Creates a new student;
also generates a student code which can be loosely used to identify a student (the codes are not unique)

It will fail if:
  * HTTP 400; the id field was set;
  * HTTP 400; a mandatory field was not set;
  * HTTP 400; both or neither of customerId and startDate were set;
  * HTTP 404; no customer with provided id exists;

Returned student will have its id field set

------

#### Update
```
    PUT   /student
    BODY  ValidStudentDTO
    RET   void
```

Updates a student

It will fail if:
  * HTTP 400; the id field was not set;
  * HTTP 400; only the id field was set and no other fields were;
  * HTTP 400; both of customerId and startDate were set;
  * HTTP 404; no student with provided id exists;
  * HTTP 404; no customer with provided id exists;

Returns HTTP 200 OK if it succeeds

------

#### Delete
```
    DELETE  /student/{studentId}
            studentId        int            1 <= studentId <= MAX_INT
    RET     void
```

Deletes a student

It will fail if:
  * HTTP 400; student cannot be deleted because it is still used;
  * HTTP 404; no student with provided id exists;

Returns HTTP 200 OK if it succeeds

### Queries

#### Read
```
    GET  /student/{studentId}
         studentId        int            1 <= studentId <= MAX_INT
    RET  ValidStudentDTO
```

Reads and returns a student by id

It will fail if:
  * HTTP 404; no student with provided id exists;

------

#### Read all
```
    GET  /student
    OPT  page           int            1 <= page <= MAX_INT; DEF 1
    OPT  per_page       int            1 <= per_page <= 100; DEF 25
    RET  List<ValidStudentDTO>
```

Reads and returns a list of all students

It shouldn't fail under normal circumstances

Returned List is paged; using DEF parameter values, only first 25 students will be returned; to access the rest,
the page parameter must be incremented, or per_page value raised

------

#### Read for table
```
    GET  /student/{tableName}/{id}
         tableName      String         group, lesson or customer
         id             int            1 <= id <= MAX_INT
    OPT  page           int            1 <= page <= MAX_INT; DEF 1
    OPT  per_page       int            1 <= per_page <= 100; DEF 25
    RET  List<ValidStudentDTO>
```

Reads and returns a list of students for group, lesson or customer

It will fail if:
  * HTTP 404; no group, lesson or customer with provided id exists;

Returned List is paged; using DEF parameter values, only first 25 students will be returned; to access the rest,
the page parameter must be incremented, or per_page value raised
