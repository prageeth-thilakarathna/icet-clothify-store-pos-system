package edu.icet.pos.controller.auth;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.model.employee.Employee;
import edu.icet.pos.model.user.User;
import edu.icet.pos.util.BoType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;

public class AuthCenterController {
    @Getter
    private static final AuthCenterController instance;
    @Getter
    private final FXMLLoader fxmlLoaderLoginPanel = new FXMLLoader(getClass().getResource("/view/auth/loginPanel.fxml"));
    @Getter
    private final Parent parentLoginPanel;
    @Getter
    private final Parent parentBanner;
    @Getter
    private final FXMLLoader fxmlLoaderForgotPassword = new FXMLLoader(getClass().getResource("/view/auth/forgotPasswordPanel.fxml"));
    @Getter
    private final Parent parentForgotPassword;
    @Setter
    private User userLogin;

    private final UserBo userBo = BoFactory.getBo(BoType.USER);

    private AuthCenterController() throws IOException {
        parentLoginPanel = fxmlLoaderLoginPanel.load();
        parentBanner = new FXMLLoader(getClass().getResource("/view/auth/banner.fxml")).load();
        parentForgotPassword = fxmlLoaderForgotPassword.load();
    }

    public static boolean isUser() {
        return AuthCenterController.getInstance().userLogin != null && Objects.equals(AuthCenterController.getInstance().userLogin.getUserRole().getName(), "user");
    }

    public static boolean isUserAssistance() {
        try {
            assert AuthCenterController.getInstance().userBo != null;
            if (isUser() && !AuthCenterController.getInstance().userBo.getAllEmployee().isEmpty()) {
                Employee employee = AuthCenterController.getInstance().userBo.getEmployeeByUserId(AuthCenterController.getInstance().userLogin.getId());
                return Objects.equals(employee.getJobRole().getName(), "assistance");
            }
            return false;
        } catch (Exception e) {
            if (e.getMessage() == null) {
                return false;
            }
        }
        return false;
    }

    public static boolean isUserCashier() {
        try {
            assert AuthCenterController.getInstance().userBo != null;
            if (isUser() && !AuthCenterController.getInstance().userBo.getAllEmployee().isEmpty()) {
                Employee employee = AuthCenterController.getInstance().userBo.getEmployeeByUserId(AuthCenterController.getInstance().userLogin.getId());
                return Objects.equals(employee.getJobRole().getName(), "cashier");
            }
            return false;
        } catch (Exception e) {
            if (e.getMessage() == null) {
                return false;
            }
        }
        return false;
    }

    public static boolean isAdmin() {
        return AuthCenterController.getInstance().userLogin != null && Objects.equals(AuthCenterController.getInstance().userLogin.getUserRole().getName(), "admin");
    }

    static {
        try {
            instance = new AuthCenterController();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating AuthCenterController singleton instance");
        }
    }
}
