### project navi/info

`src/main/kotlin/com.example.miniBank` is where all the packages are

`authentication` = for jwt authentication to provide security

`dtos` ←→ `controller` → `service` → `repository` → `entity`

`controller` = entry point for http requests (receive requests, process data, determine response)
- `GET`: retrieve data from a server
- `POST`: send data to the server to create a new resource
- `PUT`: update or replace an existing resource
- `DELETE`: remove a resource from the server

`service` = business logic (processes data, makes decisions, eg: transferring funds)

`repository` = database operations (handles communication with db: save, find, delete)

`entity` = domain model (defines what your data is in the system: fields, relationships)

`dto` = data transfer object (cleans request/response json bodies, to avoid returning entities)

### relationships
- one user, one kyc (know your customer)
- one user, many accounts
- one account, many transactions

### services
- register user (username & password)
- log in
- create or update kyc info
- get user kyc info
- create new/multiple accounts
- list accounts
- transfer money to other account
- close account

### progress
#### part 1:
- [x] spring boot setup
- [x] define structure
- [x] database setup
- [x] entities
- [x] repositories
- [x] services
- [x] controllers
- [x] test in postman
- ALL DONE!!!!!!!

#### part 2:
- [x] as a user, i can log in and get jwt token
- as a developer, i can test:
  - [ ] user registration endpoint
  - [ ] multiple account creation
  - [ ] reading a list of accounts
  - [ ] user create profile
  - [ ] user update profile
  - [ ] user read profile
  - [ ] closing a user account
  - [ ] transferring money to another account

### more notes for understanding

- foreign keys = `user_id int references users(id)`
- no `TransactionController` or `KycController` needed, nor service (because user will only interact with `/users` and `/accounts`)