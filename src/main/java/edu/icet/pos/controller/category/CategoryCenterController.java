package edu.icet.pos.controller.category;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class CategoryCenterController {
    @Getter
    private static final CategoryCenterController instance;
    private final FXMLLoader fxmlLoaderForm = new FXMLLoader(getClass().getResource("/view/category/form.fxml"));
    private final Parent parentForm;
    private final FXMLLoader fxmlLoaderSearch = new FXMLLoader(getClass().getResource("/view/category/search.fxml"));
    private final Parent parentSearch;

    private CategoryCenterController() throws IOException {
        parentForm = fxmlLoaderForm.load();
        parentSearch = fxmlLoaderSearch.load();
    }

    static {
        try{
            instance = new CategoryCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating CategoryCenterController singleton instance");
        }
    }
}
