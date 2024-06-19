package edu.icet.pos.controller.user.custom;

import edu.icet.pos.model.User;
import javafx.fxml.Initializable;

public interface UserFormCustom extends Initializable {
    void loadUserToForm(User user);

    void clearUser();
}
