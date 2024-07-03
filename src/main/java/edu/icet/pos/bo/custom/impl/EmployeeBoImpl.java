package edu.icet.pos.bo.custom.impl;

import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.dao.DaoFactory;
import edu.icet.pos.dao.custom.EmployeeDao;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class EmployeeBoImpl implements EmployeeBo {
    private final EmployeeDao employeeDao = DaoFactory.getDao(DaoType.EMPLOYEE);

    @Override
    public Employee getEmployeeByUserId(Integer id) {
        assert employeeDao != null;
        return new ModelMapper().map(employeeDao.getByUserId(id), Employee.class);
    }

    @Override
    public List<Employee> getAllEmployee() {
        assert employeeDao != null;
        return new ModelMapper().map(employeeDao.getAll(), new TypeToken<List<Employee>>() {}.getType());
    }

    @Override
    public void employeeRegister(Employee employee) {
        assert employeeDao != null;
        employeeDao.save(new ModelMapper().map(employee, EmployeeEntity.class));
    }

    @Override
    public Employee getEmployee(Integer id) {
        assert employeeDao != null;
        return new ModelMapper().map(employeeDao.get(id), Employee.class);
    }
}
