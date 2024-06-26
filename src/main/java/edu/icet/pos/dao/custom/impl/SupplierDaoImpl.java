package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.SupplierDao;
import edu.icet.pos.entity.SupplierEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public void save(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(supplierEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
