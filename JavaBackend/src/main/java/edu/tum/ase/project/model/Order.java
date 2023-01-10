package edu.tum.ase.project.model;

import edu.tum.ase.project.utils.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;

    @DBRef
    private Actor dispatcher;

    @DBRef
    private Actor deliverer;

    @DBRef
    private Actor client;

    private String street;

    private OrderStatus orderStatus;

    public Order(Actor dispatcher, Actor deliverer, Actor client, String street) {
        this.dispatcher = dispatcher;
        this.deliverer = deliverer;
        this.client = client;
        this.street = street;
        this.orderStatus = OrderStatus.OnItsWay;
    }

    public String getId() {
        return this.id;
    }

    public Actor getDispatcher() {
        return dispatcher;
    }

    public Actor getDeliverer() {
        return deliverer;
    }

    public Actor getClient() {
        return client;
    }

    public String getStreet() {
        return street;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
