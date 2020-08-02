package com.confmanage.controllers;

import com.confmanage.DAOs.ConferenceDAO;
import com.confmanage.DAOs.Conference_UserDAO;
import com.confmanage.DAOs.UserDAO;
import com.confmanage.Main;
import com.confmanage.converters.BooleanConverter;
import com.confmanage.converters.DateConverter;
import com.confmanage.converters.PermissionConverter;
import com.confmanage.converters.VenueConverter;
import com.confmanage.entities.*;
import hibernate.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    public final String STR_GUEST_VI = "Khách";

    public BorderPane mainBorderPane;
    public Label labelIntro;
    public Button btnLogIn;
    public Button btnSignUp;
    public TableView<Conference> tableViewConferences;
    public TableColumn<Conference, Date> col_date;
    public Button btnSeeAllInfo;
    public ComboBox<String> comboBoxListStyles;
    public Label labelUserInfo;
    public HBox hboxGuest;
    public Button btnLogOut;
    public HBox hboxUserOptions;
    public Tab tabConfRecord;
    public TableView<Conference> tableViewEnrolledConferences;
    public Button btnSeeAllInfo2;
    public Button btnCancelAttendance;
    public TableColumn<Conference, Date> col_date2;
    public Tab tabManageConferences;
    public Tab tabManageUsers;
    public TableView<Conference> tableViewManageConfs;
    public TableColumn col_Name;
    public TableColumn col_Brief;
    public TableColumn col_Image;
    public TableColumn<Conference, Date> col_Date;
    public TableColumn<Conference, Venue> col_Venue;
    public Button btnAdminSeeAllInfo;
    public Button btnAddNewConf;
    public Button btnEditConf;
    public TableView<User> tableViewManageUsers;
    public TableColumn col_FullName;
    public TableColumn col_Username;
    public TableColumn col_Email;
    public TableColumn<User, Boolean> col_IsActive;
    public TableColumn<User, Permission> col_Permission;
    public Button btnDisableAccount;
    public Button btnEnableAccount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        col_date.setCellFactory(new Callback<TableColumn<Conference, Date>, TableCell<Conference, Date>>() {
//            @Override
//            public TableCell<Conference, Date> call(TableColumn<Conference, Date> param) {
//                return new TableCell<Conference, Date>() {
//
//                };
//            }
//        });
        col_date.setCellFactory(TextFieldTableCell.forTableColumn(new DateConverter()));
        col_date2.setCellFactory(TextFieldTableCell.forTableColumn(new DateConverter()));

        col_IsActive.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanConverter()));
        col_Permission.setCellFactory(TextFieldTableCell.forTableColumn(new PermissionConverter()));

        col_Date.setCellFactory(TextFieldTableCell.forTableColumn(new DateConverter()));
        col_Venue.setCellFactory(TextFieldTableCell.forTableColumn(new VenueConverter()));

        ObservableList<String> styles = FXCollections.observableArrayList();
        styles.addAll("Danh sách", "Card view");
        comboBoxListStyles.setItems(styles);
        comboBoxListStyles.getSelectionModel().selectFirst();

        tableViewConferences.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableViewConferences.setRowFactory(param -> {
            TableRow<Conference> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        showConfAllInfoWindow(row.getItem());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        tableViewEnrolledConferences.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableViewEnrolledConferences.setRowFactory(param -> {
            TableRow<Conference> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        showConfAllInfoWindow(row.getItem());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        tableViewManageConfs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableViewManageConfs.setRowFactory(param -> {
            TableRow<Conference> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        showConfAllInfoWindow(row.getItem());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        Tooltip tooltip = new Tooltip("Nhấn vào đây để xem đầy đủ thông tin tài khoản người dùng");
        labelUserInfo.setTooltip(tooltip);
        tabConfRecord.setDisable(true);
        tabManageConferences.setDisable(true);
        tabManageUsers.setDisable(true);
    }

    public void populateTableViewConferences() {
        ConferenceDAO confDAO = new ConferenceDAO();
        ObservableList<Conference> listConferences = FXCollections.observableArrayList(confDAO.getAll());
        tableViewConferences.setItems(listConferences);
    }

//    public void onEditCommit(TableColumn.CellEditEvent cellEditEvent) {
//    }

    public void clickMenuItemExit(ActionEvent actionEvent) {
        Stage mainStage = (Stage) mainBorderPane.getScene().getWindow();
        if (Main.showClosingWindowDialog(mainStage)) {
            mainStage.close();
        }
    }

    public void showAboutDialog(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About...");
        alert.setResizable(false);
        alert.setHeaderText("Conferences Manager - JavaFX + Hibernate");
        alert.setContentText("Credits : Nguyen Khanh Hoang - 1712457");
        alert.initOwner(mainBorderPane.getScene().getWindow());
        alert.showAndWait();
    }

    public void seeAllConfInfo(ActionEvent actionEvent) throws IOException {
        showSelectedConfInfo(tableViewConferences);
        populateTableViewEnrolledConferences(Main.getCurrentUser());
    }

    public void showSelectedConfInfo(TableView<Conference> tableView) throws IOException {
        Conference selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Xem chi tiết Hội nghị");
            alert.setResizable(false);
            alert.setHeaderText("Chưa có hội nghị nào được chọn");
            alert.setContentText("Hãy chọn một hội nghị ở danh sách bên dưới để xem chi tiết");
            alert.initOwner(mainBorderPane.getScene().getWindow());
            alert.showAndWait();
            return;
        }
        showConfAllInfoWindow(selected);
    }

    public void showConfAllInfoWindow(Conference selected) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("seeallinfo.fxml"));
        Parent root = fxmlLoader.load();

        Scene seeAllInfoScene = new Scene(root);
        Stage seeAllInfoWindow = new Stage();
        seeAllInfoWindow.setTitle("See all Conference info");
        seeAllInfoWindow.setScene(seeAllInfoScene);
        seeAllInfoWindow.initModality(Modality.WINDOW_MODAL);
        seeAllInfoWindow.initOwner(mainBorderPane.getScene().getWindow());
        seeAllInfoWindow.setMinWidth(700.0);
        seeAllInfoWindow.setMinHeight(540.0);

        SeeAllInfoController seeAllInfoController = fxmlLoader.getController();
        seeAllInfoController.showConferenceInfo(selected);

        seeAllInfoWindow.show();

    }

    public void refreshList(ActionEvent actionEvent) {
        populateTableViewConferences();
    }

    public void userLogIn(ActionEvent actionEvent) throws IOException {
        Dialog<ButtonType> logInDialog = new Dialog<>();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("logindialog.fxml"));
        DialogPane dialogPane = loader.load();
        logInDialog.setDialogPane(dialogPane);
        logInDialog.initOwner(mainBorderPane.getScene().getWindow());
//        dialog.initModality(Modality.WINDOW_MODAL);
//        logInDialog.setResizable(true);
        logInDialog.setTitle("Đăng nhập");

        LogInDialogController controller = loader.getController();
        controller.btnSignIn.setOnAction(event -> {
            String username = controller.getUsername();
            String enteredpw = controller.getEnteredPassword();
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.getAll();
            Optional<User> optionalUser = users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
            if (optionalUser.isEmpty()) {
                controller.showInvalidLoginInfoDialog();
            } else {
                User user = optionalUser.get();
                if (BCrypt.checkpw(enteredpw, user.getPassword())) {
                    if (user.getIsActive()) {
                        Main.setCurrentUser(user);
                        labelUserInfo.setText(user.getUsername());
                        labelUserInfo.setTextFill(Paint.valueOf("blue"));
                        labelUserInfo.setUnderline(true);
                        controller.showLoginSuccessDialog(user.getUsername());
                        logInDialog.close();
                        switchTop();
                        tabConfRecord.setDisable(false);
                        Permission pmsAdmin = Main.getPermissions().get(Permission.ADMIN);
                        if (user.getPermission().getPermissionId() ==
                                pmsAdmin.getPermissionId()) {
                            tabManageConferences.setDisable(false);
                            tabManageUsers.setDisable(false);
                            populateTableViewManageUsers();
                            populateTableViewManageConfs();
                        }
                        populateTableViewEnrolledConferences(user);
                    } else {
                        showUserAccountDisabledDialog();
                    }
                } else {
                    controller.showInvalidLoginInfoDialog();
                }
            }
        });

        controller.btnCancel.setOnAction(event -> logInDialog.close());

        logInDialog.show();
    }

    private void populateTableViewManageConfs() {
        ConferenceDAO dao = new ConferenceDAO();
        ObservableList<Conference> conferences = FXCollections.observableArrayList(dao.getAll());
        tableViewManageConfs.setItems(conferences);
    }

    private void populateTableViewManageUsers() {
        UserDAO userDAO = new UserDAO();
        ObservableList<User> users = FXCollections.observableArrayList(userDAO.getAll());
        tableViewManageUsers.setItems(users);
    }

    private void showUserAccountDisabledDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Đăng nhập vào tài khoản Người dùng...");
        alert.setResizable(false);
        alert.setHeaderText("Rất tiếc! Tài khoản của bạn hiện đang bị khóa.");
        alert.setContentText("Tài khoản này hiện đang bị vô hiệu hóa. Xin liên hệ quản trị viên để" +
                "giải quyết.");
        alert.initOwner(mainBorderPane.getScene().getWindow());
        alert.showAndWait();
    }

    private void populateTableViewEnrolledConferences(User obj) {
        Session session = HibernateUtil.getSession();
        StoredProcedureQuery getParticipants = session.
                createStoredProcedureQuery("getAttendedConferencesByUser", Conference.class);
        getParticipants.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN);

        getParticipants.setParameter("userId", obj.getUserId());
        ObservableList<Conference> enrolledList = FXCollections.observableArrayList(getParticipants.getResultList());

        tableViewEnrolledConferences.setItems(enrolledList);

        session.close();
    }

    public void guestSignUp(ActionEvent actionEvent) throws IOException {
        Dialog<ButtonType> signUpDialog = new Dialog<>();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("signupdialog.fxml"));
        DialogPane dialogPane = loader.load();
        signUpDialog.setDialogPane(dialogPane);
        signUpDialog.initOwner(mainBorderPane.getScene().getWindow());
