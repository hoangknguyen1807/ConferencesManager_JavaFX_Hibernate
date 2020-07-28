package com.confmanage.controllers;

import com.confmanage.Main;
import com.confmanage.converters.DateConverter;
import com.confmanage.entities.Conference;
import com.confmanage.entities.User;
import hibernate.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SeeAllInfoController implements Initializable {

    String imagesDir;
    Image notAvailable;
    public ImageView imgConference;
    public Label lblConfId;
    public Label lblConfName;
    public Label lblConfDate;
    public Label lblConfVenue;
    public Label lblConfBrief;
    public Label lblConfDetail;
    public ListView<User> listViewParticipants;
    public Button btnRegisterConference;
    public Button btnCloseWindow;
    private Conference conf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imagesDir = "/com/confmanage/images/";
        URL na = getClass().getResource(imagesDir + "notavailable.png");
        notAvailable = new Image(na.toString());

        listViewParticipants.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewParticipants.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                return new ListCell<User>() {
                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            this.setText(item.getUsername() +
                                    " - " + item.getFullName());
                        }
                    }
                };
            }
        });
    }

    public void populateListViewParticipants(Conference obj) {
//        if (participants != null && !participants.isEmpty()) {
//            participants.removeAll(participants);
//        }

        Session session = HibernateUtil.getSession();
        StoredProcedureQuery getParticipants = session.createStoredProcedureQuery("getParticipantsByConference", User.class);
        getParticipants.registerStoredProcedureParameter("confId", Integer.class, ParameterMode.IN);

        getParticipants.setParameter("confId", obj.getConfId());
        ObservableList<User> participants = FXCollections.observableArrayList(getParticipants.getResultList());


        listViewParticipants.setItems(participants);

        session.close();
    }

    public void showConferenceInfo(Conference obj) {
        conf = obj;
        lblConfId.setText(String.valueOf(obj.getConfId()));
        lblConfName.setText(obj.getName());
        DateConverter converter = new DateConverter();
        lblConfDate.setText(converter.toString(obj.getDate()));
        lblConfVenue.setText(obj.getVenue().toString());
        lblConfBrief.setText(obj.getBrief());
        lblConfDetail.setText(obj.getDetail());

        URL url = getClass().getResource(imagesDir + obj.getImage());
        if (url != null) {
            imgConference.setImage(new Image(url.toString()));
        }

        populateListViewParticipants(obj);
    }

    public void enrollForConference(ActionEvent actionEvent) {
        User curUser = Main.getCurrentUser();
        if (curUser == null) {
            MainController controller = Main.getMainController();
            controller.btnLogIn.fire();
        } else {
            Session session = HibernateUtil.getSession();
            Transaction tx = null;

            try {
                tx = session.beginTransaction();
                String stringQuery = "select * from quanlyhoinghi.conference_user where " +
                        "conference_user.Conference_confId = :confId and " +
                        "conference_user.participants_userId = :userId ;";
                Query query = session.createSQLQuery(stringQuery);
                query.setParameter("confId", conf.getConfId());
                query.setParameter("userId", curUser.getUserId());

                List list = query.list();
                if (list.isEmpty()) {
                    stringQuery = String.
                            format("INSERT INTO quanlyhoinghi.conference_user (Conference_confId, participants_userId) VALUES (%s, %s);",
                                    conf.getConfId(), curUser.getUserId());
                    query = session.createSQLQuery(stringQuery);
                    query.executeUpdate();
                    tx.commit();
                } else {
                    showUserAlreadyEnrolledDialog();
                    return;
                }
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            showEnrollSuccessDialog();
            populateListViewParticipants(conf);
        }
    }

    private void showEnrollSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Đăng ký tham dự Hội nghị...");
        alert.setResizable(false);
        alert.setHeaderText("Bạn vừa đăng ký tham dự Hội nghị thành công");
        alert.setContentText("Bạn vừa được ghi tên trong danh sách tham dự Hội nghị này");
        alert.initOwner(btnCloseWindow.getScene().getWindow());
        alert.showAndWait();
    }

    private void showUserAlreadyEnrolledDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Đăng ký tham dự Hội nghị...");
        alert.setResizable(false);
        alert.setHeaderText("Bạn đã đăng ký tham dự Hội nghị này");
        alert.setContentText("Bạn đã có tên trong danh sách tham dự Hội nghị");
        alert.initOwner(btnCloseWindow.getScene().getWindow());
        alert.showAndWait();
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCloseWindow.getScene().getWindow();
        stage.close();
    }
}
