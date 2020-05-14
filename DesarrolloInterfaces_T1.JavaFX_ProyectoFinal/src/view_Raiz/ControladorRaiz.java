package view_Raiz;


import Hibernate.controladorHibernatePiloto;
import application.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Piloto;

public class ControladorRaiz {
	private Main main;
	private Stage stage;
	private Piloto piloto = new Piloto();

	public void setMain(Main main) {
		this.main = main;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Main getMain() {
		return main;
	}
	

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("AddressApp");
		alert.setHeaderText("About");
		alert.setContentText("Author: Six");
		alert.initOwner(stage);
		alert.showAndWait();
	}
	
	@FXML 
	private void handleShowConcurso() {
		main.showConcursoView(piloto);
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		main.showLoginView();
	}
	
	@FXML
	private void handleModifyData() {
		Piloto pilotoM = controladorHibernatePiloto.obtenerPilotoPorEmail(piloto.getEmail());
		main.showModificarDatosPiloto(pilotoM);
	}
	
	@FXML
	private void handleContactUs() {
		Piloto pilotoM = controladorHibernatePiloto.obtenerPilotoPorEmail(piloto.getEmail());
		main.showContactarConNosotros(pilotoM);
	}
	

}
