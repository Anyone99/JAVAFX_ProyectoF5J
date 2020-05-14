package view_Concurso;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import Hibernate.controladorHibernateManga;
import Util.DateUtil;
import application.Main;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Concurso;
import model.Manga;
import model.Piloto;

/**
 * Controlador para ver la lista de pilotos que están apuntado en el concursp.
 * 
 * @author six
 *
 */
public class controladorShowPilotoConcurso {

	@FXML
	private Label nombreConcursoLabel;
	@FXML
	private Label fechaConcursoLabel;
	@FXML
	private TableView<Piloto> pilotoTableView;
	@FXML
	private TableColumn<Piloto, List<Manga>> resultadoColumn;
	@FXML
	private TableColumn<Piloto, String> nombreColumn;
	@FXML
	private TableColumn<Piloto, String> apellidoColumn;
	@FXML
	private TableColumn<Piloto, String> noPilotoColumn;
	@FXML
	private TableColumn<Piloto, String> clubColumn;

	@FXML
	private TableView<Manga> show_manga_mangaTableView;
	@FXML
	private TableColumn<Manga, Integer> show_manga_numMangaColumn;
	@FXML
	private TableColumn<Manga, String> show_manga_tiempoColumn;
	@FXML
	private TableColumn<Manga, Integer> show_manga_alturaTableColumn;
	@FXML
	private TableColumn<Manga, Integer> show_manga_aterrizajeTableColumn;
	@FXML
	private TableColumn<Manga, Integer> show_manga_penalizacionTableColumn;
	@FXML
	private TableColumn<Manga, Integer> show_manga_puntTableColumn;
	@FXML
	private TableColumn<Manga, Integer> show_manga_puntFinalTableColumn;

	@FXML
	private TextField grupoTextField;
	@FXML
	private TextField puntuacionTotal;

	private Main main = new Main();
	private Stage dialogStage = new Stage();
	private Concurso concurso = new Concurso();
	private Piloto piloto = new Piloto();
	private LocalDate actuaLocalDate = LocalDate.now();
	private List<Piloto> listaPiloto = new ArrayList<Piloto>();

	private ObservableList<Piloto> listaPilotos = FXCollections.observableArrayList();
	private ObservableList<Manga> listaMangas = FXCollections.observableArrayList();

	/**
	 * Constructor.
	 */
	public controladorShowPilotoConcurso() {

	}

	public void setMain(Main main) {
		this.main = main;

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public Main getMain() {
		return main;
	}

	public Stage getDialogStage() {
		return dialogStage;
	}

	public Concurso getConcurso() {
		return concurso;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
		// Ajustar la label de concurso.
		// fecha de concurso
		fechaConcursoLabel.setText(DateUtil.format(concurso.getFechaConcurso()));
		// Nombre de concurso.
		nombreConcursoLabel.setText(concurso.getNombreConcurso());

	}

	public List<Piloto> getListaPiloto() {
		return listaPiloto;
	}

	public void setListaPiloto(List<Piloto> listaPiloto) {
		this.listaPiloto = listaPiloto;
		listaPilotos.addAll(listaPiloto);

	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	@FXML
	public void initialize() {
		initializeTablaPiloto();
	}

	private void initializeTablaPiloto() {
		// Columna de
		noPilotoColumn.setCellFactory((cellData) -> {
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
		// Initilizar las columnas.
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
		apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());
		clubColumn.setCellValueFactory(cellData -> cellData.getValue().getClubProperty());

		addMangaResultadotoTableConcurso();
		// Añadir el listado de piloto en la tabla de piloto
		pilotoTableView.setItems(listaPilotos);
		// pilotoTableView.getItems().clear();
	}

	private void show_manga_iniciarTablaMangaPorPiloto(ObservableList<Manga> listaMangas) {
		show_manga_numMangaColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumManga()));
		show_manga_tiempoColumn.setCellValueFactory(
				cellData -> new ReadOnlyObjectWrapper<>(convertirTiempo(cellData.getValue().getTiempo())));
		show_manga_alturaTableColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAltura()));
		show_manga_aterrizajeTableColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAterrizaje()));
		show_manga_penalizacionTableColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPenalizaciones()));
		show_manga_puntTableColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPuntos()));
		show_manga_puntFinalTableColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPuntosFinal()));

		show_manga_mangaTableView.setItems(listaMangas);

	}

	private void addMangaResultadotoTableConcurso() {
		Callback<TableColumn<Piloto, List<Manga>>, TableCell<Piloto, List<Manga>>> cellFactory = new Callback<TableColumn<Piloto, List<Manga>>, TableCell<Piloto, List<Manga>>>() {
			@Override
			public TableCell<Piloto, List<Manga>> call(final TableColumn<Piloto, List<Manga>> param) {
				final TableCell<Piloto, List<Manga>> cell = new TableCell<Piloto, List<Manga>>() {

					private final Button Resultado = new Button("Resultado");

					@Override
					public void updateItem(List<Manga> item, boolean empty) {

						super.updateItem(item, empty);

						if (empty) {
							setGraphic(null);
						} else {
							if (actuaLocalDate.isBefore(concurso.getFechaConcurso())) {
								setGraphic(null);
							} else {
								setGraphic(Resultado);
							}

						}
						{
							// Action de Resultado
							Resultado.setOnAction((ActionEvent) -> {
								int punto = 0;

								Piloto piloto = getTableView().getItems().get(getIndex());
								List<Manga> list = controladorHibernateManga.obtener_listaManga_ConcursoPiloto(concurso, piloto);
								grupoTextField.setText(String.valueOf(list.get(0).getGrupo()));

								listaMangas.clear();
								listaMangas.addAll(list);
								show_manga_iniciarTablaMangaPorPiloto(listaMangas);

								// Calcular la puntuacion Total de la Manga
								for (int i = 0; i < list.size(); i++) {
									punto = punto + list.get(i).getPuntosFinal();

								}
								// Set TextField la puntuacion Total.
								puntuacionTotal.setText(String.valueOf(punto));
							});
						}

					}

				};
				return cell;
			}
		};
		resultadoColumn.setCellFactory(cellFactory);
	}

	/*
	 * Method --> Convertir el tiempo de segundo en forma de hora, minuto y segundo.
	 */
	private String convertirTiempo(int segundo) {
		int h, s, m;
		final int hora = 3600;
		final int minuto = 60;
		h = segundo / hora;

		segundo = segundo % hora;

		m = segundo / minuto;

		s = segundo % minuto;

		return h + " : " + m + " : " + s;
	}

}
