package edu.icet.pos.controller.sub_category;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class SubCategoryCenterController {
    @Getter
    private static final SubCategoryCenterController instance;
    private final FXMLLoader fxmlLoaderForm = new FXMLLoader(getClass().getResource("/view/sub-category/form.fxml"));
    private final Parent parentForm;
    private final FXMLLoader fxmlLoaderSearch = new FXMLLoader(getClass().getResource("/view/sub-category/search.fxml"));
    private final Parent parentSearch;
    private final FXMLLoader fxmlLoaderTable = new FXMLLoader(getClass().getResource("/view/sub-category/table.fxml"));
    private final Parent parentTable;
    private final FXMLLoader fxmlLoaderView = new FXMLLoader(getClass().getResource("/view/sub-category/view.fxml"));
    private final Parent parentView;

    private SubCategoryCenterController() throws IOException {
        parentForm = fxmlLoaderForm.load();
        parentSearch = fxmlLoaderSearch.load();
        parentTable = fxmlLoaderTable.load();
        parentView = fxmlLoaderView.load();
    }

    static {
        try {
            instance = new SubCategoryCenterController();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating SubCategoryCenterController singleton instance");
        }
    }
}
