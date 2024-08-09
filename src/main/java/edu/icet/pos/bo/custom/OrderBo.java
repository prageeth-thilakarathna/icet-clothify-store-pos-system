package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.order.Order;

import java.util.List;

public interface OrderBo extends SuperBo {
    Order orderRegister(Order order);

    int getOrderCount();

    List<Order> getOrderPerPage(int offset);

    Order getOrder(Integer id);

    void orderReturn(Order order);
}
