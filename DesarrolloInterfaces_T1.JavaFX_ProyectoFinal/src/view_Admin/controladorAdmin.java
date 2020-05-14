package view_Admin;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import Hibernate.controladorHibernateConcurso;
import Hibernate.controladorHibernateManga;
import Hibernate.controladorHibernatePiloto;
import Util.DateUtil;
import Util.MailUtil;
import Util.Validacion;
import Util.XML;
import application.Main;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Concurso;
import model.Manga;
import model.Piloto;
import javafx.scene.control.Alert.AlertType;

public class controladorAdmin {
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField usernameField;

	@FXML
	private TabPane adminPane = new TabPane();
	@FXML
	private Tab usuarioTab = new Tab();
	@FXML
	private Tab concursoTab = new Tab();
	@FXML
	private Tab adminTab = new Tab();
	@FXML
	private Tab mangaTab = new Tab();

	@FXML
	private TabPane usuarioPane;
	@FXML
	private Tab anadirUsuarioTab;
	@FXML
	private Tab eliminarUsuarioTab;
	@FXML
	private Tab modificarUsuarioTab;
	@FXML
	private Tab listadoUsuarioTab;

	@FXML
	private TabPane concursoPane;
	@FXML
	private Tab anadirConcursoTab;
	@FXML
	private Tab eliminarConcursoTab;
	@FXML
	private Tab modificarConcursoTab;
	@FXML
	private Tab listaConcursoTab;

	@FXML
	private TabPane mangaPane;
	@FXML
	private Tab anadirMangaTab;
	@FXML
	private Tab eliminarMangaTab;
	@FXML
	private Tab showAllMangaTab;

	/**
	 * Tabla de modificar Piloto
	 */
	@FXML
	private TableView<Piloto> mod_piloto_PilotoTableView;
	@FXML
	private TableColumn<Piloto, Number> mod_piloto_idPilotoColumn;
	@FXML
	private TableColumn<Piloto, String> mod_piloto_nombreColumn;
	@FXML
	private TableColumn<Piloto, String> mod_piloto_apellidoColumn;
	@FXML
	private TableColumn<Piloto, String> mod_piloto_emailColumn;
	@FXML
	private TableColumn<Piloto, Number> mod_piloto_telefonoColumn;
	@FXML
	private TableColumn<Piloto, String> mod_piloto_ciudadColumn;
	@FXML
	private TableColumn<Piloto, String> mod_piloto_clubColumn;
	@FXML
	private TableColumn<Piloto, Number> mod_piloto_numLicenciaColumn;
	@FXML
	private TableColumn<Piloto, LocalDate> mod_piloto_fechaInsColumn;
	@FXML
	private TableColumn<Piloto, String> mod_piloto_passwordColumn;

	/**
	 * Tabla de modificar Concurso.
	 */
	@FXML
	private TableView<Concurso> mod_conc_ConcursoTableView;
	@FXML
	private TableColumn<Concurso, String> mod_conc_NoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> mod_conc_NombreColumn;
	@FXML
	private TableColumn<Concurso, String> mod_conc_TipoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> mod_conc_LugarConcursoColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> mod_conc_FechaConcursoColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> mod_conc_FinRegistroColumn;

	/**
	 * Tabla de Eliminar piloto
	 */
	@FXML
	private TableView<Piloto> elim_piloto_PilotoTableView;
	@FXML
	private TableColumn<Piloto, Number> elim_piloto_idPilotoColumn;
	@FXML
	private TableColumn<Piloto, String> elim_piloto_nombreColumn;
	@FXML
	private TableColumn<Piloto, String> elim_piloto_apellidoColumn;
	@FXML
	private TableColumn<Piloto, String> elim_piloto_emailColumn;
	@FXML
	private TableColumn<Piloto, Number> elim_piloto_telefonoColumn;
	@FXML
	private TableColumn<Piloto, String> elim_piloto_ciudadColumn;
	@FXML
	private TableColumn<Piloto, String> elim_piloto_clubColumn;
	@FXML
	private TableColumn<Piloto, Number> elim_piloto_numLicenciaColumn;
	@FXML
	private TableColumn<Piloto, LocalDate> elim_piloto_fechaInscripColumn;
	@FXML
	private TableColumn<Piloto, String> elim_piloto_passwordColumn;

	/**
	 * Tabla de ver todos los pilotos
	 */
	@FXML
	private TableView<Piloto> show_piloto_PilotoTableView;
	@FXML
	private TableColumn<Piloto, String> show_piloto_noPilotoColumn;
	@FXML
	private TableColumn<Piloto, Number> show_piloto_idPilotoColumn;
	@FXML
	private TableColumn<Piloto, String> show_piloto_NombreColumn;
	@FXML
	private TableColumn<Piloto, String> show_piloto_ApeColumn;
	@FXML
	private TableColumn<Piloto, String> show_piloto_ClubColumn;
	@FXML
	private TableColumn<Piloto, LocalDate> show_piloto_FechaInscripColumn;
	@FXML
	private TableColumn<Piloto, String> show_piloto_ConcursoColumn;

	/**
	 * Show Concurso --> Tabla para ver todos los concursos.
	 */
	@FXML
	private TableView<Concurso> show_conc_ConcursoTableView;
	@FXML
	private TableColumn<Concurso, String> show_conc_NoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> show_conc_NombreColumn;
	@FXML
	private TableColumn<Concurso, String> show_conc_TipoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> show_conc_LugarConcursoColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> show_conc_FechaConcursoColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> show_conc_FinRegistroColumn;

	/**
	 * Eliminar --> Tabla para ver todos los concursos.
	 */
	@FXML
	private TableView<Concurso> elim_conc_ConcursoTableView;
	@FXML
	private TableColumn<Concurso, String> elim_conc_NoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> elim_conc_NombreColumn;
	@FXML
	private TableColumn<Concurso, String> elim_conc_TipoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> elim_conc_LugarConcursoColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> elim_conc_FechaConcursoColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> elim_conc_FinRegistroColumn;

	/**
	 * Add Manga --> Tabla de Concurso en Añadir de Manga
	 */
	@FXML
	private TableView<Concurso> add_concManga_ConcursoTableView;
	@FXML
	private TableColumn<Concurso, String> add_concManga_NoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> add_concManga_NombreColumn;
	@FXML
	private TableColumn<Concurso, String> add_concManga_TipoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> add_concManga_LugarConcursoColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> add_concManga_FechaConcursoColumn;

	/**
	 * Add Manga --> Tabla de Piloto
	 */
	@FXML
	private TableView<Piloto> add_pilotoManga_PilotoTableView;
	@FXML
	private TableColumn<Piloto, String> add_pilotoManga_NoPilotoColumn;
	@FXML
	private TableColumn<Piloto, Number> add_pilotoManga_idPilotoColumn;
	@FXML
	private TableColumn<Piloto, String> add_pilotoManga_NombreColumn;
	@FXML
	private TableColumn<Piloto, String> add_pilotoManga_ApellidoColumn;
	@FXML
	private TableColumn<Piloto, Number> add_pilotoManga_NumLicenciaColumn;
	@FXML
	private TableColumn<Piloto, String> add_pilotoManga_EmailColumn;

	/**
	 * Show Manga --> Tabla de Concurso en Show de Manga
	 */
	@FXML
	private TableView<Concurso> show_concManga_ConcursoTableView;
	@FXML
	private TableColumn<Concurso, String> show_concManga_NoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> show_concManga_NombreColumn;
	@FXML
	private TableColumn<Concurso, String> show_concManga_TipoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> show_concManga_LugarConcursoColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> show_concManga_FechaConcursoColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> show_concManga_FechaFinRegistroConcursoColumn;

	/**
	 * Show Manga --> Tabla de Piloto en Show de Manga
	 */
	@FXML
	private TableView<Piloto> show_pilotoManga_PilotoTableView;
	@FXML
	private TableColumn<Piloto, String> show_pilotoManga_NoPilotoColumn;
	@FXML
	private TableColumn<Piloto, String> show_pilotoManga_NombreColumn;
	@FXML
	private TableColumn<Piloto, String> show_pilotoManga_ApellidoColumn;
	@FXML
	private TableColumn<Piloto, Number> show_pilotoManga_NumLicenciaColumn;
	@FXML
	private TableColumn<Piloto, String> show_pilotoManga_EmailColumn;

	/**
	 * Show Manga --> Tabla de Manga Por cada Piloto.
	 */

	@FXML
	private TableView<Manga> show_manga_MangaTableView;
	@FXML
	private TableColumn<Manga, Number> show_manga_numMangaColumn;
	@FXML
	private TableColumn<Manga, String> show_manga_tiempoColumn;
	@FXML
	private TableColumn<Manga, Number> show_manga_alturaColumn;
	@FXML
	private TableColumn<Manga, Number> show_manga_aterizajeColumn;
	@FXML
	private TableColumn<Manga, Number> show_manga_penalizacionColumn;
	@FXML
	private TableColumn<Manga, Number> show_manga_puntuacionColumn;
	@FXML
	private TableColumn<Manga, Number> show_manga_puntuacionFinalColumn;

