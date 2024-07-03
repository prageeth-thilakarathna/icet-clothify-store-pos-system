package edu.icet.pos.controller.employee;

import edu.icet.pos.controller.employee.custom.EmployeeTable;
import edu.icet.pos.model.employee.TblEmployeeView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements EmployeeTable {
    @FXML
    private TableView<TblEmployeeView> tblEmployee;
    @FXML
    private TableColumn<TblEmployeeView, String> colId;
    @FXML
    private TableColumn<TblEmployeeView, String> colFullName;
    @FXML
    private TableColumn<TblEmployeeView, String> colUserEMail;
    @FXML
    private TableColumn<TblEmployeeView, String> colJobRole;
    @FXML
    private TableColumn<TblEmployeeView, String> colContact;
    @FXML
    private TableColumn<TblEmployeeView, String> colAddress;
    @FXML
    private TableColumn<TblEmployeeView, String> colRegisterAt;
    @FXML
    private TableColumn<TblEmployeeView, String> colModifyAt;
    @FXML
    private TableColumn<TblEmployeeView, String> colStatus;

    @Override
    public TableView<TblEmployeeView> getTable() {
        return tblEmployee;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colUserEMail.setCellValueFactory(new PropertyValueFactory<>("userEMail"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colRegisterAt.setCellValueFactory(new PropertyValueFactory<>("registerAt"));
        colModifyAt.setCellValueFactory(new PropertyValueFactory<>("modifyAt"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblEmployee.setFixedCellSize(29.1);
    }
}
