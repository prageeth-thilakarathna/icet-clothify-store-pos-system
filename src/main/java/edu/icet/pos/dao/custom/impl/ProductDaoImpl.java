package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.ProductDao;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.entity.SubCategoryEntity;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductDaoImpl implements ProductDao {
    @Override
    public ProductEntity save(ProductEntity productEntity) {
        Session session = HibernateUtil.getSingletonSession();
        HibernateUtil.singletonBegin();
        session.persist(productEntity);
        return productEntity;
    }

    @Override
    public ProductEntity get(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        ProductEntity productEntity;
        try {
            tx = session.beginTransaction();
            productEntity = session.get(ProductEntity.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return productEntity;
    }

    @Override
    public void update(ProductEntity productEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.merge(productEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(ProductEntity productEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.remove(productEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public int count(boolean filter) {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet;
                if (!filter) {
                    resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM product");
                } else {
                    resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM product WHERE isActive=" + true);
                }
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        session.close();
        return count.get();
    }

    @Override
    public List<ProductEntity> getPerPage(boolean filter, int limit, int offset) {
        Session session = HibernateUtil.getSession();
        List<ProductEntity> productEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet;
                if (!filter) {
                    resultSet = statement.executeQuery("SELECT * FROM product LIMIT " + limit + " OFFSET " + offset);
                } else {
                    resultSet = statement.executeQuery("SELECT * FROM product WHERE isActive=" + true + " LIMIT " + limit + " OFFSET " + offset);
                }
                while (resultSet.next()) {
                    ProductEntity product = new ProductEntity();

                    SubCategoryEntity subCategoryEntity = new SubCategoryEntity();
                    subCategoryEntity.setId(resultSet.getInt("subCategoryId"));

                    SupplierEntity supplierEntity = new SupplierEntity();
                    supplierEntity.setId(resultSet.getInt("supplierId"));

                    product.setId(resultSet.getInt("id"));
                    product.setSubCategory(subCategoryEntity);
                    product.setSupplier(supplierEntity);
                    product.setDescription(resultSet.getString("description"));
                    product.setSize(resultSet.getString("size"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantityOnHand(resultSet.getInt("quantityOnHand"));
                    product.setImage(resultSet.getBlob("image"));
                    product.setRegisterAt(resultSet.getTimestamp("registerAt"));
                    product.setModifyAt(resultSet.getTimestamp("modifyAt"));
                    product.setIsActive(resultSet.getBoolean("isActive"));

                    productEntityList.add(product);
                }
            }
        });
        session.close();
        return productEntityList;
    }

    @Override
    public List<ProductEntity> getPerPage(String ids, int limit, int offset) {
        Session session = HibernateUtil.getSession();
        List<ProductEntity> productEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM product WHERE subCategoryId IN (" + ids + ") AND isActive=" + true + " LIMIT " + limit + " OFFSET " + offset);

                while (resultSet.next()) {
                    ProductEntity product = new ProductEntity();

                    SubCategoryEntity subCategoryEntity = new SubCategoryEntity();
                    subCategoryEntity.setId(resultSet.getInt("subCategoryId"));

                    SupplierEntity supplierEntity = new SupplierEntity();
                    supplierEntity.setId(resultSet.getInt("supplierId"));

                    product.setId(resultSet.getInt("id"));
                    product.setSubCategory(subCategoryEntity);
                    product.setSupplier(supplierEntity);
                    product.setDescription(resultSet.getString("description"));
                    product.setSize(resultSet.getString("size"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantityOnHand(resultSet.getInt("quantityOnHand"));
                    product.setImage(resultSet.getBlob("image"));
                    product.setRegisterAt(resultSet.getTimestamp("registerAt"));
                    product.setModifyAt(resultSet.getTimestamp("modifyAt"));
                    product.setIsActive(resultSet.getBoolean("isActive"));

                    productEntityList.add(product);
                }
            }
        });
        session.close();
        return productEntityList;
    }

    @Override
    public int countByFilter(String ids) {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM product WHERE subCategoryId IN (" + ids + ") AND isActive=" + true);
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        session.close();
        return count.get();
    }

    @Override
    public void updateAvaQty(ProductEntity productEntity) {
        Session session = HibernateUtil.getSingletonSession();
        session.merge(productEntity);
    }
}
