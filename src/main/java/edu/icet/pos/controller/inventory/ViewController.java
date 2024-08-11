package edu.icet.pos.controller.inventory;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.InventoryBo;
import edu.icet.pos.controller.inventory.custom.InventoryTable;
import edu.icet.pos.controller.inventory.custom.InventoryView;
import edu.icet.pos.model.inventory.Inventory;
import edu.icet.pos.model.inventory.TblInventoryView;
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

public class ViewController implements InventoryView {
    @FXML
    private Label dspCount;
    @FXML
    private Pagination tblPagination;

    private final InventoryBo inventoryBo = BoFactory.getBo(BoType.INVENTORY);
    private InventoryTable inventoryTable;

    private ObservableList<TblInventoryView> getInventoryTblPerPage(int pageIndex) {
        ObservableList<TblInventoryView> inventoryTblList = FXCollections.observableArrayList();
        try {
            assert inventoryBo != null;
            List<Inventory> inventoryList = inventoryBo.getInventoryPerPage(pageIndex * 5);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (Inventory inventory : inventoryList) {
                TblInventoryView tblInventoryOb = new TblInventoryView(
                        String.valueOf(inventory.getId()),
                        String.valueOf(inventory.getStock()),
                        dateFormat.format(inventory.getRegisterAt()),
                        String.valueOf(inventory.getProduct().getId())
                );
                inventoryTblList.add(tblInventoryOb);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return inventoryTblList;
    }

    private Node createTblPage(int pageIndex) {
        if (inventoryTable == null) {
            inventoryTable = InventoryCenterController.getInstance().getFxmlLoaderTable().getController();
        }
        TableView<TblInventoryView> tableView = inventoryTable.getTable();
        tableView.setItems(getInventoryTblPerPage(pageIndex));
        return tableView;
    }

    private int getCurrentPageIndex(String name) {
        if (Objects.equals(name, "registration")) {
            return getPageCount() - 1;
        } else {
            return 0;
        }
    }

    @Override
    public void updateTbl(String name) {
        tblPagination.setPageCount(getPageCount());
        tblPagination.setPageFactory(this::createTblPage);
        tblPagination.setCurrentPageIndex(getCurrentPageIndex(name));
        inventoryCountUpdate();
    }

    @Override
    public void loadTable() {
        inventoryCountUpdate();
        tblPagination.setPageCount(getPageCount());
        tblPagination.setPageFactory(this::createTblPage);
    }

    private int getPageCount() {
        int pageCount = 0;
        try {
            assert inventoryBo != null;
            int inventoryCount = inventoryBo.getInventoryCount();
            if (inventoryCount > 5) {
                int tempFirst = inventoryCount / 5;
                int tempSecond = inventoryCount % 5;

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

    private void inventoryCountUpdate() {
        try {
            assert inventoryBo != null;
            dspCount.setText(String.valueOf(inventoryBo.getInventoryCount()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inventoryCountUpdate();
        tblPagination.setMaxPageIndicatorCount(10);
        tblPagination.setPageCount(getPageCount());
    }
}
