package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.InventoryDao;
import edu.icet.pos.entity.InventoryEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InventoryDaoImpl implements InventoryDao {
    @Override
    public void save(InventoryEntity inventoryEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(inventoryEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void saveQtyOnHand(InventoryEntity inventoryEntity) {
        Session session = HibernateUtil.getSingletonSession();
        session.persist(inventoryEntity);
        HibernateUtil.singletonCommit();
    }
}
