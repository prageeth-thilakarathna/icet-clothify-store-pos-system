package edu.icet.pos.controller.employee;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.JobRoleBo;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.employee.custom.EmployeeTable;
import edu.icet.pos.controller.employee.custom.EmployeeView;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.model.employee.TblEmployeeView;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewController implements EmployeeView {
    @FXML
    private Label dspCount;
    @FXML
    private Pagination tblPagination;

    private final UserBo userBo = BoFactory.getBo(BoType.USER);
    private final JobRoleBo jobRoleBo = BoFactory.getBo(BoType.JOB_ROLE);
    private EmployeeTable employeeTable;
    private static final String DELETION = "deletion";

    private ObservableList<TblEmployeeView> getEmployeeTblPerPage(int pageIndex) {
        ObservableList<TblEmployeeView> employeeViewObservableList = FXCollections.observableArrayList();

        try {
            assert userBo != null;
            List<Employee> employeeList = userBo.getEmployeePerPage(pageIndex * 5);

            for (Employee employee : employeeList) {
                assert jobRoleBo != null;
                String role = jobRoleBo.getJobRole(employee.getJobRole().getId()).getName();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                TblEmployeeView tblEmployeeView = new TblEmployeeView(
                        String.valueOf(employee.getId()),
                        employee.getTitle() + ". " + employee.getFirstName() + " " + employee.getLastName(),
                        userBo.getUser(employee.getUser().getId()).getEMail(),
                        role.substring(0, 1).toUpperCase() + role.substring(1),
                        employee.getContact(),
                        employee.getAddress(),
                        dateFormat.format(employee.getRegisterAt()),
                        dateFormat.format(employee.getModifyAt()),
                        Boolean.TRUE.equals(employee.getIsActive()) ? "Active" : "Disable"
                );
                employeeViewObservableList.add(tblEmployeeView);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return employeeViewObservableList;
    }

    private void employeeCountUpdate() {
        try {
            assert userBo != null;
            dspCount.setText(String.valueOf(userBo.getEmployeeCount()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private Node createTblPage(int pageIndex) {
        if (employeeTable == null) {
            employeeTable = EmployeeCenterController.getInstance().getFxmlLoaderTable().getController();
        }
        TableView<TblEmployeeView> tableView = employeeTable.getTable();
        tableView.setItems(getEmployeeTblPerPage(pageIndex));
        return tableView;
    }

    private int getPageCount() {
        int pageCount = 0;
        try {
            assert userBo != null;
            int employeeCount = userBo.getEmployeeCount();
            if (employeeCount > 5) {
                int tempFirst = employeeCount / 5;
                int tempSecond = employeeCount % 5;

                if (tempSecond != 0) {
                    pageCount = tempFirst + 1;
                } else {
                    pageCount = tempFirst;
                }
            } else {
                pageCount = 1;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return pageCount;
    }

    private int getCurrentPageIndex(int pageIndex, String name) {
        if (Objects.equals(name, "registration")) {
            return getPageCount() - 1;
        } else if ("modification".equals(name)) {
            return pageIndex;
        } else if (DELETION.equals(name) && pageIndex == getPageCount()) {
            return pageIndex - 1;
        } else if (DELETION.equals(name) && pageIndex < getPageCount()) {
            return pageIndex;
        } else if (DELETION.equals(name) && (pageIndex + 1) == getPageCount()) {
            return pageIndex;
        } else {
            return 0;
        }
    }

    @Override
    public void loadTable() {
        tblPagination.setPageFactory(this::createTblPage);
    }

    @Override
    public void updateTbl(String name) {
        int pageIndex = tblPagination.getCurrentPageIndex();
        tblPagination.setPageCount(getPageCount());
        tblPagination.setPageFactory(this::createTblPage);
        tblPagination.setCurrentPageIndex(getCurrentPageIndex(pageIndex, name));
        employeeCountUpdate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeCountUpdate();
        tblPagination.setMaxPageIndicatorCount(10);
        tblPagination.setPageCount(getPageCount());
    }
}
