# ATM Machine Spring Boot API

Build Restful API for ATM Machine using Spring Boot, H2, JPA and Hibernate.

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/rgog/ATMMachine
```
**2. Build the JAR file**

```bash
mvn clean install
```
**3. Run the App**
You can change the profile from prod to dev in the following command:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

The app will start running at <http://localhost:8080>

## Explore Rest APIs

The app defines following APIs.`

### Auth

| Method | Url | Decription | Sample Valid Request Body | Sample Response |
| ------ | --- | ---------- | --------------------------- | --- |
| POST   | /login | Login | [JSON](#login) | [JSON](#loginresponse) |

### Account Management

| Method | Url | Decription | Sample Valid Request Body |  Request Headers |
| ------ | --- | ---------- | --------------------------- | --- | 
| POST   | /accountmgmt/account | Get getails for account | [JSON](#account) | authToken: Received in login response |
| POST   | /accountmgmt/withdraw | Withdraw amount | [JSON](#withdraw) | authToken: Received in login response |

### Admin

These APIs only work for the "dev" profile

| Method | Url | Description | 
| ------ | --- | ----------- | 
| GET    | /admin/bills | Get all the available bills and their count in the ATM Machine |
| GET    | /admin/balance | Get the total balance in the ATM |
| GET    | /admin/authlogs | Get all Login Tokens in the database |
| GET    | /admin/accounts | Get all the account information for all the accounts |


Test them using postman or any other rest client.

## Sample Valid JSON Request/Response Bodys

##### <a id="login">login -> /login</a>
```json
{
	"accountNumber": "Enter account number",
	"pin": "Enter pin"
}
```

##### <a id="loginresponse"> Login Response -> /login</a>
```json
{
    "id": 1,
    "accountNumber": 123456789,
    "token": "b2c361e3-0ea4-4fd6-bc0e-e1b6d0c4813b",
    "timestamp": "2021-07-26T09:48:52.7613846",
    "valid": true
}
```


##### <a id="account">account -> /accountmgmt/account
```json
{
	"accountNumber": "Enter account number"
}
```

##### <a id="withdraw">withdraw -> /accountmgmt/withdraw
```json
{
    "bankAccount":{
        "accountNumber": "Enter account number"
    },
    "withdrawalAmount": "Enter amount"
}
```
