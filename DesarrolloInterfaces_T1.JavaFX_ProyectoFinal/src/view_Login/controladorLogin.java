package view_Login;

import Hibernate.controladorHibernatePiloto;

import Util.Validacion;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import model.Admin;
import model.Piloto;

public class controladorLogin {

	private Main main;

	@FXML
	private PasswordField PasswordField;
	@FXML
	private TextField EmailField;

	@FXML
	private RadioButton usuarioButton;
	@FXML
	private RadioButton adminButton;

	@FXML
	ToggleGroup group = new ToggleGroup();

	@FXML
	private Button loginButton = new Button();

	private Stage escenario;
	// Piloto
	private Piloto piloto = new Piloto();
	// Administrador
	private Admin admin = new Admin();

	private boolean loginClick = false;

	/**
	 * Constructor.
	 */
	public controladorLogin() {

	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	@FXML
	private void initialize() {
		initilizeToogleGroup();

	}

	@FXML
	public void onEnter(ActionEvent ae) {
		handlerLogin();
	}

	/**
	 * inicializar el radio botón para cuando iniciar sessión que puede iniciar por
	 * usuario o administrador.
	 */
	private void initilizeToogleGroup() {
		usuarioButton.setToggleGroup(group);
		adminButton.setToggleGroup(group);
		usuarioButton.setSelected(true);

	}

	/**
	 * Comprobar si está iniciar sessión
	 * 
	 * @return
	 */
	public boolean isLogin() {
		return loginClick;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	/**
	 * Iniciar sessión del usuario.
	 */
	@FXML
	private void handlerLogin() {
		boolean okClicked = false;
		String errorMessage = "";

		// Iniciar sessión del usuario.
		if (usuarioButton.isSelected()) {
			// Comprobar si es valido el usuario (Email) y la contrasña tiene datos.
			if (isInputValidUser()) {
				piloto.setEmail(EmailField.getText());
				piloto.setPassword(PasswordField.getText());
				okClicked = true;
			}
			// Si existe dato cuando dar el botón Login, comprobar si existe el dato dentro
			// del mysql.
			if (okClicked) {
				boolean existe = controladorHibernatePiloto.logInUser(piloto.getEmail(), piloto.getPassword());
				if (!existe) {
					errorMessage = "El Email o la Contraseña del usuario es incorrecta. \n";
					EmailField.setText("");
					PasswordField.setText("");
				} else {
					main.showConcursoView(piloto);
				}
			}
		}

		// iniciar sessión de administrador.
		if (adminButton.isSelected()) {

			if (isInputValidAdmin()) {
				admin.setUsuario(EmailField.getText());
				admin.setPassword(PasswordField.getText());
				okClicked = true;
			}
			if (okClicked) {
				boolean existe = controladorHibernatePiloto.logInAdmin(EmailField.getText(), PasswordField.getText());
				// Comprobar si la contraseña y el usuario está válido.
				if (!existe) {
					errorMessage = "El usuario o la constraseña es incorrecta o no existe este usuario \n";
					EmailField.setText("");
					PasswordField.setText("");
				} else {
					// Si existe mirar abre la venta de admin.
					main.showAdminView();
				}
			}

		}

		// Si no hay error message.
		if (errorMessage.length() > 0) {

			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(escenario);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();
		}

	}

	/**
	 * Registrar el usuario.
	 */
	@FXML
	private void buttonRegistrarUser() {
		Piloto piloto = new Piloto();
		String confirmMessage = "", errorMessage = "";
		boolean okClicked = main.showRegistrar(piloto);
		if (okClicked) {
			controladorHibernatePiloto.registrar(piloto);
			confirmMessage = "Registrada !";
		} else {
			errorMessage = "No ha sido registrada";

		}

		if (confirmMessage.length() > 0) {
			// Show the error message.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(escenario);
			alert.setTitle("Registrar Piloto");
			alert.setHeaderText("Información : ");
			alert.setContentText(confirmMessage);

			alert.showAndWait();
		}
		if (errorMessage.length() > 0) {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(escenario);
			alert.setTitle("Registrar Piloto");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();
		}

	}

	/**
	 * Recuperar la contrasena
	 * 
	 * @return
	 */
	@FXML
	private void buttonRecuperarContrasena() {
		main.showRecuperarContrasena();
	}

	// Comprobar la login de Usuario.
	private boolean isInputValidUser() {
		String errorMessage = "";

		if (PasswordField.getText() == null || PasswordField.getText().length() == 0) {
			errorMessage += "La contraseña está vacía!\n";
		}
		if (EmailField.getText() == null || EmailField.getText().length() == 0
				|| !Validacion.emailFormat(EmailField.getText())) {
			errorMessage += "El email está vacía o no es válido !\n";
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

	/**
	 * Comprobar login de administrador.
	 * 
	 * @return
	 */
	private boolean isInputValidAdmin() {
		String errorMessage = "";

		if (PasswordField.getText() == null || PasswordField.getText().length() == 0) {
			errorMessage += "La contraseña está vacía!\n";
		}
		if (EmailField.getText() == null || EmailField.getText().length() == 0) {
			errorMessage += "El Usuario está vacía!\n";
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
