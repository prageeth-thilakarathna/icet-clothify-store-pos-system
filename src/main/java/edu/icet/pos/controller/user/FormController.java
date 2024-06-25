package edu.icet.pos.controller.user;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.bo.custom.UserRoleBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.user.custom.UserForm;
import edu.icet.pos.controller.user.custom.UserSearch;
import edu.icet.pos.controller.user.custom.UserView;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.model.User;
import edu.icet.pos.model.UserRole;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormController implements UserForm {
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
    private UserSearch userSearch;
    private UserView userView;
    private User searchUser;
    private static final String ACTIVE = "Active";
    private static final String DISABLE = "Disable";

    @FXML
    private void emailKeyTyped() {
        validateInputs();
    }

    @FXML
    private void passwordKeyTyped() {
        if(passwordCheckBox.isSelected()){
            dspPasswordMessage.setText(txtPassword.getText());
        } else {
            dspPasswordMessage.setText("");
        }
        validateInputs();
    }

    @FXML
    private void passwordCheckBoxAction() {
        if(passwordCheckBox.isSelected()){
            dspPasswordMessage.setText(txtPassword.getText());
        } else {
            dspPasswordMessage.setText("");
        }
    }

    @FXML
    private void optUserRoleAction() {
        validateInputs();
    }

    @FXML
    private void optStatusAction() {
        validateInputs();
    }

    @FXML
    private void btnRegisterAction() {
        if(!doesUserAlreadyExist()){
            try{
                User user = new User();
                user.setEMail(txtEmail.getText());
                user.setPassword(CenterController.getInstance().encryptPassword(txtPassword.getText()));
                user.setRegisterAt(new Date());
                user.setModifyAt(new Date());
                user.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));
                user.setUserRole(new ModelMapper().map(getRole(), UserRoleEntity.class));

                assert userBo != null;
                userBo.userRegister(user);
                if(userView ==null){
                    userView = UserCenterController.getInstance().getFxmlLoaderView().getController();
                }
                userView.updateTbl("registration");
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
            assert userBo != null;
            User user = userBo.getUserByEmail(txtEmail.getText());
            if(user!=null){
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
            assert userRoleBo != null;
            return userRoleBo.getUserRoleByName("user");
        } else {
            assert userRoleBo != null;
            return userRoleBo.getUserRoleByName("admin");
        }
    }

    @FXML
    private void btnModifyAction() {
        try{
            User user = new User(
                    searchUser.getId(),
                    txtEmail.getText(),
                    searchUser.getPassword(),
                    searchUser.getRegisterAt(),
                    new Date(),
                    Objects.equals(optStatus.getValue(), ACTIVE),
                    new ModelMapper().map(getRole(), UserRoleEntity.class)
            );

            assert userBo != null;
            userBo.userUpdate(user);
            if(userView ==null){
                userView = UserCenterController.getInstance().getFxmlLoaderView().getController();
            }
            userView.updateTbl("modification");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(searchUser.getId()+" User modification was successful.");
            alert.show();
            searchUser = null;
            clearForm();
            txtPassword.setDisable(false);
            passwordCheckBox.setDisable(false);
            if(userSearch ==null){
                userSearch = UserCenterController.getInstance().getFxmlLoaderSearch().getController();
            }
            userSearch.clearSearch();

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnDeleteAction() {
        try{
            assert userBo != null;
            userBo.userDelete(searchUser);
            if(userView ==null){
                userView = UserCenterController.getInstance().getFxmlLoaderView().getController();
            }
            userView.updateTbl("deletion");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(searchUser.getId()+" User deletion was successful.");
            alert.show();
            searchUser = null;
            clearForm();
            txtPassword.setDisable(false);
            passwordCheckBox.setDisable(false);
            if(userSearch ==null){
                userSearch = UserCenterController.getInstance().getFxmlLoaderSearch().getController();
            }
            userSearch.clearSearch();

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelAction() {
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
        btnCancel.setDisable(emailLength <= 0 && passwordLength <= 0 && optUserRole.getValue() == null && optStatus.getValue() == null);
    }

    private void validateModify(){
        if(!Objects.equals(searchUser.getEMail(), txtEmail.getText())){
            btnModify.setDisable(false);
        } else if(!Objects.equals(searchUser.getUserRole().getName().substring(0, 1).toUpperCase() + searchUser.getUserRole().getName().substring(1), optUserRole.getValue())) {
            btnModify.setDisable(false);
        } else btnModify.setDisable(Boolean.TRUE.equals(searchUser.getIsActive()) ? Objects.equals(optStatus.getValue(), ACTIVE) : Objects.equals(optStatus.getValue(), DISABLE));
    }

    private ObservableList<String> getUserRole(){
        ObservableList<String> userRoleList = FXCollections.observableArrayList();
        try{
            assert userRoleBo != null;
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
        statusList.add(ACTIVE);
        statusList.add(DISABLE);
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
        optStatus.setValue(Boolean.TRUE.equals(user.getIsActive()) ? ACTIVE:DISABLE);
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
