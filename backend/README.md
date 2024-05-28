# Project Title

## Setup Instructions

We need to add some rows into the roles table before assigning any role to User.
Run the following SQL insert statements:

```sql
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');



## Creating an Admin Account

To create an admin account in your project, you can follow these steps, assuming you have a RESTful API for user registration similar to the one shown in the image. The process involves sending a POST request to the `/api/auth/signup` endpoint with the required user details, including the admin role.

**Endpoint URL:**

```bash
POST http://localhost:8080/api/auth/signup


Headers:

Ensure the following headers are included in the request:

Content-Type: application/json
Any necessary authorization headers if required

Request Body:

Construct the JSON payload to include the admin role. Here’s an example of how the request body should look:

{
    "username": "admin",
    "email": "admin@example.com",
    "password": "adminpassword",
    "role": ["admin"]

}

Sending the Request:

Use a tool like Postman or any HTTP client to send the POST request with the above details.

```
