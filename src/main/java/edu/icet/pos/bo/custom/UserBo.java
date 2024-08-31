package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.model.user.User;

import java.util.List;

public interface UserBo extends SuperBo {
    void userRegister(User user);

    User getUserByEmail(String email);

    User getUser(Integer id);

    void userUpdate(User user);

    void userDelete(User user);

    List<User> getAllUser();

    int getUserCount();

    List<User> getUserPerPage(int offset);

    List<User> getUsersNotExistInEmployee();

    void employeeRegister(Employee employee);

    Employee getEmployeeById(Integer id);

    void employeeUpdate(Employee employee);

    void employeeDelete(Employee employee);

    int getEmployeeCount();

    List<Employee> getEmployeePerPage(int offset);

    List<Employee> getAllEmployee();

    Employee getEmployeeByUserId(Integer id);

    List<Employee> getFirstEmployee();

    List<Employee> getEmployeeByYear(String year);
}
