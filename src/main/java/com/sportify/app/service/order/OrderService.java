package com.sportify.app.service.order;

import com.sportify.app.dto.response.OrderDTO;
import com.sportify.app.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(long userId);
    OrderDTO getOrder(long orderId);
    List<OrderDTO> getOrdersByUser(long userId);
    void cancelOrder(long orderId);
}
