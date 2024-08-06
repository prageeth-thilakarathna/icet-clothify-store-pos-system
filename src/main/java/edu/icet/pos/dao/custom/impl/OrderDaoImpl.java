package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.OrderDao;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;

public class OrderDaoImpl implements OrderDao {
    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        Session session = HibernateUtil.getSingletonSession();
        HibernateUtil.singletonBegin();
        session.persist(orderEntity);
        return orderEntity;
    }
}
