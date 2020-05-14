package view_Raiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Hibernate.controladorHibernatePiloto;
import Util.DateUtil;
import Util.Validacion;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Piloto;

public class ControladorModificarDatos {
	@FXML
	private TextField idPilotoTextField;
	@FXML
	private TextField nombrePilotoTextField;
	@FXML
	private TextField apelPilotoTextField;
	@FXML
	private TextField emailPilotoTextField;
	@FXML
	private TextField telPilotoTextField;
	@FXML
	private TextField numLicenciaTextField;
	@FXML
	private TextField clubPilotoTextField;
	@FXML
	private TextField fechaInscripcionTextField;
	@FXML
	private TextField passwordTextField;

	private ObservableList<String> optionCiudad = FXCollections.observableArrayList();

	@FXML
	private ChoiceBox<String> ciudadPilotoCombo = new ChoiceBox<>(optionCiudad);

	private final File FICHERO_LISTACIUDAD = new File("resources/fichero/listaCiudad.txt");

	private Main main;
	private Stage stage;
	private Piloto piloto = new Piloto();

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
		idPilotoTextField.setText(String.valueOf(piloto.getIdPiloto()));
		nombrePilotoTextField.setText(piloto.getNombre());
		apelPilotoTextField.setText(piloto.getApellido());
		emailPilotoTextField.setText(piloto.getEmail());
		telPilotoTextField.setText(String.valueOf(piloto.getTelefono()));
		numLicenciaTextField.setText(String.valueOf(piloto.getNumLicencia()));
		clubPilotoTextField.setText(piloto.getClub());
		ciudadPilotoCombo.setValue(piloto.getCiudad());
		fechaInscripcionTextField.setText(DateUtil.format(piloto.getFechaInscripcion()));
	}

	@FXML
	private void initialize() {
		initializeCiudadComboBox();
		idPilotoTextField.setEditable(false);
		idPilotoTextField.setDisable(true);

		numLicenciaTextField.setEditable(false);
		numLicenciaTextField.setDisable(true);

		emailPilotoTextField.setEditable(false);
		emailPilotoTextField.setDisable(true);

		fechaInscripcionTextField.setEditable(false);
		fechaInscripcionTextField.setDisable(true);
	}

	/**
	 * Inicializar el combo box de ciudad.
	 */
	private void initializeCiudadComboBox() {
		// Leer todos los nombres de ciudad de fichero.
		String linea;
		try {
			BufferedReader Reader = new BufferedReader(new FileReader(FICHERO_LISTACIUDAD));
			while ((linea = Reader.readLine()) != null) {
				optionCiudad.add(linea);
			}
			Reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e + " : " + " No encuentra el fichero de la lista Ciudad");
		} catch (IOException e) {
			e.printStackTrace();
		}
		ciudadPilotoCombo.setItems(optionCiudad);
	}

	@FXML
	private void buttonModificarDatos() {
		boolean modificado = false;
		// String confirmMessage = "", errorMessage = "";
		if (isInputValid()) {
			piloto.setNombre(nombrePilotoTextField.getText());
			piloto.setApellido(apelPilotoTextField.getText());
			piloto.setCiudad(ciudadPilotoCombo.getValue());
			piloto.setClub(clubPilotoTextField.getText());
			piloto.setEmail(emailPilotoTextField.getText());
			piloto.setIdPiloto(Integer.valueOf(idPilotoTextField.getText()));
			piloto.setTelefono(Integer.valueOf(telPilotoTextField.getText()));
			// Comprobar la contraseña si quiere modificar o no .
			if (passwordTextField.getText().isEmpty()) {

			} else {
				piloto.setPassword(passwordTextField.getText());

			}
			controladorHibernatePiloto.updatePiloto(piloto);
			modificado = true;
		}

		if (modificado) {
			// Show the error message.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(stage);
			alert.setTitle("Modificar Datos");
			alert.setHeaderText("Aviso");
			alert.setContentText("Ha sido modificado...");

			alert.showAndWait();
		} else {

			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Modificar Datos");
			alert.setHeaderText("Warning");
			alert.setContentText("No Ha sido modificado...");

			alert.showAndWait();
		}

	}

	@FXML
	private void buttonCancel() {
		main.showConcursoView(piloto);

	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (nombrePilotoTextField.getText() == null || nombrePilotoTextField.getText().length() == 0) {
			errorMessage += "No valid nombre!\n";
		}
		if (apelPilotoTextField.getText() == null || apelPilotoTextField.getText().length() == 0) {
			errorMessage += "No valid apellido!\n";
		}
		if (telPilotoTextField.getText() == null || telPilotoTextField.getText().length() == 0) {
			errorMessage += "No valido Telefono!\n";
		} else {
			// try to parse the telefono into an int.
			try {
				Integer.parseInt(telPilotoTextField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid Telefono (must be an integer)!\n";
			}
			if (!Validacion.validationTelefono(telPilotoTextField.getText())) {
				errorMessage += "El formato no es valido Telefono";
			}
		}

		if (ciudadPilotoCombo.getSelectionModel().getSelectedItem() == null
				|| ciudadPilotoCombo.getSelectionModel().getSelectedItem().toString().length() == 0) {
			errorMessage += "No valid Ciudad!\n";
		}

		if (clubPilotoTextField.getText() == null || clubPilotoTextField.getText().length() == 0) {
			errorMessage += "No valid ciub!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}
