package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.OrderDetailDao;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public void save(OrderDetailEntity orderDetailEntity) {
        Session session = HibernateUtil.getSingletonSession();
        session.persist(orderDetailEntity);
    }
}
