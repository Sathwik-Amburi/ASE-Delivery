package edu.tum.ase.project.repository;

import edu.tum.ase.project.model.Order;
import edu.tum.ase.project.utils.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findAll();

    Optional<Order> findById(String orderID);

    List<Order> findAllByDispatcherId(String dispatcherId);

    List<Order> findAllByDelivererId(String delivererId);

    List<Order> findAllByClientId(String clientId);

    Optional<Order> findByDelivererIdAndOrderStatus(String delivererId, OrderStatus orderStatus);

    @Query("{'boxNumber': ?0, 'orderStatus' : {'$ne': 'Delivered'}}")
    Optional<Order> findUndeliveredByBoxNumber(int boxNumber);


    Optional<Order> findByOrderStatus(OrderStatus orderStatus);
}