package view_Manga;

import java.util.ArrayList;
import java.util.List;
import Hibernate.controladorHibernateManga;
import Util.DateUtil;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Concurso;
import model.Manga;
import model.Piloto;

public class controladorManga {

	private ObservableList<Integer> manga_optionMinutos = FXCollections.observableArrayList();
	private ObservableList<Integer> manga_optionSegundo = FXCollections.observableArrayList();

	@FXML
	private ChoiceBox<Integer> manga1_minutoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga1_segungdoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga2_minutoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga2_segungdoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga3_minutoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga3_segungdoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga4_minutoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga4_segungdoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga5_minutoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga5_segungdoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga6_minutoBox = new ChoiceBox<>();
	@FXML
	private ChoiceBox<Integer> manga6_segungdoBox = new ChoiceBox<>();

	@FXML
	private TextField manga1_alturaField;
	@FXML
	private TextField manga2_alturaField;
	@FXML
	private TextField manga3_alturaField;
	@FXML
	private TextField manga4_alturaField;
	@FXML
	private TextField manga5_alturaField;
	@FXML
	private TextField manga6_alturaField;

	@FXML
	private TextField manga1_PenalizacionField;
	@FXML
	private TextField manga2_PenalizacionField;
	@FXML
	private TextField manga3_PenalizacionField;
	@FXML
	private TextField manga4_PenalizacionField;
	@FXML
	private TextField manga5_PenalizacionField;
	@FXML
	private TextField manga6_PenalizacionField;

	@FXML
	private TextField manga1_AlterizajeField;
	@FXML
	private TextField manga2_AlterizajeField;
	@FXML
	private TextField manga3_AlterizajeField;
	@FXML
	private TextField manga4_AlterizajeField;
	@FXML
	private TextField manga5_AlterizajeField;
	@FXML
	private TextField manga6_AlterizajeField;

	@FXML
	private TextField nombreConcursoField;
	@FXML
	private TextField nombrePilotoField;
	@FXML
	private TextField fechaConcursoField;
	@FXML
	private TextField grupoMangaField;

