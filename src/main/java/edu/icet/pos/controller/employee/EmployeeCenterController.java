package edu.icet.pos.controller.employee;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;

import java.io.IOException;

@Getter
public class EmployeeCenterController {
    @Getter
    private static final EmployeeCenterController instance;
    private final FXMLLoader fxmlLoaderForm = new FXMLLoader(getClass().getResource("/view/employee/form.fxml"));
    private final Parent parentForm;
    private final FXMLLoader fxmlLoaderSearch = new FXMLLoader(getClass().getResource("/view/employee/search.fxml"));
    private final Parent parentSearch;

    private EmployeeCenterController() throws IOException {
        parentForm = fxmlLoaderForm.load();
        parentSearch = fxmlLoaderSearch.load();
    }

    static {
        try{
            instance = new EmployeeCenterController();
        } catch (Exception e){
            throw new RuntimeException("Exception occurred in creating EmployeeCenterController singleton instance");
        }
    }
}
