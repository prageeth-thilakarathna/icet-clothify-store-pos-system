package edu.icet.pos.controller.auth;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.bo.custom.UserRoleBo;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.model.User;
import edu.icet.pos.model.UserRole;
import edu.icet.pos.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.modelmapper.ModelMapper;

import java.awt.*;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox centerVBox;
    @FXML
    private VBox rightVBox;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label dspPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnExit;

    private final UserRoleBo userRoleBo = BoFactory.getBo(BoType.USER_ROLE);
    private final UserBo userBo = BoFactory.getBo(BoType.USER);

    @FXML
    private void passwordKeyPressed(KeyEvent keyEvent) {
    }

    @FXML
    private void passwordCheckBoxAction(ActionEvent actionEvent) {
    }

    @FXML
    private void forgotPasswordMouseClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void btnLoginAction() {
        try{
            assert userBo != null;
            User user = userBo.getUserByEmail(txtEmail.getText());
            if(Objects.equals(user.getPassword(), txtPassword.getText())){
                borderPane.getChildren().removeAll(borderPane.getChildren());
                Parent parentNavPanel = new FXMLLoader(getClass().getResource("/view/dashboard/navPanel.fxml")).load();
                VBox leftVBox = new VBox();
                leftVBox.setPrefWidth(258);
                leftVBox.setStyle("-fx-background-color: #d9d9d9;");
                leftVBox.getChildren().add(parentNavPanel);
                borderPane.setLeft(leftVBox);

                Parent parentHeader = new FXMLLoader(getClass().getResource("/view/dashboard/header.fxml")).load();
                VBox topVBox = new VBox();
                topVBox.setPrefWidth(1169);
                topVBox.setPrefHeight(60);
                topVBox.getChildren().add(parentHeader);
                borderPane.setTop(topVBox);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed! Please input the correct password.");
                alert.show();
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnExitAction(ActionEvent actionEvent) {
    }

    private void userRoleRegister() {
        try {
            UserRole user = new UserRole();
            user.setName("user");
            assert userRoleBo != null;
            userRoleBo.userRoleRegister(user);
            UserRole admin = userRoleBo.getUserRoleByName("admin");

        } catch (Exception e) {
            if (Objects.equals(e.getMessage(), "No result found for query [SELECT a FROM UserRoleEntity a WHERE name='admin']")) {
                UserRole admin = new UserRole();
                admin.setName("admin");
                assert userRoleBo != null;
                userRoleBo.userRoleRegister(admin);
                mainAdminRegister();
            }
        }
    }

    private void mainAdminRegister() {
        try {
            User user = userBo.getUserByEmail("ygprageethchamara2002@gmail.com");

        } catch (Exception e) {
            UserRole admin = userRoleBo.getUserRoleByName("admin");

            User mainAdmin = new User();
            mainAdmin.setEMail("ygprageethchamara2002@gmail.com");
            mainAdmin.setPassword("123");
            mainAdmin.setRegisterAt(new Date());
            mainAdmin.setModifyAt(new Date());
            mainAdmin.setIsActive(true);
            mainAdmin.setUserRole(new ModelMapper().map(admin, UserRoleEntity.class));
            assert userBo != null;
            userBo.userRegister(mainAdmin);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            UserRole user = userRoleBo.getUserRoleByName("user");

        } catch (Exception e) {
            if (Objects.equals(e.getMessage(), "No result found for query [SELECT a FROM UserRoleEntity a WHERE name='user']")) {
                userRoleRegister();
            }
        }


    }
}
