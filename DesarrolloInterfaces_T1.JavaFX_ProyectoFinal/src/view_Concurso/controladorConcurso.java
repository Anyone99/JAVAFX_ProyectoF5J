package view_Concurso;

import java.time.LocalDate;
import java.util.List;

import Hibernate.controladorHibernateConcurso;
import Hibernate.controladorHibernateListaEspera;
import Hibernate.controladorHibernateManga;
import Hibernate.controladorHibernatePiloto;
import application.Main;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Concurso;
import model.ListaEspera;
import model.Piloto;

public class controladorConcurso {

	@FXML
	private TableView<Concurso> concursoTable;
	@FXML
	private TableColumn<Concurso, String> NoColumn;
	@FXML
	private TableColumn<Concurso, String> NombreConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> TipoConcursoColumn;
	@FXML
	private TableColumn<Concurso, String> LugarConcursoColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> FechaColumn;
	@FXML
	private TableColumn<Concurso, LocalDate> FinRegistroColumn;
	@FXML
	private TableColumn<Concurso, Piloto> inscribirColumn;
	@FXML
	private TableColumn<Concurso, Piloto> showColumn;
	@FXML
	private TableColumn<Concurso, Piloto> deleteColumn;

	@FXML
	private Label nombreUsuarioLabel;

	private ObservableList<Concurso> listaConcursos = FXCollections.observableArrayList();

	private Main main;
	private Stage escenario;
	private Piloto piloto = new Piloto();

	private LocalDate actuaLocalDate = LocalDate.now();

	public controladorConcurso() {

	}

	@FXML
	private void initialize() {
		showTableDetails();

	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
		Piloto pilotoSelected = controladorHibernatePiloto.obtenerPilotoPorEmail(piloto.getEmail());
		nombreUsuarioLabel.setText(pilotoSelected.getNombre() + "," + pilotoSelected.getApellido());
	}

	public Main getMain() {
		return main;
	}

