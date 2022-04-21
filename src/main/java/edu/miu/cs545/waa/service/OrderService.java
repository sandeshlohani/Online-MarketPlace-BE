package edu.miu.cs545.waa.service;

import edu.miu.cs545.waa.domain.Order;
import edu.miu.cs545.waa.dto.FilterDto;
import edu.miu.cs545.waa.dto.OrderDto;
import edu.miu.cs545.waa.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    OrderDto saveOrder(Order order);

    OrderDto getOrderById(Long id, String userId);

    List<OrderDto> getAllOrders(FilterDto filterDto, String userId);

    List<OrderDto> createOrders(List<OrderDto> orderDto, String userId);

    OrderDto updateOrder(Long id, OrderDto orderDto, String userId);

    void deleteOrder(Long id, String userId);

    void cancelOrder(Long id, String name);

    void makePayment(List<Long> ordersId, String userId);
}
