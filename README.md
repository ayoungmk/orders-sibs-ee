# Order Management System Sibs

## Description

This is an order management system developed in JavaEE. The purpose of this system is to manage customer orders, items, and their respective completion statuses.

## Features

- **User Registration:** Allows adding, editing, and removing users.
- **Item Registration:** Allows adding, editing, and removing items.
- **Order Management:** Creation, updating, and cancellation of orders.
- **Stock Management:** Creation, updating, and deletion of items in stock.
- **Reports:** Generation of reports on completed orders, stock movements, sent emails, and errors.

## Project Setup

### Prerequisites

- Java 8
- Maven 3.6.3 or higher
- PostgreSQL
- Docker

### Step-by-Step

1. **Clone the repository:**
   ```sh
   git clone https://github.com/ayoungmk/orders-sibs-ee.git
   cd orders-sibs-ee
   ```

2. **Java EE Configuration**

#### Set up Wildfly

1. Download Wildfly at http://wildfly.org/downloads/Sample. This samples use version 26.1.3.Final.
2. Unpack the Wildfly archive.
3. Set the `WILDFLY_HOME` environment variable to point to the Wildfly installation directory.
4. Start the Wildfly

#### Set up PostgreSQL

##### Option 1
  Docker:
   ```sh
   docker compose up
   ```
##### Option 2
1. Download PostgreSQL at https://www.postgresql.org/download/
2. Connect to PostgreSQL, i.e. using psql or pgAdmin.
3. Execute the following SQL commands:
```sql
DROP DATABASE IF EXISTS order;
CREATE DATABASE order;
DROP USER IF EXISTS admin;
CREATE USER admin WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE order to admin;
```
4. The project contains a Data.sql file that initially populates some information in the system's entities.
   ```sql
   INSERT INTO TB_ITEM (name) VALUES ('arroz');
   INSERT INTO TB_ITEM (name) VALUES ('feijão');
   INSERT INTO TB_ITEM (name) VALUES ('batata');
   INSERT INTO TB_ITEM (name) VALUES ('macarrão');
   INSERT INTO TB_USER (name, email) VALUES ('adrian', 'adriankimura@gmail.com');
   INSERT INTO TB_USER (name, email) VALUES ('barbara', 'adriankimura@gmail.com');
   INSERT INTO TB_USER (name, email) VALUES ('melissa', 'adriankimura@gmail.com');
   INSERT INTO TB_USER (name, email) VALUES ('jorge', 'adriankimura@gmail.com');
   INSERT INTO TB_STOCK (item_id, quantity) VALUES ((SELECT ID FROM TB_ITEM WHERE NAME = 'arroz'), 50);
   INSERT INTO TB_STOCK (item_id, quantity) VALUES ((SELECT ID FROM TB_ITEM WHERE NAME = 'feijão'), 50);
   INSERT INTO TB_STOCK (item_id, quantity) VALUES ((SELECT ID FROM TB_ITEM WHERE NAME = 'batata'), 50);
   INSERT INTO TB_STOCK (item_id, quantity) VALUES ((SELECT ID FROM TB_ITEM WHERE NAME = 'macarrão'), 50);
   INSERT INTO TB_STOCK_MOVEMENT (creation_Date, quantity, item_id) VALUES ('2008-11-12 13:23:44', 6, (SELECT ID FROM TB_ITEM WHERE NAME = 'arroz'));
   INSERT INTO TB_STOCK_MOVEMENT (creation_Date, quantity, item_id) VALUES ('2008-11-13 13:23:44', 5, (SELECT ID FROM TB_ITEM WHERE NAME = 'feijão'));
   INSERT INTO TB_STOCK_MOVEMENT (creation_Date, quantity, item_id) VALUES ('2008-11-14 13:23:44', 9, (SELECT ID FROM TB_ITEM WHERE NAME = 'batata'));
   INSERT INTO TB_STOCK_MOVEMENT (creation_Date, quantity, item_id) VALUES ('2008-11-15 13:23:44', 3, (SELECT ID FROM TB_ITEM WHERE NAME = 'macarrão'));
   INSERT INTO TB_ORDER (creation_Date, quantity, item_id, user_id, status) VALUES ('2008-11-12 13:23:44', 6, (SELECT ID FROM TB_ITEM WHERE NAME = 'arroz'), 1, 'INCOMPLETE');
   INSERT INTO TB_ORDER (creation_Date, quantity, item_id, user_id, status) VALUES ('2008-11-14 13:23:44', 5, (SELECT ID FROM TB_ITEM WHERE NAME = 'feijão'), 2, 'INCOMPLETE');
   INSERT INTO TB_ORDER (creation_Date, quantity, item_id, user_id, status) VALUES ('2008-11-15 13:23:44', 9, (SELECT ID FROM TB_ITEM WHERE NAME = 'batata'), 4, 'INCOMPLETE');
   INSERT INTO TB_ORDER (creation_Date, quantity, item_id, user_id, status) VALUES ('2008-11-16 13:23:44', 3, (SELECT ID FROM TB_ITEM WHERE NAME = 'macarrão'), 3, 'INCOMPLETE');
```
#### Register PostgreSQL as datasource in Wildfly For Windows

Using command shell navigate to current folder.

Execute following commands:
```dos
start %WILDFLY_HOME%\bin\standalone.bat
%WILDFLY_HOME%\bin\jboss-cli.bat --connect
```

