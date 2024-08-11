package edu.icet.pos.controller.inventory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class InventoryCenterController {
    @Getter
    private static final InventoryCenterController instance;
    private final FXMLLoader fxmlLoaderForm = new FXMLLoader(getClass().getResource("/view/inventory/form.fxml"));
    private final Parent parentForm;
    private final FXMLLoader fxmlLoaderSearch = new FXMLLoader(getClass().getResource("/view/inventory/search.fxml"));
    private final Parent parentSearch;
    private final FXMLLoader fxmlLoaderView = new FXMLLoader(getClass().getResource("/view/inventory/view.fxml"));
    private final Parent parentView;
    private final FXMLLoader fxmlLoaderTable = new FXMLLoader(getClass().getResource("/view/inventory/table.fxml"));
    private final Parent parentTable;

    private InventoryCenterController() throws IOException {
        parentForm = fxmlLoaderForm.load();
        parentSearch = fxmlLoaderSearch.load();
        parentView = fxmlLoaderView.load();
        parentTable = fxmlLoaderTable.load();
    }

    static {
        try {
            instance = new InventoryCenterController();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating InventoryCenterController singleton instance");
        }
    }
}
