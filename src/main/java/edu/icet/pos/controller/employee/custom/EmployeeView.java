package edu.icet.pos.controller.employee.custom;

import javafx.fxml.Initializable;

public interface EmployeeView extends Initializable {
    void loadTable();

    void updateTbl(String name);
}
