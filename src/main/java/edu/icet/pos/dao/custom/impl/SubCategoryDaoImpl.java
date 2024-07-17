package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.SubCategoryDao;
import edu.icet.pos.entity.CategoryEntity;
import edu.icet.pos.entity.SubCategoryEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SubCategoryDaoImpl implements SubCategoryDao {
    @Override
    public void save(SubCategoryEntity subCategoryEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.persist(subCategoryEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public SubCategoryEntity get(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        SubCategoryEntity subCategoryEntity;
        try {
            tx = session.beginTransaction();
            subCategoryEntity = session.get(SubCategoryEntity.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return subCategoryEntity;
    }

    @Override
    public void update(SubCategoryEntity subCategoryEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.merge(subCategoryEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(SubCategoryEntity subCategoryEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.remove(subCategoryEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public int count() {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM sub_category");
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        session.close();
        return count.get();
    }

    @Override
    public List<SubCategoryEntity> getPerPage(int offset) {
        Session session = HibernateUtil.getSession();
        List<SubCategoryEntity> subCategoryEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM sub_category LIMIT 5 OFFSET " + offset);

                while (resultSet.next()) {
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.setId(resultSet.getInt("categoryId"));

                    SubCategoryEntity subCategory = new SubCategoryEntity();
                    subCategory.setId(resultSet.getInt("id"));
                    subCategory.setName(resultSet.getString("name"));
                    subCategory.setRegisterAt(resultSet.getTimestamp("registerAt"));
                    subCategory.setModifyAt(resultSet.getTimestamp("modifyAt"));
                    subCategory.setIsActive(resultSet.getBoolean("isActive"));
                    subCategory.setCategory(categoryEntity);

                    subCategoryEntityList.add(subCategory);
                }
            }
        });
        session.close();
        return subCategoryEntityList;
    }

    @Override
    public List<SubCategoryEntity> getByCategory(CategoryEntity categoryEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<SubCategoryEntity> subCategoryEntityList;
        try {
            tx = session.beginTransaction();
            String sql = "FROM SubCategoryEntity O WHERE O.category = :categoryEntity";
            subCategoryEntityList = session.createQuery(sql, SubCategoryEntity.class)
                    .setParameter("categoryEntity", categoryEntity)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return subCategoryEntityList;
    }

    @Override
    public SubCategoryEntity getByName(String name, CategoryEntity categoryEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        SubCategoryEntity subCategoryEntity;
        try {
            tx = session.beginTransaction();
            String sql = "FROM SubCategoryEntity S WHERE S.name = :name AND " +
                    "S.category = :categoryEntity";
            subCategoryEntity = session.createQuery(sql, SubCategoryEntity.class)
                    .setParameter("name", name)
                    .setParameter("categoryEntity", categoryEntity)
                    .getSingleResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return subCategoryEntity;
    }
}
