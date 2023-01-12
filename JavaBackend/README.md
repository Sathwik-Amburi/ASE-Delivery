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

# How to test API (small example)
```bash
curl -X GET localhost:8080/Client  # "Client" can be replaced with "Dispatcher","Deliverer" or "AllActors"
curl -X POST -H "Content-Type: application/json" \
  -d '{"email": "babushka@gmail.ru", "pass": "p@ssw0rd"}' localhost:8080/Client
curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka1@gmail.ru"}' localhost:8080/Client/email

curl -X POST -H "Content-Type: application/json" \
  -d '{"email": "disp@gmail.ru", "pass": "p@ssw0rd"}' localhost:8080/Dispatcher
curl -X POST -H "Content-Type: application/json" \
  -d '{"email": "del@gmail.ru", "pass": "p@ssw0rd"}' localhost:8080/Deliverer
  
curl -X GET localhost:8080/AllActors

curl -X POST -H "Content-Type: application/json" \
  -d '{"dispatcherId": "63bd33a9e03f596350f8afb2", "delivererId": "63bd33a9e03f596350f8afb3", "clientId": "63bd2d47dea40908ea916896", "street": "ErsteStraße"}' \
  localhost:8080/Order  # replace with your Ids!!!
curl -X GET localhost:8080/Order

curl -X POST -H "Content-Type: application/json" -d '{"delivererId": "63bd33a9e03f596350f8afb3"}' localhost:8080/Order/getUndelivOrderByDeliverer

curl -X PUT -H "Content-Type: application/json" -d '{"orderId": "638f107f6da351709abcaf70", "newOrderStatus": "Delivered"}' localhost:8080/Order

curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": "638d268e2b1ca04e2b3f573a"}' localhost:8080/Client
```
# The whole API documentation

### Return all the actors or all the actors of the specified type

        Usage:
        curl -X GET localhost:8080/AllActors  # returns all the actors
        curl -X GET localhost:8080/Client  # returns all the Clients
        curl -X GET localhost:8080/Dispatcher  # returns all the Dispatchers
        curl -X GET localhost:8080/Deliverer  # returns all the Deliverers

        Return value is the list of items (id, email, actorType)

        Example:
        >> curl -X GET localhost:8080/Client
        << status code 200
           [{"id":"638d2a011064dc1a387acc8e","email":"babushka@gmail.ru","actorType":"Client"},
            {"id":"638f0fceb39edb53d3f173d5","email":"disp@gmail.ru","actorType":"Dispatcher"}]


### Create an actor item

        Usage:
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>, "pass": <PASS>}' localhost:8080/Client  # create a new Client
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>, "pass": <PASS>}' localhost:8080/Dispatcher  # create a new Dispatcher
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>, "pass": <PASS>}' localhost:8080/Deliverer  # create a new Deliverer
        EMAIL and PASS are user strings

        Returns a created actor item, i.e. an object with fields (id, email, actorType)

        Example:
        >> curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka@gmail.ru", "pass": "p@ssw0rd"}' localhost:8080/Client
        << status code 200
           {"id":"63bd25a327dcd14c6d30451c","email":"babushka@gmail.ru","actorType":"Client"}

        >> curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka@gmail.ru", "pass": "p@ss"}' localhost:8080/Client  # try to add a new actor with the same email
        << status code 500
           {"timestamp":"2023-01-10T09:20:25.186+00:00","status":500,"error":"Internal Server Error","path":"/Client"}

        >> curl -i -X POST -H "Content-Type: application/json" -d '{"email": "fish@gmail.ru"}' localhost:8080/Client  # no "pass" filed
        << status code 400
           {"timestamp":"2023-01-10T08:48:32.783+00:00","status":400,"error":"Bad Request","path":"/Client"}



