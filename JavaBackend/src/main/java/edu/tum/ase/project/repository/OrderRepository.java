package edu.tum.ase.project.repository;

import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.model.Order;
import edu.tum.ase.project.utils.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findAll();

    Optional<Order> findById(String orderID);

    List<Order> findAllByDispatcher(Actor dispatcher);
    List<Order> findAllByDeliverer(Actor deliverer);

    Optional<Order> findByDelivererIdAndOrderStatus(String delivererId, OrderStatus orderStatus);
    Optional<Order> findByDelivererId(String delivererId);
    Optional<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findAllByClient(Actor client);
}