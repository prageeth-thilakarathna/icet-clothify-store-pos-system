package edu.icet.pos.controller.auth;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.bo.custom.UserRoleBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.layout.LayoutCenterController;
import edu.icet.pos.controller.layout.custom.LayoutCustom;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.model.User;
import edu.icet.pos.model.UserRole;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPanelController implements Initializable {
    @FXML
    private CheckBox passwordCheckBox;
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

    private final UserRoleBo userRoleBo = BoFactory.getBo(BoType.USER_ROLE);
    private final UserBo userBo = BoFactory.getBo(BoType.USER);
    private LayoutCustom layoutCustom;
    @Getter
    private User loginUser;

    @FXML
    private void emailKeyTyped() {
        validateInputs();
    }

    @FXML
    private void passwordKeyTyped() {
        if(passwordCheckBox.isSelected()){
            dspPassword.setText(txtPassword.getText());
        } else {
            dspPassword.setText("");
        }
        validateInputs();
    }

    @FXML
    private void passwordCheckBoxAction() {
        if(passwordCheckBox.isSelected()){
            dspPassword.setText(txtPassword.getText());
        } else {
            dspPassword.setText("");
        }
    }

    @FXML
    private void forgotPasswordMouseClicked() {
        if(layoutCustom==null){
            layoutCustom = LayoutCenterController.getInstance().getFxmlLoaderLayout().getController();
        }
        cancel();
        BorderPane borderPane = layoutCustom.getBorderPane();
        borderPane.getChildren().remove(borderPane.getRight());

        VBox rightVBox = new VBox();
        rightVBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightVBox.setPrefWidth(585);
        rightVBox.setPrefHeight(658);
        rightVBox.getChildren().add(AuthCenterController.getInstance().getParentForgotPassword());
        borderPane.setRight(rightVBox);
    }

    @FXML
    private void btnLoginAction() {
        try{
            assert userBo != null;
            User user = userBo.getUserByEmail(txtEmail.getText());
            if(Objects.equals(user.getPassword(), CenterController.getInstance().encryptPassword(txtPassword.getText()))){
                loginUser = user;
                if(layoutCustom==null){
                    layoutCustom = LayoutCenterController.getInstance().getFxmlLoaderLayout().getController();
                }
                cancel();
                layoutCustom.loadDashboard();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed! Please input the correct password.");
                alert.show();
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.show();
        }
    }

    @FXML
    private void btnCancelAction() {
        cancel();
    }

    @FXML
    private void btnExitAction() {
        System.exit(0);
    }

    private void cancel(){
        btnLogin.setDisable(true);
        btnCancel.setDisable(true);
        txtPassword.setText("");
        txtEmail.setText("");
        dspPassword.setText("");
        passwordCheckBox.setSelected(false);
    }

    private void validateInputs(){
        btnLogin.setDisable(txtEmail.getLength() <= 4 || txtPassword.getLength() < 8);
        btnCancel.setDisable(txtEmail.getLength() <= 0 || txtPassword.getLength() <= 0);
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
        btnLogin.setDisable(true);
        btnCancel.setDisable(true);
        try {
            UserRole user = userRoleBo.getUserRoleByName("user");
        } catch (Exception e) {
            if (Objects.equals(e.getMessage(), "No result found for query [SELECT a FROM UserRoleEntity a WHERE name='user']")) {
                userRoleRegister();
            }
        }
    }
}
