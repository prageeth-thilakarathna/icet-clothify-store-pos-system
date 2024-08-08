package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.order.Order;
import edu.icet.pos.model.order.OrderDetail;

import java.util.List;

public interface OrderDetailBo extends SuperBo {
    void orderDetailRegister(OrderDetail orderDetail);

    List<OrderDetail> getOrderDetail(Order order);
}
