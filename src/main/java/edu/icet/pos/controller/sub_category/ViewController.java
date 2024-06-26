package edu.icet.pos.controller.sub_category;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.bo.custom.SubCategoryBo;
import edu.icet.pos.controller.sub_category.custom.SubCategoryTable;
import edu.icet.pos.controller.sub_category.custom.SubCategoryView;
import edu.icet.pos.model.sub_category.SubCategory;
import edu.icet.pos.model.sub_category.TblSubCategoryView;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewController implements SubCategoryView {
    @FXML
    private Label dspCount;
    @FXML
    private Pagination tblPagination;

    private final SubCategoryBo subCategoryBo = BoFactory.getBo(BoType.SUB_CATEGORY);
    private final CategoryBo categoryBo = BoFactory.getBo(BoType.CATEGORY);
    private SubCategoryTable subCategoryTable;
    private static final String DELETION = "deletion";

    private ObservableList<TblSubCategoryView> getSubCategoryTblPerPage(int pageIndex){
        ObservableList<TblSubCategoryView> subCategoryTblList = FXCollections.observableArrayList();
        try{
            assert subCategoryBo != null;
            List<SubCategory> subCategoryList = subCategoryBo.getSubCategoryPerPage(pageIndex*5);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            for(SubCategory subCategory : subCategoryList){
                assert categoryBo != null;
                TblSubCategoryView tblSubCategoryOb = new TblSubCategoryView(
                        String.valueOf(subCategory.getId()),
                        categoryBo.getCategory(subCategory.getCategory().getId()).getName(),
                        subCategory.getName(),
                        dateFormat.format(subCategory.getRegisterAt()),
                        dateFormat.format(subCategory.getModifyAt()),
                        Boolean.TRUE.equals(subCategory.getIsActive()) ? "Active":"Disable"
                );
                subCategoryTblList.add(tblSubCategoryOb);
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return subCategoryTblList;
    }

    private void subCategoryCountUpdate(){
        try{
            assert subCategoryBo != null;
            dspCount.setText(String.valueOf(subCategoryBo.getSubCategoryCount()));
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private Node createTblPage(int pageIndex){
        if(subCategoryTable ==null){
            subCategoryTable = SubCategoryCenterController.getInstance().getFxmlLoaderTable().getController();
        }
        TableView<TblSubCategoryView> tableView = subCategoryTable.getTable();
        tableView.setItems(getSubCategoryTblPerPage(pageIndex));
        return tableView;
    }

    private int getPageCount(){
        int pageCount = 0;
        try{
            assert subCategoryBo != null;
            int subCategoryCount = subCategoryBo.getSubCategoryCount();
            if(subCategoryCount>5){
                int tempFirst = subCategoryCount/5;
                int tempSecond = subCategoryCount%5;

                if(tempSecond!=0){
                    pageCount = tempFirst+1;
                } else {
                    pageCount = tempFirst;
                }
            } else {
                pageCount = 1;
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return pageCount;
    }

    private int getCurrentPageIndex(int pageIndex, String name){
        if(Objects.equals(name, "registration")){
            return getPageCount()-1;
        } else if("modification".equals(name)){
            return pageIndex;
        } else if(DELETION.equals(name) && pageIndex==getPageCount()) {
            return pageIndex-1;
        } else if(DELETION.equals(name) && pageIndex<getPageCount()) {
            return pageIndex;
        } else if(DELETION.equals(name) && (pageIndex+1)==getPageCount()) {
            return pageIndex;
        } else {
            return 0;
        }
    }

    @Override
    public void updateTbl(String name) {
        int pageIndex = tblPagination.getCurrentPageIndex();
        tblPagination.setPageCount(getPageCount());
        tblPagination.setPageFactory(this::createTblPage);
        tblPagination.setCurrentPageIndex(getCurrentPageIndex(pageIndex, name));
        subCategoryCountUpdate();
    }

    @Override
    public void loadTable() {
        tblPagination.setPageFactory(this::createTblPage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subCategoryCountUpdate();
        tblPagination.setMaxPageIndicatorCount(10);
        tblPagination.setPageCount(getPageCount());
    }
}
