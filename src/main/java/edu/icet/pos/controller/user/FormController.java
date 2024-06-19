package edu.icet.pos.controller.user;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.bo.custom.UserRoleBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.user.custom.UserFormCustom;
import edu.icet.pos.controller.user.custom.UserSearchCustom;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.model.User;
import edu.icet.pos.model.UserRole;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormController implements UserFormCustom {
    @FXML
    private Button btnCancel;
    @FXML
    private JFXComboBox<String> optUserRole;
    @FXML
    private JFXComboBox<String> optStatus;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;
    @FXML
    private CheckBox passwordCheckBox;
    @FXML
    private Button infoPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label dspPasswordMessage;
    @FXML
    private Button btnRegister;

    private final UserRoleBo userRoleBo = BoFactory.getBo(BoType.USER_ROLE);
    private final UserBo userBo = BoFactory.getBo(BoType.USER);
    private final UserSearchCustom userSearchCustom = UserCenterController.getInstance().getFxmlLoaderSearch().getController();
    private User searchUser;

    @FXML
    private void emailKeyPressed(KeyEvent keyEvent) {
    }

    @FXML
    private void emailKeyTyped(KeyEvent keyEvent) {
        validateInputs();
    }

    @FXML
    private void passwordKeyPressed(KeyEvent keyEvent) {

    }

    @FXML
    private void passwordKeyTyped(KeyEvent keyEvent) {
        if(passwordCheckBox.isSelected()){
            dspPasswordMessage.setText(txtPassword.getText());
        } else {
            dspPasswordMessage.setText("");
        }
        validateInputs();
    }

    @FXML
    private void passwordCheckBoxAction(ActionEvent actionEvent) {
        if(passwordCheckBox.isSelected()){
            dspPasswordMessage.setText(txtPassword.getText());
        } else {
            dspPasswordMessage.setText("");
        }
    }

    @FXML
    private void optUserRoleAction(ActionEvent actionEvent) {
        validateInputs();
    }

    @FXML
    private void optStatusAction(ActionEvent actionEvent) {
        validateInputs();
    }

    @FXML
    private void btnRegisterAction(ActionEvent actionEvent) {
        if(!doesUserAlreadyExist()){
            try{
                User user = new User();
                user.setEMail(txtEmail.getText());
                user.setPassword(CenterController.getInstance().encryptPassword(txtPassword.getText()));
                user.setRegisterAt(new Date());
                user.setModifyAt(new Date());
                user.setIsActive(Objects.equals(optStatus.getValue(), "Active"));
                user.setUserRole(new ModelMapper().map(getRole(), UserRoleEntity.class));

                userBo.userRegister(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(txtEmail.getText()+" User registration was successful.");
                alert.show();
                clearForm();

            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed! Your entered email already exists.");
            alert.show();
        }
    }

    private boolean doesUserAlreadyExist(){
        try{
            User searchUser = userBo.getUserByEmail(txtEmail.getText());
            if(Objects.equals(searchUser.getEMail(), txtEmail.getText())){
                return true;
            }
        } catch (Exception e){
            if(Objects.equals(e.getMessage(), "No result found for query [SELECT a FROM UserEntity a WHERE eMail='" + txtEmail.getText() + "']")){
                return false;
            }
        }
        return true;
    }

    private UserRole getRole(){
        if(Objects.equals(optUserRole.getValue(), "User")){
            return userRoleBo.getUserRoleByName("user");
        } else {
            return userRoleBo.getUserRoleByName("admin");
        }
    }

    @FXML
    private void btnModifyAction(ActionEvent actionEvent) {
        try{
            User user = new User(
                    searchUser.getId(),
                    txtEmail.getText(),
                    searchUser.getPassword(),
                    searchUser.getRegisterAt(),
                    new Date(),
                    Objects.equals(optStatus.getValue(), "Active"),
                    new ModelMapper().map(getRole(), UserRoleEntity.class)
            );

            userBo.userUpdate(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(searchUser.getId()+" User modification was successful.");
            alert.show();
            searchUser = null;
            clearForm();
            txtPassword.setDisable(false);
            passwordCheckBox.setDisable(false);
            userSearchCustom.clearSearch();

        } catch (Exception e){
            System.out.println(e.toString());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnDeleteAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnCancelAction(ActionEvent actionEvent) {
        clearForm();
    }

    private void clearForm(){
        txtEmail.setText("");
        txtPassword.setText("");
        dspPasswordMessage.setText("");
        optUserRole.setValue(null);
        optUserRole.setPromptText("   Select a User Role");
        optStatus.setValue(null);
        optStatus.setPromptText("   Select a Status");
        passwordCheckBox.setSelected(false);
        btnRegister.setDisable(true);
        btnCancel.setDisable(true);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void validateInputs(){
        int emailLength = txtEmail.getLength();
        int passwordLength = txtPassword.getLength();

        if(emailLength>4 && passwordLength>=8 && optUserRole.getValue()!=null && optStatus.getValue()!=null && searchUser==null){
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
            if(searchUser!=null){
                validateModify();
            }
        }

        if(emailLength>0 || passwordLength>0 || optUserRole.getValue()!=null || optStatus.getValue()!=null){
            btnCancel.setDisable(false);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private void validateModify(){
        if(!Objects.equals(searchUser.getEMail(), txtEmail.getText())){
            btnModify.setDisable(false);
        } else if(!Objects.equals(searchUser.getUserRole().getName().substring(0, 1).toUpperCase() + searchUser.getUserRole().getName().substring(1), optUserRole.getValue())) {
            btnModify.setDisable(false);
        } else if(searchUser.getIsActive()==true ? optStatus.getValue()!="Active":optStatus.getValue()!="Disable"){
            btnModify.setDisable(false);
        } else {
            btnModify.setDisable(true);
        }
    }

    private ObservableList<String> getUserRole(){
        ObservableList<String> userRoleList = FXCollections.observableArrayList();
        try{
            List<UserRole> roleList = userRoleBo.getAllUserRole();
            for(UserRole userRole : roleList){
                userRoleList.add(userRole.getName().substring(0, 1).toUpperCase() + userRole.getName().substring(1));
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return userRoleList;
    }

    private ObservableList<String> getStatus(){
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add("Active");
        statusList.add("Disable");
        return statusList;
    }

    @Override
    public void loadUserToForm(User user) {
        searchUser = user;
        btnDelete.setDisable(false);
        txtEmail.setText(user.getEMail());
        txtPassword.setText("");
        dspPasswordMessage.setText("");
        txtPassword.setDisable(true);
        passwordCheckBox.setSelected(false);
        passwordCheckBox.setDisable(true);
        optUserRole.setValue(user.getUserRole().getName().substring(0, 1).toUpperCase() + user.getUserRole().getName().substring(1));
        optStatus.setValue(user.getIsActive()== true ? "Active":"Disable");
        validateModify();

    }

    @Override
    public void clearUser() {
        searchUser = null;
        clearForm();
        txtPassword.setDisable(false);
        passwordCheckBox.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Tooltip tooltip = new Tooltip("8 characters or more.\nincluding numbers, letters, and symbols.");
        tooltip.setStyle("-fx-font-size: 14px");
        infoPassword.setTooltip(tooltip);

        optUserRole.setItems(getUserRole());
        optStatus.setItems(getStatus());

        btnRegister.setDisable(true);
        btnCancel.setDisable(true);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);


    }
}
