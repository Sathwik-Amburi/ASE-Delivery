package edu.tum.ase.project.service;

import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.model.Order;
import edu.tum.ase.project.repository.ActorRepository;
import edu.tum.ase.project.repository.OrderRepository;
import edu.tum.ase.project.utils.ActorType;
import edu.tum.ase.project.utils.ObjectDoesNotExist;
import edu.tum.ase.project.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ActorRepository actorRepository;


    public Order createOrder(String dispatcherId, String delivererId, String clientId, String street)
            throws ObjectDoesNotExist {

        Optional<Actor> dispatcher = actorRepository.findByIdAndActorType(dispatcherId, ActorType.dispatcher);
        if (dispatcher.isEmpty()) {
            throw new ObjectDoesNotExist(String.format("A dispatcher with id '%s' does not exist", dispatcherId));
        }

        Optional<Actor> deliverer = actorRepository.findByIdAndActorType(delivererId, ActorType.deliverer);
        if (deliverer.isEmpty()) {
            throw new ObjectDoesNotExist(String.format("A deliverer with id '%s' does not exist", delivererId));
        }

        Optional<Actor> client = actorRepository.findByIdAndActorType(clientId, ActorType.client);
        if (client.isEmpty()) {
            throw new ObjectDoesNotExist(String.format("A client with id '%s' does not exist", delivererId));
        }

        Order newOrder = new Order(dispatcher.get(), deliverer.get(), client.get(), street);
        return orderRepository.save(newOrder);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getAllOrdersByActor(ActorType actorType, String actorId) {
        return switch (actorType) {
            case client -> orderRepository.findAllByClientId(actorId);
            case deliverer -> orderRepository.findAllByDelivererId(actorId);
            case dispatcher -> orderRepository.findAllByDispatcherId(actorId);
        };
    }

    public Order updateOrderStatus(String orderId, OrderStatus orderStatus) throws ObjectDoesNotExist {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new ObjectDoesNotExist(String.format("An order with id '%s' does not exist", orderId));
        }
        order.get().setOrderStatus(orderStatus);
        return orderRepository.save(order.get());
    }

    public void deleteOrder(String orderId) throws ObjectDoesNotExist {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new ObjectDoesNotExist(String.format("An order with id '%s' does not exist", orderId));
        }
        orderRepository.delete(order.get());
        return;
    }
}
