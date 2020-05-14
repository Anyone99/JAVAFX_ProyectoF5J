package view_Concurso;

import Util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Concurso;
import model.Piloto;

public class controladorPilotoInscripcionConcurso {
	@FXML
	private TextField nombreConcursoField;
	@FXML
	private TextField fechaConcursoField;
	@FXML
	private TextField emailPilotoField;
	@FXML
	private TextField modeloField;
	@FXML
	private TextField frecuenciaField;
	@FXML
	private TextField envergaduraTextField;
	@FXML
	private TextField pesoField;
	@FXML
	private TextField consumoField;

	private ObservableList<String> optionCategoria = FXCollections.observableArrayList();
	private ObservableList<Double> optionVoltajeNominal = FXCollections.observableArrayList();

	@FXML
	private ChoiceBox<String> categoriaChoiceBox = new ChoiceBox<>(optionCategoria);
	@FXML
	private ChoiceBox<Double> voltajeNominalChoiceBox = new ChoiceBox<>(optionVoltajeNominal);

	private Concurso concurso;
	private Piloto piloto;
	private Stage stage;

	public Concurso getConcurso() {
		return concurso;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
		nombreConcursoField.setText(concurso.getNombreConcurso());
		fechaConcursoField.setText(DateUtil.format(concurso.getFechaConcurso()));
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
		emailPilotoField.setText(piloto.getNombre());

	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public boolean isOkClicked() {
		return true;
	}

	@FXML
	private void initialize() {
		optionCategoria.addAll("K4", "K6");
		optionVoltajeNominal.addAll(3.7, 3.8, 7.5, 7.6, 11.1, 11.4, 14.8, 15.2, 18.5, 19.0, 22.2, 22.8);

		categoriaChoiceBox.setItems(optionCategoria);
		voltajeNominalChoiceBox.setItems(optionVoltajeNominal);

		nombreConcursoField.setEditable(false);
		emailPilotoField.setEditable(false);
		fechaConcursoField.setEditable(false);

	}
	@FXML
	public void buttonAceptar() {
		if (isInputValid()) {
			
		}
		
	}

	public boolean isInputValid() {
		String errorMessage = "";

		if (modeloField.getText() == null || modeloField.getText().length() == 0) {
			errorMessage += "No valid modelo \n";
		}

		if (categoriaChoiceBox.getSelectionModel().getSelectedItem() == null
				|| categoriaChoiceBox.getSelectionModel().getSelectedItem().length() == 0) {
			errorMessage += "No selected Categoría \n";

		}

		if (voltajeNominalChoiceBox.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "No selected Voltaje Nominal \n";
		}

		if (frecuenciaField.getText() == null || frecuenciaField.getText().length() == 0) {
			errorMessage += "No valid frecuenciaField \n";
		}

		if (envergaduraTextField.getText() == null || envergaduraTextField.getText().length() == 0) {
			errorMessage += "No valid envergadura \n";
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
