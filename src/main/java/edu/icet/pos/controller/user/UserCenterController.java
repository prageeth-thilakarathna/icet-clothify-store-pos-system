package edu.icet.pos.controller.user;

import javafx.fxml.FXMLLoader;
import lombok.Getter;

@Getter
public class UserCenterController {
    private static UserCenterController instance;
    private final FXMLLoader fxmlLoaderForm = new FXMLLoader(getClass().getResource("/view/user/form.fxml"));
    private final FXMLLoader fxmlLoaderSearch = new FXMLLoader(getClass().getResource("/view/user/search.fxml"));
    private final FXMLLoader fxmlLoaderView = new FXMLLoader(getClass().getResource("/view/user/view.fxml"));
    private final FXMLLoader fxmlLoaderTable = new FXMLLoader(getClass().getResource("/view/user/table.fxml"));

    private UserCenterController(){}

    public static UserCenterController getInstance() {
        if(instance==null){
            instance = new UserCenterController();
        }
        return instance;
    }
}
