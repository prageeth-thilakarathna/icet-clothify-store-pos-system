package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.CategoryDao;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public void save(CategoryEntity categoryEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(categoryEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public CategoryEntity getByName(String name) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        CategoryEntity categoryEntity;
        try{
            tx = session.beginTransaction();
            categoryEntity = session.createQuery("SELECT a FROM CategoryEntity a WHERE name='"+name+"'", CategoryEntity.class).getSingleResult();
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return categoryEntity;
    }
}
