package edu.icet.pos.controller.employee;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.JobRoleBo;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.employee.custom.EmployeeForm;
import edu.icet.pos.controller.employee.custom.EmployeeSearch;
import edu.icet.pos.controller.employee.custom.EmployeeView;
import edu.icet.pos.entity.JobRoleEntity;
import edu.icet.pos.entity.UserEntity;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.model.employee.JobRole;
import edu.icet.pos.model.user.User;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.modelmapper.ModelMapper;

import java.net.URL;
import java.util.*;

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
    private Button btnRegister;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnDelete;

    private final JobRoleBo jobRoleBo = BoFactory.getBo(BoType.JOB_ROLE);
    private final UserBo userBo = BoFactory.getBo(BoType.USER);
    private static final String ACTIVE = "Active";
    private static final String DISABLE = "Disable";
    private Employee searchEmployee;
    private EmployeeSearch employeeSearch;
    private EmployeeView employeeView;

    @FXML
    private void optUserAction() {
        validateInputs();
    }

    @FXML
    private void optJobRoleAction() {
        validateInputs();
    }

    @FXML
    private void optTitleAction() {
        validateInputs();
    }

    @FXML
    private void firstNameKeyTyped() {
        validateInputs();
    }

    @FXML
    private void lastNameKeyTyped() {
        validateInputs();
    }

    @FXML
    private void contactKeyTyped() {
        validateInputs();
    }

    @FXML
    private void addressKeyTyped() {
        validateInputs();
    }

    @FXML
    private void optStatusAction() {
        validateInputs();
    }

    @FXML
    private void btnRegisterAction() {
        try {
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

            userBo.employeeRegister(employee);
            if (employeeView == null) {
                employeeView = EmployeeCenterController.getInstance().getFxmlLoaderView().getController();
            }
            employeeView.updateTbl("registration");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(optTitle.getValue() + " " + txtFirstName.getText() + " " + txtLastName.getText() + " Employee registration was successful.");
            alert.show();
            clearForm();
            optUser.setItems(getUsers());

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("first" + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnModifyAction() {
        try {
            Employee employee = searchEmployee;

            String roleName = optJobRole.getValue().substring(0, 1).toLowerCase() + optJobRole.getValue().substring(1);
            assert jobRoleBo != null;
            employee.setJobRole(new ModelMapper().map(jobRoleBo.getJobRoleByName(roleName), JobRoleEntity.class));
            employee.setTitle(optTitle.getValue());
            employee.setFirstName(txtFirstName.getText());
            employee.setLastName(txtLastName.getText());
            employee.setContact(txtContact.getText());
            employee.setAddress(txtAddress.getText());
            employee.setModifyAt(new Date());
            employee.setIsActive(Objects.equals(optStatus.getValue(), ACTIVE));

            assert userBo != null;
            userBo.employeeUpdate(employee);
            if (employeeView == null) {
                employeeView = EmployeeCenterController.getInstance().getFxmlLoaderView().getController();
            }
            employeeView.updateTbl("modification");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(employee.getTitle() + " " + employee.getFirstName() + " " + employee.getLastName() + " Employee modification was successful.");
            alert.show();
            clearEmployee();
            if (employeeSearch == null) {
                employeeSearch = EmployeeCenterController.getInstance().getFxmlLoaderSearch().getController();
            }
            employeeSearch.clearSearch();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void btnCancelAction() {
        clearForm();
    }

    @FXML
    private void btnDeleteAction() {
        try {
            assert userBo != null;
            userBo.employeeDelete(searchEmployee);
            if (employeeView == null) {
                employeeView = EmployeeCenterController.getInstance().getFxmlLoaderView().getController();
            }
            employeeView.updateTbl("deletion");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(searchEmployee.getTitle() + " " + searchEmployee.getFirstName() + " " + searchEmployee.getLastName() + " Employee deletion was successful.");
            alert.show();
            clearEmployee();
            if (employeeSearch == null) {
                employeeSearch = EmployeeCenterController.getInstance().getFxmlLoaderSearch().getController();
            }
            employeeSearch.clearSearch();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void validateInputs() {
        if (!isInputEmpty() && searchEmployee == null) {
            btnRegister.setDisable(false);
        } else {
            btnRegister.setDisable(true);
            if (searchEmployee != null) {
                validateModify();
            } else {
                btnModify.setDisable(true);
            }
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

    private void validateModify() {
        if (!searchEmployee.getJobRole().getName().equalsIgnoreCase(optJobRole.getValue())) {
            btnModify.setDisable(isInputEmpty());
        } else if (!Objects.equals(searchEmployee.getTitle(), optTitle.getValue())) {
            btnModify.setDisable(isInputEmpty());
        } else if (!Objects.equals(txtFirstName.getText(), searchEmployee.getFirstName())) {
            btnModify.setDisable(isInputEmpty());
        } else if (!Objects.equals(txtLastName.getText(), searchEmployee.getLastName())) {
            btnModify.setDisable(isInputEmpty());
        } else if (!Objects.equals(txtContact.getText(), searchEmployee.getContact())) {
            btnModify.setDisable(isInputEmpty());
        } else if (!Objects.equals(txtAddress.getText(), searchEmployee.getAddress())) {
            btnModify.setDisable(isInputEmpty());
        } else if (Objects.equals(optStatus.getValue(), ACTIVE) != Boolean.TRUE.equals(searchEmployee.getIsActive())) {
            btnModify.setDisable(isInputEmpty());
        } else {
            btnModify.setDisable(true);
        }
    }

    private boolean isInputEmpty() {
        return optUser.getValue() == null ||
                optJobRole.getValue() == null ||
                optTitle.getValue() == null ||
                txtFirstName.getLength() <= 0 ||
                txtLastName.getLength() <= 0 ||
                txtContact.getLength() < 10 ||
                txtAddress.getLength() <= 0 ||
                optStatus.getValue() == null;
    }

    private void clearForm() {
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

    private ObservableList<String> getUsers() {
        ObservableList<String> userList = FXCollections.observableArrayList();
        try {
            assert userBo != null;
            List<User> users = new ArrayList<>();
            if (searchEmployee == null) {
                users = userBo.getUsersNotExistInEmployee();
            }
            if (searchEmployee != null) {
                users = userBo.getAllUser();
            }
            for (User user : users) {
                if (Boolean.TRUE.equals(user.getIsActive())) {
                    userList.add(user.getEMail());
                }
                if (searchEmployee != null &&
                        (Objects.equals(searchEmployee.getUser().getId(), user.getId()) &&
                                !user.getIsActive())) {
                    userList.add(user.getEMail());
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return userList;
    }

    private void jobRoleRegister() {
        try {
            JobRole assistance = new JobRole();
            assistance.setName("assistance");
            assert jobRoleBo != null;
            jobRoleBo.jobRoleRegister(assistance);

            JobRole cashier = new JobRole();
            cashier.setName("cashier");
            jobRoleBo.jobRoleRegister(cashier);

            setJobRoles();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("second" + e.getMessage());
            alert.show();
        }
    }

    private void setJobRoles() {
        try {
            assert jobRoleBo != null;
            List<JobRole> jobRoleList = jobRoleBo.getAllJobRole();

            ObservableList<String> roles = FXCollections.observableArrayList();
            roles.add(jobRoleList.get(0).getName().substring(0, 1).toUpperCase() + jobRoleList.get(0).getName().substring(1));
            roles.add(jobRoleList.get(1).getName().substring(0, 1).toUpperCase() + jobRoleList.get(1).getName().substring(1));
            optJobRole.setItems(roles);
        } catch (Exception e) {
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
    public void loadEmployeeToForm(Employee employee) {
        searchEmployee = employee;
        btnDelete.setDisable(false);

        optUser.setItems(getUsers());
        optUser.setValue(employee.getUser().getEMail());
        optUser.setDisable(true);
        optJobRole.setValue(employee.getJobRole().getName().substring(0, 1).toUpperCase() + employee.getJobRole().getName().substring(1));
        optTitle.setValue(employee.getTitle());
        txtFirstName.setText(employee.getFirstName());
        txtLastName.setText(employee.getLastName());
        txtContact.setText(employee.getContact());
        txtAddress.setText(employee.getAddress());
        optStatus.setValue(Boolean.TRUE.equals(employee.getIsActive()) ? ACTIVE : DISABLE);
        validateInputs();
    }

    @Override
    public void clearEmployee() {
        searchEmployee = null;
        clearForm();
        optUser.setItems(getUsers());
        optUser.setDisable(false);
        btnDelete.setDisable(true);
    }

    @Override
    public void refreshForm() {
        clearEmployee();
        loadForm();
    }

    private void loadForm() {
        optUser.setItems(getUsers());
        optUser.setVisibleRowCount(5);
        assert jobRoleBo != null;
        if (jobRoleBo.getAllJobRole().isEmpty()) {
            jobRoleRegister();
        }
        setJobRoles();

        optTitle.setItems(getTitles());
        optStatus.setItems(getStatus());
        btnRegister.setDisable(true);
        btnModify.setDisable(true);
        btnCancel.setDisable(true);
        btnDelete.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadForm();
    }
}
