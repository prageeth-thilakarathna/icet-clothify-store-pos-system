package edu.icet.pos.controller.user;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.SuperBo;
import edu.icet.pos.bo.custom.UserRoleBo;
import edu.icet.pos.model.UserRole;
import edu.icet.pos.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class FormController implements Initializable {
    @FXML
    private Button infoPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label dspPasswordMessage;
    @FXML
    private ComboBox optUserRole;
    @FXML
    private ComboBox optStatus;
    @FXML
    private Button btnRegister;

    private UserRoleBo userRoleBo = BoFactory.getBo(BoType.USER_ROLE);
    //private SuperBo userBo = BoFactory.getBo(BoType.USER);

    @FXML
    private void passwordKeyPressed(KeyEvent keyEvent) {
    }

    @FXML
    private void passwordCheckBoxAction(ActionEvent actionEvent) {
    }

    @FXML
    private void optUserRoleAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnRegisterAction(ActionEvent actionEvent) {
    }

    public void optStatusAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Tooltip tooltip = new Tooltip("8 characters or more.\nincluding numbers, letters, and symbols.");
        tooltip.setStyle("-fx-font-size: 14px");
        infoPassword.setTooltip(tooltip);

        // register user role
        UserRole userRoleADMIN = new UserRole();
        userRoleADMIN.setName("admin");
        //userRoleBo.registerUserRole(userRoleADMIN);

        UserRole userRole = userRoleBo.getUserRole(1);
        System.out.println(userRole.getId()+", "+userRole.getName());

    }
}
