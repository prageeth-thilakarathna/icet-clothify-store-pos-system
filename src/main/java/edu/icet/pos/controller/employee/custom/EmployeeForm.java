package edu.icet.pos.controller.employee.custom;

import edu.icet.pos.model.employee.Employee;
import javafx.fxml.Initializable;

public interface EmployeeForm extends Initializable {
    void loadEmployeeToForm(Employee employee);

    void clearEmployee();

    void refreshForm();
}
