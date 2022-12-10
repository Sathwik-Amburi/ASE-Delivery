package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Order;
import edu.tum.ase.project.service.OrderService;
import edu.tum.ase.project.utils.ObjectDoesNotExist;
import edu.tum.ase.project.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/Order")
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

    @GetMapping("/listAll")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/getUndelivOrderByDeliverer")
    public Order getUndelivOrderByDeliverer(@RequestParam("delivererId") String delivererId){
        Optional<Order> order = orderService.getUndelivOrderByDelivererId(delivererId);
        if (order.isEmpty()){
            throw new ResponseStatusException(NOT_ACCEPTABLE,
                    String.format("Undelivered order of the deliverer '%s' was not found", delivererId));
        }
        return order.get();
    }

    @PostMapping("/create")
    public Order createOrder(@RequestParam("dispatcherId") String dispatcherId,
                             @RequestParam("delivererId") String delivererId,
                             @RequestParam("clientId") String clientId,
                             @RequestParam("street") String street) {
        try {
            return orderService.createOrder(dispatcherId, delivererId, clientId, street);
        } catch (ObjectDoesNotExist e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @PutMapping("/updateOrderStatus")
    public Order updateOrderStatus(@RequestParam("orderId") String OrderId,
                             @RequestParam("newOrderStatus") String orderStatusStr) {
        try {
            return orderService.updateOrderStatus(OrderId, str2orderStatus(orderStatusStr));
        } catch (ObjectDoesNotExist e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public void deleteOrderById(@RequestParam("orderId") String OrderId){
        try {
            orderService.deleteOrder(OrderId);
            return;
        } catch (ObjectDoesNotExist e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, e.getMessage());
        }
    }
}
