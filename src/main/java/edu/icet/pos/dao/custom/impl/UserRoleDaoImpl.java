package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.UserRoleDao;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserRoleDaoImpl implements UserRoleDao {
    @Override
    public void save(UserRoleEntity userRoleEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(userRoleEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public UserRoleEntity get(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        UserRoleEntity userRoleEntity;
        try {
            tx = session.beginTransaction();
            userRoleEntity = session.get(UserRoleEntity.class, id);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return userRoleEntity;
    }

    @Override
    public UserRoleEntity getByName(String name) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        UserRoleEntity userRoleEntity;
        try{
            tx = session.beginTransaction();
            userRoleEntity = session.createQuery("SELECT a FROM UserRoleEntity a WHERE name='"+name+"'", UserRoleEntity.class).getSingleResult();
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return userRoleEntity;
    }
}
