# Customer API

This application is used to add/update/delete/view customer details There is view option to view entire customer or individual customer based on customer ID;

Please refer attached postman collection having web service which has POST/GET/PUT/DELETE API calls along with JWT token generation API.
POST (http://host:port/api/customers)
Save customer Details
REQUEST:
{
    "id": "CUST1",
    "name": "test",
    "email": "test@test.com",
    "phone": "987654321"
}
PUT (http://host:port/api/customers)
Update customer Details
REQUEST:
{
    "id": "CUST1",
    "name": "test",
    "email": "test@test.com",
    "phone": "987654321"
}

DELETE: (http://host:port/api/customers/{customerId})
Delete customer based on customer ID

GET: (http://host:port/api/customers/{customerId})
Retrieve customer based on customer ID

GET: (http://host:port/api/customers/{customerId})
Retrieve All customers.

Generate JWT Token
http://host:port/authenticate
REQUEST:
{
    "username": "admin",
    "password": "admin"
}

ADMIN/USER role based authentication is provided in rest controllers.

Possible error status code:
1. 200 - Successful response
2. 201 - customer created successfully.
3. 204 - Customer details not found
4. 400 - Invalid customer request.
5. 401 - Invalid JWT token
6. 403 - Forbidden due to access privilege.
7. 500 - unexpected error.

Swagger configured using spring boot open API.
Swagger UI
http://localhost:8787/api/swagger-ui.html
Swagger Docs:
http://localhost:8787/api/v3/api-docs

While converting spring boot to reactive web flux, there were additional code changes. The spring boot version of the code is commented where ever present to avoid dependency injection.

Enhancements that can be done:
1. Exception handling when customer is not found/customer already present in DB using global controller advice  exception handler.
2. Addition of more junit test cases for code coverage.
3. In-memory data base is used. Can be migrated to SQL data source.
4. Addition of logic to refresh/logout of the JWT token.
5. AWS deployment
6. Data synchronization.



