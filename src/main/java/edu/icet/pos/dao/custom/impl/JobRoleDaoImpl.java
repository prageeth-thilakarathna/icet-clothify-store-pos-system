package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.JobRoleDao;
import edu.icet.pos.entity.JobRoleEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class JobRoleDaoImpl implements JobRoleDao {
    @Override
    public void save(JobRoleEntity jobRoleEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.persist(jobRoleEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public JobRoleEntity getByName(String name) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        JobRoleEntity jobRoleEntity;
        try {
            tx = session.beginTransaction();
            jobRoleEntity = session.createQuery("SELECT a FROM JobRoleEntity a WHERE name='" + name + "'", JobRoleEntity.class).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return jobRoleEntity;
    }

    @Override
    public List<JobRoleEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<JobRoleEntity> jobRoleEntityList;
        try {
            tx = session.beginTransaction();
            jobRoleEntityList = session.createQuery("SELECT a FROM JobRoleEntity a", JobRoleEntity.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return jobRoleEntityList;
    }

    @Override
    public JobRoleEntity get(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        JobRoleEntity jobRoleEntity;
        try {
            tx = session.beginTransaction();
            jobRoleEntity = session.get(JobRoleEntity.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return jobRoleEntity;
    }
}
