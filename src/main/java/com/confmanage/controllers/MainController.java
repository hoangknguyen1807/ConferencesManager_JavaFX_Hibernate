package com.confmanage.controllers;

import com.confmanage.DAOs.ConferenceDAO;
import com.confmanage.DAOs.UserDAO;
import com.confmanage.Main;
import com.confmanage.converters.DateConverter;
import com.confmanage.entities.Conference;
import com.confmanage.entities.User;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public final String STR_GUEST_VI = "Khách";

    public BorderPane mainBorderPane;
    public Label labelIntro;
    public Button btnLogIn;
    public Button btnSignUp;
    public TableView<Conference> tableViewConferences;
    public TableColumn<Conference, String> col_brief;
    public TableColumn<Conference, Date> col_date;
    public Button btnSeeAllInfo;
    public ComboBox<String> comboBoxListStyles;
    public Label labelUserInfo;
    public HBox hboxGuest;
    public Button btnLogOut;
    public StackPane stackPaneUserOptions;
    public HBox hboxUserOptions;
    public Tab tabConfRecord;

    private ObservableList<Conference> listConferences;

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
                        seeAllConfInfo(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        Tooltip tooltip = new Tooltip("Nhấn vào đây để xem đầy đủ thông tin tài khoản người dùng");
        labelUserInfo.setTooltip(tooltip);
    }

    public void populateTableViewConferences() {
        if (listConferences != null && listConferences.size() != 0) {
            listConferences.removeAll();
        }

//        final Session session = HibernateUtil.getSession();
//        try {
//            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            EntityType<?> entityType = metamodel.entity(Conference.class);
//            final Query query = session.createQuery("from " + entityType.getName());
//            ObservableList<Conference> observableListConferences = FXCollections.observableArrayList(query.list());
//            String dt = observableListConferences.get(0).getDate().toString();
//            tableViewConferences.setItems(observableListConferences);
//
//        }
//        finally {
//            session.close();
//        }

        ConferenceDAO confDAO = new ConferenceDAO();
        listConferences = FXCollections.observableArrayList(confDAO.getAll());
        tableViewConferences.setItems(listConferences);
    }

    public void onEditCommit(TableColumn.CellEditEvent cellEditEvent) {
    }

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
        Conference selected = tableViewConferences.getSelectionModel().getSelectedItem();
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
                    Main.setCurrentUser(user);
                    labelUserInfo.setText(user.getUsername());
                    labelUserInfo.setUnderline(true);
                    controller.showLoginSuccessDialog(user.getUsername());
                    logInDialog.close();
                    switchTop();
                } else {
                    controller.showInvalidLoginInfoDialog();
                }
            }
        });

        controller.btnCancel.setOnAction(event -> {
            logInDialog.close();
        });

        logInDialog.show();
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
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Đăng ký");
                alert.setResizable(false);
                alert.setHeaderText("Mật khẩu không hợp lệ hoặc nhập lại không trùng. Vui lòng thử lại");
                alert.setContentText("Mật khẩu không được để trống hoặc chứa khoảng trắng");
                alert.initOwner(dialogPane.getScene().getWindow());
                alert.showAndWait();
            }
        });
        controller.btnCancel.setOnAction(event -> {
            signUpDialog.close();
        });
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
        labelUserInfo.setUnderline(false);
    }

    public void openUserInfoWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("userinfo.fxml"));
        Parent root = fxmlLoader.load();

        Scene userInfoScene = new Scene(root);
        Stage userInfoWindow = new Stage();
        userInfoWindow.setTitle("User account info");
        userInfoWindow.setScene(userInfoScene);
        userInfoWindow.initModality(Modality.WINDOW_MODAL);
        userInfoWindow.initOwner(mainBorderPane.getScene().getWindow());
        userInfoWindow.setMinWidth(600.0);
        userInfoWindow.setMinHeight(400.0);

        UserInfoController userInfoController = fxmlLoader.getController();

        userInfoWindow.show();
    }
}
