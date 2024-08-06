package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.order.OrderDetail;

public interface OrderDetailBo extends SuperBo {
    void orderDetailRegister(OrderDetail orderDetail);
}
