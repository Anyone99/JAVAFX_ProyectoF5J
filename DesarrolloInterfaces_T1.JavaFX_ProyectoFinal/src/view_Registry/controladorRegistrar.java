package view_Registry;

import java.io.BufferedReader;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import Hibernate.controladorHibernatePiloto;
import Util.MailUtil;
import Util.Validacion;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import model.Piloto;

public class controladorRegistrar {

	@FXML
	private TextField nombreField;
	@FXML
	private TextField ApellidoField;
	@FXML
	private TextField EmailField;
	@FXML
	private PasswordField PasswordField;
	@FXML
	private TextField NumeroLicenciaField;
	@FXML
	private TextField TelefonoField;
	@FXML
	private TextField ClubField;
	@FXML
	private DatePicker fechaInscripcionField;
	// Combo box.
	private ObservableList<String> optionCiudad = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<String> ciudadCombo = new ChoiceBox<>(optionCiudad);

	final Tooltip tooltip_Password = new Tooltip();
	final Tooltip tooltip_NumeroLicencia = new Tooltip();
	final Tooltip tooltip_Telefono = new Tooltip();

	private Stage escenario;
	private Piloto piloto;
	@SuppressWarnings("unused")
	private Main main;
	private boolean okClicked = false;
	private final LocalDate actuaLocalDate = LocalDate.now();

	private final File FICHERO_LISTACIUDAD = new File("resources/fichero/listaCiudad.txt");

	private Random random = new Random();
	private int numLicencia;
	private MailUtil mailUtil;

	@FXML
	private void initialize() {
		mailUtil = new MailUtil();

		do {
			numLicencia = 100000000 + random.nextInt(900000000);

		} while (controladorHibernatePiloto.comprobarNumLicencia(numLicencia));

		NumeroLicenciaField.setText(String.valueOf(numLicencia));
		NumeroLicenciaField.setEditable(false);

		setTooltop();
		initilizateCiudadComboBox();
		initilizableFechaInscripcion();

	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Set piloto
	 * 
	 * @param piloto
	 */
	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
		nombreField.setText(piloto.getNombre());
		ApellidoField.setText(piloto.getApellido());
		ciudadCombo.setValue(piloto.getCiudad());
		EmailField.setText(piloto.getEmail());
		// NumeroLicenciaField.setText(String.valueOf(piloto.getNumLicencia()));
		ClubField.setText(piloto.getClub());
		PasswordField.setText(piloto.getPassword());
		TelefonoField.setText(String.valueOf(piloto.getTelefono()));
		// fechaInscripcionField.setValue(piloto.getFechaInscripcion());
	}

	/**
	 * Inicializar el tooltip de contraseña, numerolicencia y telefono.
	 */
	private void setTooltop() {

		tooltip_Password.setText("La longitud de contraseña minímo 8 caracteres ! \n");
		PasswordField.setTooltip(tooltip_Password);

		//tooltip_NumeroLicencia.setText("La longitud de numero de licencia es 9 caracteres \n");
		//NumeroLicenciaField.setTooltip(tooltip_NumeroLicencia);

		tooltip_Telefono.setText("La longitud de Teléfono es 9 caraceres y empieza por 6 o 9 \n");
		TelefonoField.setTooltip(tooltip_Telefono);
	}

	/**
	 * Inicializar el combo box de ciudad.
	 */
	private void initilizateCiudadComboBox() {
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
		ciudadCombo.setItems(optionCiudad);
	}

	/**
	 * Inicializar la fecha de inscripcion para que cuando registrar sea de mismo
	 * día y el usuario no se puede modificar.
	 */
	private void initilizableFechaInscripcion() {
		fechaInscripcionField.setValue(actuaLocalDate);
		fechaInscripcionField.setDisable(true);
	}

	/**
	 * botón de cancelar
	 */
	@FXML
	private void handleCancel() {
		escenario.close();
	}

