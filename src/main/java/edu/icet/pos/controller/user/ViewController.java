package edu.icet.pos.controller.user;

import edu.icet.pos.bo.BoFactory;
import edu.icet.pos.bo.custom.UserBo;
import edu.icet.pos.controller.user.custom.UserTableCustom;
import edu.icet.pos.controller.user.custom.UserViewCustom;
import edu.icet.pos.model.TblUserView;
import edu.icet.pos.model.User;
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
import java.util.ResourceBundle;

public class ViewController implements UserViewCustom {
    @FXML
    private Pagination tblPagination;
    @FXML
    private Label dspCount;

    private final UserBo userBo = BoFactory.getBo(BoType.USER);
    private final UserTableCustom userTableCustom = UserCenterController.getInstance().getFxmlLoaderTable().getController();

    private ObservableList<TblUserView> getUserTblPerPage(int pageIndex){
        ObservableList<TblUserView> userTblList = FXCollections.observableArrayList();
        try{
            assert userBo != null;
            List<User> userList = userBo.getUserPerPage(pageIndex*5);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            for(User user : userList){
                TblUserView tblUserOb = new TblUserView(
                        String.valueOf(user.getId()),
                        user.getEMail(),
                        user.getUserRole().getId()==1 ? "User":"Admin",
                        dateFormat.format(user.getRegisterAt()),
                        dateFormat.format(user.getModifyAt()),
                        Boolean.TRUE.equals(user.getIsActive()) ? "Active":"Disable"
                );
                userTblList.add(tblUserOb);
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return userTblList;
    }

    private void userCountUpdate(){
        try{
            assert userBo != null;
            dspCount.setText(String.valueOf(userBo.getUserCount()));
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private Node createTblPage(int pageIndex){
        TableView<TblUserView> tableView = userTableCustom.getTable();
        tableView.setItems(getUserTblPerPage(pageIndex));
        return tableView;
    }

    private int getPageCount(){
        int pageCount = 0;
        try{
            assert userBo != null;
            int userCount = userBo.getUserCount();
            if(userCount>5){
                int tempFirst = userCount/5;
                int tempSecond = userCount%5;

                if(tempSecond!=0){
                    pageCount = tempFirst+1;
                } else {
                    pageCount = tempFirst;
                }
            } else {
                pageCount = 1;
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
        return pageCount;
    }

    @Override
    public void updateTbl() {
        int pageIndex = tblPagination.getCurrentPageIndex();
        tblPagination.setPageFactory(e -> createTblPage(pageIndex));
        userCountUpdate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userCountUpdate();
        tblPagination.setPageCount(getPageCount());
        tblPagination.setPageFactory(this::createTblPage);
    }
}
