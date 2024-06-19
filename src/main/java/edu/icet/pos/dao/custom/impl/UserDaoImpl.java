package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.UserDao;
import edu.icet.pos.entity.UserEntity;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoImpl implements UserDao {
    @Override
    public void save(UserEntity userEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(userEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public UserEntity getByEmail(String email) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        UserEntity userEntity;
        try{
            tx = session.beginTransaction();
            userEntity = session.createQuery("SELECT a FROM UserEntity a WHERE eMail='"+email+"'", UserEntity.class).getSingleResult();
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return userEntity;
    }

    @Override
    public UserEntity get(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        UserEntity userEntity;
        try {
            tx = session.beginTransaction();
            userEntity = session.get(UserEntity.class, id);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return userEntity;
    }
}
