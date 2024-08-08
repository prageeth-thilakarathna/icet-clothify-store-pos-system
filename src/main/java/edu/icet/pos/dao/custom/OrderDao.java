package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends SuperDao {
    OrderEntity save(OrderEntity orderEntity);

    int count();

    List<OrderEntity> getPerPage(int offset);
}
