# Group API

[Back to APIv2](./APIv2.md#api-v2)

## Relevant classes

[ValidGroupDTO](../../src/main/java/com/superum/api/v2/group/ValidGroupDTO.java)

### Commands

#### Create
```
    POST  /group
    BODY  ValidGroupDTO
    RET   ValidGroupDTO
```

Creates a new group

It will fail if:
  * HTTP 400; the id field was set;
  * HTTP 400; a mandatory field was not set;
  * HTTP 404; no customer with provided id exists;
  * HTTP 404; no teacher with provided id exists;

Returned group will have its id field set

------

#### Update
```
    PUT   /group
    BODY  ValidGroupDTO
    RET   void
```

Updates a group

It will fail if:
  * HTTP 400; the id field was not set;
  * HTTP 400; only the id field was set and no other fields were;
  * HTTP 404; no group with provided id exists;
  * HTTP 404; no customer with provided id exists;
  * HTTP 404; no teacher with provided id exists;

Returns HTTP 200 OK if it succeeds

------

#### Delete
```
    DELETE  /group/{groupId}
            groupId        int            1 <= groupId <= MAX_INT
    RET     void
```

Deletes a group

It will fail if:
  * HTTP 400; group cannot be deleted because it is still used;
  * HTTP 404; no group with provided id exists;

Returns HTTP 200 OK if it succeeds

### Queries

#### Read
```
    GET  /group/{groupId}
         groupId        int            1 <= groupId <= MAX_INT
    RET  ValidGroupDTO
```

Reads and returns a group by id

It will fail if:
  * HTTP 404; no group with provided id exists;

------

#### Read all
```
    GET  /group
    OPT  page           int            1 <= page <= MAX_INT; DEF 1
    OPT  per_page       int            1 <= per_page <= 100; DEF 25
    RET  List<ValidGroupDTO>
```

Reads and returns a list of all groups

It shouldn't fail under normal circumstances

Returned List is paged; using DEF parameter values, only first 25 groups will be returned; to access the rest,
the page parameter must be incremented, or per_page value raised

------

#### Read for table
```
    GET  /group/{tableName}/{id}
         tableName      String         teacher, customer or student
         id             int            1 <= id <= MAX_INT
    OPT  page           int            1 <= page <= MAX_INT; DEF 1
    OPT  per_page       int            1 <= per_page <= 100; DEF 25
    RET  List<ValidGroupDTO>
```

Reads and returns a list of groups for teacher, customer or student

It will fail if:
  * HTTP 404; no teacher, customer or student with provided id exists;

Returned List is paged; using DEF parameter values, only first 25 groups will be returned; to access the rest,
the page parameter must be incremented, or per_page value raised

------

#### Read for no customer
```
    GET  /group/customer/none
    OPT  page           int            1 <= page <= MAX_INT; DEF 1
    OPT  per_page       int            1 <= per_page <= 100; DEF 25
    RET  List<ValidGroupDTO>
```

Reads and returns a list of groups that have no customer

It shouldn't fail under normal circumstances

Returned List is paged; using DEF parameter values, only first 25 groups will be returned; to access the rest,
the page parameter must be incremented, or per_page value raised
