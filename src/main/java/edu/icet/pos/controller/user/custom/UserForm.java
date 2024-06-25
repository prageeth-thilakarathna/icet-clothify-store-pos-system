package edu.icet.pos.controller.user.custom;

import edu.icet.pos.model.user.User;
import javafx.fxml.Initializable;

public interface UserForm extends Initializable {
    void loadUserToForm(User user);

    void clearUser();
}
