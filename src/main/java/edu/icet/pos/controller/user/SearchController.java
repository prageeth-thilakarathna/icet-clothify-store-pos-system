package edu.icet.pos.controller.user;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.user.custom.UserForm;
import edu.icet.pos.controller.user.custom.UserSearch;
import edu.icet.pos.model.user.User;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class SearchController implements UserSearch {
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private Label dspRegisterAt;
    @FXML
    private Label dspModifyAt;
    @FXML
    private TextField txtUserId;

    private final UserBo userBo = BoFactory.getBo(BoType.USER);
    private UserForm userForm;
    private User searchUser;

    @FXML
    private void btnSearchAction() {
        try{
            assert userBo != null;
            User user = userBo.getUser(Integer.parseInt(txtUserId.getText()));
            searchUser = user;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dspRegisterAt.setText(dateFormat.format(user.getRegisterAt()));
            dspModifyAt.setText(dateFormat.format(user.getModifyAt()));

            if(userForm ==null){
                userForm = UserCenterController.getInstance().getFxmlLoaderForm().getController();
            }
            userForm.loadUserToForm(user);
            validateInputs();

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
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
        txtUserId.setDisable(false);
        txtUserId.setText("");
        dspRegisterAt.setText("");
        dspModifyAt.setText("");
        searchUser = null;
        userForm.clearUser();
    }

    @FXML
    private void userIdKeyTyped() {
        validateInputs();
    }

    private void validateInputs(){
        btnSearch.setDisable(txtUserId.getLength() <= 0 || searchUser != null);

        if(searchUser!=null){
            btnCancel.setDisable(false);
            txtUserId.setDisable(true);
        } else {
            btnCancel.setDisable(true);
        }
    }

    @Override
    public void clearSearch() {
        clearForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
    }
}
