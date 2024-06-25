package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.CategoryDao;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Override
    public CategoryEntity get(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        CategoryEntity categoryEntity;
        try {
            tx = session.beginTransaction();
            categoryEntity = session.get(CategoryEntity.class, id);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return categoryEntity;
    }

    @Override
    public void delete(CategoryEntity categoryEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(categoryEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(CategoryEntity categoryEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(categoryEntity);
            tx.commit();
        } catch (Exception e){
            if(tx!=null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public int count() {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM category");
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        session.close();
        return count.get();
    }

    @Override
    public List<CategoryEntity> getPerPage(int offset) {
        Session session = HibernateUtil.getSession();
        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM category LIMIT 5 OFFSET "+offset);

                while(resultSet.next()){
                    CategoryEntity category = new CategoryEntity();
                    category.setId(resultSet.getInt("id"));
                    category.setName(resultSet.getString("name"));
                    category.setRegisterAt(resultSet.getDate("registerAt"));
                    category.setModifyAt(resultSet.getDate("modifyAt"));
                    category.setIsActive(resultSet.getBoolean("isActive"));

                    categoryEntityList.add(category);
                }
            }
        });
        session.close();
        return categoryEntityList;
    }
}
