package com.ust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private BookFeignClient bookFeignClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order placeOrder(Order order) {

        try {
            Book book = bookFeignClient.getBookById(order.getBookId());
            if (book == null) {
                throw new RuntimeException("Book not found");
            }
            if (book.getStock() < order.getQuantity()) {
                throw new RuntimeException("Insufficient stock for book ID: " + order.getBookId());
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Book not found", e);
        }


        if (!"PENDING".equals(order.getStatus()) &&
                !"CONFIRMED".equals(order.getStatus()) &&
                !"CANCELLED".equals(order.getStatus())) {
            throw new IllegalArgumentException("Invalid status: " + order.getStatus());
        }


        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }

        if (!"PENDING".equals(order.getStatus()) &&
                !"CONFIRMED".equals(order.getStatus()) &&
                !"CANCELLED".equals(order.getStatus())) {
            throw new IllegalArgumentException("Invalid status: " + order.getStatus());
        }
        order.setId(id);
        return orderRepository.save(order);
    }

    public void cancelOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
    }
}
