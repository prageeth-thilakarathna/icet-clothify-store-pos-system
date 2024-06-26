package edu.icet.pos.controller.subCategory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class SubCategoryCenterController {
    @Getter
    private static final SubCategoryCenterController instance;
    private final FXMLLoader fxmlLoaderForm = new FXMLLoader(getClass().getResource("/view/sub-category/form.fxml"));
    private final Parent parentLayout;

    private SubCategoryCenterController() throws IOException {
        parentLayout = fxmlLoaderForm.load();
    }

    static {
        try{
            instance = new SubCategoryCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating SubCategoryCenterController singleton instance");
        }
    }
}