	/**
	 * botón de registrar
	 */
	@FXML
	private void handleRegistry() {
		if (isInputValid()) {

			piloto.setNombre(nombreField.getText());
			piloto.setApellido(ApellidoField.getText());
			piloto.setCiudad(ciudadCombo.getSelectionModel().getSelectedItem().toString());
			piloto.setEmail(EmailField.getText());
			piloto.setNumLicencia(Integer.parseInt(NumeroLicenciaField.getText()));
			piloto.setClub(ClubField.getText());
			piloto.setPassword(PasswordField.getText());
			piloto.setTelefono(Integer.parseInt(TelefonoField.getText()));
			piloto.setFechaInscripcion(fechaInscripcionField.getValue());

			okClicked = true;

		}

		if (okClicked) {
			Session session = mailUtil.createTLSSession();
			Message message = MailUtil.enviarConfirmacionRegistracionMessage(session, EmailField.getText(), PasswordField.getText());
			try {
				Transport.send(message);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			// Show the error message.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(escenario);
			alert.setTitle("Aviso");
			alert.setHeaderText("Informacion");
			alert.setContentText("Está registrada");

			alert.showAndWait();
			escenario.close();

		}
	}

	/**
	 * Comprobar si el campo está introducido correctamente.
	 * 
	 * @return
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (nombreField.getText() == null || nombreField.getText().length() == 0) {
			errorMessage += "No valid nombre!\n";
		}
		if (ApellidoField.getText() == null || ApellidoField.getText().length() == 0) {
			errorMessage += "No valid apellido!\n";
		}
		if (EmailField.getText() == null || EmailField.getText().length() == 0
				|| !Validacion.emailFormat(EmailField.getText())
				|| controladorHibernatePiloto.obtenerPilotoPorEmail(EmailField.getText()) != null) {

			if (controladorHibernatePiloto.obtenerPilotoPorEmail(EmailField.getText()) != null) {
				errorMessage += "Este correo electónico ya está registrada en el sistema \n";
			} else {
				errorMessage += "No valido Email !\n";
			}
		}

		if (TelefonoField.getText() == null || TelefonoField.getText().length() == 0) {
			errorMessage += "No valido Telefono!\n";
		} else {
			// try to parse the telefono into an int.
			try {
				Integer.parseInt(TelefonoField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid Telefono (must be an integer)!\n";
			}
			if (!Validacion.validationTelefono(TelefonoField.getText())) {
				errorMessage += "El formato no es valido Telefono";
			}
		}

		/*
		 * if (NumeroLicenciaField.getText() == null ||
		 * NumeroLicenciaField.getText().length() == 0 ||
		 * NumeroLicenciaField.getText().length() != 9) { errorMessage +=
		 * "No valido Numero de Licencia (la longitud tiene que ser 9 caracteres)!\n"; }
		 * else { // try to parse the num licnecia into an int. try {
		 * Integer.parseInt(NumeroLicenciaField.getText()); } catch
		 * (NumberFormatException e) { errorMessage +=
		 * "El formato no es valido Numero Licencia (must be an integer)!\n"; } }
		 */

		// Si el campo de la contraseña es nulo o la longitud de contraseña es menor que
		// 8.
		if (PasswordField.getText() == null || PasswordField.getText().length() == 0
				|| PasswordField.getText().length() < 8) {

			errorMessage += "No valido la Contraseña (la longitud de Contraseña debería ser mayor que 8 caracteres) !\n";
		}

		if (ciudadCombo.getSelectionModel().getSelectedItem() == null
				|| ciudadCombo.getSelectionModel().getSelectedItem().toString().length() == 0) {
			errorMessage += "No valid Ciudad!\n";
		}

		if (ClubField.getText() == null || ClubField.getText().length() == 0) {
			errorMessage += "No valid ciub!\n";
		}

		if (fechaInscripcionField.getValue() == null || fechaInscripcionField.getValue().toString().length() == 0) {
			errorMessage += "No valid fechaInscripcion!\n";

		}
		if (errorMessage.length() == 0) {
			return true;
			
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(escenario);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}
