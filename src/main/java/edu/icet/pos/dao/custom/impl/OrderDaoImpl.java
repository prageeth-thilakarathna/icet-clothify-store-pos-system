package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.OrderDao;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.entity.OrderEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderDaoImpl implements OrderDao {
    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        Session session = HibernateUtil.getSingletonSession();
        HibernateUtil.singletonBegin();
        session.persist(orderEntity);
        return orderEntity;
    }

    @Override
    public int count() {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM orders");
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        session.close();
        return count.get();
    }

    @Override
    public List<OrderEntity> getPerPage(int offset) {
        Session session = HibernateUtil.getSession();
        List<OrderEntity> orderEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orders LIMIT 5 OFFSET " + offset);

                while (resultSet.next()) {
                    OrderEntity orderEntity = new OrderEntity();
                    EmployeeEntity employeeEntity = new EmployeeEntity();
                    employeeEntity.setId(resultSet.getInt("employeeId"));

                    orderEntity.setId(resultSet.getInt("id"));
                    orderEntity.setEmployee(employeeEntity);
                    orderEntity.setEmployeeName(resultSet.getString("employeeName"));
                    orderEntity.setCustomerName(resultSet.getString("customerName"));
                    orderEntity.setCustomerEMail(resultSet.getString("customerEMail"));
                    orderEntity.setPaymentType(resultSet.getString("paymentType"));
                    orderEntity.setRegisterAt(resultSet.getTimestamp("registerAt"));
                    orderEntity.setReturnAt(resultSet.getTimestamp("returnAt"));

                    orderEntityList.add(orderEntity);
                }
            }
        });
        session.close();
        return orderEntityList;
    }

    @Override
    public OrderEntity get(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        OrderEntity orderEntity;
        try {
            tx = session.beginTransaction();
            orderEntity = session.get(OrderEntity.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return orderEntity;
    }

    @Override
    public void update(OrderEntity orderEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.merge(orderEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public List<OrderEntity> getFirstOrder() {
        Session session = HibernateUtil.getSession();
        List<OrderEntity> orderEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orders LIMIT 1 OFFSET 0");

                while (resultSet.next()) {
                    OrderEntity orderEntity = new OrderEntity();
                    EmployeeEntity employeeEntity = new EmployeeEntity();
                    employeeEntity.setId(resultSet.getInt("employeeId"));

                    orderEntity.setId(resultSet.getInt("id"));
                    orderEntity.setEmployee(employeeEntity);
                    orderEntity.setEmployeeName(resultSet.getString("employeeName"));
                    orderEntity.setCustomerName(resultSet.getString("customerName"));
                    orderEntity.setCustomerEMail(resultSet.getString("customerEMail"));
                    orderEntity.setPaymentType(resultSet.getString("paymentType"));
                    orderEntity.setRegisterAt(resultSet.getTimestamp("registerAt"));
                    orderEntity.setReturnAt(resultSet.getTimestamp("returnAt"));

                    orderEntityList.add(orderEntity);
                }
            }
        });
        session.close();
        return orderEntityList;
    }

    @Override
    public List<OrderEntity> getOrderByYear(String year) {
        Session session = HibernateUtil.getSession();
        List<OrderEntity> orderEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE registerAt LIKE '" + year + "%'");

                while (resultSet.next()) {
                    OrderEntity orderEntity = new OrderEntity();
                    EmployeeEntity employeeEntity = new EmployeeEntity();
                    employeeEntity.setId(resultSet.getInt("employeeId"));

                    orderEntity.setId(resultSet.getInt("id"));
                    orderEntity.setEmployee(employeeEntity);
                    orderEntity.setEmployeeName(resultSet.getString("employeeName"));
                    orderEntity.setCustomerName(resultSet.getString("customerName"));
                    orderEntity.setCustomerEMail(resultSet.getString("customerEMail"));
                    orderEntity.setPaymentType(resultSet.getString("paymentType"));
                    orderEntity.setRegisterAt(resultSet.getTimestamp("registerAt"));
                    orderEntity.setReturnAt(resultSet.getTimestamp("returnAt"));

                    orderEntityList.add(orderEntity);
                }
            }
        });
        session.close();
        return orderEntityList;
    }
}
