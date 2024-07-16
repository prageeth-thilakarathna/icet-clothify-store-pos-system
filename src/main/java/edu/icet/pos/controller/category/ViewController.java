package edu.icet.pos.controller.category;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.CategoryBo;
import edu.icet.pos.controller.category.custom.CategoryTable;
import edu.icet.pos.controller.category.custom.CategoryView;
import edu.icet.pos.model.category.Category;
import edu.icet.pos.model.category.TblCategoryView;
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

public class ViewController implements CategoryView {
    @FXML
    private Label dspCount;
    @FXML
    private Pagination tblPagination;

    private final CategoryBo categoryBo = BoFactory.getBo(BoType.CATEGORY);
    private CategoryTable categoryTable;
    private static final String DELETION = "deletion";

    private ObservableList<TblCategoryView> getCategoryTblPerPage(int pageIndex) {
        ObservableList<TblCategoryView> categoryTblList = FXCollections.observableArrayList();
        try {
            assert categoryBo != null;
            List<Category> categoryList = categoryBo.getCategoryPerPage(pageIndex * 5);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (Category category : categoryList) {
                TblCategoryView tblCategoryOb = new TblCategoryView(
                        String.valueOf(category.getId()),
                        category.getName(),
                        dateFormat.format(category.getRegisterAt()),
                        dateFormat.format(category.getModifyAt()),
                        Boolean.TRUE.equals(category.getIsActive()) ? "Active" : "Disable"
                );
                categoryTblList.add(tblCategoryOb);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return categoryTblList;
    }

    private void categoryCountUpdate() {
        try {
            assert categoryBo != null;
            dspCount.setText(String.valueOf(categoryBo.getCategoryCount()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private Node createTblPage(int pageIndex) {
        if (categoryTable == null) {
            categoryTable = CategoryCenterController.getInstance().getFxmlLoaderTable().getController();
        }
        TableView<TblCategoryView> tableView = categoryTable.getTable();
        tableView.setItems(getCategoryTblPerPage(pageIndex));
        return tableView;
    }

    private int getPageCount() {
        int pageCount = 0;
        try {
            assert categoryBo != null;
            int categoryCount = categoryBo.getCategoryCount();
            if (categoryCount > 5) {
                int tempFirst = categoryCount / 5;
                int tempSecond = categoryCount % 5;

                if (tempSecond != 0) {
                    pageCount = tempFirst + 1;
                } else {
                    pageCount = tempFirst;
                }
            } else {
                pageCount = 1;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return pageCount;
    }

    private int getCurrentPageIndex(int pageIndex, String name) {
        if (Objects.equals(name, "registration")) {
            return getPageCount() - 1;
        } else if ("modification".equals(name)) {
            return pageIndex;
        } else if (DELETION.equals(name) && pageIndex == getPageCount()) {
            return pageIndex - 1;
        } else if (DELETION.equals(name) && pageIndex < getPageCount()) {
            return pageIndex;
        } else if (DELETION.equals(name) && (pageIndex + 1) == getPageCount()) {
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
        categoryCountUpdate();
    }

    @Override
    public void loadTable() {
        tblPagination.setPageFactory(this::createTblPage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryCountUpdate();
        tblPagination.setMaxPageIndicatorCount(10);
        tblPagination.setPageCount(getPageCount());
    }
}
