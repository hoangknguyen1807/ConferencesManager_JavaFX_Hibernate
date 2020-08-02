package com.confmanage.controllers;

import com.confmanage.DAOs.UserDAO;
import com.confmanage.Main;
import com.confmanage.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {
    public Label lblUserID;
    public TextField txtfldFullName;
    public TextField txtfldEmail;
    public TextField txtfldUsername;
    public PasswordField txtfldOldPassword;
    public PasswordField txtfldNewPassword;
    public PasswordField txtfldRetypeNewPassword;
    public Button btnChangePassword;
    public Button btnUpdateInfo;
    public Button btnCancel;
    private User curUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        curUser = Main.getCurrentUser();
        lblUserID.setText(String.valueOf(curUser.getUserId()));
        txtfldFullName.setText(curUser.getFullName());
        txtfldEmail.setText(curUser.getEmail());
        txtfldUsername.setText(curUser.getUsername());
    }

    public void changeUserPassword(ActionEvent actionEvent) {
        String oldpw = txtfldOldPassword.getText();
        String newpw = txtfldNewPassword.getText();
        String retypenewpw = txtfldRetypeNewPassword.getText();

        if (!BCrypt.checkpw(oldpw, curUser.getPassword())) {
            showIncorrectPasswordDialog();
        } else {
            if (SignUpDialogController.checkValidAndMatch(newpw, retypenewpw)) {
                String hashed = BCrypt.hashpw(newpw, BCrypt.gensalt());
                curUser.setPassword(hashed);
                UserDAO userDAO = new UserDAO();
                userDAO.update(curUser);

            } else {
                showInvalidPasswordDialog();
            }
        }
    }

    public void updateUserInfo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("confirmwithpassword.fxml"));
        DialogPane dialogPane = loader.load();
        ConfirmWithPasswordController controller = loader.getController();

        Dialog<ButtonType> confirmDialog = new Dialog<>();
        confirmDialog.setDialogPane(dialogPane);
        confirmDialog.setTitle("Cập nhật thông tin người dùng...");
        confirmDialog.setResizable(false);
        confirmDialog.setHeaderText("Vui lòng nhập password hiện tại để xác nhận");

        confirmDialog.getDialogPane();
        confirmDialog.initOwner(btnUpdateInfo.getScene().getWindow());
        confirmDialog.showAndWait();

        if (controller.getResult()) {
            curUser.setFullName(txtfldFullName.getText());
            curUser.setEmail(txtfldEmail.getText());
            curUser.setUsername(txtfldUsername.getText());
            UserDAO userDAO = new UserDAO();
            userDAO.update(curUser);
        }
    }

    public void showIncorrectPasswordDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Đổi mật khẩu...");
        alert.setResizable(false);
        alert.setHeaderText("Mật khẩu đã nhập không đúng");
        alert.setContentText("Mật khẩu sai. Hãy thử lại.");
        alert.initOwner(btnChangePassword.getScene().getWindow());
        alert.showAndWait();
    }

    public void showInvalidPasswordDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Đổi mật khẩu...");
        alert.setResizable(false);
        alert.setHeaderText("Mật khẩu không hợp lệ hoặc nhập lại không trùng. Vui lòng thử lại");
        alert.setContentText("Mật khẩu không được để trống hoặc chứa khoảng trắng");
        alert.initOwner(btnChangePassword.getScene().getWindow());
        alert.showAndWait();
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
