package edu.icet.pos.controller.category;

import edu.icet.pos.controller.category.custom.CategoryTable;
import edu.icet.pos.model.category.TblCategoryView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements CategoryTable {
    @FXML
    private TableView<TblCategoryView> tblCategory;
    @FXML
    private TableColumn<TblCategoryView, String> colId;
    @FXML
    private TableColumn<TblCategoryView, String> colName;
    @FXML
    private TableColumn<TblCategoryView, String> colRegisterAt;
    @FXML
    private TableColumn<TblCategoryView, String> colModifyAt;
    @FXML
    private TableColumn<TblCategoryView, String> colStatus;

    @Override
    public TableView<TblCategoryView> getTable() {
        return tblCategory;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRegisterAt.setCellValueFactory(new PropertyValueFactory<>("registerAt"));
        colModifyAt.setCellValueFactory(new PropertyValueFactory<>("modifyAt"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblCategory.setFixedCellSize(31.5);
    }
}
