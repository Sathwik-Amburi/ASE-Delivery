# How to run mongo

Install mongo according to 
https://www.moodle.tum.de/pluginfile.php/4245898/mod_folder/content/0/Exercise_Sheet_03.pdf

```bash
mongod  # run mongo server
mongosh --port 27017  # mongo shell

sudo service mongod status
sudo systemctl status mongod
```

Execute this in the mongoshell to inspect the tables. Only if you are interested in
```bash
show dbs
use test
show collections

```

# How to test DB
```bash
curl -X GET localhost:8080/Client/listAll  # "Client" can be replaced with "Dispatcher","Deliverer" or "AllActors"
curl -d "email=babushka1@gmail.ru&pass=p@ssw0rd" -X POST localhost:8080/Client/create
curl -d "email=babushka1@gmail.ru" -X POST localhost:8080/Client/find

curl -d "email=disp@gmail.ru&pass=p@ssw0rd1" -X POST localhost:8080/Dispatcher/create
curl -d "email=del@gmail.ru&pass=p@ssw0rd2" -X POST localhost:8080/Deliverer/create
curl -X GET localhost:8080/AllActors/listAll

curl -d "dispatcherId=638f0fceb39edb53d3f173d5&delivererId=638f0fceb39edb53d3f173d6&clientId=638f0fc1b39edb53d3f173d4&street=ErsteStraße" \
  -X POST localhost:8080/Order/create  # replace with your Ids!!!
curl -X GET localhost:8080/Order/listAll
curl -d "delivererId=638f0fceb39edb53d3f173d6" -X POST localhost:8080/Order/getUndelivOrderByDeliverer
curl -d "delivererId=638f0fc1b39edb53d3f173d4" -X POST localhost:8080/Order/getUndelivOrderByDeliverer  # should not work

curl -d "orderId=638f107f6da351709abcaf70&newOrderStatus=Delivered" -X PUT localhost:8080/Order/updateOrderStatus

curl -d "actorId=638d268e2b1ca04e2b3f573a" -X DELETE localhost:8080/Client/delete
```
curl -d "dispatcherId=69&delivererId=638f0fceb39edb53d3f173d6&clientId=638f0fc1b39edb53d3f173d4&street=ErsteStraße" \
-X PUT localhost:8080/Order/create 