package com.confmanage.controllers;

import com.confmanage.Main;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInDialogController implements Initializable {
    public TextField txtfldUserName;
    public PasswordField passwordField;
    public Button btnSignIn;
    public Button btnCancel;
    public DialogPane dialogPaneLogIn;
    public Label labelRegister;

    private Font system_bold;
    private Font system_regular;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Node closeButton = dialogPaneLogIn.lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);

        system_regular = new Font("System", 10.0);
        system_bold = new Font("System Bold", 10.0);

    }

    public String getUsername() {
        return txtfldUserName.getText();
    }

    public String getEnteredPassword() {
        return passwordField.getText();
    }

    public void showInvalidLoginInfoDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Đăng nhập...");
        alert.setResizable(false);
        alert.setHeaderText("Thông tin đăng nhập không hợp lệ");
        alert.setContentText("Tên đăng nhập không tồn tại hoặc mật khẩu sai");
        alert.initOwner(dialogPaneLogIn.getScene().getWindow());
        alert.showAndWait();
    }

    public void showLoginSuccessDialog(String username) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Đăng nhập...");
        alert.setResizable(false);
        alert.setHeaderText("Đăng nhập thành công!");
        alert.setContentText("Xin chào người dùng " + username + " !");
        alert.initOwner(dialogPaneLogIn.getScene().getWindow());
        alert.showAndWait();
    }

    public void labelRegisterMouseEnter(MouseEvent mouseEvent) {
        labelRegister.setFont(system_bold);
        labelRegister.setUnderline(true);
    }

    public void labelRegisterMouseExit(MouseEvent mouseEvent) {
        labelRegister.setFont(system_regular);
        labelRegister.setUnderline(false);
    }

    public void labelRegisterClick(MouseEvent mouseEvent) {
        MainController controller = Main.getMainController();
        controller.btnSignUp.fire();
    }
}
