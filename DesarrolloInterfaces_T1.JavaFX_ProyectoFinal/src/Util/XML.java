package Util;

import java.io.File;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Concurso;
import model.Piloto;
import model_Lista.ListaConcurso;
import model_Lista.ListaPiloto;

public class XML {

	private Stage primaryStage;
	private ObservableList<Piloto> pilotoData = FXCollections.observableArrayList();

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public ObservableList<Piloto> getPilotoData() {
		return pilotoData;
	}

	public void setPilotoData(ObservableList<Piloto> pilotoData) {
		this.pilotoData = pilotoData;
	}

	// -------------Guardar los datos en el XML ---------------//
	/**
	 * 
	 * @return
	 */
	public File getFilePath() {
		Preferences preferences = Preferences.userNodeForPackage(XML.class);
		String filePath = preferences.get("filePath", null);
		System.out.println(filePath);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	public void setFilePath(File file) {
		Preferences preferences = Preferences.userNodeForPackage(XML.class);
		if (file != null) {
			preferences.put("filePath", file.getPath());
			primaryStage.setTitle("F5JApp - " + file.getName());
		} else {
			preferences.remove("filePath");
			primaryStage.setTitle("F5JApp");
		}
	}

	public void loadPilotoDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ListaPiloto.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			ListaPiloto wrapper = (ListaPiloto) um.unmarshal(file);

			// datos de persona.
			pilotoData.clear();
			pilotoData.addAll(wrapper.getLista());

			// Save the file path to the registry.
			setFilePath(file);

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());

			alert.showAndWait();
		}

	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void savePilotoDataToFile(File file, ObservableList<Piloto> pilotos) {
		try {
			JAXBContext context = JAXBContext.newInstance(ListaPiloto.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our person data.
			ListaPiloto wrapper = new ListaPiloto();
			wrapper.setLista(pilotos);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);
			System.out.println(file.getName());
			// Save the file path to the registry.
			setFilePath(file);
		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());

			alert.showAndWait();
		}
	}
	
	/**Concurso to File
	 * 
	 * @param file
	 * @param Concurso
	 */
	public void saveConcursoDataToFile(File file, ObservableList<Concurso> concurso) {
		try {
			JAXBContext context = JAXBContext.newInstance(ListaConcurso.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our person data.
			ListaConcurso wrapper = new ListaConcurso();
			wrapper.setLista(concurso);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);
			System.out.println(file.getName());
			// Save the file path to the registry.
			setFilePath(file);
		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());

			alert.showAndWait();
		}
	}


}
