package edu.icet.pos.controller.user;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class UserCenterController {
    @Getter
    private static final UserCenterController instance;
    private final FXMLLoader fxmlLoaderForm = new FXMLLoader(getClass().getResource("/view/user/form.fxml"));
    private final Parent parentForm;
    private final FXMLLoader fxmlLoaderSearch = new FXMLLoader(getClass().getResource("/view/user/search.fxml"));
    private final Parent parentSearch;
    private final FXMLLoader fxmlLoaderView = new FXMLLoader(getClass().getResource("/view/user/view.fxml"));
    private final Parent parentView;
    private final FXMLLoader fxmlLoaderTable = new FXMLLoader(getClass().getResource("/view/user/table.fxml"));
    private final Parent parentTable;

    private UserCenterController() throws IOException {
        parentForm = fxmlLoaderForm.load();
        parentSearch = fxmlLoaderSearch.load();
        parentView = fxmlLoaderView.load();
        parentTable = fxmlLoaderTable.load();
    }

    static {
        try {
            instance = new UserCenterController();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating UserCenterController singleton instance");
        }
    }
}
