package edu.icet.pos.controller.employee;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.EmployeeBo;
import edu.icet.pos.bo.custom.JobRoleBo;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.employee.custom.EmployeeForm;
import edu.icet.pos.entity.EmployeeEntity;
import edu.icet.pos.entity.JobRoleEntity;
import edu.icet.pos.entity.UserEntity;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.model.employee.JobRole;
import edu.icet.pos.model.user.User;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FormController implements EmployeeForm {
    @FXML
    private JFXComboBox<String> optUser;
    @FXML
    private JFXComboBox<String> optJobRole;
    @FXML
    private JFXComboBox<String> optTitle;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtAddress;
    @FXML
    private JFXComboBox<String> optStatus;
    @FXML
    private HBox userController;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnCancel;
    @FXML
    private HBox adminController;
    @FXML
    private Button btnActive;
    @FXML
    private Button btnDisable;
    @FXML
    private Button btnDelete;

    private final JobRoleBo jobRoleBo = BoFactory.getBo(BoType.JOB_ROLE);
    private final UserBo userBo = BoFactory.getBo(BoType.USER);
    private final EmployeeBo employeeBo = BoFactory.getBo(BoType.EMPLOYEE);
    private static final String ACTIVE = "Active";
    private static final String DISABLE = "Disable";

    @FXML
    private void optUserAction(ActionEvent actionEvent) {
        validateInputs();
    }

    @FXML
    private void optJobRoleAction(ActionEvent actionEvent) {
        validateInputs();
    }

    @FXML
    private void optTitleAction(ActionEvent actionEvent) {
        validateInputs();
    }

    @FXML
    private void firstNameKeyTyped(KeyEvent keyEvent) {
        validateInputs();
    }

    @FXML
    private void lastNameKeyTyped(KeyEvent keyEvent) {
        validateInputs();
    }

    @FXML
    private void contactKeyTyped(KeyEvent keyEvent) {
        validateInputs();
    }

    @FXML
    private void addressKeyTyped(KeyEvent keyEvent) {
        validateInputs();
    }

    @FXML
    private void optStatusAction(ActionEvent actionEvent) {
        validateInputs();
    }

    @FXML
    private void btnRegisterAction(ActionEvent actionEvent) {
        try{
            Employee employee = new Employee();

            assert userBo != null;
            employee.setUser(new ModelMapper().map(userBo.getUserByEmail(optUser.getValue()), UserEntity.class));
            String roleName = optJobRole.getValue().substring(0, 1).toLowerCase() + optJobRole.getValue().substring(1);
            assert jobRoleBo != null;
            employee.setJobRole(new ModelMapper().map(jobRoleBo.getJobRoleByName(roleName), JobRoleEntity.class));
            employee.setTitle(optTitle.getValue());
            employee.setFirstName(txtFirstName.getText());
            employee.setLastName(txtLastName.getText());
            employee.setContact(txtContact.getText());
            employee.setAddress(txtAddress.getText());
            employee.setRegisterAt(new Date());
            employee.setModifyAt(new Date());
            employee.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));

            assert employeeBo != null;
            userBo.employeeRegister(employee);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(optTitle.getValue() + " " + txtFirstName.getText() + " " + txtLastName.getText() + " Employee registration was successful.");
            alert.show();
            clearForm();
            optUser.setItems(getUsers());

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.show();
        }
    }

    @FXML
    private void btnModifyAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnCancelAction(ActionEvent actionEvent) {
        clearForm();
    }

    @FXML
    private void btnActiveAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnDisableAction(ActionEvent actionEvent) {
    }

    @FXML
    private void btnDeleteAction(ActionEvent actionEvent) {
    }

    private void validateInputs(){
        if(!isInputEmpty()){
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
        }

        btnCancel.setDisable(optUser.getValue() == null &&
                optJobRole.getValue() == null &&
                optTitle.getValue() == null &&
                txtFirstName.getLength() <= 0 &&
                txtLastName.getLength() <= 0 &&
                txtContact.getLength() <= 0 &&
                txtAddress.getLength() <= 0 &&
                optStatus.getValue() == null);
    }

    private boolean isInputEmpty(){
        if(
                optUser.getValue()!=null &&
                        optJobRole.getValue()!=null &&
                        optTitle.getValue()!=null &&
                        txtFirstName.getLength() > 0 &&
                        txtLastName.getLength() > 0 &&
                        txtContact.getLength() >=10 &&
                        txtAddress.getLength() > 0 &&
                        optStatus.getValue()!=null
        ){
            return false;
        } else {
            return true;
        }
    }

    private void clearForm(){
        optUser.setValue(null);
        optUser.setPromptText("   Select a User");
        optJobRole.setValue(null);
        optJobRole.setPromptText("   Select a Job Role");
        optTitle.setValue(null);
        optTitle.setPromptText("   Select a Title");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        optStatus.setValue(null);
        optStatus.setPromptText("   Select a Status");
        validateInputs();
    }

    private ObservableList<String> getUsers(){
        ObservableList<String> userList = FXCollections.observableArrayList();
        try{
            assert userBo != null;
            List<User> users = userBo.getUsersNotExistInEmployee();
            for(User user : users){
                userList.add(user.getEMail());
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return userList;
    }

    private void jobRoleRegister(){
        try{
            JobRole assistance = new JobRole();
            assistance.setName("assistance");
            assert jobRoleBo != null;
            jobRoleBo.jobRoleRegister(assistance);

            JobRole cashier = new JobRole();
            cashier.setName("cashier");
            jobRoleBo.jobRoleRegister(cashier);

            setJobRoles();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.show();
        }
    }

    private void setJobRoles(){
        try{
            assert jobRoleBo != null;
            List<JobRole> jobRoleList = jobRoleBo.getAllJobRole();

            ObservableList<String> roles = FXCollections.observableArrayList();
            roles.add(jobRoleList.get(0).getName().substring(0, 1).toUpperCase() + jobRoleList.get(0).getName().substring(1));
            roles.add(jobRoleList.get(1).getName().substring(0, 1).toUpperCase() + jobRoleList.get(1).getName().substring(1));
            optJobRole.setItems(roles);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private ObservableList<String> getTitles() {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Mrs");
        titles.add("Miss");
        titles.add("Ms");
        return titles;
    }

    private ObservableList<String> getStatus() {
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add(ACTIVE);
        statusList.add(DISABLE);
        return statusList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        optUser.setItems(getUsers());
        optUser.setVisibleRowCount(5);
        assert jobRoleBo != null;
        if(jobRoleBo.getAllJobRole().isEmpty()){
            jobRoleRegister();
        } else {
            setJobRoles();
        }
        optTitle.setItems(getTitles());
        optStatus.setItems(getStatus());
        btnRegister.setDisable(true);
        btnModify.setDisable(true);
        btnCancel.setDisable(true);
        btnActive.setDisable(true);
        btnDisable.setDisable(true);
        btnDelete.setDisable(true);
    }
}
