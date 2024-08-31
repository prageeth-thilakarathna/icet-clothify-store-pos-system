package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.entity.UserEntity;

import java.util.List;

public interface UserDao extends SuperDao {
    void save(UserEntity userEntity);

    UserEntity getByEmail(String email);

    UserEntity get(Integer id);

    void update(UserEntity userEntity);

    void delete(UserEntity userEntity);

    List<UserEntity> getAll();

    int count();

    List<UserEntity> getPerPage(int offset);

    List<UserEntity> getNotIsExist();

    void employeeSave(EmployeeEntity employeeEntity);

    EmployeeEntity getEmployee(Integer id);

    void update(EmployeeEntity employeeEntity);

    void delete(EmployeeEntity employeeEntity);

    int employeeCount();

    List<EmployeeEntity> getEmployeePerPage(int offset);

    List<EmployeeEntity> getAllEmployee();

    EmployeeEntity getEmployeeByUserId(Integer id);

    List<EmployeeEntity> getFirstEmployee();

    List<EmployeeEntity> getEmployeeByYear(String year);
}
