package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.EmployeeDao;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public EmployeeEntity getByUserId(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        EmployeeEntity employeeEntity;
        try{
            tx = session.beginTransaction();
            employeeEntity = session.createQuery("SELECT a FROM EmployeeEntity a WHERE userId="+id, EmployeeEntity.class).getSingleResult();
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return employeeEntity;
    }

    @Override
    public List<EmployeeEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<EmployeeEntity> employeeEntityList;
        try {
            tx = session.beginTransaction();
            employeeEntityList = session.createQuery("SELECT a FROM EmployeeEntity a", EmployeeEntity.class).getResultList();
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return employeeEntityList;
    }

    @Override
    public void save(EmployeeEntity employeeEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(employeeEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public EmployeeEntity get(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        EmployeeEntity employeeEntity;
        try {
            tx = session.beginTransaction();
            employeeEntity = session.get(EmployeeEntity.class, id);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return employeeEntity;
    }
}
