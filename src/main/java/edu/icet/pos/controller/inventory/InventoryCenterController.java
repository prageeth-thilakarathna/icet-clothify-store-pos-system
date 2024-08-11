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

    private InventoryCenterController() throws IOException {
        parentForm = fxmlLoaderForm.load();
        parentSearch = fxmlLoaderSearch.load();
    }

    static {
        try {
            instance = new InventoryCenterController();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating InventoryCenterController singleton instance");
        }
    }
}
