package edu.icet.pos.controller.product;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class ProductCenterController {
    @Getter
    private static final ProductCenterController instance;
    private final FXMLLoader fxmlLoaderForm = new FXMLLoader(getClass().getResource("/view/product/form.fxml"));
    private final Parent parentForm;
    private final FXMLLoader fxmlLoaderSearch = new FXMLLoader(getClass().getResource("/view/product/search.fxml"));
    private final Parent parentSearch;
    private final FXMLLoader fxmlLoaderView = new FXMLLoader(getClass().getResource("/view/product/view.fxml"));
    private final Parent parentView;

    private ProductCenterController() throws IOException {
        parentForm = fxmlLoaderForm.load();
        parentSearch = fxmlLoaderSearch.load();
        parentView = fxmlLoaderView.load();
    }

    static {
        try{
            instance = new ProductCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating ProductCenterController singleton instance");
        }
    }
}