These commands should be executed in jboss-cli
```
module add --name=org.postgres --resources=.\lib\postgresql-9.4.1211.jar --dependencies=javax.api,javax.transaction.api
/subsystem=datasources/jdbc-driver=postgres:add(driver-name="postgres",driver-module-name="org.postgres",driver-class-name=org.postgresql.Driver)
data-source add --name=JavaEEDS --jndi-name=java:jboss/datasources/JavaEEDS --driver-name=postgres --connection-url=jdbc:postgresql://localhost:5432/order --user-name=admin --password=1234
```

#### Run application

1. Launch Postgres

For Windows start the Service with name `PostgreSQL 9.6 Server` or similar for other PostgreSQL versions.

2. Launch Wildfly

For Windows execute following command:
```dos
start %WILDFLY_HOME%\bin\standalone.bat
```

For Unix-based execute following command:
```shell
%WILDFLY_HOME%/bin/standalone.sh &
```

3. Run the application::
```shell
mvn clean install
mvn wildfly:deploy
```

## Entity Relationships

- **User:**
  - **ID:** Unique identifier for the user.
  - **Name:** User's name.
  - **Email:** User's email.

- **Item:**
  - **ID:** Unique identifier for the item.
  - **Name:** Item name.

- **Order:**
  - **ID:** Unique identifier for the order.
  - **Creation Date:** Order creation date.
  - **Item:** ID of the requested item.
  - **Quantity:** Total quantity of items in the order.
  - **User:** ID of the user who placed the order.
  - **Status:** Order status (e.g., Complete, Incomplete).

- **Stock:**
  - **ID:** Unique identifier for the stock.
  - **Item:** ID of the item in stock.
  - **Quantity:** Total quantity of items currently in stock.

- **Stock Movement:**
  - **ID:** Unique identifier for the movement.
  - **Creation Date:** Movement creation date.
  - **Item:** ID of the item associated with the movement.
  - **Quantity:** Total quantity of items associated with the movement.

## Business Rules

1. **Item Registration:**
   - The item name cannot be blank.
   - The item name must be at least 2 characters long.

2. **Order Management:**
   - An order can only be completed if there are products available in stock.
   - The system automatically checks the stock every time a stock movement or an order is created to complete incomplete orders.
   - After this initial check, the stock quantity must be updated.

3. **Stock Movement Management:**
   - When a stock movement is created, the system checks if the quantity is sufficient to complete incomplete orders.

4. **Reports:**
   - The report generated via Logger shows orders when they are complete, stock movements performed, sent emails, and errors.

## Route Instructions
Root: /orderSibs/v1

### Users

- **GET /users:** Returns a list of all users.
- **GET /users/{id}:** Returns details of a specific user.
- **POST /users:** Creates a new user.
  - Body (JSON):
    ```json
    {
      "name": "User Name",
      "email": "user@email.com"
    }
    ```
- **PUT /users/{id}:** Updates details of a specific user.
  - Body (JSON):
    ```json
    {
      "name": "User Name",
      "email": "user@email.com"
    }
    ```
- **DELETE /users/{id}:** Removes a specific user.

### Items

- **GET /items:** Returns a list of all items.
- **GET /items/{id}:** Returns details of a specific item.
- **POST /items:** Creates a new item.
  - Body (JSON):
    ```json
    {
      "name": "Item Name"
    }
    ```
- **PUT /items/{id}:** Updates details of a specific item.
  - Body (JSON):
    ```json
    {
      "name": "Item Name"
    }
    ```
- **DELETE /items/{id}:** Removes a specific item.

### Orders

- **GET /orders:** Returns a list of all orders.
- **GET /orders/{id}:** Returns details of a specific order.
- **POST /orders:** Creates a new order.
  - Body (JSON):
    ```json
    {
      "itemName": "Item Name",
      "quantity": "Item Quantity",
      "userName": "User Name"
    }
    ```
- **PUT /orders/{id}:** Updates details of a specific order.
  - Body (JSON):
    ```json
    {
      "itemName": "Item Name",
      "quantity": "Item Quantity",
      "userName": "User Name"
    }
    ```
- **DELETE /orders/{id}:** Removes a specific order.

### Stock

- **GET /stock:** Returns a list of all stock items.
- **GET /stock/{id}:** Returns details of a specific stock item.
- **POST /stock:** Creates a new stock item.
  - Body (JSON):
    ```json
    {
      "item": {
        "id": "Item ID",
        "name": "Item Name"
      },
      "quantity": "Item Quantity"
    }
    ```
- **PUT /stock/{id}:** Updates the quantity of a specific stock item.
  - Body (JSON):
    ```json
    {
      "quantity": "Item Quantity"
    }
    ```
- **DELETE /stock/{id}:** Removes a specific stock item.

### Stock Movements

- **GET /stockMovements:** Returns a list of all stock movements.
- **GET /stockMovements/{id}:** Returns details of a specific stock movement.
- **POST /stockMovements:** Creates a new stock movement.
  - Body (JSON):
    ```json
    {
      "creationDate": "Creation Timestamp",
      "itemName": "Item Name",
      "quantity": "Item Quantity"
    }
    ```
- **PUT /stockMovements/{id}:** Updates details of a specific stock movement.
  - Body (JSON):
    ```json
    {
      "creationDate": "Creation Timestamp",
      "itemName": "Item Name",
      "quantity": "Item Quantity"
    }
    ```
- **DELETE /stockMovements/{id}:** Removes a specific stock movement.

---
