package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.OrderBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.OrderDao;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.model.order.Order;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class OrderBoImpl implements OrderBo {
    private final OrderDao orderDao = DaoFactory.getDao(DaoType.ORDER);

    @Override
    public Order orderRegister(Order order) {
        assert orderDao != null;
        return new ModelMapper().map(orderDao.save(new ModelMapper().map(order, OrderEntity.class)), Order.class);
    }

    @Override
    public int getOrderCount() {
        assert orderDao != null;
        return orderDao.count();
    }

    @Override
    public List<Order> getOrderPerPage(int offset) {
        assert orderDao != null;
        return new ModelMapper().map(orderDao.getPerPage(offset), new TypeToken<List<Order>>() {
        }.getType());
    }
}
