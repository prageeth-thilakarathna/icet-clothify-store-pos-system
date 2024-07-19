package edu.icet.pos.dao.custom.impl;

import edu.icet.pos.dao.custom.UserDao;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.entity.JobRoleEntity;
import edu.icet.pos.entity.UserEntity;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDaoImpl implements UserDao {
    private static final String IS_ACTIVE = "isActive";

    @Override
    public void save(UserEntity userEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.persist(userEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public UserEntity getByEmail(String email) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        UserEntity userEntity;
        try {
            tx = session.beginTransaction();
            userEntity = session.createQuery("SELECT a FROM UserEntity a WHERE eMail='" + email + "'", UserEntity.class).getSingleResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
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
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return userEntity;
    }

    @Override
    public void update(UserEntity userEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.merge(userEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(UserEntity userEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.remove(userEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public List<UserEntity> getAll() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<UserEntity> userEntityList;
        try {
            tx = session.beginTransaction();
            userEntityList = session.createQuery("SELECT a FROM UserEntity a", UserEntity.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return userEntityList;
    }

    @Override
    public int count() {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM user");
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        session.close();
        return count.get();
    }

    @Override
    public List<UserEntity> getPerPage(int offset) {
        Session session = HibernateUtil.getSession();
        List<UserEntity> userEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM user LIMIT 5 OFFSET " + offset);

                while (resultSet.next()) {
                    UserEntity user = new UserEntity();
                    UserRoleEntity userRoleEntity = new UserRoleEntity();
                    userRoleEntity.setId(resultSet.getInt("userRoleId"));

                    user.setId(resultSet.getInt("id"));
                    user.setEMail(resultSet.getString("eMail"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRegisterAt(resultSet.getTimestamp("registerAt"));
                    user.setModifyAt(resultSet.getTimestamp("modifyAt"));
                    user.setIsActive(resultSet.getBoolean(IS_ACTIVE));
                    user.setUserRole(userRoleEntity);

                    userEntityList.add(user);
                }
            }
        });
        session.close();
        return userEntityList;
    }

    @Override
    public List<UserEntity> getNotIsExist() {
        Session session = HibernateUtil.getSession();
        List<UserEntity> userEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT eMail,isActive FROM user " +
                        "WHERE NOT EXISTS (SELECT * FROM employee " +
                        "WHERE user.id = employee.userId)");
                while (resultSet.next()) {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setEMail(resultSet.getString("eMail"));
                    userEntity.setIsActive(resultSet.getBoolean(IS_ACTIVE));
                    userEntityList.add(userEntity);
                }
            }
        });
        session.close();
        return userEntityList;
    }

    @Override
    public void employeeSave(EmployeeEntity employeeEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.persist(employeeEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public EmployeeEntity getEmployee(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        EmployeeEntity employeeEntity;
        try {
            tx = session.beginTransaction();
            employeeEntity = session.get(EmployeeEntity.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return employeeEntity;
    }

    @Override
    public void update(EmployeeEntity employeeEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.merge(employeeEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(EmployeeEntity employeeEntity) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSession()) {
            tx = session.beginTransaction();
            session.remove(employeeEntity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public int employeeCount() {
        Session session = HibernateUtil.getSession();
        AtomicInteger count = new AtomicInteger();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM employee");
                resultSet.next();
                count.set(resultSet.getInt("row_count"));
            }
        });
        session.close();
        return count.get();
    }

    @Override
    public List<EmployeeEntity> getEmployeePerPage(int offset) {
        Session session = HibernateUtil.getSession();
        List<EmployeeEntity> employeeEntityList = new ArrayList<>();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM employee LIMIT 5 OFFSET " + offset);

                while (resultSet.next()) {
                    EmployeeEntity employee = new EmployeeEntity();
                    employee.setId(resultSet.getInt("id"));

                    UserEntity user = new UserEntity();
                    user.setId(resultSet.getInt("userId"));
                    employee.setUser(user);

                    JobRoleEntity jobRole = new JobRoleEntity();
                    jobRole.setId(resultSet.getInt("jobRoleId"));
                    employee.setJobRole(jobRole);

                    employee.setTitle(resultSet.getString("title"));
                    employee.setFirstName(resultSet.getString("firstName"));
                    employee.setLastName(resultSet.getString("lastName"));
                    employee.setContact(resultSet.getString("contact"));
                    employee.setAddress(resultSet.getString("address"));
                    employee.setRegisterAt(resultSet.getTimestamp("registerAt"));
                    employee.setModifyAt(resultSet.getTimestamp("modifyAt"));
                    employee.setIsActive(resultSet.getBoolean(IS_ACTIVE));

                    employeeEntityList.add(employee);
                }
            }
        });
        session.close();
        return employeeEntityList;
    }

    @Override
    public List<EmployeeEntity> getAllEmployee() {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<EmployeeEntity> employeeEntityList;
        try {
            tx = session.beginTransaction();
            employeeEntityList = session.createQuery("SELECT a FROM EmployeeEntity a", EmployeeEntity.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
        return employeeEntityList;
    }

    @Override
    public EmployeeEntity getEmployeeByUserId(Integer id) {
        Session session = HibernateUtil.getSession();
        EmployeeEntity employeeEntity = new EmployeeEntity();
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM employee WHERE userId=" + id);

                while (resultSet.next()) {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setId(resultSet.getInt("userId"));

                    JobRoleEntity jobRoleEntity = new JobRoleEntity();
                    jobRoleEntity.setId(resultSet.getInt("jobRoleId"));

                    employeeEntity.setId(resultSet.getInt("id"));
                    employeeEntity.setUser(userEntity);
                    employeeEntity.setJobRole(jobRoleEntity);
                    employeeEntity.setTitle(resultSet.getString("title"));
                    employeeEntity.setFirstName(resultSet.getString("firstName"));
                    employeeEntity.setLastName(resultSet.getString("lastName"));
                    employeeEntity.setContact(resultSet.getString("contact"));
                    employeeEntity.setAddress(resultSet.getString("address"));
                    employeeEntity.setRegisterAt(resultSet.getTimestamp("registerAt"));
                    employeeEntity.setModifyAt(resultSet.getTimestamp("modifyAt"));
                    employeeEntity.setIsActive(resultSet.getBoolean(IS_ACTIVE));
                }
            }
        });
        session.close();
        return employeeEntity;
    }
}
