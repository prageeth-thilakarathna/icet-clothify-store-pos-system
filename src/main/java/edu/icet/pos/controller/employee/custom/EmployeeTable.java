package edu.icet.pos.controller.employee.custom;

import edu.icet.pos.model.employee.TblEmployeeView;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public interface EmployeeTable extends Initializable {
    TableView<TblEmployeeView> getTable();
}
