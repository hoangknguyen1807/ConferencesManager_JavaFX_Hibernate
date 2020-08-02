package com.confmanage.controllers;

import com.confmanage.DAOs.Conference_UserDAO;
import com.confmanage.Main;
import com.confmanage.converters.DateConverter;
import com.confmanage.entities.Conference;
import com.confmanage.entities.Conference_User;
import com.confmanage.entities.User;
import hibernate.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.Session;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.net.URL;
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
    private ObservableList<User> participants;

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
        participants = FXCollections.observableArrayList(getParticipants.getResultList());

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
            FilteredList<User> filtered = participants.
                    filtered(user -> user.getUsername().equals(curUser.getUsername()));
            if (filtered.isEmpty()) {
//                Session session = HibernateUtil.getSession();
//                Transaction tx = null;
//                try {
///*              tx = session.beginTransaction();
//                String stringQuery = "select * from quanlyhoinghi.conference_user where " +
//                        "conference_user.Conference_confId = :confId and " +
//                        "conference_user.participants_userId = :userId ;";
//                Query query = session.createSQLQuery(stringQuery);
//                query.setParameter("confId", conf.getConfId());
//                query.setParameter("userId", curUser.getUserId());
//                List list = query.list();*/
//                    tx = session.beginTransaction();
////                    String stringQuery = String.
////                            format("INSERT INTO quanlyhoinghi.conference_user (Conference_confId, participants_userId) VALUES (%s, %s);",
////                                    conf.getConfId(), curUser.getUserId());
////                    Query query = session.createSQLQuery(stringQuery);
////                    query.executeUpdate();
//                    tx.commit();
//                } catch (Exception e) {
//                    if (tx != null) tx.rollback();
//                    e.printStackTrace();
//                } finally {
//                    session.close();
//                }

                if (participants.size() >= conf.getVenue().getCapacity()) {
                    showConferenceIsFullDialog();
                    return;
                }
                Conference_UserDAO cuDAO = new Conference_UserDAO();
                Conference_User cu = new Conference_User();
                cu.setUser(curUser);
                cu.setConference(conf);
                cu.setApproved(false);
                cuDAO.save(cu);
            } else {
                showUserAlreadyEnrolledDialog();
                return;
            }

            showEnrollSuccessDialog();
            btnCloseWindow.fire();
            Main.getMainController().btnSeeAllInfo.fire();
//            Main.getMainController().showConfAllInfoWindow(conf);
        }
    }

    private void showConferenceIsFullDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Đăng ký tham dự Hội nghị...");
        alert.setResizable(false);
        alert.setHeaderText("Hội nghị này đã đủ số lượng tham gia");
        alert.setContentText("Hội nghị đang tạm dừng nhận đăng ký. Xin thử lại sau.");
        alert.initOwner(btnCloseWindow.getScene().getWindow());
        alert.showAndWait();
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
