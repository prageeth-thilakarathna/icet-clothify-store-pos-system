package edu.icet.pos.controller.order;

import edu.icet.pos.controller.order.custom.OrderTable;
import edu.icet.pos.model.order.TblOrderView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements OrderTable {
    @FXML
    private TableView<TblOrderView> tblOrder;
    @FXML
    private TableColumn<TblOrderView, String> colId;
    @FXML
    private TableColumn<TblOrderView, String> colCustomerName;
    @FXML
    private TableColumn<TblOrderView, String> colEmployeeName;
    @FXML
    private TableColumn<TblOrderView, String> colPaymentType;
    @FXML
    private TableColumn<TblOrderView, String> colNetTotal;
    @FXML
    private TableColumn<TblOrderView, String> colRegisterAt;
    @FXML
    private TableColumn<TblOrderView, String> colReturn;

    @Override
    public TableView<TblOrderView> getTable() {
        return tblOrder;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colNetTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));
        colRegisterAt.setCellValueFactory(new PropertyValueFactory<>("registerAt"));
        colReturn.setCellValueFactory(new PropertyValueFactory<>("returnStatus"));
        tblOrder.setFixedCellSize(29.1);
    }
}
