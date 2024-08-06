package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.OrderDetailEntity;

public interface OrderDetailDao extends SuperDao {
    void save(OrderDetailEntity orderDetailEntity);
}
