package edu.tum.ase.project.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.tum.ase.project.model.Order;
import edu.tum.ase.project.service.OrderService;
import edu.tum.ase.project.utils.ActorType;
import edu.tum.ase.project.utils.WrongObject;
import edu.tum.ase.project.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    private OrderStatus str2orderStatus(String orderStatusStr) {
        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(orderStatusStr);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Wrong orderStatus");
        }
        return orderStatus;
    }

    private ActorType str2actorType(String actorTypeStr) {
        ActorType actorType;
        try {
            actorType = ActorType.valueOf(actorTypeStr);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Wrong actorType");
        }
        return actorType;
    }

    @CrossOrigin
    @GetMapping("")
    public List<Order> getAllOrders() {
        /*
        Returns all the orders

        Usage:
        curl -X GET localhost:8080/order

        Return value is the list of items
            (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType),
             boxNumber, street, orderStatus)

        Example:
        >> curl -X GET localhost:8080/order
        << status code 200
           [{"id":"63de9c0d780af609c4300ff7",
             "dispatcher":{"id":"63c1776762cd023293ebaa9f","email":"disp0@gmail.ru","pass":"p@ssw0rd","actorType":"dispatcher"},
             "deliverer":{"id":"63c177d662cd023293ebaaa2","email":"del@gmail.ru","pass":"p@ssw0rd","actorType":"deliverer"},
             "client":{"id":"63c1778662cd023293ebaaa1","email":"babushka9@gmail.ru","pass":"p@ssw0rd","actorType":"client"},
             "boxNumber":13,"street":"ErsteStraße","orderStatus":"OnItsWay"}]
        */
        return orderService.getAllOrders();
    }

    @CrossOrigin
    @PostMapping("/{actorType}")
    public List<Order> getAllOrdersByActor(@PathVariable(value = "actorType") final String actorTypeStr,
                                           @RequestBody ObjectNode json) {
        /*
        Returns all the orders of a specified actor

        Usage:
        curl -X POST -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/order/client
        curl -X POST -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/order/dispatcher
        curl -X POST -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/order/dispatcher
        ACTOR_ID is a user string representing Id of an object from the actor database

        Return value is the list of items
            (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType),
             boxNumber, street, orderStatus)

        Example:
        >> curl -X POST -H "Content-Type: application/json" -d '{"actorId": "63c1778662cd023293ebaaa1"}' localhost:8080/order/client
        << status code 200
           [{"id":"63de9c0d780af609c4300ff7",
             "dispatcher":{"id":"63c1776762cd023293ebaa9f","email":"disp0@gmail.ru","pass":"p@ssw0rd","actorType":"dispatcher"},
             "deliverer":{"id":"63c177d662cd023293ebaaa2","email":"del@gmail.ru","pass":"p@ssw0rd","actorType":"deliverer"},
             "client":{"id":"63c1778662cd023293ebaaa1","email":"babushka9@gmail.ru","pass":"p@ssw0rd","actorType":"client"},
             "boxNumber":13,"street":"ErsteStraße","orderStatus":"OnItsWay"}]
        */
        String actorId;
        try {
            actorId = json.get("actorId").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }
        return orderService.getAllOrdersByActor(str2actorType(actorTypeStr), actorId);
    }

    @CrossOrigin
    @PostMapping("/get-undeliv-order-by-box")
    public List<Order> getUndelivOrderByBox(@RequestBody ObjectNode json) {
        /*
        Returns undelivered orders assigned to a specified box

        Usage:
        curl -X POST -H "Content-Type: application/json" -d '{"delivererId": <DELIVERER_ID>}' localhost:8080/order/get-undeliv-order-by-box
        DELIVERER_ID is a user string representing Id of an object from the actor database

        Returns a created order item, i.e. an object with fields
            (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType),
             boxNumber, street, orderStatus)

        Example:
        >> curl -X POST -H "Content-Type: application/json" -d '{"boxNumber": "13"}' localhost:8080/order/get-undeliv-order-by-box
        << status code 200
            [{"id":"63de9c0d780af609c4300ff7",
              "dispatcher":{"id":"63c1776762cd023293ebaa9f","email":"disp0@gmail.ru","pass":"p@ssw0rd","actorType":"dispatcher"},
              "deliverer":{"id":"63c177d662cd023293ebaaa2","email":"del@gmail.ru","pass":"p@ssw0rd","actorType":"deliverer"},
              "client":{"id":"63c1778662cd023293ebaaa1","email":"babushka9@gmail.ru","pass":"p@ssw0rd","actorType":"client"},
              "boxNumber":13,"street":"ErsteStraße","orderStatus":"OnItsWay"},
             {"id":"63dfdaaadc550042e0abc758",
              "dispatcher":{"id":"63c1776762cd023293ebaa9f","email":"disp0@gmail.ru","pass":"p@ssw0rd","actorType":"dispatcher"},
              "deliverer":{"id":"63c177d662cd023293ebaaa2","email":"del@gmail.ru","pass":"p@ssw0rd","actorType":"deliverer"},
              "client":{"id":"63c1778662cd023293ebaaa1","email":"babushka9@gmail.ru","pass":"p@ssw0rd","actorType":"client"},
              "boxNumber":13,"street":"ErsteStraße","orderStatus":"OrderCreated"}]
        */
        int boxNumber;
        try {
            boxNumber = Integer.parseInt(json.get("boxNumber").asText());
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }
        List<Order> orders = orderService.getUndelivOrderByBoxNumber(boxNumber);
        if (orders.isEmpty()) {
            throw new ResponseStatusException(NOT_ACCEPTABLE,
                    String.format("Undelivered order of the box '%d' was not found", boxNumber));
        }
        return orders;
    }

    @CrossOrigin
    @PostMapping("")
    public Order createOrder(@RequestBody ObjectNode json) {
        /*
        Creates an order

        Usage:
        curl -X POST -H "Content-Type: application/json" \
            -d '{"dispatcherId": <DISPATCHER_ID>, "delivererId": <DELIVERER_ID>, "clientId": <CLIENT_ID>,
                 "boxNumber" <BOX_NUMBER>,"street": <STREET>}' \
            localhost:8080/order
        DISPATCHER_ID, DELIVERER_ID and CLIENT_ID are user strings representing Ids of objects from the actor database

        Returns a created order item, i.e. an object with fields
            (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType),
             boxNumber, street, orderStatus)
        Fails if the new order refers to the actors which don't exist in the actor table.

        Example:
        >> curl -X POST -H "Content-Type: application/json" \
            -d '{"dispatcherId": "63c1776762cd023293ebaa9f", "delivererId": "63c177d662cd023293ebaaa2", "clientId": "63c1778662cd023293ebaaa1",
                "boxNumber": 13,"street": "ErsteStraße"}' \
            localhost:8080/order
        << status code 200
            {"id":"63dfdaaadc550042e0abc758",
             "dispatcher":{"id":"63c1776762cd023293ebaa9f","email":"disp0@gmail.ru","pass":"p@ssw0rd","actorType":"dispatcher"},
             "deliverer":{"id":"63c177d662cd023293ebaaa2","email":"del@gmail.ru","pass":"p@ssw0rd","actorType":"deliverer"},
             "client":{"id":"63c1778662cd023293ebaaa1","email":"babushka9@gmail.ru","pass":"p@ssw0rd","actorType":"client"},
             "boxNumber":13,"street":"ErsteStraße","orderStatus":"OrderCreated"}
        >> curl -X POST -H "Content-Type: application/json" \
            -d '{"dispatcherId": "1", "delivererId": "2", "clientId": "3", "street": "ErsteStraße"}' \
            localhost:8080/order  # not existing ids
        << status code 406
           {"timestamp":"2023-01-10T10:02:29.693+00:00","status":406,"error":"Not Acceptable","path":"/order"}
        */
        String dispatcherId;
        String delivererId;
        String clientId;
        int boxNumber;
        String street;
        try {
            dispatcherId = json.get("dispatcherId").asText();
            delivererId = json.get("delivererId").asText();
            clientId = json.get("clientId").asText();
            boxNumber = Integer.parseInt(json.get("boxNumber").asText());
            street = json.get("street").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }
        try {
            return orderService.createOrder(dispatcherId, delivererId, clientId, boxNumber, street);
        } catch (WrongObject e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @CrossOrigin
    @PutMapping("")
    public Order updateOrderStatus(@RequestBody ObjectNode json) {
        /*
        Updates an order status

        Usage:
        curl -X PUT -H "Content-Type: application/json" -d '{"orderId": <ORDER_ID>, "newOrderStatus": "Delivered"}' localhost:8080/order
        curl -X PUT -H "Content-Type: application/json" -d '{"orderId": <ORDER_ID>, "newOrderStatus": "OnItsWay"}' localhost:8080/order
        ORDER_ID is a user string representing Id of an object from the order database

        Returns a created order item, i.e. an object with fields
            (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType),
             boxNumber, street, orderStatus)

        Example:
        >> curl -X PUT -H "Content-Type: application/json" -d '{"orderId": "63bd3723e03f596350f8afb6", "newOrderStatus": "Delivered"}' localhost:8080/order
        << status code 200
            {"id":"63bd3723e03f596350f8afb6",
            "dispatcher":{"id":"63bd33a9e03f596350f8afb2","email":"disp@gmail.ru","actorType":"dispatcher"},
            "deliverer":{"id":"63bd33a9e03f596350f8afb3","email":"del@gmail.ru","actorType":"dispatcher"},
            "client":{"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"client"},"orderStatus":"OnItsWay"}
        */
        String orderId;
        String orderStatusStr;
        try {
            orderId = json.get("orderId").asText();
            orderStatusStr = json.get("newOrderStatus").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }
        try {
            return orderService.updateOrderStatus(orderId, str2orderStatus(orderStatusStr));
        } catch (WrongObject e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/delete")  // TODO change this if we have authentication
    public void deleteOrderById(@RequestBody ObjectNode json) {
        /*
        Deletes an order by id

        Usage
        curl -X POST -H "Content-Type: application/json" -d '{"orderId": <ORDER_ID>}' localhost:8080/order/delete
        ORDER_ID is a user string representing Id of an object from the order database

        Returns only status code (or an error)

        Example:
        >> curl -X POST -H "Content-Type: application/json" -d '{"orderId": "63bd3723e03f596350f8afb6"}' localhost:8080/order/delete
        << status code 200

        >> curl -X POST -H "Content-Type: application/json" -d '{"orderId": "63bd3723e03f596350f8afb6"}' localhost:8080/order/delete
           # try to delete a key missing from the table
        << status code 406
           {"timestamp":"2023-01-10T10:24:00.980+00:00","status":406,"error":"Not Acceptable","path":"/order"}
        */
        String orderId;
        try {
            orderId = json.get("orderId").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }
        try {
            orderService.deleteOrder(orderId);
            return;
        } catch (WrongObject e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, e.getMessage());
        }
    }
}
