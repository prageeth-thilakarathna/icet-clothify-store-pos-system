package edu.icet.pos.controller.auth.custom;

import edu.icet.pos.model.user.User;
import javafx.fxml.Initializable;

public interface LoginPanel extends Initializable {
    User getLoginUser();

    void clearUser();
}
