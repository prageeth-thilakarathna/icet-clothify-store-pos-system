package edu.icet.pos.controller.user;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.CenterController;
import edu.icet.pos.controller.user.custom.UserCustom;
import edu.icet.pos.model.User;
import edu.icet.pos.util.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
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
    private final UserCustom userCustom = UserCenterController.getInstance().getFxmlLoaderForm().getController();
    private User searchUser;

    @FXML
    private void btnSearchAction(ActionEvent actionEvent) {
        try{
            assert userBo != null;
            User user = userBo.getUser(Integer.parseInt(txtUserId.getText()));
            searchUser = user;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dspRegisterAt.setText(dateFormat.format(user.getRegisterAt()));
            dspModifyAt.setText(dateFormat.format(user.getModifyAt()));

            userCustom.loadUserToForm(user);
            validateInputs();

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelAction(ActionEvent actionEvent) {
        btnSearch.setDisable(false);
        btnCancel.setDisable(true);
        txtUserId.setDisable(false);
        txtUserId.setText("");
        dspRegisterAt.setText("");
        dspModifyAt.setText("");
        searchUser = null;
    }

    @FXML
    private void userIdKeyTyped(KeyEvent keyEvent) {
        validateInputs();
    }

    private void validateInputs(){
        if(txtUserId.getLength()>0 && searchUser==null){
            btnSearch.setDisable(false);
        } else {
            btnSearch.setDisable(true);
        }

        if(searchUser!=null){
            btnCancel.setDisable(false);
            txtUserId.setDisable(true);
        } else {
            btnCancel.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
    }
}