	/**
	 * Los campos de añadir el piloto.
	 */
	@FXML
	private TextField add_piloto_NombleField;
	@FXML
	private TextField add_piloto_ApeField;
	@FXML
	private TextField add_piloto_EmailField;
	@FXML
	private TextField add_piloto_EmailFieldMod;
	@FXML
	private TextField add_piloto_TelefonoFiled;
	@FXML
	private TextField add_piloto_NumLicenField;
	@FXML
	private TextField add_piloto_ClubField;
	@FXML
	private DatePicker add_piloto_FechaInscripDatePicker;

	/**
	 * Label de Show Todos los pilotos
	 */
	@FXML
	private Label show_piloto_IdLabel;
	@FXML
	private Label show_piloto_nombreLabel;
	@FXML
	private Label show_piloto_apellidoLabel;
	@FXML
	private Label show_piloto_EmailLabel;
	@FXML
	private Label show_piloto_telefonoLabel;
	@FXML
	private Label show_piloto_ciudadLabel;
	@FXML
	private Label show_piloto_clubLabel;
	@FXML
	private Label show_piloto_numLicenciaLabel;
	@FXML
	private Label show_piloto_ConcursoLabel;
	@FXML
	private Label show_piloto_fechaInscripLabel;

	/**
	 * Modificar concurso.
	 */
	@FXML
	private TextField mod_conc_noConcursoTextField;
	@FXML
	private TextField mod_conc_nombreConcursoTextField;
	@FXML
	private DatePicker mod_conc_FecFinRegConcursoDatePicker;
	@FXML
	private DatePicker mod_conc_fechaConcDatePicker;

	/**
	 * Campo de buscar el piloto.
	 */
	@FXML
	private TextField elim_piloto_BuscarField;

	/**
	 * Añadir concurso
	 */
	@FXML
	private TextField add_conc_nombreConcursoField;
	@FXML
	private DatePicker add_conc_FecFinRegisConcDatePicker;
	@FXML
	private DatePicker add_conc_FechaConcDatePicker;

	/**
	 * Observable list
	 */
	private ObservableList<String> optionCiudad = FXCollections.observableArrayList();
	private ObservableList<String> optionBuscarElim = FXCollections.observableArrayList();
	private ObservableList<Piloto> optionListaPiloto = FXCollections.observableArrayList();
	private ObservableList<String> optionTipoConcurso = FXCollections.observableArrayList();
	private ObservableList<String> optionLugarConcurso = FXCollections.observableArrayList();
	private ObservableList<Piloto> showAll_piloto_listaPilotoData = FXCollections.observableArrayList();
	private ObservableList<Concurso> showAll_conc_listaConcursoData = FXCollections.observableArrayList();
	private ObservableList<Concurso> mod_conc_listadoConcursos = FXCollections.observableArrayList();
	private ObservableList<Concurso> add_manga_listadoConcursos = FXCollections.observableArrayList();
	private ObservableList<Piloto> add_manga_listaPiloto = FXCollections.observableArrayList();
	private ObservableList<Integer> elim_conc_listaFecha = FXCollections.observableArrayList();
	private ObservableList<Concurso> elim_conc_listaConcurso = FXCollections.observableArrayList();
	private ObservableList<Concurso> show_manga_listaConcurso = FXCollections.observableArrayList();
	private ObservableList<Piloto> show_manga_listaPiloto = FXCollections.observableArrayList();
	private ObservableList<Manga> show_manga_listaManga = FXCollections.observableArrayList();

	/**
	 * Combo box
	 */
	@FXML
	private ChoiceBox<String> add_piloto_CiudadCombo = new ChoiceBox<String>(optionCiudad);
	@FXML
	private ComboBox<String> elim_piloto_buscarCombo = new ComboBox<String>(optionBuscarElim);
	@FXML
	private ComboBox<String> add_conc_tipoConcursoComboBox = new ComboBox<String>(optionTipoConcurso);
	@FXML
	private ComboBox<String> add_conc_LugarConcComboBox = new ComboBox<String>(optionLugarConcurso);
	@FXML
	private ChoiceBox<String> mod_conc_TipoConcursoChoiceBox = new ChoiceBox<>(optionTipoConcurso);
	@FXML
	private ChoiceBox<String> mod_conc_LugarConcursoChoiceBox = new ChoiceBox<>(optionLugarConcurso);
	@FXML
	private ChoiceBox<Integer> elim_conc_FechaChoiceBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> show_manga_FechaChoiceBox = new ChoiceBox<>();

	/**
	 * Check box
	 */
	@FXML
	private CheckBox add_piloto_PasswordBox = new CheckBox();

	/**
	 * Ficheros
	 */
	private final File FICHERO_LISTACIUDAD = new File("resources/fichero/listaCiudad.txt");
	private final File FICHERO_LISTAMENUBUSCAR = new File("resources/fichero/listaBuscar.txt");
	private final File FICHERO_LISTALUGARCONCURSO = new File("resources/fichero/listaLugarConcurso.txt");
	private final File FICHERO_LISTATIPOCONCURSO = new File("resources/fichero/listaTipoConcurso.txt");

	private final LocalDate actualDate = LocalDate.now();

	private Piloto piloto = new Piloto();
	private Concurso concurso = new Concurso();
	private Stage escenario;
	private Main main;
	private XML xml = new XML();
	private boolean okClicked = false;
	private BufferedReader reader;
	private Random random = new Random();
	private MailUtil mailUtil = new MailUtil();

	public controladorAdmin() {

	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public Piloto getPiloto() {
		return this.piloto;
	}

	@FXML
	private void initialize() {

		// Inicializar la tabla de modificación, para cuando hace la actualización se
		// puede modificar inmediatamente.
		add_piloto_insertarNumLicenciaDefault();

		mod_piloto_ShowTablaPilotoDetails();
		elim_piloto_ShowTablaPilotoDetails();
		showAll_piloto_ShowTablaPilotoDetails();

		inicializarSeleccionFecha();

		/**
		 * Iniciarlizar la lista de la tabla de Piloto y Concurso.
		 */
		mod_conc_ShowTablaConcursoDetails();
		showAll_conc_ShowTablaConcursoDetails();
		elim_conc_ShowTablaConcursoDetails();

		/**
		 * Inicializar la tabla de Concurso y Piloto en añadir la Manga
		 */
		add_manga_IniciarTablaConcursoDetails();
		showAll_manga_ShowTablaConcursoDetails();

		// leer la lista de combo de ciudad. oara que pueda seleccionar..
		leerListaCiudad();
		// option para elegir
		leerListaOpcionEliminar();

		// combo de lugar y el tipo de Concurso.
		leerListaOpcionLugarConcurso();
		leerListaOpcionTipoConcurso();
		add_mod_concurso_ajusteFecha();
		
		add_piloto_FechaInscripDatePicker.setValue(actualDate);

	}

	/**
	 * La contraseña por defecto.
	 * 
	 * @return
	 */
	public String defaultPassoword() {
		return "12345678";
	}

	private void add_mod_concurso_ajusteFecha() {
		add_conc_FecFinRegisConcDatePicker.setDisable(true);
		mod_conc_FecFinRegConcursoDatePicker.setDisable(true);

		add_conc_FechaConcDatePicker.setValue(actualDate);
		add_conc_FecFinRegisConcDatePicker.setValue(actualDate.minusDays(1));
		
		
		add_conc_FechaConcDatePicker.setDayCellFactory(disableMonthAugust());
		add_conc_FecFinRegisConcDatePicker.setDayCellFactory(disableMonthAugust());
		add_conc_FecFinRegisConcDatePicker.setDayCellFactory(anteUnDiaFinRegistro());

		add_conc_FechaConcDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
			add_conc_FecFinRegisConcDatePicker.setValue(newValue.minusDays(1));
		});

		mod_conc_fechaConcDatePicker.setDayCellFactory(disableMonthAugust());
		mod_conc_FecFinRegConcursoDatePicker.setDayCellFactory(disableMonthAugust());
		 mod_conc_FecFinRegConcursoDatePicker.setDayCellFactory(anteUnDiaFinRegistro());
		
