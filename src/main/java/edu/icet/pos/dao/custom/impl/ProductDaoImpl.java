package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.ProductDao;
import edu.icet.pos.entity.ProductEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProductDaoImpl implements ProductDao {
    @Override
    public void save(ProductEntity productEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(productEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
