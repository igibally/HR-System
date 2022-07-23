Global HR platform enabling companies to hire & onboard their employees internationally, at the push of a button.
THE system is designed to track the status for  a new hires empoyees from the begining until the end

It's allow the users to create new employees and updates thier status from the begining "Added" to "aproved" and "active"
Also you can list all employees with their details or only employee
 
The states for a given Employee are:
- `ADDED`
- `IN-CHECK`
- `APPROVED`
- `ACTIVE`

Furthermore, `IN-CHECK` state is special and has the following child substates:

- `SECURITY_CHECK_STARTED`
- `SECURITY_CHECK_FINISHED`


- `WORK_PERMIT_CHECK_STARTED`
- `WORK_PERMIT_CHECK_PENDING_VERIFICATION`
- `WORK_PERMIT_CHECK_FINISHED`


by default the employee created with initial status "Added" and
by sending request with these events "BEGIN_CHECK","FINISH_SECURITY_CHECK","FINISH_WORK_PERMIT_CHECK","COMPLETE_INITIAL_WORK_PERMIT_CHECK","ACTIVATE"
you can make a transition for the employee's status

The allowed state transitions
 
- `BEGIN_CHECK`: `ADDED` -> `IN_CHECK`
- `FINISH_SECURITY_CHECK`: `SECURITY_CHECK_STARTED` -> `SECURITY_CHECK_FINISHED`
- `COMPLETE_INITIAL_WORK_PERMIT_CHECK`: `WORK_PERMIT_CHECK_STARTED` -> `WORK_PERMIT_CHECK_PENDING_VERIFICATION`
- `FINISH_WORK_PERMIT_CHECK`: `WORK_PERMIT_CHECK_PENDING_VERIFICATION` -> `WORK_PERMIT_CHECK_FINISHED` -> APROVED as join point 
- `ACTIVATE`: `APROVED` -> `ACTIVE`

How to run

 
1- docker build -t employee-managment-system  .


docker-compose up


you can access the endpoints:

POST
http://ipaddress:8080/v1/employees  TO add employees

GET
http://ipaddress:8080/v1/employees  TO get employees


GET
http://ipaddress:8080/v1/employees/{id} TO get employee by id



http://ipaddress:8080/v1/employees/1/state for (change-state) employee	

swagger documentation API
http://ipaddress:8080/swagger-ui/index.html

Provided PostMan collection for sample  for each request "create employee" ,"list all employees" "get employee by id"


employee-management-system.postman_collection.json

