package application;


import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Concurso;
import model.Manga;
import model.Piloto;
import view_Admin.controladorAdmin;
import view_Admin.controladorAdminModificacion;
import view_Concurso.controladorConcurso;
import view_Concurso.controladorPilotoInscripcionConcurso;
import view_Concurso.controladorShowPilotoConcurso;
import view_Login.controladorLogin;
import view_Manga.controladorManga;
import view_Raiz.ControladorModificarDatos;
import view_Raiz.ControladorRaiz;
import view_RecuperarContrasena.controladorRecuperarContrasena;
import view_Registry.controladorRegistrar;
import view_contactUS.controlador_contactUs;

public class Main extends Application {
	// primaryStage
	private Stage primaryStage;
	// El panel de raiz.
	private BorderPane raiz;
	private Piloto piloto = new Piloto();
	private ObservableList<Piloto> pilotoData = FXCollections.observableArrayList();
	private List<Piloto> listaPilotos = new ArrayList<Piloto>();

	public ObservableList<Piloto> getPilotoData() {
		return pilotoData;
	}

	public void setPilotoData(ObservableList<Piloto> pilotoData) {
		this.pilotoData = pilotoData;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public List<Piloto> getListaPilotos() {
		return listaPilotos;
	}

	public void setListaPilotos(List<Piloto> listaPilotos) {
		this.listaPilotos = listaPilotos;
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Aplicacion JF5");

			// iniciarRaizView();
			showLoginView();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void iniciarRaizView(Piloto piloto) {
		try {
			// Cargar el fxml file de raiz.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Raiz/Raiz.fxml"));
			raiz = (BorderPane) loader.load();
			// Mirar el raiz que contiene un primaryStage.
			Scene scene = new Scene(raiz);
			primaryStage.setScene(scene);

			ControladorRaiz controlador = loader.getController();
			controlador.setMain(this);
			controlador.setStage(primaryStage);
			controlador.setPiloto(piloto);
			primaryStage.show();

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public void showLoginView() {
		try {
			// Cargar principal
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Login/Login.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Scene escena = new Scene(pane);
			primaryStage.setScene(escena);

			controladorLogin controlador = loader.getController();
			controlador.setEscenario(primaryStage);
			controlador.setMain(this);

			primaryStage.show();

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public boolean showRegistrar(Piloto piloto) {
		try {
			// Cargar Registrar∫
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Registry/Registrar.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Registrar la cuenta");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);

			Scene escena = new Scene(pane);
			dialogStage.setScene(escena);

			controladorRegistrar controlador = new controladorRegistrar();
			controlador = loader.getController();
			controlador.setEscenario(dialogStage);
			controlador.setPiloto(piloto);

			dialogStage.showAndWait();

			return controlador.isOkClicked();

		} catch (IOException io) {
			io.printStackTrace();
			return false;

		}

	}

	public void showAdminView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Admin/AdminPanel.fxml"));

			AnchorPane pane = (AnchorPane) loader.load();

			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);

			controladorAdmin controlador = loader.getController();
			controlador.setEscenario(primaryStage);
			controlador.setMain(this);

			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showConcursoView(Piloto piloto) {
		iniciarRaizView(piloto);

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Concurso/ConcursoPanel.fxml"));

			AnchorPane pane = (AnchorPane) loader.load();
			raiz.setCenter(pane);

			controladorConcurso controladorConcurso = loader.getController();
			controladorConcurso.setMain(this);
			controladorConcurso.setPiloto(piloto);

			controladorConcurso.setEscenario(primaryStage);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean showModificarUsuarioDialog(Piloto piloto) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Admin/ModUsuarioPanel.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modificar Usuario");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			controladorAdminModificacion controlador = loader.getController();
			controlador.setDialogStage(dialogStage);
			controlador.setPiloto(piloto);
			dialogStage.showAndWait();

			return controlador.isOkClicked();
		} catch (IOException e) {
			return false;
		}
	}

	public void showModificarDatosPiloto(Piloto piloto) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Raiz/ModificarDatos.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			raiz.setCenter(pane);

			ControladorModificarDatos controladorModificarDatos = loader.getController();
			controladorModificarDatos.setMain(this);
			controladorModificarDatos.setPiloto(piloto);
			controladorModificarDatos.setStage(primaryStage);

			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param piloto
	 * @return
	 */
	public boolean showPilotoApuntarConcursoDialog(Piloto piloto, Concurso concurso) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Concurso/ModificacionPilotoEnConcursoPanel.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Inscripción de Concurso");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			controladorPilotoInscripcionConcurso controlador = loader.getController();
			controlador.setStage(dialogStage);
			controlador.setConcurso(concurso);
			controlador.setPiloto(piloto);
			dialogStage.showAndWait();
			return controlador.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void showPilotoEnConcursoDialog(Concurso concurso, List<Piloto> listaPilotos) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Concurso/PilotoConcursoPanel.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Lista de Piloto");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			controladorShowPilotoConcurso controlador = loader.getController();
			controlador.setMain(this);
			controlador.setConcurso(concurso);
			controlador.setListaPiloto(listaPilotos);
			controlador.setDialogStage(dialogStage);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean showAddMangaDialog(Piloto piloto, Concurso concurso, List<Manga> listaMangas) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Manga/MangaPanel.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Anadir Manga");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			controladorManga controlador = loader.getController();
			controlador.setMain(this);
			controlador.setPiloto(piloto);
			controlador.setConcurso(concurso);
			controlador.setListaMangas(listaMangas);
			controlador.setDialogStage(dialogStage);

			dialogStage.showAndWait();
			return controlador.returnOkClicked();
		} catch (IOException h) {
			h.printStackTrace();
			return false;
		}

	}

	public void showRecuperarContrasena() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_Recuperarcontrasena/RecuperarContrasenaPanel.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Recuperar Contraseña");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			controladorRecuperarContrasena controlador = loader.getController();
			controlador.setMain(this);
			controlador.setDialogStage(dialogStage);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showContactarConNosotros(Piloto piloto) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view_contactUS/contactUsView.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Contactar con nosotros");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);

			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			controlador_contactUs controlador = loader.getController();
			controlador.setMain(this);
			controlador.setStage(dialogStage);
			controlador.setPiloto(piloto);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
