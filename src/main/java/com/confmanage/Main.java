package com.confmanage;

import com.confmanage.DAOs.PermissionDAO;
import com.confmanage.controllers.MainController;
import com.confmanage.entities.Permission;
import com.confmanage.entities.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * JavaFX Main
 */

public class Main extends Application {

    private static User currentUser = null;

    private static List<Permission> permissions;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Main.currentUser = currentUser;
    }

    public static List<Permission> getPermissions() {
        return permissions;
    }

    private static MainController mainController;

    public static MainController getMainController() {
        return mainController;
    }

    @Override
    public void init() throws Exception {
        super.init();
        PermissionDAO permissionDAO = new PermissionDAO();
        permissions = permissionDAO.getAll();

        if (permissions == null || permissions.isEmpty()) {
            for (Integer k : Permission.getMap().keySet()) {
                permissionDAO.save(new Permission(k));
            }
            permissions = permissionDAO.getAll();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Conferences Manager");
        stage.setMinWidth(960.0);
        stage.setMinHeight(500.0);
        stage.setOnCloseRequest(e -> {
            if (!showClosingWindowDialog(stage))
                e.consume();
        });
        stage.show();

        mainController = fxmlLoader.getController();
        mainController.populateTableViewConferences();
    }

    public static void main(String[] args) {
        launch();
    }

    public static boolean showClosingWindowDialog(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting..");
        alert.setResizable(false);
        alert.setContentText("Are you sure you want to quit?");
        alert.initOwner(stage);

        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent() && result.get() == ButtonType.OK);
    }
}