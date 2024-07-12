package edu.icet.pos.controller.auth;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.bo.custom.UserRoleBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.auth.custom.LoginPanel;
import edu.icet.pos.controller.layout.LayoutCenterController;
import edu.icet.pos.controller.layout.custom.Layout;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.model.user.User;
import edu.icet.pos.model.user.UserRole;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
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

public class LoginPanelController implements LoginPanel {
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
    private Layout layout;
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
        if(layout ==null){
            layout = LayoutCenterController.getInstance().getFxmlLoaderLayout().getController();
        }
        cancel();
        BorderPane borderPane = layout.getBorderPane();
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
                if(layout ==null){
                    layout = LayoutCenterController.getInstance().getFxmlLoaderLayout().getController();
                }
                cancel();
                layout.loadDashboard();
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
    private void btnCancelOnAction() {
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
        UserRole user = new UserRole();
        user.setName("user");

        UserRole admin = new UserRole();
        admin.setName("admin");

        try {
            assert userRoleBo != null;
            userRoleBo.userRoleRegister(user);
            userRoleBo.userRoleRegister(admin);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void userRegister(){
        User mainAdmin = new User();
        mainAdmin.setEMail("admin@email.com");
        mainAdmin.setPassword(CenterController.getInstance().encryptPassword("12345678"));
        mainAdmin.setRegisterAt(new Date());
        mainAdmin.setModifyAt(new Date());
        mainAdmin.setIsActive(true);

        try{
            assert userRoleBo != null;
            UserRole admin = userRoleBo.getUserRoleByName("admin");

            mainAdmin.setUserRole(new ModelMapper().map(admin, UserRoleEntity.class));
            assert userBo != null;
            userBo.userRegister(mainAdmin);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void clearUser() {
        loginUser = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnLogin.setDisable(true);
        btnCancel.setDisable(true);
        assert userRoleBo != null;
        if(userRoleBo.getAllUserRole().isEmpty()){
            userRoleRegister();
        }
        assert userBo != null;
        if(userBo.getAllUser().isEmpty()){
            userRegister();
        }
    }
}
