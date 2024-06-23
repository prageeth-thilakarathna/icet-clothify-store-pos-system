package edu.icet.pos.controller.auth.custom;

import edu.icet.pos.model.User;
import javafx.fxml.Initializable;

public interface LoginPanelCustom extends Initializable {
    User getLoginUser();

    void clearUser();
}
