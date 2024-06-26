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

    private SupplierCenterController() throws IOException {
        parentForm = fxmlLoaderForm.load();
    }

    static {
        try {
            instance = new SupplierCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating SupplierCenterController singleton instance");
        }
    }
}
