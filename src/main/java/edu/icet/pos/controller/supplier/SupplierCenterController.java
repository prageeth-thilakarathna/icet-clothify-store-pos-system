package edu.icet.pos.controller.supplier;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class SupplierCenterController {
    @Getter
    private static final SupplierCenterController instance;
    private final FXMLLoader fxmlLoaderForm = new FXMLLoader(getClass().getResource("/view/supplier/form.fxml"));
    private final Parent parentForm;
    private final FXMLLoader fxmlLoaderSearch = new FXMLLoader(getClass().getResource("/view/supplier/search.fxml"));
    private final Parent parentSearch;
    private final FXMLLoader fxmlLoaderTable = new FXMLLoader(getClass().getResource("/view/supplier/table.fxml"));
    private final Parent parentTable;
    private final FXMLLoader fxmlLoaderView = new FXMLLoader(getClass().getResource("/view/supplier/view.fxml"));
    private final Parent parentView;

    private SupplierCenterController() throws IOException {
        parentForm = fxmlLoaderForm.load();
        parentSearch = fxmlLoaderSearch.load();
        parentTable = fxmlLoaderTable.load();
        parentView = fxmlLoaderView.load();
    }

    static {
        try {
            instance = new SupplierCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating SupplierCenterController singleton instance");
        }
    }
}
