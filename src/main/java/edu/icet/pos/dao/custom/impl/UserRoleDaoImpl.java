package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.UserRoleDao;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserRoleDaoImpl implements UserRoleDao {
    @Override
    public void save(UserRoleEntity userRoleEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.persist(userRoleEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
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
        } catch (Exception e) {
            if (tx != null) tx.rollback();
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
        try {
            tx = session.beginTransaction();
            userRoleEntity = session.createQuery("SELECT a FROM UserRoleEntity a WHERE name='" + name + "'", UserRoleEntity.class).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return userRoleEntity;
    }

    @Override
    public List<UserRoleEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<UserRoleEntity> userRoleEntityList;
        try {
            tx = session.beginTransaction();
            userRoleEntityList = session.createQuery("SELECT a FROM UserRoleEntity a", UserRoleEntity.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return userRoleEntityList;
    }
}