	private Stage dialogStage;
	private Main main;
	private Piloto piloto = new Piloto();
	private Concurso concurso = new Concurso();
	private List<Manga> listaMangas = new ArrayList<Manga>();

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

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
		nombrePilotoField.setText(piloto.getNombre());
	}

	public Concurso getConcurso() {
		return concurso;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
		nombreConcursoField.setText(concurso.getNombreConcurso());
		fechaConcursoField.setText(DateUtil.format(concurso.getFechaConcurso()));
	}

	public List<Manga> getListaMangas() {
		return listaMangas;
	}

	public void setListaMangas(List<Manga> listaMangas) {
		this.listaMangas = listaMangas;

		int segundo = listaMangas.get(0).getTiempo();
		int minuto = segundo % 3600 / 60;
		segundo = segundo % 60;

		manga1_minutoBox.setValue(minuto);
		manga1_segungdoBox.setValue(segundo);
		manga1_AlterizajeField.setText(String.valueOf(listaMangas.get(0).getAterrizaje()));
		manga1_alturaField.setText(String.valueOf(listaMangas.get(0).getAltura()));
		manga1_PenalizacionField.setText(String.valueOf(listaMangas.get(0).getPenalizaciones()));

		segundo = listaMangas.get(1).getTiempo();
		minuto = segundo % 3600 / 60;
		segundo = segundo % 60;

		manga2_minutoBox.setValue(minuto);
		manga2_segungdoBox.setValue(segundo);
		manga2_AlterizajeField.setText(String.valueOf(listaMangas.get(1).getAterrizaje()));
		manga2_alturaField.setText(String.valueOf(listaMangas.get(1).getAltura()));
		manga2_PenalizacionField.setText(String.valueOf(listaMangas.get(1).getPenalizaciones()));

		segundo = listaMangas.get(2).getTiempo();
		minuto = segundo % 3600 / 60;
		segundo = segundo % 60;

		manga3_minutoBox.setValue(minuto);
		manga3_segungdoBox.setValue(segundo);
		manga3_AlterizajeField.setText(String.valueOf(listaMangas.get(2).getAterrizaje()));
		manga3_alturaField.setText(String.valueOf(listaMangas.get(2).getAltura()));
		manga3_PenalizacionField.setText(String.valueOf(listaMangas.get(2).getPenalizaciones()));

		segundo = listaMangas.get(3).getTiempo();
		minuto = segundo % 3600 / 60;
		segundo = segundo % 60;

		manga4_minutoBox.setValue(minuto);
		manga4_segungdoBox.setValue(segundo);
		manga4_AlterizajeField.setText(String.valueOf(listaMangas.get(3).getAterrizaje()));
		manga4_alturaField.setText(String.valueOf(listaMangas.get(3).getAltura()));
		manga4_PenalizacionField.setText(String.valueOf(listaMangas.get(3).getPenalizaciones()));

		segundo = listaMangas.get(4).getTiempo();
		minuto = segundo % 3600 / 60;
		segundo = segundo % 60;

		manga5_minutoBox.setValue(minuto);
		manga5_segungdoBox.setValue(segundo);
		manga5_AlterizajeField.setText(String.valueOf(listaMangas.get(4).getAterrizaje()));
		manga5_alturaField.setText(String.valueOf(listaMangas.get(4).getAltura()));
		manga5_PenalizacionField.setText(String.valueOf(listaMangas.get(4).getPenalizaciones()));

		segundo = listaMangas.get(5).getTiempo();
		minuto = segundo % 3600 / 60;
		segundo = segundo % 60;

		manga6_minutoBox.setValue(minuto);
		manga6_segungdoBox.setValue(segundo);
		manga6_AlterizajeField.setText(String.valueOf(listaMangas.get(5).getAterrizaje()));
		manga6_alturaField.setText(String.valueOf(listaMangas.get(5).getAltura()));
		manga6_PenalizacionField.setText(String.valueOf(listaMangas.get(5).getPenalizaciones()));

		grupoMangaField.setText(String.valueOf(listaMangas.get(1).getGrupo()));

	}

	public boolean returnOkClicked() {
		return true;
	}

	@FXML
	private void initialize() {
		inicializarOptionMinutoSegundo();
		iniciarlizarPenalizaciones();
		nombreConcursoField.setEditable(false);
		nombrePilotoField.setEditable(false);
		fechaConcursoField.setEditable(false);
	}

	/**
	 * Inicializar la opción de minuto y segundo.
	 */
	private void inicializarOptionMinutoSegundo() {

		for (int i = 0; i <= 9; i++) {
			manga_optionMinutos.add(i);
		}
		manga1_minutoBox.setItems(manga_optionMinutos);
		manga2_minutoBox.setItems(manga_optionMinutos);
		manga3_minutoBox.setItems(manga_optionMinutos);
		manga4_minutoBox.setItems(manga_optionMinutos);
		manga5_minutoBox.setItems(manga_optionMinutos);
		manga6_minutoBox.setItems(manga_optionMinutos);

		for (int i = 0; i <= 59; i++) {
			manga_optionSegundo.add(i);
		}
		
		manga1_segungdoBox.setItems(manga_optionSegundo);
		manga2_segungdoBox.setItems(manga_optionSegundo);
		manga3_segungdoBox.setItems(manga_optionSegundo);
		manga4_segungdoBox.setItems(manga_optionSegundo);
		manga5_segungdoBox.setItems(manga_optionSegundo);
		manga6_segungdoBox.setItems(manga_optionSegundo);

	}

	private void iniciarlizarPenalizaciones() {
		manga1_PenalizacionField.setText(String.valueOf(0));
		manga2_PenalizacionField.setText(String.valueOf(0));
		manga3_PenalizacionField.setText(String.valueOf(0));
		manga4_PenalizacionField.setText(String.valueOf(0));
		manga5_PenalizacionField.setText(String.valueOf(0));
		manga6_PenalizacionField.setText(String.valueOf(0));

	}
	
	/**
	 * Add Manga -> Añadir los datos de mangas para los pilotos
	 */
	@FXML
	private void add_manga_buttonAdd() {
		int minuto, segundo, tiempoFinal;

		if (add_Manga_isInputValid()) {
			minuto = manga1_minutoBox.getValue();
			segundo = manga1_segungdoBox.getValue();
			tiempoFinal = (minuto * 60) + segundo;
			listaMangas.get(0).setTiempo(tiempoFinal);
			listaMangas.get(0).setAltura(Integer.parseInt(manga1_alturaField.getText()));
			listaMangas.get(0).setAterrizaje(Integer.parseInt(manga1_AlterizajeField.getText()));
			listaMangas.get(0).setPenalizaciones(Integer.parseInt(manga1_PenalizacionField.getText()));

			minuto = manga2_minutoBox.getValue();
			segundo = manga2_segungdoBox.getValue();
			tiempoFinal = (minuto * 60) + segundo;
			listaMangas.get(1).setTiempo(tiempoFinal);
			listaMangas.get(1).setAltura(Integer.parseInt(manga2_alturaField.getText()));
			listaMangas.get(1).setAterrizaje(Integer.parseInt(manga2_AlterizajeField.getText()));
			listaMangas.get(1).setPenalizaciones(Integer.parseInt(manga2_PenalizacionField.getText()));

			minuto = manga3_minutoBox.getValue();
			segundo = manga3_segungdoBox.getValue();
			tiempoFinal = (minuto * 60) + segundo;
			listaMangas.get(2).setTiempo(tiempoFinal);
			listaMangas.get(2).setAltura(Integer.parseInt(manga3_alturaField.getText()));
			listaMangas.get(2).setAterrizaje(Integer.parseInt(manga3_AlterizajeField.getText()));
			listaMangas.get(2).setPenalizaciones(Integer.parseInt(manga3_PenalizacionField.getText()));

			minuto = manga4_minutoBox.getValue();
			segundo = manga4_segungdoBox.getValue();
			tiempoFinal = (minuto * 60) + segundo;
			listaMangas.get(3).setTiempo(tiempoFinal);
			listaMangas.get(3).setAltura(Integer.parseInt(manga4_alturaField.getText()));
			listaMangas.get(3).setAterrizaje(Integer.parseInt(manga4_AlterizajeField.getText()));
			listaMangas.get(3).setPenalizaciones(Integer.parseInt(manga4_PenalizacionField.getText()));

			minuto = manga5_minutoBox.getValue();
			segundo = manga5_segungdoBox.getValue();
			tiempoFinal = (minuto * 60) + segundo;
			listaMangas.get(4).setTiempo(tiempoFinal);
			listaMangas.get(4).setAltura(Integer.parseInt(manga5_alturaField.getText()));
			listaMangas.get(4).setAterrizaje(Integer.parseInt(manga5_AlterizajeField.getText()));
			listaMangas.get(4).setPenalizaciones(Integer.parseInt(manga5_PenalizacionField.getText()));

			minuto = manga6_minutoBox.getValue();
			segundo = manga6_segungdoBox.getValue();
			tiempoFinal = (minuto * 60) + segundo;
			listaMangas.get(5).setTiempo(tiempoFinal);
			listaMangas.get(5).setAltura(Integer.parseInt(manga6_alturaField.getText()));
			listaMangas.get(5).setAterrizaje(Integer.parseInt(manga6_AlterizajeField.getText()));
			listaMangas.get(5).setPenalizaciones(Integer.parseInt(manga6_PenalizacionField.getText()));

			int[] puntoTotal = new int[listaMangas.size()];
			int[] puntoTiempo = calcularPuntosTiempo(listaMangas);
			int[] puntoAterrizaje = calcularPuntosAterrizaje(listaMangas);
			int[] puntoAltura = calcularPuntosAltura(listaMangas);
			for (int i = 0; i < listaMangas.size(); i++) {
				puntoTotal[i] = puntoTiempo[i] + puntoAterrizaje[i] - puntoAltura[i];
				listaMangas.get(i).setPuntos(puntoTotal[i]);
			}

			List<Manga> list = controladorHibernateManga.update_puntosMangas(listaMangas);
			if (list != null) {
				// Show the error message.
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(dialogStage);
				alert.setTitle("Añadir los datos de mangas");
				alert.setHeaderText("Información : ");
				alert.setContentText("Has conseguido guardar los datos de mangas para los pilotos");

				alert.showAndWait();
				dialogStage.close();
			}else {
				// Show the error message.
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("Añadir los datos de mangas");
				alert.setHeaderText("Error : ");
				alert.setContentText("Error de modificar o guardar los datos de mangas de los pilotos");

				alert.showAndWait();
			}

		}


	}

	private boolean add_Manga_isInputValid() {
		String errorMessage = "";

		if (manga1_alturaField.getText() == null || manga1_alturaField.getText().length() == 0) {
			errorMessage += "No valid la altura de manga 1, debe ser numero de 0-1000+ \n";
		}
		if (manga2_alturaField.getText() == null || manga2_alturaField.getText().length() == 0) {
			errorMessage += "No valid la altura de manga 2, debe ser numero de 0-1000+ \n";
		}
		if (manga3_alturaField.getText() == null || manga3_alturaField.getText().length() == 0) {
			errorMessage += "No valid la altura de manga 3, debe ser numero de 0-1000+ \n";
		}
		if (manga4_alturaField.getText() == null || manga4_alturaField.getText().length() == 0) {
			errorMessage += "No valid la altura de manga 4, debe ser numero de 0-1000+ \n";
		}
		if (manga5_alturaField.getText() == null || manga5_alturaField.getText().length() == 0) {
			errorMessage += "No valid la altura de manga 5, debe ser numero de 0-1000+ \n";
		}
		if (manga6_alturaField.getText() == null || manga6_alturaField.getText().length() == 0) {
			errorMessage += "No valid la altura de manga 6, debe ser numero de 0-1000+ \n";
		}

		if (manga1_minutoBox.getValue() == null || manga1_minutoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el minuto de manga 1 \n";
		}
		if (manga2_minutoBox.getValue() == null || manga2_minutoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el minuto de manga 2 \n";
		}
		if (manga3_minutoBox.getValue() == null || manga3_minutoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el minuto de manga 3 \n";
		}
		if (manga4_minutoBox.getValue() == null || manga4_minutoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el minuto de manga 4 \n";
		}
		if (manga5_minutoBox.getValue() == null || manga5_minutoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el minuto de manga 5 \n";
		}
		if (manga6_minutoBox.getValue() == null || manga6_minutoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el minuto de manga 6 \n";
		}

		if (manga1_segungdoBox.getValue() == null || manga1_segungdoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el sengundo de manga 1 \n";
		}
		if (manga2_segungdoBox.getValue() == null || manga2_segungdoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el sengundo de manga 2 \n";
		}
		if (manga3_segungdoBox.getValue() == null || manga3_segungdoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el sengundo de manga 3 \n";
		}
		if (manga4_segungdoBox.getValue() == null || manga4_segungdoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el sengundo de manga 4 \n";
		}
		if (manga5_segungdoBox.getValue() == null || manga5_segungdoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el sengundo de manga 5 \n";
		}
		if (manga6_segungdoBox.getValue() == null || manga6_segungdoBox.getValue().toString().length() == 0) {
			errorMessage += "No has seleccionado el sengundo de manga 6 \n";
		}

		if (manga1_AlterizajeField.getText() == null || manga1_AlterizajeField.getText().length() == 0) {
			errorMessage += "No has introuducido el campo de Alterizaje de Manga 1 \n";
		}
		if (manga2_AlterizajeField.getText() == null || manga2_AlterizajeField.getText().length() == 0) {
			errorMessage += "No has introuducido el campo de Alterizaje de Manga 2 \n";
		}
		if (manga3_AlterizajeField.getText() == null || manga3_AlterizajeField.getText().length() == 0) {
			errorMessage += "No has introuducido el campo de Alterizaje de Manga 3 \n";
		}
		if (manga4_AlterizajeField.getText() == null || manga4_AlterizajeField.getText().length() == 0) {
			errorMessage += "No has introuducido el campo de Alterizaje de Manga 4 \n";
		}
		if (manga5_AlterizajeField.getText() == null || manga5_AlterizajeField.getText().length() == 0) {
			errorMessage += "No has introuducido el campo de Alterizaje de Manga 5 \n";
		}
		if (manga6_AlterizajeField.getText() == null || manga6_AlterizajeField.getText().length() == 0) {
			errorMessage += "No has introuducido el campo de Alterizaje de Manga 6 \n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Añadir los datos de mangas");
			alert.setHeaderText("Error : ");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;

		}

	}

	private int[] calcularPuntosTiempo(List<Manga> listaMangas) {
		@SuppressWarnings("unused")
		int segundo, minuto, hora;
		int[] puntoTiempo = new int[listaMangas.size()];
		for (int i = 0; i < listaMangas.size(); i++) {
			segundo = listaMangas.get(i).getTiempo();
			if (segundo <= 0) {
				hora = 0;
				minuto = 0;
				segundo = 0;
			} else {
				hora = segundo / 3600;
				minuto = segundo % 3600 / 60;
				// segundo = segundo % 60;

				if (minuto == 10) {
					puntoTiempo[i] = 600;
				} else {
					if (minuto < 10) {
						puntoTiempo[i] = segundo;
					} else {
						puntoTiempo[i] = 0;
					}
				}

			}

		}
		return puntoTiempo;
	}

	private int[] calcularPuntosAterrizaje(List<Manga> listaMangas) {
		int aterrizaje;
		int[] puntosAterrizaje = new int[listaMangas.size()];

		for (int i = 0; i < listaMangas.size(); i++) {
			aterrizaje = listaMangas.get(i).getAterrizaje();

			switch (aterrizaje) {
			case 0:
				puntosAterrizaje[i] = 50;
				break;
			case 1:
				puntosAterrizaje[i] = 50;
				break;
			case 2:
				puntosAterrizaje[i] = 45;
				break;
			case 3:
				puntosAterrizaje[i] = 40;
				break;
			case 4:
				puntosAterrizaje[i] = 35;
				break;
			case 5:
				puntosAterrizaje[i] = 30;
				break;
			case 6:
				puntosAterrizaje[i] = 25;
				break;
			case 7:
				puntosAterrizaje[i] = 20;
				break;
			case 8:
				puntosAterrizaje[i] = 15;
				break;
			case 9:
				puntosAterrizaje[i] = 10;
				break;
			case 10:
				puntosAterrizaje[i] = 5;
				break;
			default:
				puntosAterrizaje[i] = 0;
				break;
			}
		}
		return puntosAterrizaje;
	}

	private int[] calcularPuntosAltura(List<Manga> listaMangas) {
		int[] puntosAltura = new int[listaMangas.size()];
		int puntos, puntoRest;
		final double puntosNeg = 0.5;
		final int puntosMax = 200, puntoNeg3 = 3;
		for (int i = 0; i < listaMangas.size(); i++) {
			puntos = listaMangas.get(i).getAltura();
			if (puntos <= puntosMax && puntos >= 0) {
				puntosAltura[i] = (int) (puntos * puntosNeg);

			} else {
				puntoRest = puntos - puntosMax;
				puntosAltura[i] = (int) ((puntosMax * puntosNeg) + (puntoRest * puntoNeg3));

			}
		}

		return puntosAltura;
	}

}
