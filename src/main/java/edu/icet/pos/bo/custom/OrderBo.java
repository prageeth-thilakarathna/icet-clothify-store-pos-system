package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.order.Order;

public interface OrderBo extends SuperBo {
    Order orderRegister(Order order);
}
