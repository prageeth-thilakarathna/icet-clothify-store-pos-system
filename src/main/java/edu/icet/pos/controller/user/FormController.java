package edu.icet.pos.controller.user;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.bo.custom.UserRoleBo;
import edu.icet.pos.entity.UserRoleEntity;
import edu.icet.pos.model.User;
import edu.icet.pos.model.UserRole;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormController implements Initializable {
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

    private UserRoleBo userRoleBo = BoFactory.getBo(BoType.USER_ROLE);
    private UserBo userBo = BoFactory.getBo(BoType.USER);

    @FXML
    private void emailKeyPressed(KeyEvent keyEvent) {
    }

    @FXML
    private void emailKeyTyped(KeyEvent keyEvent) {
        validateInputs();
    }

    @FXML
    private void passwordKeyPressed(KeyEvent keyEvent) {
        //System.out.println(txtPassword.getText());
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
            User user = new User();
            user.setEMail(txtEmail.getText());
            user.setPassword(encryptPassword());
            user.setRegisterAt(new Date());
            user.setModifyAt(new Date());
            user.setIsActive(Objects.equals(optStatus.getValue(), "Active"));
            user.setUserRole(new ModelMapper().map(getRole(), UserRoleEntity.class));

            try{
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

    private String encryptPassword() {
        try{
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(txtPassword.getText().getBytes());

            byte[] bytes = m.digest();

            StringBuilder stringBuilder = new StringBuilder();

            for(int i=0; i<bytes.length; i++){
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0*100, 16).substring(1));
            }

            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return null;
    }

    @FXML
    private void btnModifyAction(ActionEvent actionEvent) {
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
    }

    private void validateInputs(){
        int emailLength = txtEmail.getLength();
        int passwordLength = txtPassword.getLength();

        if(emailLength>4 && passwordLength>=8 && optUserRole.getValue()!=null && optStatus.getValue()!=null){
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
        }

        if(emailLength>0 || passwordLength>0 || optUserRole.getValue()!=null || optStatus.getValue()!=null){
            btnCancel.setDisable(false);
        } else {
            btnCancel.setDisable(true);
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
