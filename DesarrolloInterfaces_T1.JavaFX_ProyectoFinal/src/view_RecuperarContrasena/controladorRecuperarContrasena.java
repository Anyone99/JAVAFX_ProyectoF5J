package view_RecuperarContrasena;

import java.util.Calendar;

import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import Hibernate.controladorHibernateCodigoVerificacion;
import Hibernate.controladorHibernatePiloto;
import Util.MailUtil;
import Util.Validacion;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.CodigoVerificacion;

public class controladorRecuperarContrasena {
	@FXML
	private TextField emailField;
	@FXML
	private TextField codigoVerfTextField;
	@FXML
	private Button enviarButton = new Button();
	@FXML
	private Button verficarButton = new Button();

	private Stage dialogStage;
	private Main main;
	private Calendar now = Calendar.getInstance();

	@FXML
	private void initialize() {
		setTooltip();
		List<CodigoVerificacion> list = controladorHibernateCodigoVerificacion.obtener_CodigoVerificacion();
		for (int i = 0; i < list.size(); i++) {
			if (!Validacion.comprobarDifFecha(now, list.get(i).getTiempoApuntado())) {
				boolean eliminado = controladorHibernateCodigoVerificacion
						.delete_codigoVerf_usuario(list.get(i).getEmail());
				if (eliminado) {
				}
			}
		}
	}

	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	private void setTooltip() {
		Tooltip emailTooltip = new Tooltip();
		emailTooltip.setText("example@example.com");
		emailField.setTooltip(emailTooltip);
	}

	@FXML
	public void buttonEnviar() {
		String email = emailField.getText().toString();
		String errorMessage = "", confirmMessage = "";
		String codigo;
		Calendar fechaActual = Calendar.getInstance();

		// Comprobar el formato de email si es válido.
		if (!Validacion.emailFormat(emailField.getText())) {
			errorMessage = "El formato de correo no es válido, por favor corregirlo";
		} else {
			// Comprobar si existe email.
			if (controladorHibernatePiloto.obtenerPilotoPorEmail(email) == null) {
				errorMessage = "No existe el usuario con este email";
			} else {
				CodigoVerificacion cod = controladorHibernateCodigoVerificacion.obtener_usuario(email);
				if (cod != null) {
					if (Validacion.comprobarDifFecha(fechaActual, cod.getTiempoApuntado())) {
						confirmMessage = "Utilizar el código que te envíe anteriormente a tu correo \n";
						emailField.setDisable(true);
						enviarButton.setDisable(true);
					} else {
						confirmMessage = "Enviar a tu correo un número de vertificación, por favor，abrir tu correo para verificar sólo es válido 60 min.";
						codigo = String.valueOf((int) (Math.random() * 90000 + 10000));

						MailUtil mailUtil = new MailUtil();
						Session session = mailUtil.createTLSSession();
						Message message = MailUtil.createActivacionCodeMessage(session, email, codigo);

						if (message != null) {
							try {
								Transport.send(message);
								fechaActual = Calendar.getInstance();
								CodigoVerificacion codigoVerificacion = controladorHibernateCodigoVerificacion
										.add_codigoVerf_usuario(email, codigo, fechaActual);

								if (codigoVerificacion != null) {
									enviarButton.setDisable(true);
									emailField.setDisable(true);
									codigoVerfTextField.setDisable(false);
									verficarButton.setDisable(false);
								}
							} catch (MessagingException e) {
								errorMessage = "Error de Crear por fallo de la aplicación " + e;
							}
						}

					}

				} else {
					confirmMessage = "Enviar a tu correo un número de vertificación, por favor，abrir tu correo para verificar sólo es válido 60 min.";
					codigo = String.valueOf((int) (Math.random() * 90000 + 10000));

					MailUtil mailUtil = new MailUtil();
					Session session = mailUtil.createTLSSession();
					Message message = MailUtil.createActivacionCodeMessage(session, email, codigo);

					if (message != null) {
						try {
							Transport.send(message);
							fechaActual = Calendar.getInstance();
							CodigoVerificacion codigoVerificacion = controladorHibernateCodigoVerificacion
									.add_codigoVerf_usuario(email, codigo, fechaActual);

							if (codigoVerificacion != null) {
								enviarButton.setDisable(true);
								emailField.setDisable(true);
								codigoVerfTextField.setDisable(false);
								verficarButton.setDisable(false);
							}
						} catch (MessagingException e) {
							errorMessage = "Error de Crear por fallo de la aplicación " + e;
						}
					}
				}

			}

		}

		if (errorMessage.length() > 0) {

			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Aviso");
			alert.setHeaderText("Mensaje Error:");
			alert.setContentText(errorMessage);

			alert.showAndWait();
		}

		if (confirmMessage.length() > 0) {
			// Show the error message.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(dialogStage);
			alert.setTitle("Aviso");
			alert.setHeaderText("Informacion");
			alert.setContentText(confirmMessage);

			alert.showAndWait();
		}
	}

	@FXML
	public void buttonVerificar() {
		String codigo = codigoVerfTextField.getText().toString();
		String email = emailField.getText().toString();
		String errorMessage = "", confirmMessage = "";
		Calendar actual = Calendar.getInstance();
		Calendar tiempoVerificacion;
		if (email != null) {
			CodigoVerificacion codigoVerificacion = controladorHibernateCodigoVerificacion
					.obtener_codigoVerf_usuario(codigo, email);
			// Comprobar si el codigo que ha introducido pertenece al usuario.
			if (codigoVerificacion != null) {
				tiempoVerificacion = codigoVerificacion.getTiempoApuntado();
				if (Validacion.comprobarDifFecha(tiempoVerificacion, actual)) {

					MailUtil mailUtil = new MailUtil();
					Session session = mailUtil.createTLSSession();
					Message message = MailUtil.enviarContrasena(session, email);

					if (message != null) {
						try {
							Transport.send(message);
							controladorHibernateCodigoVerificacion.delete_codigoVerf_usuario(email);
						} catch (MessagingException e) {
							errorMessage = "Error de Crear por fallo de la aplicación " + e;
						}
					}

				} else {
					errorMessage = "EL código verificación está caducado, si quiere vuelve a enviar pulsa Enviar";
					controladorHibernateCodigoVerificacion.delete_codigoVerf_usuario(email);
				}
			} else {
				errorMessage = "El código verificación se incorrecto, no pertenece a este usuario";
			}
		} else {
			errorMessage = "EL correo electrónico está vacía.";
		}

		enviarButton.setDisable(false);
		emailField.setDisable(false);

		if (errorMessage.length() > 0) {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Aviso");
			alert.setHeaderText("Mensaje Error:");
			alert.setContentText(errorMessage);

			alert.showAndWait();
		}

		if (confirmMessage.length() > 0) {
			// Show the error message.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(dialogStage);
			alert.setTitle("Aviso");
			alert.setHeaderText("Informacion");
			alert.setContentText(confirmMessage);

			alert.showAndWait();
		}
	}

	/**
	 * botón de cancelar
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
}