### Return an actor by email

    Usage
    curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>}' localhost:8080/Client/email  # find a Client
    curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>}' localhost:8080/Dispatcher/email  # find a Dispatcher
    curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>}' localhost:8080/Deliverer/email  # find a Deliverer
    EMAIL is a user string
    
    Returns the actor item, i.e. an object with fields (id, email, actorType) or null
    
    Example:
    >> curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka@gmail.ru"}' localhost:8080/Client/email
    << status code 200
       {"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"Client"}
     


### Delete an actor by id

    Usage
    curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/Client  # delete a Client
    curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/Dispatcher  # delete a Dispatcher
    curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/Deliverer  # delete a Deliverer
    ACTOR_ID is a String representing Id of an object from the actor database
    
    Returns only status code (or an error)
    
    Example:
    >> curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": "63bd3438e03f596350f8afb5"}' localhost:8080/Client
    << status code 200
    
    >> curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": "63bd3438e03f596350f8afb5"}' localhost:8080/Client  # try to delete a key missing from the table
    << status code 406
       {"timestamp":"2023-01-10T09:48:24.762+00:00","status":406,"error":"Not Acceptable","path":"/Client"}
         


### Return all the orders

    Usage:
    curl -X GET localhost:8080/Order
    
    Return value is the list of items (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType), street, orderStatus)
    
    Example:
    >> curl -X GET localhost:8080/Order
    << status code 200
       [{"id":"63bd6531f5305824ce4b9854",
         "dispatcher":{"id":"63bd33a9e03f596350f8afb2","email":"disp@gmail.ru","actorType":"Dispatcher"},
         "deliverer":{"id":"63bd33a9e03f596350f8afb3","email":"del@gmail.ru","actorType":"Deliverer"},
         "client":{"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"Client"},
         "street":"ErsteStraße","orderStatus":"OnItsWay"}]



### Return all the orders of a specified actor

    Usage:
    curl -X POST -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/Order/Client
    curl -X POST -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/Order/Deliverer
    curl -X POST -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/Order/Dispatcher
    ACTOR_ID is a user string representing Id of an object from the actor database

    Return value is the list of items
        (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType), street, orderStatus)

    Example:
    >> curl -X POST -H "Content-Type: application/json" -d '{"actorId": "63bd2d47dea40908ea916896"}' localhost:8080/Order/Client
    << status code 200
       [{"id":"63bd6531f5305824ce4b9854",
         "dispatcher":{"id":"63bd33a9e03f596350f8afb2","email":"disp@gmail.ru","actorType":"Dispatcher"},
         "deliverer":{"id":"63bd33a9e03f596350f8afb3","email":"del@gmail.ru","actorType":"Deliverer"},
         "client":{"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"Client"},
         "street":"ErsteStraße","orderStatus":"Delivered"}]
      
### Return undelivered order assigned to a specified deliverer

    Usage:
    curl -X POST -H "Content-Type: application/json" -d '{"delivererId": <DELIVERER_ID>}' localhost:8080/Order/getUndelivOrderByDeliverer
    DELIVERER_ID is a user string representing Id of an object from the actor database
    
    Returns a created order item, i.e. an object with fields
        (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType), street, orderStatus)
    
    Example:
    >> curl -X POST -H "Content-Type: application/json" -d '{"delivererId": "63bd33a9e03f596350f8afb3"}' localhost:8080/Order/getUndelivOrderByDeliverer
    << status code 200
        {"id":"63bd3723e03f596350f8afb6",
        "dispatcher":{"id":"63bd33a9e03f596350f8afb2","email":"disp@gmail.ru","actorType":"Dispatcher"},
        "deliverer":{"id":"63bd33a9e03f596350f8afb3","email":"del@gmail.ru","actorType":"Deliverer"},
        "client":{"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"Client"}, "street":"ErsteStraße", "orderStatus":"OnItsWay"}
    


### Create an order

    Usage:
    curl -X POST -H "Content-Type: application/json" \
        -d '{"dispatcherId": <DISPATCHER_ID>, "delivererId": <DELIVERER_ID>, "clientId": <CLIENT_ID>, "street": "ErsteStraße"}' \
        localhost:8080/Order
    DISPATCHER_ID, DELIVERER_ID and CLIENT_ID are user strings representing Ids of objects from the actor database
    
    Returns a created order item, i.e. an object with fields
        (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType), street, orderStatus)
    
    Example:
    >> curl -X POST -H "Content-Type: application/json" \
        -d '{"dispatcherId": "63bd33a9e03f596350f8afb2", "delivererId": "63bd33a9e03f596350f8afb3", "clientId": "63bd2d47dea40908ea916896", "street": "ErsteStraße"}' \
        localhost:8080/Order
    << status code 200
        {"id":"63bd3723e03f596350f8afb6",
        "dispatcher":{"id":"63bd33a9e03f596350f8afb2","email":"disp@gmail.ru","actorType":"Dispatcher"},
        "deliverer":{"id":"63bd33a9e03f596350f8afb3","email":"del@gmail.ru","actorType":"Deliverer"},
        "client":{"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"Client"},"street":"ErsteStraße","orderStatus":"OnItsWay"}
    >> curl -X POST -H "Content-Type: application/json" \
        -d '{"dispatcherId": "1", "delivererId": "2", "clientId": "3", "street": "ErsteStraße"}' \
        localhost:8080/Order  # not existing ids
    << status code 406
       {"timestamp":"2023-01-10T10:02:29.693+00:00","status":406,"error":"Not Acceptable","path":"/Order"}
    
    
    
### Update an order status

    Usage:
    curl -X PUT -H "Content-Type: application/json" -d '{"orderId": <ORDER_ID>, "newOrderStatus": "Delivered"}' localhost:8080/Order
    curl -X PUT -H "Content-Type: application/json" -d '{"orderId": <ORDER_ID>, "newOrderStatus": "OnItsWay"}' localhost:8080/Order
    ORDER_ID is a user string representing Id of an object from the order database
    
    Returns a created order item, i.e. an object with fields
        (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType), street, orderStatus)
    
    Example:
    >> curl -X PUT -H "Content-Type: application/json" -d '{"orderId": "63bd3723e03f596350f8afb6", "newOrderStatus": "Delivered"}' localhost:8080/Order
    << status code 200
        {"id":"63bd3723e03f596350f8afb6",
        "dispatcher":{"id":"63bd33a9e03f596350f8afb2","email":"disp@gmail.ru","actorType":"Dispatcher"},
        "deliverer":{"id":"63bd33a9e03f596350f8afb3","email":"del@gmail.ru","actorType":"Deliverer"},
        "client":{"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"Client"},"street":"ErsteStraße","orderStatus":"OnItsWay"}
    
    
    
### Delete an order by id

    Usage
    curl -X DELETE -H "Content-Type: application/json" -d '{"orderId": <ORDER_ID>}' localhost:8080/Order
    ORDER_ID is a user string representing Id of an object from the order database
    
    Returns only status code (or an error)
    
    Example:
    >> curl -X DELETE -H "Content-Type: application/json" -d '{"orderId": "63bd3723e03f596350f8afb6"}' localhost:8080/Order
    << status code 200
    
    >> curl -X DELETE -H "Content-Type: application/json" -d '{"orderId": "63bd3723e03f596350f8afb6"}' localhost:8080/Order  # try to delete a key missing from the table
    << status code 406
       {"timestamp":"2023-01-10T10:24:00.980+00:00","status":406,"error":"Not Acceptable","path":"/Order"}
