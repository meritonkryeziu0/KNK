package admin.controllers.rooms;

import java.net.URL;
import java.util.ResourceBundle;

import admin.controllers.MainController;
import components.ErrorPopupComponent;
import components.SuccessPopupComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Rooms;
import repositories.RoomRepository;

public class EditRoomController implements Initializable {
	@FXML
	private TextField roomNumber;
	@FXML
	private TextField floorNumber;
	@FXML
	private TextField roomCapacity;
	@FXML
	private TextField bedNumber;
	@SuppressWarnings("rawtypes")
	@FXML
	private ChoiceBox roomType;
	@FXML
	private TextField price;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadTypes();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void loadTypes() {
		ObservableList roomTypeSelectorList = FXCollections.observableArrayList("All", "Single", "Double", "Triple",
				"Quad", "Suite");
		roomType.setItems(roomTypeSelectorList);
		roomType.setValue("None");
	}

	@FXML
	private void updateRoom(ActionEvent e) throws Exception {
		int roomNum = Integer.parseInt(roomNumber.getText());
		int floorNum = Integer.parseInt(floorNumber.getText());
		int roomCpc = Integer.parseInt(roomCapacity.getText());
		int bedNum = Integer.parseInt(bedNumber.getText());
		String roomTp = roomType.getValue().toString();
		double prc = Double.parseDouble(price.getText());
		Rooms room = new Rooms(roomNum, floorNum, roomCpc, bedNum, roomTp, prc);

		if (RoomRepository.update(room) != null) {
			SuccessPopupComponent.show("Successfully edited ", "Edited");
		} else {
			ErrorPopupComponent.show("Failed to edit");
		}

	}

	@SuppressWarnings("unchecked")
	public void setData(Rooms room) {
		roomNumber.setText(Integer.toString(room.getRoom_number()));
		floorNumber.setText(Integer.toString(room.getFloor_number()));
		roomCapacity.setText(Integer.toString(room.getCapacity()));
		bedNumber.setText(Integer.toString(room.getBed_number()));
		roomType.setValue(room.getRoom_type());
		price.setText(Double.toString(room.getPrice()));
	}

	@FXML
	private void cancleButton(ActionEvent e) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../../views/admin-screen.fxml"));
		Parent parent = loader.load();
		MainController controller = loader.getController();
		controller.setView(MainController.ROOMS_DASHBOARD);

		Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
	}
}
