package view_contactUS;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import Util.MailUtil;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Piloto;

public class controlador_contactUs {
	private Stage stage = new Stage();

	private Main main;

	@FXML
	private TextArea bodyTextArea;
	@FXML
	private TextField subjectField;
	@FXML
	private Button enviarButton;
	@FXML
	private TextField emailField;

	private Piloto piloto;
	private MailUtil mailUtil;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
		if (piloto != null) {
			emailField.setText(piloto.getEmail());
			emailField.setDisable(true);
		} else {
			emailField.setDisable(false);

		}
	}

	@FXML
	private void initialize() {
		mailUtil = new MailUtil();

	}

	@FXML
	private void buttonEnviarMensaje() {
		if (isInputValid()) {
			Session session = mailUtil.createTLSSession();
			Message message = MailUtil.enviarMensajeContactar(session, emailField.getText(), subjectField.getText(),
					bodyTextArea.getText());
			try {
				Transport.send(message);
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(stage);
			alert.setTitle("Información");
			alert.setContentText("Enviado");
			alert.showAndWait();

			stage.close();

		}
	}

	private boolean isInputValid() {

		String errorMessage = "";

		if (emailField.getText() == null || emailField.getText().length() == 0) {
			errorMessage = "El campo de email no puede ser vacio";
		}

		if (bodyTextArea.getText() == null || bodyTextArea.getText().length() == 0) {
			errorMessage = "El campo de body no puede ser vacío";
		}

		if (subjectField.getText() == null || subjectField.getText().length() == 0) {
			errorMessage = "El campo de título no puede ser vacío";
		}

		if (errorMessage.length() > 0) {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Error");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;

		} else {
			return true;
		}
	}

}
