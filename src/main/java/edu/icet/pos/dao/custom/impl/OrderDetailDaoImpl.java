package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.OrderDetailDao;
import edu.icet.pos.entity.OrderDetailEntity;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public void save(OrderDetailEntity orderDetailEntity) {
        Session session = HibernateUtil.getSingletonSession();
        session.persist(orderDetailEntity);
    }

    @Override
    public List<OrderDetailEntity> getOrderDetail(OrderEntity orderEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<OrderDetailEntity> orderDetailEntityList;
        try {
            tx = session.beginTransaction();
            String sql = "FROM OrderDetailEntity O WHERE O.orders = :orderDetailEntity";
            orderDetailEntityList = session.createQuery(sql, OrderDetailEntity.class)
                    .setParameter("orderDetailEntity", orderEntity)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return orderDetailEntityList;
    }
}
