package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.SupplierDao;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public void save(SupplierEntity supplierEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.persist(supplierEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public SupplierEntity get(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        SupplierEntity supplierEntity;
        try {
            tx = session.beginTransaction();
            supplierEntity = session.get(SupplierEntity.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return supplierEntity;
    }

    @Override
    public void update(SupplierEntity supplierEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.merge(supplierEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(SupplierEntity supplierEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.remove(supplierEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public int count() {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM supplier");
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        session.close();
        return count.get();
    }

    @Override
    public List<SupplierEntity> getPerPage(int offset) {
        Session session = HibernateUtil.getSession();
        List<SupplierEntity> supplierEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM supplier LIMIT 5 OFFSET " + offset);

                while (resultSet.next()) {
                    SupplierEntity supplier = new SupplierEntity();
                    supplier.setId(resultSet.getInt("id"));
                    supplier.setTitle(resultSet.getString("title"));
                    supplier.setFirstName(resultSet.getString("firstName"));
                    supplier.setLastName(resultSet.getString("lastName"));
                    supplier.setContact(resultSet.getString("contact"));
                    supplier.setAddress(resultSet.getString("address"));
                    supplier.setRegisterAt(resultSet.getTimestamp("registerAt"));
                    supplier.setModifyAt(resultSet.getTimestamp("modifyAt"));
                    supplier.setIsActive(resultSet.getBoolean("isActive"));

                    supplierEntityList.add(supplier);
                }
            }
        });
        session.close();
        return supplierEntityList;
    }

    @Override
    public List<SupplierEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<SupplierEntity> supplierEntityList;
        try {
            tx = session.beginTransaction();
            supplierEntityList = session.createQuery("SELECT a FROM SupplierEntity a", SupplierEntity.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return supplierEntityList;
    }
}