	/**
	 * Tabla Concurso --> Mostrar el contenido de la tabla.
	 */
	private void showTableDetails() {

		NoColumn.setCellFactory((cellData) -> {
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
		NombreConcursoColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNombreConcurso()));
		FechaColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFechaConcurso()));
		FinRegistroColumn.setCellValueFactory(
				cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getFinRegistroConcurso()));
		TipoConcursoColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTipoConcurso()));
		LugarConcursoColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLugarConcurso()));
		// Obtener la lista del concurso
		List<Concurso> lista = controladorHibernateConcurso.obtenerListaConcursos();

		// Añadir a la tabla
		listaConcursos.addAll(lista);
		concursoTable.setItems(listaConcursos);

		// Actualizar la tabla del concurso. si el concurso ya está finalizado,
		// actualizar los datos
		// del piloto.
		for (int i = 0; i < lista.size(); i++) {
			if (actuaLocalDate.isAfter(lista.get(i).getFechaConcurso())) {
				controladorHibernatePiloto.updatePilotoIdConcurso(lista.get(i));
			}
		}
		// Añadir los botones en la tabla.
		apuntar_concTabla_inscribirButton();
		show_concTabla_ShowButton();
		elim_concTabla_EliminiarConcursoButton();

	}

	/**
	 * Añadir el botón
	 */
	private void apuntar_concTabla_inscribirButton() {
		Callback<TableColumn<Concurso, Piloto>, TableCell<Concurso, Piloto>> cellFactory = new Callback<TableColumn<Concurso, Piloto>, TableCell<Concurso, Piloto>>() {
			@Override
			public TableCell<Concurso, Piloto> call(final TableColumn<Concurso, Piloto> param) {
				final TableCell<Concurso, Piloto> cell = new TableCell<Concurso, Piloto>() {
					private String errorMessage = "", confirmMenssage = "";
					private final Button inscribir = new Button("Inscribir");

					@Override
					public void updateItem(Piloto item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							Concurso concurso = getTableView().getItems().get(getIndex());
							if (actuaLocalDate.isAfter(concurso.getFinRegistroConcurso().minusDays(2))) {
								setDisable(true);
							} else {
								if (actuaLocalDate.isAfter(concurso.getFechaConcurso())) {
									setDisable(true);
								}
							}
							setGraphic(inscribir);
						}
					}

					{
						// el button para inscribir, si el piloto no está registrar en ningún concurso y
						// está registrado dentro de la fecha limitada.
						inscribir.setOnAction((ActionEvent event) -> {
							Concurso concursoSelected = getTableView().getItems().get(getIndex());
							Piloto pilotoSelected = controladorHibernatePiloto.obtenerConcursoPorPiloto(piloto);
							
							// si la fecha que apunta es antes de 2 dias que termine el registro y el
							if (actuaLocalDate.isBefore(concursoSelected.getFinRegistroConcurso().minusDays(2))
									&& controladorHibernatePiloto.contarPilotoConcurso(concursoSelected) < 20) {
								System.out.println(pilotoSelected);
								// modificar el idConcurso en piloto, también comprobar si existe el concurso.
								Piloto modfPiloto = controladorHibernatePiloto.inscribir_piloto_updateConcurso(pilotoSelected, concursoSelected);
								// comprobar si ha sido modficiado
								if (modfPiloto != null) {
									// entre 1- 2
									int grupo = comprobarGrupo(concursoSelected);
									System.out.println(grupo);
									boolean insertado = controladorHibernateManga.insertarGrupoManga(concursoSelected, modfPiloto, grupo);
									if (insertado) {
										confirmMenssage = "Estás registrada en el concurso , Pertenece grupo : " + grupo
												+ " \n";
										// si existe ya no se puede apuntar.
									}else {
										errorMessage = "No está registrada, por error de Aplicacion, contacta con Programador \n";
									}
								} else {
									errorMessage = "Está registrada "
											+ pilotoSelected.getConcurso().getNombreConcurso() + " Concurso \n";
								}

							} else {
								// si la fecha de actual es después de la fecha fin de registro.
								if (actuaLocalDate.isAfter(concursoSelected.getFinRegistroConcurso())) {
									errorMessage = "No se puede registrar después del fin de Registro. \n";
								} else {
									// Si la fecha actual es =  de 2 dias.
									if (actuaLocalDate.isEqual(concursoSelected.getFinRegistroConcurso().minusDays(2))) {
										errorMessage = "El registro ya está finalizado \n";
									}else {
										if (actuaLocalDate.isAfter(concursoSelected.getFinRegistroConcurso().minusDays(2))) {
											errorMessage = "El registro ya está finalizado \n";

										}
									}
								}

								// Si el registro ya está lleno, ir a la lista de espera.
								if (controladorHibernatePiloto.contarPilotoConcurso(concursoSelected) > 20) {
									// Comprobar si está en la lista de Espera.
									if (controladorHibernateListaEspera.comprobacion_ListaEspera(pilotoSelected)) {
										errorMessage = "Estás en la lista de Espera del "
												+ controladorHibernateListaEspera
														.obtener_listaEspera_byPiloto(pilotoSelected).getConcurso()
														.getNombreConcurso();
									} else {
										ListaEspera listaEspera = controladorHibernateListaEspera
												.saveListaEspera(concursoSelected, pilotoSelected);
										errorMessage = "El registro de este concurso está lleno, estás en la lista de Espera : "
												+ listaEspera.getIdEspera();
									}

								}
							}
							if (errorMessage.length() > 0) {
								// Show the error message.
								Alert alert = new Alert(AlertType.ERROR);
								alert.initOwner(escenario);
								alert.setTitle("Invalid Fields");
								alert.setContentText(errorMessage);
								alert.showAndWait();
							}
							if (confirmMenssage.length() > 0) {
								// Show the error message.
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.initOwner(escenario);
								alert.setTitle("Notification : ");
								alert.setContentText(confirmMenssage);
								alert.showAndWait();
							}
						});
					}

				};
				return cell;
			}
		};
		inscribirColumn.setCellFactory(cellFactory);
	}

	private void show_concTabla_ShowButton() {
		Callback<TableColumn<Concurso, Piloto>, TableCell<Concurso, Piloto>> cellFactory = new Callback<TableColumn<Concurso, Piloto>, TableCell<Concurso, Piloto>>() {
			@Override
			public TableCell<Concurso, Piloto> call(final TableColumn<Concurso, Piloto> param) {
				final TableCell<Concurso, Piloto> cell = new TableCell<Concurso, Piloto>() {
					private final Button show = new Button("Show");

					{
						// Action de botón show.
						show.setOnAction((ActionEvent) -> {
							Concurso concurso = getTableView().getItems().get(getIndex());
							List<Piloto> listaPiloto = controladorHibernateManga
									.obtener_piloto_PorConcurso_EnManga(concurso);
							// Aparece otra ventana.
							main.showPilotoEnConcursoDialog(concurso, listaPiloto);

						});
					}

					@Override
					public void updateItem(Piloto item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(show);
						}
					}
				};
				return cell;
			}
		};
		showColumn.setCellFactory(cellFactory);
	}

	private void elim_concTabla_EliminiarConcursoButton() {
		Callback<TableColumn<Concurso, Piloto>, TableCell<Concurso, Piloto>> cellFactory = new Callback<TableColumn<Concurso, Piloto>, TableCell<Concurso, Piloto>>() {
			@Override
			public TableCell<Concurso, Piloto> call(final TableColumn<Concurso, Piloto> param) {
				final TableCell<Concurso, Piloto> cell = new TableCell<Concurso, Piloto>() {
					private String errorMessage = "";
					private String confirmMessage = "";
					private final Button delete = new Button("Delete");

					@Override
					public void updateItem(Piloto item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							Concurso concurso = getTableView().getItems().get(getIndex());
							if (actuaLocalDate.isAfter(concurso.getFinRegistroConcurso().minusDays(2))) {
								setDisable(true);
							} else {
								if (actuaLocalDate.isAfter(concurso.getFechaConcurso())) {
									setDisable(true);
								}

							}
							setGraphic(delete);

						}
					}

					{
						delete.setOnAction((ActionEvent) -> {
							Concurso concurso = getTableView().getItems().get(getIndex());
							// Obtener el piloto por Email de iniciar Sessión
							Piloto pilotoObtenido = controladorHibernatePiloto.obtenerPilotoPorEmail(piloto.getEmail());
							// Obtener el piloto por concurso de la tabla y el piloto Obtenido por Iniciar
							// sessión.
							Piloto selectdPiloto = controladorHibernatePiloto.obtenerPiloto_ConcursoYPiloto(concurso,
									pilotoObtenido);
							// Si el piloto obtenido no es nulo.
							if (selectdPiloto != null) {

								// Comprobar si la fecha actualidad está antes de la fecha que termine el
								// concurso.
								if (actuaLocalDate.isBefore(concurso.getFechaConcurso())) {
									// Eliminar el piloto
									Piloto elimPiloto = controladorHibernatePiloto.deleteConcursoPiloto(selectdPiloto);
									// Si elimina el concurso del piloto, también eliminar la manga.
									controladorHibernateManga.delete_manga_PorConcursoPiloto(concurso, selectdPiloto);
									// Comporbar ha sido correctamente
									if (elimPiloto != null) {
										confirmMessage = "Ha sido eliminado correctamente";

										List<ListaEspera> listaEspera = controladorHibernateListaEspera
												.obtener_listaEspera_byConcurso(concurso);

										if (listaEspera != null) {
											for (int i = 0; i < listaEspera.size(); i++) {
												if (controladorHibernatePiloto.contarPilotoConcurso(concurso) < 20) {
													controladorHibernatePiloto.inscribir_piloto_updateConcurso(
															listaEspera.get(i).getPiloto(),
															listaEspera.get(i).getConcurso());
												}

											}
										}
									} else {
										errorMessage = "No ha sido eliminado correctamente";
									}

								} else {
									// si ha pasado la fecha del concurso.
									errorMessage = "Ya no se puede eliminar el concurso, ha pasado la fecha";
								}
							} else {
								// decir que no está registrada en este concurso.
								errorMessage = "No estás registrada en este concurso : " + concurso.getNombreConcurso();
							}

							if (errorMessage.length() > 0) {
								// Show the error message.
								Alert alert = new Alert(AlertType.ERROR);
								alert.initOwner(escenario);
								alert.setTitle("Invalid ");
								alert.setContentText(errorMessage);
								alert.showAndWait();
							}
							if (confirmMessage.length() > 0) {
								// Show the error message.
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.initOwner(escenario);
								alert.setTitle("Eliminar Concurso ");
								alert.setHeaderText("¿Estás seguro que quieres eliminar el concurso ? ");
								alert.setContentText(confirmMessage);
								alert.showAndWait();
								confirmMessage = "";
							}
						});
					}

				};
				return cell;
			}
		};
		deleteColumn.setCellFactory(cellFactory);
	}

	// Comprobar el grupo si está en mismo cantidad.
	public static int comprobarGrupo(Concurso concurso) {
		int grupoCambiar = 0, grupo1 = 0, grupo2 = 0;
		int grupo = (int) (Math.random() * 2) + 1;

		// Obtener los datos de cantidad de grupo.
		List<Long> list = controladorHibernateManga.obtener_GrupoManga_PorConcurso(concurso);
		
		if (list.isEmpty()) {
			grupoCambiar = grupo;
		} else {
			// Si el piloto que pertenece al grupo 1 que sea menor pues se cambiar a
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == 1) {
					grupo1++;
				} else {
					if (list.get(i) == 2) {
						grupo2++;
					}
				}
			}

			if (grupo1 < grupo2) {
				grupoCambiar = 1;
			} else {
				if (grupo1 > grupo2) {
					grupoCambiar = 2;
				} else {
					grupoCambiar = grupo;
				}
			}
			

		}
		return grupoCambiar;

	}

}
