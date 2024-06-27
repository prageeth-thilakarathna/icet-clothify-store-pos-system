package edu.icet.pos.controller.supplier;

import edu.icet.pos.controller.supplier.custom.SupplierTable;
import edu.icet.pos.model.supplier.TblSupplierView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements SupplierTable {
    @FXML
    private TableView<TblSupplierView> tblSupplier;
    @FXML
    private TableColumn<TblSupplierView, String> colId;
    @FXML
    private TableColumn<TblSupplierView, String> colFullName;
    @FXML
    private TableColumn<TblSupplierView, String> colContact;
    @FXML
    private TableColumn<TblSupplierView, String> colAddress;
    @FXML
    private TableColumn<TblSupplierView, String> colRegisterAt;
    @FXML
    private TableColumn<TblSupplierView, String> colModifyAt;
    @FXML
    private TableColumn<TblSupplierView, String> colStatus;

    @Override
    public TableView<TblSupplierView> getTable() {
        return tblSupplier;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colRegisterAt.setCellValueFactory(new PropertyValueFactory<>("registerAt"));
        colModifyAt.setCellValueFactory(new PropertyValueFactory<>("modifyAt"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
