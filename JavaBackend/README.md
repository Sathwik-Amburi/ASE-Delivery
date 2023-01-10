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
curl -X POST -H "Content-Type: application/json" \
  -d '{"email": "babushka@gmail.ru", "pass": "p@ssw0rd"}' localhost:8080/Client/create
curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka1@gmail.ru"}' localhost:8080/Client/find

curl -X POST -H "Content-Type: application/json" \
  -d '{"email": "disp@gmail.ru", "pass": "p@ssw0rd"}' localhost:8080/Dispatcher/create
curl -X POST -H "Content-Type: application/json" \
  -d '{"email": "del@gmail.ru", "pass": "p@ssw0rd"}' localhost:8080/Deliverer/create
  
curl -X GET localhost:8080/AllActors/listAll

curl -X POST -H "Content-Type: application/json" \
  -d '{"dispatcherId": "63bd33a9e03f596350f8afb2", "delivererId": "63bd33a9e03f596350f8afb3", "clientId": "63bd2d47dea40908ea916896", "street": "ErsteStraße"}' \
  localhost:8080/Order/create  # replace with your Ids!!!
curl -X GET localhost:8080/Order/listAll

curl -X POST -H "Content-Type: application/json" -d '{"delivererId": "63bd33a9e03f596350f8afb3"}' localhost:8080/Order/getUndelivOrderByDeliverer

curl -X PUT -H "Content-Type: application/json" -d '{"orderId": "638f107f6da351709abcaf70", "newOrderStatus": "Delivered"}' localhost:8080/Order/updateOrderStatus

curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": "638d268e2b1ca04e2b3f573a"}' localhost:8080/Client/delete
```
