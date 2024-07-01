package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.ProductDao;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return productEntity;
    }

    @Override
    public void update(ProductEntity productEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(productEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(ProductEntity productEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(productEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
