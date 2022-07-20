Global HR platform enabling companies to hire & onboard their employees internationally, at the push of a button.
It is our mission to create opportunities for anyone to work from anywhere.
As work is becoming even more global and remote, there has never been a bigger chance to build a truly global HR-tech company.

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


The allowed state transitions within `IN_CHECK` state are:
- `FINISH SECURITY CHECK`: `SECURITY_CHECK_STARTED` -> `SECURITY_CHECK_FINISHED`
- `COMPLETE INITIAL WORK PERMIT CHECK`: `WORK_PERMIT_CHECK_STARTED` -> `WORK_PERMIT_CHECK_PENDING_VERIFICATION`
- `FINISH WORK PERMIT CHECK`: `WORK_PERMIT_CHECK_PENDING_VERIFICATION` -> `WORK_PERMIT_CHECK_FINISHED` -> APROVED as join point 
- `ACTIVATE`: `APROVED` -> `ACTIVE`

How to run
 
docker build -t employee-managment-system  .

docker-compose up

you can access the  endpoins:

http://ipaddress:8080/v1/employees  for (add-get) employees

http://ipaddress:8080/v1/employees/1/state for (change-state) employee	
 
swagger documentation

http://ipaddress:8080/swagger-ui/index.html

Provided PostMan collection for sample requet and response

employee-management-system.postman_collection.json



