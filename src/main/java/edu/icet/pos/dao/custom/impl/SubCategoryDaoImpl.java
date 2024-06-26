package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.SubCategoryDao;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.entity.SubCategoryEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SubCategoryDaoImpl implements SubCategoryDao {
    @Override
    public void save(SubCategoryEntity subCategoryEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(subCategoryEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public SubCategoryEntity getByName(String name) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        SubCategoryEntity subCategoryEntity;
        try{
            tx = session.beginTransaction();
            subCategoryEntity = session.createQuery("SELECT a FROM SubCategoryEntity a WHERE name='"+name+"'", SubCategoryEntity.class).getSingleResult();
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return subCategoryEntity;
    }
}
