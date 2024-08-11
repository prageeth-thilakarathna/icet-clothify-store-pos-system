package edu.icet.pos.controller.inventory;

import edu.icet.pos.controller.inventory.custom.InventoryTable;
import edu.icet.pos.model.inventory.TblInventoryView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements InventoryTable {
    @FXML
    private TableView<TblInventoryView> tblInventory;
    @FXML
    private TableColumn<TblInventoryView, String> colId;
    @FXML
    private TableColumn<TblInventoryView, String> colStock;
    @FXML
    private TableColumn<TblInventoryView, String> colRegisterAt;
    @FXML
    private TableColumn<TblInventoryView, String> colProductId;

    @Override
    public TableView<TblInventoryView> getTable() {
        return tblInventory;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colRegisterAt.setCellValueFactory(new PropertyValueFactory<>("registerAt"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        tblInventory.setFixedCellSize(29.1);
    }
}