//        dialog.initModality(Modality.WINDOW_MODAL);
//        logInDialog.setResizable(true);
        signUpDialog.setTitle("Đăng ký tài khoản mới");

        SignUpDialogController controller = loader.getController();
        controller.btnRegister.setOnAction(event -> {
            if (controller.checkRetypePassword()) {
                User newUser = controller.getNewSignedUpUser();
                UserDAO userDAO = new UserDAO();
                userDAO.save(newUser);
                signUpDialog.close();
            } else {
                controller.showInvalidPasswordDialog();
            }
        });
        controller.btnCancel.setOnAction(event -> signUpDialog.close());
        signUpDialog.show();
    }

    private void switchTop() {
        ObservableList<Node> children = hboxUserOptions.getChildren();
        Node top = children.get(children.size() - 1);
        Node newTop = children.get(0);

        top.toBack();
        top.setVisible(false);
        newTop.toFront();
        newTop.setVisible(true);
    }

    public void userLogOut(ActionEvent actionEvent) {
        Main.setCurrentUser(null);
        switchTop();
        labelUserInfo.setText(STR_GUEST_VI);
        labelUserInfo.setTextFill(Paint.valueOf("black"));
        labelUserInfo.setUnderline(false);
        tabConfRecord.setDisable(true);
        tabManageConferences.setDisable(true);
        tabManageUsers.setDisable(true);
    }

    public void openUserInfoWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("userinfo.fxml"));
        Parent root = fxmlLoader.load();

        Scene userInfoScene = new Scene(root);
        Stage userInfoWindow = new Stage();
        userInfoWindow.setTitle("Thông tin tài khoản Người dùng");
        userInfoWindow.setScene(userInfoScene);
        userInfoWindow.initModality(Modality.WINDOW_MODAL);
        userInfoWindow.initOwner(mainBorderPane.getScene().getWindow());
        userInfoWindow.setResizable(false);
        userInfoWindow.setMinWidth(600.0);
        userInfoWindow.setMinHeight(400.0);

        UserInfoController userInfoController = fxmlLoader.getController();
