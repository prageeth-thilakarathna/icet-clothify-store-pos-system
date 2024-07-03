package edu.icet.pos.controller.user;

import edu.icet.pos.controller.user.custom.UserTable;
import edu.icet.pos.model.user.TblUserView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements UserTable {
    @FXML
    private TableView<TblUserView> tblUser;
    @FXML
    private TableColumn<TblUserView, String> colId;
    @FXML
    private TableColumn<TblUserView, String> colEmail;
    @FXML
    private TableColumn<TblUserView, String> colRole;
    @FXML
    private TableColumn<TblUserView, String> colRegisterAt;
    @FXML
    private TableColumn<TblUserView, String> colModifyAt;
    @FXML
    private TableColumn<TblUserView, String> colStatus;

    @Override
    public TableView<TblUserView> getTable() {
        return tblUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("eMail"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        colRegisterAt.setCellValueFactory(new PropertyValueFactory<>("registerAt"));
        colModifyAt.setCellValueFactory(new PropertyValueFactory<>("modifyAt"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblUser.setFixedCellSize(29.1);
    }
}
