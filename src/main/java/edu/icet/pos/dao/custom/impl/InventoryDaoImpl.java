package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.InventoryDao;
import edu.icet.pos.entity.InventoryEntity;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryDaoImpl implements InventoryDao {
    @Override
    public void save(InventoryEntity inventoryEntity) {
        Session session = HibernateUtil.getSingletonSession();
        HibernateUtil.singletonBegin();
        session.persist(inventoryEntity);
    }

    @Override
    public void saveQtyOnHand(InventoryEntity inventoryEntity) {
        Session session = HibernateUtil.getSingletonSession();
        session.persist(inventoryEntity);
        HibernateUtil.singletonCommit();
    }

    @Override
    public InventoryEntity get(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        InventoryEntity inventoryEntity;
        try {
            tx = session.beginTransaction();
            inventoryEntity = session.get(InventoryEntity.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return inventoryEntity;
    }

    @Override
    public int count() {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM inventory");
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        session.close();
        return count.get();
    }

    @Override
    public List<InventoryEntity> getPerPage(int offset) {
        Session session = HibernateUtil.getSession();
        List<InventoryEntity> inventoryEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM inventory LIMIT 5 OFFSET " + offset);

                while (resultSet.next()) {
                    InventoryEntity inventory = new InventoryEntity();
                    ProductEntity productEntity = new ProductEntity();
                    productEntity.setId(resultSet.getInt("productId"));

                    inventory.setId(resultSet.getInt("id"));
                    inventory.setProduct(productEntity);
                    inventory.setStock(resultSet.getInt("stock"));
                    inventory.setRegisterAt(resultSet.getTimestamp("registerAt"));

                    inventoryEntityList.add(inventory);
                }
            }
        });
        session.close();
        return inventoryEntityList;
    }
}
