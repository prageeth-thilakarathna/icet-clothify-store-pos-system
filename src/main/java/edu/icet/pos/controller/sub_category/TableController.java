package edu.icet.pos.controller.sub_category;

import edu.icet.pos.controller.sub_category.custom.SubCategoryTable;
import edu.icet.pos.model.sub_category.TblSubCategoryView;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements SubCategoryTable {
    @FXML
    private TableView<TblSubCategoryView> tblSubCategory;
    @FXML
    private TableColumn<TblSubCategoryView, String> colId;
    @FXML
    private TableColumn<TblSubCategoryView, String> colCategoryName;
    @FXML
    private TableColumn<TblSubCategoryView, String> colSubCategoryName;
    @FXML
    private TableColumn<TblSubCategoryView, String> colRegisterAt;
    @FXML
    private TableColumn<TblSubCategoryView, String> colModifyAt;
    @FXML
    private TableColumn<TblSubCategoryView, String> colStatus;

    @Override
    public TableView<TblSubCategoryView> getTable() {
        return tblSubCategory;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colSubCategoryName.setCellValueFactory(new PropertyValueFactory<>("subCategoryName"));
        colRegisterAt.setCellValueFactory(new PropertyValueFactory<>("registerAt"));
        colModifyAt.setCellValueFactory(new PropertyValueFactory<>("modifyAt"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblSubCategory.setFixedCellSize(31.5);
    }
}
