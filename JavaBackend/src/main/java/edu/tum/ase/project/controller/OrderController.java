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
            (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType), street, orderStatus)

        Example:
        >> curl -X GET localhost:8080/order
        << status code 200
           [{"id":"63bd6531f5305824ce4b9854",
             "dispatcher":{"id":"63bd33a9e03f596350f8afb2","email":"disp@gmail.ru","actorType":"dispatcher"},
             "deliverer":{"id":"63bd33a9e03f596350f8afb3","email":"del@gmail.ru","actorType":"dispatcher"},
             "client":{"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"client"},
             "street":"ErsteStraße","orderStatus":"OnItsWay"}]
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
            (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType), street, orderStatus)

        Example:
        >> curl -X POST -H "Content-Type: application/json" -d '{"actorId": "63bd2d47dea40908ea916896"}' localhost:8080/order/client
        << status code 200
           [{"id":"63bd6531f5305824ce4b9854",
             "dispatcher":{"id":"63bd33a9e03f596350f8afb2","email":"disp@gmail.ru","actorType":"dispatcher"},
             "deliverer":{"id":"63bd33a9e03f596350f8afb3","email":"del@gmail.ru","actorType":"dispatcher"},
             "client":{"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"client"},
             "street":"ErsteStraße","orderStatus":"Delivered"}]
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
    @PostMapping("/get-undeliv-order-by-deliverer")
    public Order getUndelivOrderByDeliverer(@RequestBody ObjectNode json) {
        /*
        Returns undelivered order assigned to a specified deliverer

        Usage:
        curl -X POST -H "Content-Type: application/json" -d '{"delivererId": <DELIVERER_ID>}' localhost:8080/order/get-undeliv-order-by-deliverer
        DELIVERER_ID is a user string representing Id of an object from the actor database

        Returns a created order item, i.e. an object with fields
            (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType), street, orderStatus)

        Example:
        >> curl -X POST -H "Content-Type: application/json" -d '{"delivererId": "63bd33a9e03f596350f8afb3"}' localhost:8080/order/get-undeliv-order-by-deliverer
        << status code 200
            {"id":"63bd3723e03f596350f8afb6",
            "dispatcher":{"id":"63bd33a9e03f596350f8afb2","email":"disp@gmail.ru","actorType":"dispatcher"},
            "deliverer":{"id":"63bd33a9e03f596350f8afb3","email":"del@gmail.ru","actorType":"dispatcher"},
            "client":{"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"client"},"orderStatus":"OnItsWay"}
        */
        String delivererId;
        try {
            delivererId = json.get("delivererId").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }
        Optional<Order> order = orderService.getUndelivOrderByDelivererId(delivererId);
        if (order.isEmpty()) {
            throw new ResponseStatusException(NOT_ACCEPTABLE,
                    String.format("Undelivered order of the deliverer '%s' was not found", delivererId));
        }
        return order.get();
    }

    @CrossOrigin
    @PostMapping("")
    public Order createOrder(@RequestBody ObjectNode json) {
        /*
        Creates an order

        Usage:
        curl -X POST -H "Content-Type: application/json" \
            -d '{"dispatcherId": <DISPATCHER_ID>, "delivererId": <DELIVERER_ID>, "clientId": <CLIENT_ID>, "street": "ErsteStraße"}' \
            localhost:8080/order
        DISPATCHER_ID, DELIVERER_ID and CLIENT_ID are user strings representing Ids of objects from the actor database

        Returns a created order item, i.e. an object with fields
            (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType), street, orderStatus)
        Fails if the new order refers to the actors which don't exist in the actor table.

        Example:
        >> curl -X POST -H "Content-Type: application/json" \
            -d '{"dispatcherId": "63bd33a9e03f596350f8afb2", "delivererId": "63bd33a9e03f596350f8afb3", "clientId": "63bd2d47dea40908ea916896", "street": "ErsteStraße"}' \
            localhost:8080/order
        << status code 200
            {"id":"63bd3723e03f596350f8afb6",
            "dispatcher":{"id":"63bd33a9e03f596350f8afb2","email":"disp@gmail.ru","actorType":"dispatcher"},
            "deliverer":{"id":"63bd33a9e03f596350f8afb3","email":"del@gmail.ru","actorType":"dispatcher"},
            "client":{"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"client"},"orderStatus":"OnItsWay"}
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
            (id, dispatcher: (id, email, actorType), deliverer: (id, email, actorType), client: (id, email, actorType), street, orderStatus)

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
        curl -X DELETE -H "Content-Type: application/json" -d '{"orderId": <ORDER_ID>}' localhost:8080/order
        ORDER_ID is a user string representing Id of an object from the order database

        Returns only status code (or an error)

        Example:
        >> curl -X DELETE -H "Content-Type: application/json" -d '{"orderId": "63bd3723e03f596350f8afb6"}' localhost:8080/order
        << status code 200

        >> curl -X DELETE -H "Content-Type: application/json" -d '{"orderId": "63bd3723e03f596350f8afb6"}' localhost:8080/order  # try to delete a key missing from the table
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
