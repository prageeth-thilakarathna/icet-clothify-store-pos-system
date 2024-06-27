package edu.icet.pos.controller.supplier;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.SupplierBo;
import edu.icet.pos.controller.supplier.custom.SupplierTable;
import edu.icet.pos.controller.supplier.custom.SupplierView;
import edu.icet.pos.model.supplier.Supplier;
import edu.icet.pos.model.supplier.TblSupplierView;
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

public class ViewController implements SupplierView {
    @FXML
    private Label dspCount;
    @FXML
    private Pagination tblPagination;

    private final SupplierBo supplierBo = BoFactory.getBo(BoType.SUPPLIER);
    private SupplierTable supplierTable;
    private static final String DELETION = "deletion";

    private ObservableList<TblSupplierView> getSupplierTblPerPage(int pageIndex){
        ObservableList<TblSupplierView> supplierTblList = FXCollections.observableArrayList();
        try{
            assert supplierBo != null;
            List<Supplier> supplierList = supplierBo.getSupplierPerPage(pageIndex*5);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            for(Supplier supplier : supplierList){
                TblSupplierView tblSupplierOb = new TblSupplierView(
                        String.valueOf(supplier.getId()),
                        supplier.getTitle()+". "+supplier.getFirstName()+" "+supplier.getLastName(),
                        supplier.getContact(),
                        supplier.getAddress(),
                        dateFormat.format(supplier.getRegisterAt()),
                        dateFormat.format(supplier.getModifyAt()),
                        Boolean.TRUE.equals(supplier.getIsActive()) ? "Active":"Disable"
                );
                supplierTblList.add(tblSupplierOb);
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return supplierTblList;
    }

    private void supplierCountUpdate(){
        try{
            assert supplierBo != null;
            dspCount.setText(String.valueOf(supplierBo.getSupplierCount()));
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private Node createTblPage(int pageIndex){
        if(supplierTable ==null){
            supplierTable = SupplierCenterController.getInstance().getFxmlLoaderTable().getController();
        }
        TableView<TblSupplierView> tableView = supplierTable.getTable();
        tableView.setItems(getSupplierTblPerPage(pageIndex));
        return tableView;
    }

    private int getPageCount(){
        int pageCount = 0;
        try{
            assert supplierBo != null;
            int supplierCount = supplierBo.getSupplierCount();
            if(supplierCount>5){
                int tempFirst = supplierCount/5;
                int tempSecond = supplierCount%5;

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
        supplierCountUpdate();
    }

    @Override
    public void loadTable() {
        tblPagination.setPageFactory(this::createTblPage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supplierCountUpdate();
        tblPagination.setMaxPageIndicatorCount(10);
        tblPagination.setPageCount(getPageCount());
    }
}
