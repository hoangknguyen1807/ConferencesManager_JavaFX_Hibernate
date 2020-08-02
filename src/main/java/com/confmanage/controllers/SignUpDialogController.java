package com.confmanage.controllers;

import com.confmanage.Main;
import com.confmanage.entities.Permission;
import com.confmanage.entities.User;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpDialogController implements Initializable {
    public TextField txtfldFullName;
    public TextField txtfldEmail;
    public TextField txtfldUsername;
    public PasswordField passwordField;
    public PasswordField retypePasswordField;
    public Button btnRegister;
    public DialogPane dialogPaneSignUp;
    public Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Node closeButton = dialogPaneSignUp.lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
    }

    public boolean checkRetypePassword() {
        String pw = passwordField.getText();
        String rtpw = retypePasswordField.getText();
        return checkValidAndMatch(pw, rtpw);
    }

    public static boolean checkValidAndMatch(String pw, String rtpw) {
        if (pw == null || pw.isBlank()) {
            return false;
        }
        if (pw.indexOf(' ') >= 0) {
            return false;
        }

        return pw.equals(rtpw);
    }

    public void showInvalidPasswordDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Đăng ký...");
        alert.setResizable(false);
        alert.setHeaderText("Mật khẩu không hợp lệ hoặc nhập lại không trùng. Vui lòng thử lại");
        alert.setContentText("Mật khẩu không được để trống hoặc chứa khoảng trắng");
        alert.initOwner(dialogPaneSignUp.getScene().getWindow());
        alert.showAndWait();
    }

    public User getNewSignedUpUser() {
        User newUser = new User(Main.getPermissions().get(Permission.USER));
        newUser.setFullName(txtfldFullName.getText());
        newUser.setEmail(txtfldEmail.getText());
        newUser.setUsername(txtfldUsername.getText());

        String pw = passwordField.getText();
        // Hash a password for the first time
        String hashed = BCrypt.hashpw(pw, BCrypt.gensalt());

        newUser.setPassword(hashed);

        return newUser;
    }
}
