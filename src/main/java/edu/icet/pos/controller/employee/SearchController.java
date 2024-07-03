package edu.icet.pos.controller.employee;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.employee.custom.EmployeeForm;
import edu.icet.pos.controller.employee.custom.EmployeeSearch;
import edu.icet.pos.model.employee.Employee;
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

public class SearchController implements EmployeeSearch {
    @FXML
    private TextField txtEmployeeId;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private Label dspRegisterAt;
    @FXML
    private Label dspModifyAt;

    private Employee searchEmployee;
    private EmployeeForm employeeForm;
    private final UserBo userBo = BoFactory.getBo(BoType.USER);

    @FXML
    private void employeeIdKeyTyped() {
        validateInputs();
    }

    @FXML
    private void btnSearchAction() {
        try{
            assert userBo != null;
            searchEmployee = userBo.getEmployeeById(Integer.parseInt(txtEmployeeId.getText()));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dspRegisterAt.setText(dateFormat.format(searchEmployee.getRegisterAt()));
            dspModifyAt.setText(dateFormat.format(searchEmployee.getModifyAt()));

            if(employeeForm==null){
                employeeForm = EmployeeCenterController.getInstance().getFxmlLoaderForm().getController();
            }
            employeeForm.loadEmployeeToForm(searchEmployee);
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

    private void validateInputs(){
        btnSearch.setDisable(txtEmployeeId.getLength() <= 0 || searchEmployee != null);
        if(searchEmployee!=null){
            btnCancel.setDisable(false);
            txtEmployeeId.setDisable(true);
        } else {
            btnCancel.setDisable(true);
        }
    }

    private void clearForm(){
        btnSearch.setDisable(true);
        btnCancel.setDisable(true);
        txtEmployeeId.setDisable(false);
        txtEmployeeId.setText("");
        dspRegisterAt.setText("");
        dspModifyAt.setText("");
        searchEmployee = null;
        employeeForm.clearEmployee();
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
