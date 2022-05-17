package controllers;

import java.awt.image.ImagingOpException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import helpers.SessionManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainViewController implements Initializable {


    public static final String LOGOUT_VIEW = "login-view";
    public static final String RESERVATION_ROOM_VIEW = "reservation-rooms";
    public static final String PAYMENT_VIEW = "payments-view";

    public static final String EVENTS_VIEW = "event-view";
    public static final String REGISTER_GUEST = "register-guest-view";


    @FXML
    private Label loggedInUser;

    @FXML
    private Button mainBtn;
    @FXML
    private Button reservationsBtn;
    @FXML
    private Button paymentsBtn;
    @FXML
    private Button logOutBtn;
    @FXML
    private Pane mainPane;
    @FXML
    private MenuItem logoutButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        loggedInUser.setText(SessionManager.user.getUsername() + " " + SessionManager.lastLogin);

    }


    @FXML
    private void onOverviewAction(ActionEvent e ) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/room-details.fxml"));
        Parent parent = loader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(parent);
    }


    public void setView(String view) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent parent = null;


        switch (view) {
            case RESERVATION_ROOM_VIEW:
                loader.setLocation(getClass().getResource(setPath(RESERVATION_ROOM_VIEW)));
                parent = loader.load();
                break;
            case PAYMENT_VIEW:
                loader.setLocation(getClass().getResource(setPath(PAYMENT_VIEW)));
                parent = loader.load();
                break;
            case LOGOUT_VIEW:
                loader.setLocation(getClass().getResource(setPath(LOGOUT_VIEW)));
                parent = loader.load();
                break;
            case EVENTS_VIEW:
                loader.setLocation(getClass().getResource(setPath(EVENTS_VIEW)));
                parent = loader.load();
                break;
            default:
                parent = null;
        }


        mainPane.getChildren().clear();
        mainPane.getChildren().add(parent);
    }

    @FXML
    private void onReservationAction(ActionEvent e) throws Exception {
        setView(RESERVATION_ROOM_VIEW);
    }

    @FXML
    private void onPaymentsAction(ActionEvent e) throws Exception {
        setView(PAYMENT_VIEW);
    }

    @FXML
    private void onEventsAction(ActionEvent  e ) throws  Exception{
        setView(EVENTS_VIEW);
    }

    @FXML
    private void onLogoutAction(ActionEvent e) throws Exception {
        changeStage(LOGOUT_VIEW, e);
    }

    @FXML
    private void onMenuLogoutAction(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource(setPath(LOGOUT_VIEW)));
        Stage stage = (Stage) loggedInUser.getScene().getWindow();
        stage.setScene(new Scene(parent));
    }

    @FXML
    private void onMenuInsertAction(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource(setPath(REGISTER_GUEST)));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void onMenuAboutAction(ActionEvent e) throws Exception {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/about-view.fxml"))));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }



    private void changeStage(String path, ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource(setPath(path)));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    private String setPath(String path) {
        return "../views/" + path + ".fxml";
    }


}