		mod_conc_fechaConcDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
			mod_conc_FecFinRegConcursoDatePicker.setValue(newValue.minusDays(1));
		});

	}

	/**
	 * Añaidr numero de licencia por defecto.
	 */
	private void add_piloto_insertarNumLicenciaDefault() {
		int numLicencia;
		do {
			numLicencia = 100000000 + random.nextInt(900000000);

		} while (controladorHibernatePiloto.comprobarNumLicencia(numLicencia));

		add_piloto_NumLicenField.setText(String.valueOf(numLicencia));
		add_piloto_NumLicenField.setEditable(false);
	}

	/**
	 * El mes de Agosto se queda disable. No puede seleccionar.
	 * 
	 * @return
	 */
	private Callback<DatePicker, DateCell> disableMonthAugust() {
		final Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (item.getMonthValue() == 8) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
				}
			}
		};
		return dayCellFactory;
	}

	/**
	 * Para que el fin de registro sea antes de un dia de la fecha de concurso.
	 * 
	 * @return
	 */
	private Callback<DatePicker, DateCell> anteUnDiaFinRegistro() {
		final Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (anadirConcursoTab.isSelected()) {
					if (item.isBefore(add_conc_FechaConcDatePicker.getValue().minusDays(1))) {
						setDisable(true);
						setStyle("-fx-background-color: #EEEEEE;");
					}

					if (item.isAfter(add_conc_FechaConcDatePicker.getValue().minusDays(1))) {
						setDisable(true);
						setStyle("-fx-background-color: #EEEEEE;");
					}

				}

				if (modificarConcursoTab.isSelected()) {
					if (item.isBefore(mod_conc_fechaConcDatePicker.getValue().minusDays(1))) {
						setDisable(true);
						setStyle("-fx-background-color: #EEEEEE;");
					}

					if (item.isAfter(mod_conc_fechaConcDatePicker.getValue().minusDays(1))) {
						setDisable(true);
						setStyle("-fx-background-color: #EEEEEE;");
					}
				}

			}
		};
		return dayCellFactory;
	}

	/**
	 * Leer la lista de opcion de Ciudad.
	 */
	private void leerListaCiudad() {
		String linea;
		try {
			reader = new BufferedReader(new FileReader(FICHERO_LISTACIUDAD));
			while ((linea = reader.readLine()) != null) {
				optionCiudad.add(linea);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e + " : " + " No encuentra el fichero de la lista Ciudad");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		add_piloto_CiudadCombo.setItems(optionCiudad);
	}

	/**
	 * Leer la lista opcion de eliminar.
	 */
	private void leerListaOpcionEliminar() {
		String linea;
		try {
			reader = new BufferedReader(new FileReader(FICHERO_LISTAMENUBUSCAR));
			while ((linea = reader.readLine()) != null) {
				optionBuscarElim.add(linea);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e + " : " + " No encuentra el fichero de la lista de Buscar");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		elim_piloto_buscarCombo.setItems(optionBuscarElim);
	}

	/**
	 * Leer la lista opcion de lugar de Concurso.
	 */
	private void leerListaOpcionLugarConcurso() {
		String linea;
		try {
			reader = new BufferedReader(new FileReader(FICHERO_LISTALUGARCONCURSO));
			while ((linea = reader.readLine()) != null) {
				optionLugarConcurso.add(linea);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e + " : " + " No encuentra el fichero de la lista de lugar de concurso");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		add_conc_LugarConcComboBox.setItems(optionLugarConcurso);
		mod_conc_LugarConcursoChoiceBox.setItems(optionLugarConcurso);
	}

	/**
	 * Leer la lista opcion de tipo de concurso.
	 */
	private void leerListaOpcionTipoConcurso() {
		String linea;
		try {
			reader = new BufferedReader(new FileReader(FICHERO_LISTATIPOCONCURSO));
			while ((linea = reader.readLine()) != null) {
				optionTipoConcurso.add(linea);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e + " : " + " No encuentra el fichero de la lista  de ");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		add_conc_tipoConcursoComboBox.setItems(optionTipoConcurso);
		mod_conc_TipoConcursoChoiceBox.setItems(optionTipoConcurso);
	}

	/**
	 * Delete Concurso --> Iniciar la opcion de Fecha.
	 */
	private void inicializarSeleccionFecha() {
		if (elim_conc_FechaChoiceBox.getItems().isEmpty()) {
			elim_conc_listaFecha.addAll(controladorHibernateConcurso.distinctFechaConcursos());
			elim_conc_FechaChoiceBox.setItems(elim_conc_listaFecha);
			show_manga_FechaChoiceBox.setItems(elim_conc_listaFecha);

		} else {
			elim_conc_FechaChoiceBox.getItems().clear();
			elim_conc_listaFecha.clear();
			elim_conc_listaFecha.addAll(controladorHibernateConcurso.distinctFechaConcursos());
			elim_conc_FechaChoiceBox.setItems(elim_conc_listaFecha);
			show_manga_FechaChoiceBox.setItems(elim_conc_listaFecha);

		}

	}

	/**
	 * Iniciar la tabla de modificación
	 */
	private void mod_piloto_ShowTablaPilotoDetails() {
		mod_piloto_idPilotoColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getIdPiloto()));
		mod_piloto_nombreColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
		mod_piloto_apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());
		mod_piloto_emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
		mod_piloto_passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
		mod_piloto_numLicenciaColumn.setCellValueFactory(cellData -> cellData.getValue().getNumLicenciaProperty());
		mod_piloto_telefonoColumn.setCellValueFactory(cellData -> cellData.getValue().getTelefonoProperty());
		mod_piloto_ciudadColumn.setCellValueFactory(cellData -> cellData.getValue().getCiudadProperty());
		mod_piloto_clubColumn.setCellValueFactory(cellData -> cellData.getValue().getClubProperty());
		mod_piloto_fechaInsColumn.setCellValueFactory(cellData -> cellData.getValue().getfechaInscripcionProperty());
	}

	/**
	 * Eliminar Piloto --> Iniciar la tabla de Eliminar los Pilotos
	 */
	private void elim_piloto_ShowTablaPilotoDetails() {
		elim_piloto_idPilotoColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getIdPiloto()));
		elim_piloto_nombreColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
		elim_piloto_apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());
		elim_piloto_emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
		elim_piloto_passwordColumn.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
		elim_piloto_numLicenciaColumn.setCellValueFactory(cellData -> cellData.getValue().getNumLicenciaProperty());
		elim_piloto_telefonoColumn.setCellValueFactory(cellData -> cellData.getValue().getTelefonoProperty());
		elim_piloto_ciudadColumn.setCellValueFactory(cellData -> cellData.getValue().getCiudadProperty());
		elim_piloto_clubColumn.setCellValueFactory(cellData -> cellData.getValue().getClubProperty());
		elim_piloto_fechaInscripColumn
				.setCellValueFactory(cellData -> cellData.getValue().getfechaInscripcionProperty());
	}

	/**
	 * Show Piloto --> Inicializar la tabla de mirar todos los pilotos.
	 */
	private void showAll_piloto_ShowTablaPilotoDetails() {

		show_piloto_noPilotoColumn.setCellFactory((cellData) -> {
			TableCell<Piloto, String> cell = new TableCell<Piloto, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// Si no es nulo.
					if (!empty) {
						// Numerar la columna
						int row = this.getIndex() + 1;
						this.setText(String.valueOf(row));
					}
				}
			};
			return cell;
		});

		show_piloto_idPilotoColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getIdPiloto()));
		show_piloto_NombreColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
		show_piloto_ApeColumn.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());
		show_piloto_ConcursoColumn.setCellFactory((cellData) -> {
			TableCell<Piloto, String> cell = new TableCell<Piloto, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (!empty) {
						if (getTableView().getItems().get(getIndex()).getConcurso() == null) {
							this.setText("");
						} else {
							this.setText(getTableView().getItems().get(getIndex()).getConcurso().getNombreConcurso());
						}
					}
				}
			};
			return cell;
		});
		show_piloto_ClubColumn.setCellValueFactory(cellData -> cellData.getValue().getClubProperty());
		show_piloto_FechaInscripColumn
				.setCellValueFactory(cellData -> cellData.getValue().getfechaInscripcionProperty());

		showAll_piloto_listaPilotoData.addAll(controladorHibernatePiloto.obtenerListaPiloto());
		show_piloto_PilotoTableView.setItems(showAll_piloto_listaPilotoData);

		// Pulsa dos clicked se puede ver los demás datos.
		show_piloto_PilotoTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1) {
					showAll_piloto_ShowPilotoDetails();
				}
			}
		});
	}

	/**
	 * Show Piloto --> los datos seleccionado por tabla e introducir en el label.
	 */
	private void showAll_piloto_ShowPilotoDetails() {
		Tab selectedTab = usuarioPane.getSelectionModel().getSelectedItem();
		if (selectedTab.equals(listadoUsuarioTab)) {
			boolean selected = listadoUsuarioTab.isSelected();
			if (selected) {
				Piloto piloto = show_piloto_PilotoTableView.getSelectionModel().getSelectedItem();
				if (piloto != null) {
					show_piloto_IdLabel.setText(Integer.toString(piloto.getIdPiloto()));
					show_piloto_nombreLabel.setText(piloto.getNombre());
					show_piloto_apellidoLabel.setText(piloto.getApellido());
					show_piloto_EmailLabel.setText(piloto.getEmail());
					show_piloto_ciudadLabel.setText(piloto.getCiudad());
					show_piloto_telefonoLabel.setText(Integer.toString(piloto.getTelefono()));
					show_piloto_clubLabel.setText(piloto.getClub());
					show_piloto_fechaInscripLabel.setText(DateUtil.format(piloto.getFechaInscripcion()));
					show_piloto_numLicenciaLabel.setText(Integer.toString(piloto.getNumLicencia()));
					show_piloto_telefonoLabel.setText(Integer.toString(piloto.getTelefono()));
					// Si no ha apuntado ningún concurso. = "";
					if (piloto.getConcurso() == null) {
						show_piloto_ConcursoLabel.setText("");
					} else {
						show_piloto_ConcursoLabel.setText(piloto.getConcurso().getNombreConcurso());
					}

				}
			}
		}
	}

	/**
	 * Show concurso --> Iniciar la lista de tabla de concurso
	 */
	private void showAll_conc_ShowTablaConcursoDetails() {
		show_conc_NoConcursoColumn.setCellFactory((cellData) -> {
			TableCell<Concurso, String> cell = new TableCell<Concurso, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// Si no es nulo.
					if (!empty) {
						// Numerar la columna
						int row = this.getIndex() + 1;
						this.setText(String.valueOf(row));
					}
				}
			};
			return cell;
		});

		show_conc_NombreColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getNombreConcurso()));
		show_conc_TipoConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getTipoConcurso()));
		show_conc_LugarConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getTipoConcurso()));
		show_conc_FechaConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getFechaConcurso()));
		show_conc_FinRegistroColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getFinRegistroConcurso()));

		showAll_conc_listaConcursoData.addAll(controladorHibernateConcurso.obtenerListaConcursos());
		show_conc_ConcursoTableView.setItems(showAll_conc_listaConcursoData);

	}

	/**
	 * Modify Concurso --> Iniciar la lista de table de concurso
	 */
	private void mod_conc_ShowTablaConcursoDetails() {
		mod_conc_NoConcursoColumn.setCellFactory((cellData) -> {
			TableCell<Concurso, String> cell = new TableCell<Concurso, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// Si no es nulo.
					if (!empty) {
						// Numerar la columna
						int row = this.getIndex() + 1;
						this.setText(String.valueOf(row));
					}
				}
			};
			return cell;
		});

		mod_conc_NombreColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNombreConcurso()));
		mod_conc_LugarConcursoColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLugarConcurso()));
		mod_conc_TipoConcursoColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTipoConcurso()));
		mod_conc_FechaConcursoColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFechaConcurso()));
		mod_conc_FinRegistroColumn.setCellValueFactory(
				cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFinRegistroConcurso()));

		mod_conc_listadoConcursos.addAll(controladorHibernateConcurso.obtenerListaConcursos());
		mod_conc_ConcursoTableView.setItems(mod_conc_listadoConcursos);

		// Pulsa dos clicked se puede ver los demás datos.
		mod_conc_ConcursoTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1) {
					mod_concurso_ShowTablaConcursoDetails();
				}
			}
		});
	}

	/**
	 * Modify Concurso --> Comprobar los datos modificados.
	 */
	private void mod_concurso_ShowTablaConcursoDetails() {
		Tab selectedTab = concursoPane.getSelectionModel().getSelectedItem();
		if (selectedTab.equals(modificarConcursoTab)) {
			boolean selected = modificarConcursoTab.isSelected();
			if (selected) {
				Concurso selectedConcurso = mod_conc_ConcursoTableView.getSelectionModel().getSelectedItem();
				if (selectedConcurso != null) {
					mod_conc_noConcursoTextField.setText(String.valueOf(selectedConcurso.getIdConcurso()));
					mod_conc_noConcursoTextField.setDisable(true);
					mod_conc_nombreConcursoTextField.setText(selectedConcurso.getNombreConcurso());
					mod_conc_fechaConcDatePicker.setValue(selectedConcurso.getFechaConcurso());
					mod_conc_FecFinRegConcursoDatePicker.setValue(selectedConcurso.getFinRegistroConcurso());
					mod_conc_LugarConcursoChoiceBox.setValue(selectedConcurso.getLugarConcurso());
					mod_conc_TipoConcursoChoiceBox.setValue(selectedConcurso.getTipoConcurso());
				}
			}
		}
	}

	/**
	 * Delete Concurso --> Iniciar la lista de Tabla de Concurso.
	 */

	private void elim_conc_ShowTablaConcursoDetails() {

		elim_conc_NoConcursoColumn.setCellFactory((cellData) -> {
			TableCell<Concurso, String> cell = new TableCell<Concurso, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					int row;
					// Si no es nulo.
					if (!empty) {
						// Numerar la columna
						row = this.getIndex() + 1;
						this.setText(String.valueOf(row));
					} else {
						row = 0;
						this.setText("");

					}
				}
			};
			return cell;
		});

		elim_conc_NombreColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getNombreConcurso()));
		elim_conc_TipoConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getTipoConcurso()));
		elim_conc_LugarConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getLugarConcurso()));
		elim_conc_FechaConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getFechaConcurso()));
		elim_conc_FinRegistroColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getFinRegistroConcurso()));

		ChangeListener<Integer> changeListener = new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				if (newValue != null) {
					elim_conc_ConcursoTableView.getItems().clear();
					elim_conc_listaConcurso
							.addAll(controladorHibernateConcurso.obtenerConcursosPorDistinctFecha(newValue));
					elim_conc_ConcursoTableView.setItems(elim_conc_listaConcurso);
					elim_conc_FechaChoiceBox.getSelectionModel().clearSelection();
				}

			}
		};

		elim_conc_FechaChoiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);

	}

	/**
	 * ShowAll Manga --> Iniciar la tabla de Concurso.
	 */
	private void showAll_manga_ShowTablaConcursoDetails() {
		show_concManga_NoConcursoColumn.setCellFactory((cellData) -> {
			TableCell<Concurso, String> cell = new TableCell<Concurso, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					int row;
					// Si no es nulo.
					if (!empty) {
						// Numerar la columna
						row = this.getIndex() + 1;
						this.setText(String.valueOf(row));
					} else {
						row = 0;
						this.setText("");

					}
				}
			};
			return cell;
		});

		show_concManga_NombreColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getNombreConcurso()));
		show_concManga_TipoConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getTipoConcurso()));
		show_concManga_LugarConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getLugarConcurso()));
		show_concManga_FechaConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getFechaConcurso()));
		show_concManga_FechaFinRegistroConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getFinRegistroConcurso()));

		ChangeListener<Integer> changeListener = new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newAno) {
				if (newAno != null) {

					show_conc_ConcursoTableView.getItems().clear();
					show_manga_listaConcurso.clear();
					show_manga_listaConcurso
							.addAll(controladorHibernateConcurso.obtenerConcursosPorDistinctFecha(newAno));
					show_concManga_ConcursoTableView.setItems(show_manga_listaConcurso);
					show_manga_FechaChoiceBox.getSelectionModel().clearSelection();

				}

			}
		};

		show_manga_FechaChoiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);

		// Pulsa dos clicked se puede ver los demás datos.
		show_concManga_ConcursoTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1) {
					// Si la lista de la tabla de piloto no existen datos, se añade directamente.
					if (show_pilotoManga_PilotoTableView.getItems().isEmpty()) {
						showAll_manga_ShowTablaPilotoDetails();
					} else {
						// si existe datos. pues limpiar. y añadir de nuevo
						show_pilotoManga_PilotoTableView.getItems().clear();
						showAll_manga_ShowTablaPilotoDetails();
					}
				}
			}
		});

	}

	/**
	 * ShowAll Manga --> Tabla de Piloto en Manga,
	 */
	private void showAll_manga_ShowTablaPilotoDetails() {
		show_manga_listaPiloto.removeAll(show_pilotoManga_PilotoTableView.getSelectionModel().getSelectedItems());
		show_pilotoManga_NoPilotoColumn.setCellFactory((cellData) -> {
			TableCell<Piloto, String> cell = new TableCell<Piloto, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// Si no es nulo.
					if (!empty) {
						// Numerar la columna
						int row = this.getIndex() + 1;
						this.setText(String.valueOf(row));
					}
				}
			};
			return cell;
		});

		show_pilotoManga_NombreColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
		show_pilotoManga_ApellidoColumn.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());
		show_pilotoManga_EmailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
		show_pilotoManga_NumLicenciaColumn
				.setCellValueFactory(cellData -> cellData.getValue().getNumLicenciaProperty());

		if (show_concManga_ConcursoTableView.getSelectionModel().getSelectedItem() != null) {
			if (show_pilotoManga_PilotoTableView.getItems() == null) {
				show_manga_listaPiloto.addAll(controladorHibernateManga.obtener_piloto_PorConcurso_EnManga(
						show_concManga_ConcursoTableView.getSelectionModel().getSelectedItem()));
				show_pilotoManga_PilotoTableView.setItems(show_manga_listaPiloto);
			} else {
				show_pilotoManga_PilotoTableView.getItems().clear();
				show_manga_listaPiloto.addAll(controladorHibernateManga.obtener_piloto_PorConcurso_EnManga(
						show_concManga_ConcursoTableView.getSelectionModel().getSelectedItem()));
				show_pilotoManga_PilotoTableView.setItems(show_manga_listaPiloto);

			}

			// Pulsa dos clicked se puede ver los demás datos.
			show_pilotoManga_PilotoTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if (event.getClickCount() == 1) {

						// Si la lista de la tabla de manga está vacia, se añade directamente.
						if (show_manga_MangaTableView.getItems().isEmpty()) {
							showAll_manga_ShowTablaMangaDetails();
						} else {
							// si existe datos. pues limpiar. y añadir de nuevo
							show_manga_MangaTableView.getItems().clear();
							showAll_manga_ShowTablaMangaDetails();
						}
					}
				}
			});

		}

	}

	private void showAll_manga_ShowTablaMangaDetails() {

		show_manga_numMangaColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumManga()));
		show_manga_alturaColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAltura()));
		show_manga_aterizajeColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAterrizaje()));
		show_manga_penalizacionColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPenalizaciones()));
		show_manga_puntuacionColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPuntos()));
		show_manga_puntuacionFinalColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPuntosFinal()));
		show_manga_tiempoColumn.setCellValueFactory(
				cellData -> new ReadOnlyObjectWrapper<>(Validacion.convertirTiempo(cellData.getValue().getTiempo())));

		if (show_manga_MangaTableView.getItems() == null) {
			show_manga_listaManga.addAll(controladorHibernateManga.obtener_listaManga_ConcursoPiloto(
					show_concManga_ConcursoTableView.getSelectionModel().getSelectedItem(),
					show_pilotoManga_PilotoTableView.getSelectionModel().getSelectedItem()));

			show_manga_MangaTableView.setItems(show_manga_listaManga);
		} else {
			show_manga_MangaTableView.getItems().clear();
			show_manga_listaManga.addAll(controladorHibernateManga.obtener_listaManga_ConcursoPiloto(
					show_concManga_ConcursoTableView.getSelectionModel().getSelectedItem(),
					show_pilotoManga_PilotoTableView.getSelectionModel().getSelectedItem()));

			show_manga_MangaTableView.setItems(show_manga_listaManga);

		}

	}

	/**
	 * Add Manga --> Inicializar la tabla de concurso.
	 */
	private void add_manga_IniciarTablaConcursoDetails() {
		add_concManga_NoConcursoColumn.setCellFactory((cellData) -> {
			TableCell<Concurso, String> cell = new TableCell<Concurso, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// Si no es nulo.
					if (!empty) {
						// Numerar la columna
						int row = this.getIndex() + 1;
						this.setText(String.valueOf(row));
					}
				}
			};
			return cell;
		});

		add_concManga_NombreColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getNombreConcurso()));
		add_concManga_TipoConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getTipoConcurso()));
		add_concManga_LugarConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getTipoConcurso()));
		add_concManga_FechaConcursoColumn
				.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getFechaConcurso()));

		add_manga_listadoConcursos.addAll(controladorHibernateConcurso.obtenerListaConcursos());
		add_concManga_ConcursoTableView.setItems(add_manga_listadoConcursos);

		// Pulsa dos clicked se puede ver los demás datos.
		add_concManga_ConcursoTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1) {
					// Si la lista de la tabla de piloto no existen datos, se añade directamente.
					if (add_pilotoManga_PilotoTableView.getItems().isEmpty()) {
						add_manga_IniciarTablePilotoDetails();
					} else {
						// si existe datos. pues limpiar. y añadir de nuevo
						add_pilotoManga_PilotoTableView.getItems().clear();
						add_manga_IniciarTablePilotoDetails();
					}
				}
			}
		});
	}

	/**
	 * Add Manga --> Inicializar la tabla de Piloto
	 */
	private void add_manga_IniciarTablePilotoDetails() {
		add_manga_listaPiloto.removeAll(add_pilotoManga_PilotoTableView.getSelectionModel().getSelectedItems());
		add_pilotoManga_NoPilotoColumn.setCellFactory((cellData) -> {
			TableCell<Piloto, String> cell = new TableCell<Piloto, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					// Si no es nulo.
					if (!empty) {
						// Numerar la columna
						int row = this.getIndex() + 1;
						this.setText(String.valueOf(row));
					}
				}
			};
			return cell;
		});

		add_pilotoManga_idPilotoColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getIdPiloto()));
		add_pilotoManga_NombreColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
		add_pilotoManga_ApellidoColumn.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());
		add_pilotoManga_EmailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
		add_pilotoManga_NumLicenciaColumn.setCellValueFactory(cellData -> cellData.getValue().getNumLicenciaProperty());

		add_manga_listaPiloto.addAll(controladorHibernateManga.obtener_piloto_PorConcurso_EnManga(
				add_concManga_ConcursoTableView.getSelectionModel().getSelectedItem()));
		add_pilotoManga_PilotoTableView.setItems(add_manga_listaPiloto);

	}

	/**
	 * Add Piloto --> Button Add Piloto
	 */
	@FXML
	public void add_piloto_ButtonAdd() {
		Piloto piloto = new Piloto();
		// Obtiene la tab seleccionado.
		Tab tabSelected = usuarioPane.getSelectionModel().getSelectedItem();
		String errorMessage = "", confirmMessage = "";
		// System.out.println(tabSelected.getText());
		// si la tab seleccionado es igual que anadir piloto tab.
		if (tabSelected.equals(anadirUsuarioTab)) {
			// comprar si está seleccionado anadir piloto tab,
			boolean selected = anadirUsuarioTab.isSelected();

			// si está seleccionado. entra.
			if (selected) {
				if (isInputValidPiloto()) {
					piloto.setNombre(add_piloto_NombleField.getText());
					piloto.setApellido(add_piloto_ApeField.getText());
					piloto.setCiudad(add_piloto_CiudadCombo.getSelectionModel().getSelectedItem().toString());
					piloto.setEmail(add_piloto_EmailField.getText());
					piloto.setNumLicencia(Integer.parseInt(add_piloto_NumLicenField.getText()));
					piloto.setClub(add_piloto_ClubField.getText());
					piloto.setPassword(this.defaultPassoword());
					piloto.setTelefono(Integer.parseInt(add_piloto_TelefonoFiled.getText()));
					piloto.setFechaInscripcion(add_piloto_FechaInscripDatePicker.getValue());
					// si la dato introducido está correcto. devuelve el botón true.
					okClicked = true;
				}

				// Si ha introducido dato, se guarda en el base de datos.
				if (okClicked) {
					Piloto pilotoRegistrado = controladorHibernatePiloto.registrar(piloto);
					if (pilotoRegistrado != null) {
						confirmMessage = "Registrada ! ";
						Session session = mailUtil.createTLSSession();
						Message message = MailUtil.enviarConfirmacionRegistracionMessage(session,
								add_piloto_EmailField.getText(), defaultPassoword());
						try {
							Transport.send(message);
						} catch (MessagingException e) {
							// Show the error message.
							Alert alert = new Alert(AlertType.ERROR);
							alert.initOwner(escenario);
							alert.setTitle("Añadir Piloto");
							alert.setHeaderText("Error de Mensaje!");
							alert.setContentText("Error de Enviar el correo al usuario");

							alert.showAndWait();

						}

					} else {
						errorMessage = "No ha sido Registrado";
					}
				}

				if (confirmMessage.length() > 0) {
					// Show the error message.
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(escenario);
					alert.setTitle("Añadir Piloto");
					alert.setHeaderText("Información : ");
					alert.setContentText(confirmMessage);

					alert.showAndWait();
				}
				if (errorMessage.length() > 0) {
					// Show the error message.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(escenario);
					alert.setTitle("Añadir Piloto");
					alert.setHeaderText("Please correct invalid fields");
					alert.setContentText(errorMessage);

					alert.showAndWait();
				}
			}
		}
	}

	/**
	 * Button Add Concurso
	 */
	@FXML
	private void add_concurso_ButtonAdd() {
		Concurso concurso = new Concurso();
		Tab tabSelected = concursoPane.getSelectionModel().getSelectedItem();
		String errorMessage = "", confirmMessage = "";

		if (tabSelected.equals(anadirConcursoTab)) {
			boolean selected = anadirConcursoTab.isSelected();
			if (selected) {
				if (isInputValidConcurso()) {
					concurso.setNombreConcurso(add_conc_nombreConcursoField.getText());
					concurso.setTipoConcurso(add_conc_tipoConcursoComboBox.getSelectionModel().getSelectedItem());
					concurso.setLugarConcurso(add_conc_LugarConcComboBox.getSelectionModel().getSelectedItem());
					concurso.setFinRegistroConcurso(add_conc_FecFinRegisConcDatePicker.getValue());
					concurso.setFechaConcurso(add_conc_FecFinRegisConcDatePicker.getValue());
					okClicked = true;
				}
				if (okClicked) {
					Concurso saveConcurso = controladorHibernateConcurso.anadirConcurso(concurso);
					if (saveConcurso != null) {
						confirmMessage = "Añadido Concurso: ";
					} else {
						errorMessage = "No ha sido añadido !";
					}
				} else {
					errorMessage = "No ha sido añadido !";

				}

				if (confirmMessage.length() > 0) {
					// Show the error message.
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(escenario);
					alert.setTitle("Añadir Concurso");
					alert.setHeaderText("Información : ");
					alert.setContentText(confirmMessage);

					alert.showAndWait();
				}
				if (errorMessage.length() > 0) {
					// Show the error message.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(escenario);
					alert.setTitle("Añadir Concurso");
					alert.setHeaderText("Mensaje Error :");
					alert.setContentText(errorMessage);

					alert.showAndWait();
				}

			}

		}

	}

	/**
	 * Button Add Manga
	 */
	@SuppressWarnings("unused")
	@FXML
	private void add_Manga_ButtonAdd() {
		String errorMessage = "";
		Tab tabSelected = mangaPane.getSelectionModel().getSelectedItem();
		if (tabSelected.equals(anadirMangaTab)) {
			boolean selected = anadirMangaTab.isSelected();
			if (selected) {
				Concurso concursoSelected = add_concManga_ConcursoTableView.getSelectionModel().getSelectedItem();
				Piloto pilotoSelected = add_pilotoManga_PilotoTableView.getSelectionModel().getSelectedItem();

				if (actualDate.isAfter(concursoSelected.getFechaConcurso())) {
					if (concursoSelected != null) {
						if (pilotoSelected != null) {
							List<Manga> listadoMangas = controladorHibernateManga
									.obtener_listaManga_ConcursoPiloto(concursoSelected, pilotoSelected);
							boolean okClicked = main.showAddMangaDialog(pilotoSelected, concursoSelected,
									listadoMangas);
							if (okClicked) {
								add_Manga_ButtonCalcularPuntuacion();
							}
						} else {
							errorMessage = "No has seleccionado el piloto \n ";
						}
					} else {
						errorMessage = "No has seleccionado el concurso \n s";
					}

				} else {
					errorMessage = "El concurso todavía no finalizado, no se puede insertar las mangas \n";
				}

				if (errorMessage.length() == 0) {

				} else {
					// Show the error message.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(escenario);
					alert.setTitle("Añadir Manga");
					alert.setHeaderText("Mensaje Error : ");
					alert.setContentText(errorMessage);

					alert.showAndWait();
				}

			}

		}

	}

	/**
	 * Add Manga --> Button Calcular la puntuacion Final
	 */
	@FXML
	private void add_Manga_ButtonCalcularPuntuacion() {
		String errorMessage = "", confirmMessage = "";
		int puntoMayor = 0, posicionPtoMayor = 0;
		Tab selectedTab = mangaPane.getSelectionModel().getSelectedItem();
		if (selectedTab.equals(anadirMangaTab)) {

			if (anadirMangaTab.isSelected()) {
				// Obtener los datos del concurso de la tabla de concurso.
				Concurso selectedConcurso = add_concManga_ConcursoTableView.getSelectionModel().getSelectedItem();
				// Si el concurso no es nulo.
				if (selectedConcurso != null) {
					// Si la fecha acutal está despues del fecha de concurso.
					if (actualDate.isAfter(selectedConcurso.getFechaConcurso())) {

						for (int i = 1; i <= 6; i++) {
							// Obtener la puntuación de cada piloto en cada num de manga que está apuntado
							// en este concurso.
							List<Manga> listaMangas = controladorHibernateManga
									.obtener_manga_puntuacion(selectedConcurso, i);

							// Comprobar si tiene la puntaciones.
							if (listaMangas != null) {
								puntoMayor = 0;
								// Comprobar el punto Mayor de los pilotos en cada numero de manga.
								for (int j = 0; j < listaMangas.size(); j++) {
									if (puntoMayor < listaMangas.get(j).getPuntos()) {
										puntoMayor = listaMangas.get(j).getPuntos();
										posicionPtoMayor = j;
									}

								}
								// Modificar la puntación final de cada piloto en cada numero de manga.
								List<Manga> mod_ListaMangas = controladorHibernateManga
										.update_manga_puntosFinal(listaMangas, posicionPtoMayor);

								if (mod_ListaMangas != null) {
									confirmMessage = "Ha sido actualizado las puntaciones";

								} else {
									errorMessage = "No ha podido actualizar las puntuaciones";
								}

							} else {
								errorMessage = "No hay ningún piloto apuntado en este concurso";
							}
						}
						// Enviar correo al cliente para obtener la clasificación final;
						List<Piloto> listPiloto = controladorHibernateManga
								.obtener_piloto_PorConcurso_EnManga(selectedConcurso);
						Session session = mailUtil.createTLSSession();

						for (int j = 0; j < listPiloto.size(); j++) {
							Piloto piloto = listPiloto.get(j);
							List<Manga> listMangas = controladorHibernateManga
									.obtener_listaManga_ConcursoPiloto(selectedConcurso, piloto);
							Message message = MailUtil.enviarMensajeClasificacion(session, piloto, listMangas);
							try {
								Transport.send(message);
							} catch (MessagingException e) {
								errorMessage = "Error de Enviar el correo al cliente";
							}
						}

					} else {
						errorMessage = "No puede calcular el punto, porque el concurso todavía no ha empezado";
					}

				} else {
					errorMessage = "No hay ningún concurso seleccionado";
				}

			}
		}

		if (errorMessage.length() > 0) {

			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(escenario);
			alert.setTitle("Añadir Manga");
			alert.setHeaderText("Error de caclular la puntuación.");
			alert.setContentText(errorMessage);

			alert.showAndWait();

		}

		// Show the correct message.
		if (confirmMessage.length() > 0) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(escenario);
			alert.setTitle("Aviso:");
			alert.setHeaderText("Has conseguido: ");
			alert.setContentText(confirmMessage);

			alert.showAndWait();

		}

	}

	/**
	 * Modify Piloto --> Button Search
	 */
	@FXML
	private void mod_piloto_ButtonSearch() {
		// Obtener la tab seleccionado del piloto panel
		Tab tabSelected = usuarioPane.getSelectionModel().getSelectedItem();
		String errorMessage = "";
		// si el tab seleccionado es igual que el tab de modificar piloto
		if (tabSelected.equals(modificarUsuarioTab)) {
			// devolver si está seleccionado
			boolean selected = modificarUsuarioTab.isSelected();
			// si está seleccionado.
			if (selected) {
				Piloto piloto = controladorHibernatePiloto.obtenerPilotoPorEmail(add_piloto_EmailFieldMod.getText());
				// Si encuentra elpiloto con el Email.
				if (piloto != null) {
					optionListaPiloto.add(piloto);
					mod_piloto_PilotoTableView.setItems(optionListaPiloto);
				} else {
					// Si el correo de piloto no existe o el campo no hay nada.
					errorMessage += "El piloto no existe o El campo no puede ser Vacío \n";
				}
			} else {
				optionListaPiloto = null;
			}

		}
		if (errorMessage.length() == 0) {

		} else {
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
	 * Delete Piloto --> Button Search
	 */
	@FXML
	private void elim_piloto_ButtonSearch() {
		// Obtener la tab seleccionado del piloto panel
		Tab tabSelected = usuarioPane.getSelectionModel().getSelectedItem();
		String errorMessage = "";
		// si el tab seleccionado es igual que el tab de modificar piloto
		if (tabSelected.equals(eliminarUsuarioTab)) {
			// devolver si está seleccionado
			boolean selected = eliminarUsuarioTab.isSelected();
			// si está seleccionado.
			if (selected) {
				String comboSelected = elim_piloto_buscarCombo.getSelectionModel().getSelectedItem();
				if (comboSelected.equalsIgnoreCase("ID")) {
					Piloto piloto = controladorHibernatePiloto
							.obtenerPilotoPorID(Integer.parseInt(elim_piloto_BuscarField.getText()));
					if (piloto != null) {
						optionListaPiloto.add(piloto);
						elim_piloto_PilotoTableView.setItems(optionListaPiloto);
					} else {
						errorMessage += "No existe el id de piloto o El campo de ID está vacía \n";
					}
				}

				// Si el combo de búsqueda seleccionado es igual al nombre.
				if (comboSelected.equalsIgnoreCase("Nombre")) {
					List<Piloto> listaPiloto = controladorHibernatePiloto
							.obtenerPilotoPorNombre(elim_piloto_BuscarField.getText());
					if (!listaPiloto.isEmpty()) {
						for (int i = 0; i < listaPiloto.size(); i++) {
							optionListaPiloto.add(listaPiloto.get(i));
						}
						elim_piloto_PilotoTableView.setItems(optionListaPiloto);
					} else {
						errorMessage += "No existe el nombre de piloto o el campo de Nombre está vacía \n";
					}
				}

				if (comboSelected.equalsIgnoreCase("Email")) {
					Piloto piloto = controladorHibernatePiloto.obtenerPilotoPorEmail(elim_piloto_BuscarField.getText());
					// Si encuentra el piloto con el Email.
					if (piloto != null) {
						optionListaPiloto.add(piloto);
						elim_piloto_PilotoTableView.setItems(optionListaPiloto);
					} else {
						// Si el correo de piloto no existe o el campo no hay nada.
						errorMessage += "El piloto no existe o El campo no puede ser Vacío \n";
					}
				}

				if (comboSelected.equalsIgnoreCase("Apellido")) {
					List<Piloto> listaPiloto = controladorHibernatePiloto
							.obtenerPilotoPorApellido(elim_piloto_BuscarField.getText());
					if (!listaPiloto.isEmpty()) {
						optionListaPiloto.addAll(listaPiloto);
						elim_piloto_PilotoTableView.setItems(optionListaPiloto);
					} else {
						errorMessage += "No existe el apellido de piloto o el campo de apellido está vacía \n";
					}
				}

				if (comboSelected.equalsIgnoreCase("Telefono")) {
					List<Piloto> listaPilotos = controladorHibernatePiloto
							.obtenerPilotoPorTelefono(Integer.parseInt(elim_piloto_BuscarField.getText()));
					if (!listaPilotos.isEmpty()) {
						optionListaPiloto.addAll(listaPilotos);
						elim_piloto_PilotoTableView.setItems(optionListaPiloto);
					} else {
						errorMessage += "No existe el telefono de piloto o el campo de telefono está vacía \n";
					}
				}
			}
		}
		if (errorMessage.length() == 0) {

		} else {
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
	 * Modify Piloto --> Button Accept Modify Piloto
	 */
	@FXML
	public void mod_piloto_ButtonModify() {
		Tab tabSelected = usuarioPane.getSelectionModel().getSelectedItem();
		String confirmMessage = "", errorMessage = "";
		if (tabSelected.equals(modificarUsuarioTab)) {
			// devolver si está seleccionado

			boolean selected = modificarUsuarioTab.isSelected();
			// si está seleccionado.
			if (selected) {

				Piloto selectedPiloto = mod_piloto_PilotoTableView.getSelectionModel().getSelectedItem();
				boolean okClicked = main.showModificarUsuarioDialog(selectedPiloto);

				if (okClicked) {
					Piloto piloto = controladorHibernatePiloto.updatePiloto(selectedPiloto);
					if (piloto != null) {
						confirmMessage = "Modificado el piloto con Num Licencia: \n" + selectedPiloto.getNumLicencia();
					} else {
						errorMessage = "No ha sido modificado el piloto con Num Licencia \n "
								+ selectedPiloto.getNumLicencia();
					}
				}

				// Si no existe mensaje error.
				if (confirmMessage.length() > 0) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(escenario);
					alert.setTitle("Information");
					alert.setContentText(confirmMessage);

					alert.showAndWait();
				} else {
					// Show the error message.
					Alert alert = new Alert(AlertType.ERROR);
					alert.initOwner(escenario);
					alert.setTitle("Invalid Fields");
					alert.setHeaderText("Please correct invalid fields");
					alert.setContentText(errorMessage);

					alert.showAndWait();

				}
			}
		}

	}

	/**
	 * Modify Concurso --> Button Accept Modify Concurso
	 */
	@FXML
	public void mod_conc_ButtonModify() {
		boolean okClicked = false;
		String errorMessage = "";
		Tab selectedTab = concursoPane.getSelectionModel().getSelectedItem();
		if (selectedTab.equals(modificarConcursoTab)) {
			boolean selected = modificarConcursoTab.isSelected();
			if (selected) {
				if (mod_conc_isInputValidConcurso()) {
					concurso.setIdConcurso(Integer.parseInt(mod_conc_noConcursoTextField.getText()));
					concurso.setNombreConcurso(mod_conc_nombreConcursoTextField.getText());
					concurso.setFechaConcurso(mod_conc_fechaConcDatePicker.getValue());
					concurso.setFinRegistroConcurso(mod_conc_FecFinRegConcursoDatePicker.getValue());
					concurso.setLugarConcurso(mod_conc_LugarConcursoChoiceBox.getValue());
					concurso.setTipoConcurso(mod_conc_TipoConcursoChoiceBox.getValue());
					okClicked = true;
				} else {
					errorMessage = "No ha sido modificado";
				}

			}
			if (okClicked) {
				controladorHibernateConcurso.updateConcurso(concurso);
				// Actualizar el choice box de la fecha del concurso.
				inicializarSeleccionFecha();
			}
		}
		// Si no existe mensaje error.
		if (errorMessage.length() == 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(escenario);
			alert.setTitle("Information");
			alert.setContentText(" Ha sido modificado correctamente");

			alert.showAndWait();
		} else {
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
	 * Delete Piloto --> Button delete
	 */
	@FXML
	public void elim_piloto_ButtonDelete() {
		Tab tabSelected = usuarioPane.getSelectionModel().getSelectedItem();
		String confirmMessage = "", errorMessage = "";
		if (tabSelected.equals(eliminarUsuarioTab)) {
			// devolver si está seleccionado
			boolean selected = eliminarUsuarioTab.isSelected();
			// si está seleccionado.
			if (selected) {
				Piloto selectedPiloto = elim_piloto_PilotoTableView.getSelectionModel().getSelectedItem();
				if (selectedPiloto != null) {
					controladorHibernatePiloto.deletePiloto(selectedPiloto);
					confirmMessage = "Eliminado!!";
				} else {
					errorMessage = "No ha sido eliminado";
				}

			}

			if (confirmMessage.length() > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(escenario);
				alert.setTitle("Information");
				alert.setContentText(confirmMessage);

				alert.showAndWait();
			}
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

	}

	/**
	 * Delete Concurso --> Button delete concurso.
	 */
	@FXML
	private void elim_conc_ButtonDelete() {

		boolean eliminado = false;
		boolean selected = eliminarConcursoTab.isSelected();
		if (selected) {
			Concurso concurso = elim_conc_ConcursoTableView.getSelectionModel().getSelectedItem();
			if (concurso != null) {
				controladorHibernateConcurso.eliminarConcurso(concurso);
				eliminado = true;
			} else {
				eliminado = false;

			}
		}

		if (eliminado) {
			// Show the confirmation message.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(escenario);
			alert.setTitle("Eliminar Concurso");
			alert.setHeaderText("Eliminar: ");
			alert.setContentText("¿Estás seguro que quieres eliminar?");
			alert.showAndWait();

			elim_conc_ConcursoTableView.getItems().clear();
			elim_conc_ShowTablaConcursoDetails();
			elim_conc_FechaChoiceBox.getItems().clear();
			inicializarSeleccionFecha();

		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(escenario);
			alert.setTitle("Invalid Select Concurso");
			alert.setHeaderText("Please select correct concurso");
			alert.setContentText("No has seleccionado el concurso");

			alert.showAndWait();
		}

	}

	@FXML
	private void elim_conc_ButtonSelectedFecha() {
		elim_conc_FechaChoiceBox.getSelectionModel().getSelectedItem();
	}

	/**
	 * Clean --> Botón eliminar todos los contenidos.
	 */
	@FXML
	public void buttonClean() {
		Tab tabSelected = usuarioPane.getSelectionModel().getSelectedItem();

		if (tabSelected.equals(anadirUsuarioTab)) {
			add_piloto_NombleField.setText("");
			add_piloto_ApeField.setText("");
			add_piloto_EmailField.setText("");
			add_piloto_TelefonoFiled.setText("");
			// se limpia la que está seleccionado.
			add_piloto_CiudadCombo.getSelectionModel().clearSelection();
			add_piloto_ClubField.setText("");
			add_piloto_NumLicenField.setText("");
			// cuando está seleccionado =true;
			add_piloto_PasswordBox.setSelected(false);
			add_piloto_FechaInscripDatePicker.setValue(DateUtil.parse(""));
		}
		if (tabSelected.equals(modificarUsuarioTab)) {
			mod_piloto_PilotoTableView.getItems().clear();
		}
		if (tabSelected.equals(eliminarUsuarioTab)) {
			elim_piloto_PilotoTableView.getItems().clear();
		}

		if (modificarConcursoTab.isSelected()) {
			mod_conc_FecFinRegConcursoDatePicker.setValue(DateUtil.parse(""));
			mod_conc_fechaConcDatePicker.setValue(DateUtil.parse(""));
			mod_conc_LugarConcursoChoiceBox.getSelectionModel().clearSelection();
			mod_conc_TipoConcursoChoiceBox.getSelectionModel().clearSelection();
			mod_conc_nombreConcursoTextField.clear();
			mod_conc_noConcursoTextField.clear();
		}
	}

	/**
	 * Refresh --> Botón para Actualizar todas las tablas.
	 */
	@FXML
	public void buttonRefresh() {

		if (listadoUsuarioTab.isSelected()) {
			show_piloto_PilotoTableView.getItems().clear();
			showAll_piloto_ShowTablaPilotoDetails();
		}

		if (listaConcursoTab.isSelected()) {
			show_conc_ConcursoTableView.getItems().clear();
			showAll_conc_ShowTablaConcursoDetails();

		}

		if (anadirMangaTab.isSelected()) {
			add_concManga_ConcursoTableView.getItems().clear();
			add_manga_IniciarTablaConcursoDetails();
		}

		if (modificarConcursoTab.isSelected()) {
			mod_conc_ConcursoTableView.getItems().clear();

			mod_conc_ShowTablaConcursoDetails();
			mod_conc_FecFinRegConcursoDatePicker.setValue(DateUtil.parse(""));
			mod_conc_fechaConcDatePicker.setValue(DateUtil.parse(""));
			mod_conc_LugarConcursoChoiceBox.getSelectionModel().clearSelection();
			mod_conc_TipoConcursoChoiceBox.getSelectionModel().clearSelection();
			mod_conc_nombreConcursoTextField.clear();
			mod_conc_noConcursoTextField.clear();
		}

		if (showAllMangaTab.isSelected()) {
			show_conc_ConcursoTableView.getItems().clear();
			show_pilotoManga_PilotoTableView.getItems().clear();
			show_manga_MangaTableView.getItems().clear();
			show_manga_FechaChoiceBox.getSelectionModel().clearAndSelect(0);
			showAll_manga_ShowTablaConcursoDetails();
		}

		if (eliminarConcursoTab.isSelected()) {
			elim_conc_ConcursoTableView.getItems().clear();
			elim_conc_FechaChoiceBox.getSelectionModel().clearAndSelect(0);
			elim_conc_ShowTablaConcursoDetails();
		}
		inicializarSeleccionFecha();
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		main.showLoginView();
	}

	@FXML
	private void handleSaveAsFile() {
		xml.setPrimaryStage(escenario);
		if (listadoUsuarioTab.isSelected()) {
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
			fileChooser.getExtensionFilters().add(extFilter);

			File file = fileChooser.showSaveDialog(xml.getPrimaryStage());

			if (file != null) {
				if (!file.getPath().endsWith(".xml")) {
					file = new File(file.getPath() + ".xml");
				}
				xml.savePilotoDataToFile(file, showAll_piloto_listaPilotoData);
			}
		} else {
			if (listaConcursoTab.isSelected()) {
				FileChooser fileChooser = new FileChooser();

				// Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
				fileChooser.getExtensionFilters().add(extFilter);

				File file = fileChooser.showSaveDialog(xml.getPrimaryStage());

				if (file != null) {
					if (!file.getPath().endsWith(".xml")) {
						file = new File(file.getPath() + ".xml");
					}
					xml.saveConcursoDataToFile(file, showAll_conc_listaConcursoData);
				}
			}
		}

	}

	@FXML
	private void handleSaveFile() {
		xml.setPrimaryStage(escenario);
		if (listadoUsuarioTab.isSelected()) {
			// xml.setPilotoData(showAll_piloto_listaPilotoData);
			File personFile = xml.getFilePath();
			if (personFile != null) {
				xml.savePilotoDataToFile(personFile, showAll_piloto_listaPilotoData);
			} else {
				handleSaveAsFile();
			}
		} else {
			if (listaConcursoTab.isSelected()) {
				// xml.setPilotoData(showAll_piloto_listaPilotoData);
				File concursoFile = xml.getFilePath();
				if (concursoFile != null) {
					xml.saveConcursoDataToFile(concursoFile, showAll_conc_listaConcursoData);
				} else {
					handleSaveAsFile();
				}

			}

		}
	}

	/**
	 * Comprobar si ha añadido el piloto con válidez.
	 * 
	 * @return
	 */
	private boolean isInputValidPiloto() {
		String errorMessage = "";

		if (add_piloto_NombleField.getText() == null || add_piloto_NombleField.getText().length() == 0) {
			errorMessage += "No valid nombre!\n";
		}
		if (add_piloto_ApeField.getText() == null || add_piloto_ApeField.getText().length() == 0) {
			errorMessage += "No valid apellido!\n";
		}
		if (add_piloto_EmailField.getText() == null || add_piloto_EmailField.getText().length() == 0
				|| !Validacion.emailFormat(add_piloto_EmailField.getText())
				|| controladorHibernatePiloto.obtenerPilotoPorEmail(add_piloto_EmailField.getText()) != null) {
			if (controladorHibernatePiloto.obtenerPilotoPorEmail(add_piloto_EmailField.getText()) != null) {
				errorMessage += "Este correo electónico ya está registrada en el sistema \n";
			} else {
				errorMessage += "No valido Email !\n";
			}
		}

		if (add_piloto_TelefonoFiled.getText() == null || add_piloto_TelefonoFiled.getText().length() == 0) {
			errorMessage += "No valido Telefono!\n";
		} else {
			// try to parse the telefono into an int.
			try {
				Integer.parseInt(add_piloto_TelefonoFiled.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid Telefono (must be an integer)!\n";
			}
			if (!Validacion.validationTelefono(add_piloto_TelefonoFiled.getText())) {
				errorMessage += "El formato no es valido Telefono";
			}
		}

//		if (add_piloto_NumLicenField.getText() == null || add_piloto_NumLicenField.getText().length() == 0
//				|| add_piloto_NumLicenField.getText().length() != 9) {
//			errorMessage += "No valido Numero de Licencia (la longitud tiene que ser 9 caracteres)!\n";
//		} else {
//			// try to parse the num licnecia into an int.
//			try {
//				Integer.parseInt(add_piloto_NumLicenField.getText());
//			} catch (NumberFormatException e) {
//				errorMessage += "El formato no es valido Numero Licencia (must be an integer)!\n";
//			}
//		}

		if (!add_piloto_PasswordBox.isSelected()) {
			errorMessage += "La contraseña no está seleccionada! \n";
		}

		if (add_piloto_CiudadCombo.getSelectionModel().getSelectedItem() == null
				|| add_piloto_CiudadCombo.getSelectionModel().getSelectedItem().toString().length() == 0) {
			errorMessage += "No valid Ciudad!\n";
		}

		if (add_piloto_ClubField.getText() == null || add_piloto_ClubField.getText().length() == 0) {
			errorMessage += "No valid Club!\n";
		}

		if (add_piloto_FechaInscripDatePicker.getValue() == null
				|| add_piloto_FechaInscripDatePicker.getValue().toString().length() == 0) {
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

	/**
	 * Comprobar si es válido el concurso.
	 * 
	 * @return
	 */
	private boolean isInputValidConcurso() {
		String errorMessage = "";

		if (add_conc_nombreConcursoField.getText() == null || add_conc_nombreConcursoField.getText().length() == 0) {
			errorMessage += "No valid nombre de Concurso!\n";
		}
		if (add_conc_tipoConcursoComboBox.getValue() == null
				|| add_conc_tipoConcursoComboBox.getValue().length() == 0) {
			errorMessage += "No valid tipo Concurso!\n";
		}
		if (add_conc_FechaConcDatePicker.getValue() == null
				|| add_conc_FechaConcDatePicker.getValue().toString().length() == 0) {
			errorMessage += "No valido fecha Concurso!\n";
		}
		if (add_conc_LugarConcComboBox.getValue() == null || add_conc_LugarConcComboBox.getValue().length() == 0) {
			errorMessage += "No valido lugar de Concurso!\n";
		}

		if (add_conc_FecFinRegisConcDatePicker.getValue() == null
				|| add_conc_FecFinRegisConcDatePicker.getValue().toString().length() == 0) {
			errorMessage += "No valid fecha fin de registro!\n";

		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
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
	 * 
	 * @return
	 */
	private boolean mod_conc_isInputValidConcurso() {
		String errorMessage = "";

		if (mod_conc_nombreConcursoTextField.getText() == null
				|| mod_conc_nombreConcursoTextField.getText().length() == 0) {
			errorMessage += "No valid nombre de Concurso!\n";
		}
		if (mod_conc_TipoConcursoChoiceBox.getValue() == null
				|| mod_conc_TipoConcursoChoiceBox.getValue().length() == 0) {
			errorMessage += "No valid tipo Concurso!\n";
		}
		if (mod_conc_fechaConcDatePicker.getValue() == null
				|| mod_conc_fechaConcDatePicker.getValue().toString().length() == 0) {
			errorMessage += "No valido fecha Concurso!\n";
		}
		if (mod_conc_LugarConcursoChoiceBox.getValue() == null
				|| mod_conc_LugarConcursoChoiceBox.getValue().length() == 0) {
			errorMessage += "No valido lugar de Concurso!\n";
		}

		if (mod_conc_FecFinRegConcursoDatePicker.getValue() == null
				|| mod_conc_FecFinRegConcursoDatePicker.getValue().toString().length() == 0) {
			errorMessage += "No valid fecha fin de registro!\n";

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
