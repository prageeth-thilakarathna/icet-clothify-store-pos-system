package edu.icet.pos.bo.custom;

import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.model.employee.Employee;

import java.util.List;

public interface EmployeeBo extends SuperBo {
    Employee getEmployeeByUserId(Integer id);

    List<Employee> getAllEmployee();

    void employeeRegister(Employee employee);

    Employee getEmployee(Integer id);
}
