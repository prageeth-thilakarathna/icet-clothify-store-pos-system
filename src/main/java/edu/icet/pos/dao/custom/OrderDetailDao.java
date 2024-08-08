package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.entity.OrderEntity;

import java.util.List;

public interface OrderDetailDao extends SuperDao {
    void save(OrderDetailEntity orderDetailEntity);

    List<OrderDetailEntity> getOrderDetail(OrderEntity orderEntity);
}
