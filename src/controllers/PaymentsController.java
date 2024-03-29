package controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import components.ErrorPopupComponent;
import helpers.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.PaymentModel;
import repositories.PaymentsModelRepository;
import utilities.I18N;

public class PaymentsController implements Initializable {

	@FXML
	private TableView<PaymentModel> paymentsTableView;
	@FXML
	private TableColumn<PaymentModel, Integer> paymentIdCol;
	@FXML
	private TableColumn<PaymentModel, String> firstNameCol;
	@FXML
	private TableColumn<PaymentModel, String> lastNameCol;
	@FXML
	private TableColumn<PaymentModel, Date> dateCol;
	@FXML
	private TableColumn<PaymentModel, Double> priceCol;
	@FXML
	private DatePicker dateFilter;
	@FXML
	private Button search;
	@FXML
	private Button refresh;
	@FXML
	private Button payncheck;

	ObservableList<PaymentModel> paymentsModel;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			initializePayments();
			paymentsModel = FXCollections.observableArrayList(loadGuestPayments());
			paymentsTableView.setItems(paymentsModel);
		} catch (Exception ex) {
			ErrorPopupComponent.show(ex);
		}
		paymentIdCol.textProperty().bind(I18N.createStringBinding("paymentIdCol"));
		firstNameCol.textProperty().bind(I18N.createStringBinding("firstNameCol"));
		lastNameCol.textProperty().bind(I18N.createStringBinding("lastNameCol"));
		dateCol.textProperty().bind(I18N.createStringBinding("dateCol"));
		priceCol.textProperty().bind(I18N.createStringBinding("priceCol"));
		search.textProperty().bind(I18N.createStringBinding("search"));
		refresh.textProperty().bind(I18N.createStringBinding("refresh"));
		payncheck.textProperty().bind(I18N.createStringBinding("payncheck"));

	}

	@FXML
	private void onSearchAction(ActionEvent e) throws Exception {
		String date = dateFilter.getValue().toString();
		if (date == null)
			return;

		ArrayList<PaymentModel> filteredPayment = PaymentsModelRepository.filterPayment(date);
		if (filteredPayment == null)
			return;

		paymentsModel = FXCollections.observableArrayList(filteredPayment);
		paymentsTableView.setItems(paymentsModel);
		paymentsTableView.refresh();
	}

	@FXML
	private void onRefreshAction(ActionEvent e) throws Exception {
		dateFilter.setValue(null);
		paymentsModel = FXCollections.observableArrayList(loadGuestPayments());
		paymentsTableView.setItems(paymentsModel);
		paymentsTableView.refresh();
	}

	@FXML
	private void onPayPaymentAction(ActionEvent e) throws Exception {

		PaymentModel selected = paymentsTableView.getSelectionModel().getSelectedItem();
		if (selected == null)
			return;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../views/pay-payment-view.fxml"));
		Parent parent = loader.load();

		PayPaymentsController controller = loader.getController();
		controller.loadPrice(selected.getPrice(), selected.getPayment_id());

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
	}

//	@FXML
//	private void onCancleAction(ActionEvent e) throws Exception {
//		Parent parent = FXMLLoader.load(getClass().getResource("../views/main-view.fxml"));
//		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//		stage.setScene(new Scene(parent));
//	}

	private void initializePayments() {
		this.paymentIdCol.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
		this.firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.lastNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		this.dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		this.priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
	}


	public ArrayList<PaymentModel> loadGuestPayments() throws Exception {
		return PaymentsModelRepository.findSpecificPayments(SessionManager.user.getId());
	}

}
