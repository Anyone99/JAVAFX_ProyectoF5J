package view_Admin;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Util.DateUtil;
import Util.Validacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Piloto;

public class controladorAdminModificacion {

	@FXML
	private Label idLabel;
	@FXML
	private TextField nombreField;
	@FXML
	private TextField apellidoField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField telefonoField;
	@FXML
	private TextField ciudadField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField clubField;
	@FXML
	private TextField numLicenciaField;
	@FXML
	private Label fechaInscripcionLabel;

	private ObservableList<String> optionCiudad = FXCollections.observableArrayList();

	@FXML
	private ChoiceBox<String> ciudadCombo = new ChoiceBox<>(optionCiudad);
	private final File FICHERO_LISTACIUDAD = new File("resources/listaCiudad.txt");

	private Stage dialogStage;
	private Piloto piloto;
	private boolean okClicked = false;

	/*
	 * Inicializar el controlador clase.
	 */
	@FXML
	private void initialize() {
		// Guardar todos los nombres.
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
		ciudadCombo.setItems(optionCiudad);

	}

	/**
	 * Ajustar el dialogo de escenario.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;

		idLabel.setText(String.valueOf(piloto.getIdPiloto()));
		nombreField.setText(piloto.getNombre());
		apellidoField.setText(piloto.getApellido());
		emailField.setText(piloto.getEmail());
		telefonoField.setText(Integer.toString(piloto.getTelefono()));
		ciudadCombo.setValue(piloto.getCiudad());

		passwordField.setText(piloto.getPassword());
		clubField.setText(piloto.getClub());
		numLicenciaField.setText(String.valueOf(piloto.getNumLicencia()));
		fechaInscripcionLabel.setText(DateUtil.format(piloto.getFechaInscripcion()));

	}

	/**
	 * Devuelve true si el usuario clicked ok.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * El botón para cancelar.
	 */
	@FXML
	public void buttonCancel() {
		dialogStage.close();
	}

	/**
	 * Botón de OK.
	 */
	@FXML
	private void buttonOK() {
		if (isInputValid()) {

			piloto.setNombre(nombreField.getText());
			piloto.setApellido(apellidoField.getText());
			piloto.setCiudad(ciudadCombo.getSelectionModel().getSelectedItem().toString());
			piloto.setEmail(emailField.getText());
			piloto.setNumLicencia(Integer.parseInt(numLicenciaField.getText()));
			piloto.setClub(clubField.getText());
			piloto.setPassword(passwordField.getText());
			piloto.setTelefono(Integer.parseInt(telefonoField.getText()));
			piloto.setFechaInscripcion(DateUtil.parse(fechaInscripcionLabel.getText()));

			okClicked = true;
			dialogStage.close();
		}

	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (nombreField.getText() == null || nombreField.getText().length() == 0) {
			errorMessage += "No valid nombre!\n";
		}
		if (apellidoField.getText() == null || apellidoField.getText().length() == 0) {
			errorMessage += "No valid apellido!\n";
		}
		if (emailField.getText() == null || emailField.getText().length() == 0
				|| !Validacion.emailFormat(emailField.getText())) {
			errorMessage += "No valido emailField!\n";
		}

		if (telefonoField.getText() == null || telefonoField.getText().length() == 0) {
			errorMessage += "No valido Telefono!\n";
		} else {
			// try to parse the telefono into an int.
			try {
				Integer.parseInt(telefonoField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid Telefono (must be an integer)!\n";
			}
			if (!Validacion.validationTelefono(telefonoField.getText())) {
				errorMessage += "El formato no es valido Telefono";
			}
		}

		if (numLicenciaField.getText() == null || numLicenciaField.getText().length() == 0
				|| numLicenciaField.getText().length() != 9) {
			errorMessage += "No valido Numero de Licencia (la longitud tiene que ser 9 caracteres)!\n";
		} else {
			// try to parse the num licnecia into an int.
			try {
				Integer.parseInt(numLicenciaField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "El formato no es valido Numero Licencia (must be an integer)!\n";
			}
		}

		if (passwordField.getText() == null || passwordField.getText().length() == 0
				|| passwordField.getText().length() != 8) {
			errorMessage += "No valido la Contraseña (la longitud de Contraseña debería ser 8 caracteres) !\n";
		}

		if (ciudadCombo.getSelectionModel().getSelectedItem() == null
				|| ciudadCombo.getSelectionModel().getSelectedItem().toString().length() == 0) {
			errorMessage += "No valid Ciudad!\n";
		}
		if (clubField.getText() == null || clubField.getText().length() == 0) {
			errorMessage += "No valid ciub!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}
