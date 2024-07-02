package edu.icet.pos.dao.custom;

import edu.icet.pos.dao.SuperDao;
import edu.icet.pos.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeDao extends SuperDao {
    EmployeeEntity getByUserId(Integer id);

    List<EmployeeEntity> getAll();

    void save(EmployeeEntity employeeEntity);
}
