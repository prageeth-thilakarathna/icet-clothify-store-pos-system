package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.InventoryDao;
import edu.icet.pos.entity.InventoryEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;

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
}