//        userInfoController.showAllUserInfo();

        userInfoWindow.showAndWait();
        labelUserInfo.setText(Main.getCurrentUser().getUsername());
    }

    public void seeAllConfInfo2(ActionEvent actionEvent) throws IOException {
        showSelectedConfInfo(tableViewEnrolledConferences);
    }

    public void cancelConfAttendance(ActionEvent actionEvent) {
        Conference selected = tableViewEnrolledConferences.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Xem chi tiết Hội nghị");
            alert.setResizable(false);
            alert.setHeaderText("Chưa có hội nghị nào được chọn");
            alert.setContentText("Hãy chọn một hội nghị ở danh sách bên dưới để huỷ tham dự");
            alert.initOwner(mainBorderPane.getScene().getWindow());
            alert.showAndWait();
            return;
        }

        Calendar calendar = Calendar.getInstance();
        Date curDate = new Date(System.currentTimeMillis());
        calendar.setTime(curDate);
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int tomonth = calendar.get(Calendar.MONTH);
        int toyear = calendar.get(Calendar.YEAR);

        calendar.setTime(selected.getDate());
        int confday = calendar.get(Calendar.DAY_OF_MONTH);
        int confmonth = calendar.get(Calendar.MONTH);
        int confyear = calendar.get(Calendar.YEAR);

        if (toyear < confyear) {
            cancelUserEnrollment(Main.getCurrentUser(), selected);
        } else {
            if (toyear == confyear) {
                if (tomonth < confmonth) {
                    cancelUserEnrollment(Main.getCurrentUser(), selected);
                } else {
                    if (tomonth == confmonth) {
                        if (today < confday) {
                            cancelUserEnrollment(Main.getCurrentUser(), selected);
                        } else {
                            showConfAlreadyTookPlace();
                        }
                    } else {
                        showConfAlreadyTookPlace();
                    }
                }
            } else {
                showConfAlreadyTookPlace();
            }
        }
    }

    private void showConfAlreadyTookPlace() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Huỷ tham dự Hội nghị...");
        alert.setResizable(false);
        alert.setHeaderText("Hội nghị đã diễn ra.");
        alert.setContentText("Không thể hủy tham dự hội nghị đã diễn ra.");
        alert.initOwner(mainBorderPane.getScene().getWindow());
        alert.showAndWait();
    }

    private void cancelUserEnrollment(User user, Conference conf) {
        Conference_UserDAO dao = new Conference_UserDAO();
        Iterator<Conference_User> it = dao.getAll().stream().
                filter(cu -> cu.getUser().getUserId() == user.getUserId()
                        && cu.getConference().getConfId() == conf.getConfId()).iterator();
        if (it.hasNext()) {
            dao.delete(it.next());
        }

        populateTableViewEnrolledConferences(user);
    }

    public void disableUserAccount(ActionEvent actionEvent) {
        User user = tableViewManageUsers.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Vô hiệu hóa người dùng");
            alert.setResizable(false);
            alert.setHeaderText("Chưa có người dùng nào được chọn");
            alert.setContentText("Hãy chọn một người dùng ở danh sách bên dưới để vô hiệu hóa");
            alert.initOwner(mainBorderPane.getScene().getWindow());
            alert.showAndWait();
            return;
        }

        if (user.getIsActive()) {
            user.setIsActive(false);
            UserDAO userDAO = new UserDAO();
            userDAO.update(user);
        }

        populateTableViewManageUsers();
    }

    public void enableUserAccount(ActionEvent actionEvent) {
        User user = tableViewManageUsers.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Kích hoạt người dùng");
            alert.setResizable(false);
            alert.setHeaderText("Chưa có người dùng nào được chọn");
            alert.setContentText("Hãy chọn một người dùng ở danh sách bên dưới để kích hoạt");
            alert.initOwner(mainBorderPane.getScene().getWindow());
            alert.showAndWait();
            return;
        }

        if (!user.getIsActive()) {
            user.setIsActive(true);
            UserDAO userDAO = new UserDAO();
            userDAO.update(user);
        }

        populateTableViewManageUsers();
    }

    public void seeAllConfInfo3(ActionEvent actionEvent) throws IOException {
        showSelectedConfInfo(tableViewManageConfs);
    }

    public void editConferenceInfo(ActionEvent actionEvent) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("seeallinfo.fxml"));
//        Parent root = fxmlLoader.load();
//
//        Scene seeAllInfoScene = new Scene(root);
//        Stage seeAllInfoWindow = new Stage();
//        seeAllInfoWindow.setTitle("See all Conference info");
//        seeAllInfoWindow.setScene(seeAllInfoScene);
//        seeAllInfoWindow.initModality(Modality.WINDOW_MODAL);
//        seeAllInfoWindow.initOwner(mainBorderPane.getScene().getWindow());
//        seeAllInfoWindow.setMinWidth(700.0);
//        seeAllInfoWindow.setMinHeight(540.0);
//
//        SeeAllInfoController seeAllInfoController = fxmlLoader.getController();
//        seeAllInfoController.showConferenceInfo(selected);
//
//        seeAllInfoWindow.show();
    }
}
