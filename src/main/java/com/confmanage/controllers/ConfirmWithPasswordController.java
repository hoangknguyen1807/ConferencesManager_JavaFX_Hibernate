package com.confmanage.controllers;

import com.confmanage.Main;
import com.confmanage.entities.User;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmWithPasswordController implements Initializable {
    public DialogPane dialogPaneConfirmWithPassword;
    public Button btnOK;
    public Button btnCancel;
    public PasswordField txtfldEnterPassword;
    private boolean result;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Node closeButton = dialogPaneConfirmWithPassword.lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);

        btnCancel.setOnAction(event -> {
            result = false;
            stage = getStage();
            stage.close();
        });

        btnOK.setOnAction(event -> {
            String enteredpw = txtfldEnterPassword.getText();
            User curUser = Main.getCurrentUser();
            stage = getStage();
            if (BCrypt.checkpw(enteredpw, curUser.getPassword())) {
                result = true;
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Nhập mật khẩu...");
                alert.setResizable(false);
                alert.setHeaderText("Mật khẩu đã nhập không đúng");
                alert.setContentText("Mật khẩu sai. Hãy thử lại.");
                alert.initOwner(btnOK.getScene().getWindow());
                alert.showAndWait();
            }
        });
    }

    public boolean getResult() {
        return result;
    }

    public Stage getStage() {
        return (Stage) btnCancel.getScene().getWindow();
    }
}
