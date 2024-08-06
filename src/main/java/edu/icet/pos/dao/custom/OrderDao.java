package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.OrderEntity;

public interface OrderDao extends SuperDao {
    OrderEntity save(OrderEntity orderEntity);
}
