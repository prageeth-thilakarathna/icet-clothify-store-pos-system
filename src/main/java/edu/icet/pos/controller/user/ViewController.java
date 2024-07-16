package edu.icet.pos.controller.user;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.user.custom.UserTable;
import edu.icet.pos.controller.user.custom.UserView;
import edu.icet.pos.model.user.TblUserView;
import edu.icet.pos.model.user.User;
import edu.icet.pos.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewController implements UserView {
    @FXML
    private Pagination tblPagination;
    @FXML
    private Label dspCount;

    private final UserBo userBo = BoFactory.getBo(BoType.USER);
    private UserTable userTable;
    private static final String DELETION = "deletion";

    private ObservableList<TblUserView> getUserTblPerPage(int pageIndex) {
        ObservableList<TblUserView> userTblList = FXCollections.observableArrayList();
        try {
            assert userBo != null;
            List<User> userList = userBo.getUserPerPage(pageIndex * 5);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (User user : userList) {
                TblUserView tblUserOb = new TblUserView(
                        String.valueOf(user.getId()),
                        user.getEMail(),
                        user.getUserRole().getId() == 1 ? "User" : "Admin",
                        dateFormat.format(user.getRegisterAt()),
                        dateFormat.format(user.getModifyAt()),
                        Boolean.TRUE.equals(user.getIsActive()) ? "Active" : "Disable"
                );
                userTblList.add(tblUserOb);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return userTblList;
    }

    private void userCountUpdate() {
        try {
            assert userBo != null;
            dspCount.setText(String.valueOf(userBo.getUserCount()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private Node createTblPage(int pageIndex) {
        if (userTable == null) {
            userTable = UserCenterController.getInstance().getFxmlLoaderTable().getController();
        }
        TableView<TblUserView> tableView = userTable.getTable();
        tableView.setItems(getUserTblPerPage(pageIndex));
        return tableView;
    }

    private int getPageCount() {
        int pageCount = 0;
        try {
            assert userBo != null;
            int userCount = userBo.getUserCount();
            if (userCount > 5) {
                int tempFirst = userCount / 5;
                int tempSecond = userCount % 5;

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
    public void updateTbl(String name) {
        int pageIndex = tblPagination.getCurrentPageIndex();
        tblPagination.setPageCount(getPageCount());
        tblPagination.setPageFactory(this::createTblPage);
        tblPagination.setCurrentPageIndex(getCurrentPageIndex(pageIndex, name));
        userCountUpdate();
    }

    @Override
    public void loadTable() {
        tblPagination.setPageFactory(this::createTblPage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userCountUpdate();
        tblPagination.setMaxPageIndicatorCount(10);
        tblPagination.setPageCount(getPageCount());
    }
}
