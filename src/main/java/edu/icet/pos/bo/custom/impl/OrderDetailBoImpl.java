package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.OrderDetailBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.OrderDetailDao;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.model.order.Order;
import edu.icet.pos.model.order.OrderDetail;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {
    private final OrderDetailDao orderDetailDao = DaoFactory.getDao(DaoType.ORDER_DETAIL);

    @Override
    public void orderDetailRegister(OrderDetail orderDetail) {
        assert orderDetailDao != null;
        orderDetailDao.save(new ModelMapper().map(orderDetail, OrderDetailEntity.class));
    }

    @Override
    public List<OrderDetail> getOrderDetail(Order order) {
        assert orderDetailDao != null;
        return new ModelMapper().map(orderDetailDao.getOrderDetail(new ModelMapper().map(order, OrderEntity.class)), new TypeToken<List<OrderDetail>>() {
        }.getType());
    }
}
