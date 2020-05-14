package model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Piloto {
	private Integer idPiloto;
	private final StringProperty nombre;
	private final StringProperty apellido;
	private final StringProperty email;
	private final StringProperty password;
	private final IntegerProperty numLicencia;
	private final IntegerProperty telefono;
	private final StringProperty Ciudad;
	private final ObjectProperty<LocalDate> fechaInscripcion;
	private final StringProperty club;
	private Concurso concurso;

	public Piloto() {
		this(null, null, null, null, 0, null, 0, null, null);
	}

	/**
	 * Constructor
	 * 
	 * @param nombre
	 * @param apellido
	 * @param email
	 * @param password
	 * @param telefono
	 * @param Ciudad
	 * @param numLicencia
	 * @param fechaInscripcion
	 * @param club
	 */
	public Piloto(String nombre, String apellido, String email, String password, int telefono, String Ciudad,
			int numLicencia, LocalDate fechaInscripcion, String club) {

		this.nombre = new SimpleStringProperty(nombre);
		this.apellido = new SimpleStringProperty(apellido);
		this.email = new SimpleStringProperty(email);
		this.password = new SimpleStringProperty(password);
		this.telefono = new SimpleIntegerProperty(telefono);
		this.Ciudad = new SimpleStringProperty(Ciudad);
		this.numLicencia = new SimpleIntegerProperty(numLicencia);
		this.club = new SimpleStringProperty(club);
		this.fechaInscripcion = new SimpleObjectProperty<LocalDate>(fechaInscripcion);
	}

	public Piloto(String nombre, String apellido, String email, String password, int telefono, String Ciudad,
			int numLicencia, LocalDate fechaInscripcion, String club, Concurso concurso) {

		this.nombre = new SimpleStringProperty(nombre);
		this.apellido = new SimpleStringProperty(apellido);
		this.email = new SimpleStringProperty(email);
		this.password = new SimpleStringProperty(password);
		this.telefono = new SimpleIntegerProperty(telefono);
		this.Ciudad = new SimpleStringProperty(Ciudad);
		this.numLicencia = new SimpleIntegerProperty(numLicencia);
		this.club = new SimpleStringProperty(club);
		this.fechaInscripcion = new SimpleObjectProperty<LocalDate>(fechaInscripcion);
		this.concurso = concurso;
	}

	public Concurso getConcurso() {
		return concurso;
	}

	public Integer getIdPiloto() {
		return idPiloto;
	}

	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}

	public String getNombre() {
		return nombre.get();
	}

	public String getApellido() {
		return apellido.get();
	}

	public String getEmail() {
		return email.get();
	}

	public String getPassword() {
		return password.get();
	}

	public int getTelefono() {
		return telefono.get();
	}

	public String getCiudad() {
		return Ciudad.get();
	}

	public int getNumLicencia() {
		return numLicencia.get();
	}

	public LocalDate getFechaInscripcion() {
		return fechaInscripcion.get();
	}

	public String getClub() {
		return club.get();
	}

	public StringProperty getNombreProperty() {
		return nombre;
	}

	public StringProperty getApellidoProperty() {
		return apellido;
	}

	public IntegerProperty getNumLicenciaProperty() {
		return numLicencia;
	}

	public StringProperty getEmailProperty() {
		return email;
	}

	public StringProperty getPasswordProperty() {
		return password;
	}

	public IntegerProperty getTelefonoProperty() {
		return telefono;
	}

	public StringProperty getCiudadProperty() {
		return Ciudad;
	}

	public StringProperty getClubProperty() {
		return club;
	}

	public ObjectProperty<LocalDate> getfechaInscripcionProperty() {
		return fechaInscripcion;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public void setApellido(String apellido) {
		this.apellido.set(apellido);
	}

	public void setNumLicencia(int numLicencia) {
		this.numLicencia.set(numLicencia);
	}

	public void setFechaInscripcion(LocalDate fechaInscripcion) {
		this.fechaInscripcion.set(fechaInscripcion);
	}

	public void setClub(String club) {
		this.club.set(club);
	}

	public void setTelefono(int telefono) {
		this.telefono.set(telefono);
	}

	public void setCiudad(String ciudad) {
		this.Ciudad.set(ciudad);
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	@Override
	public String toString() {
		String string = "";
		if (getConcurso() == null) {
			string = "Piloto [idPiloto=" + idPiloto + ", nombre=" + nombre + ", apellido=" + apellido + ", email="
					+ email + ", password=" + password + ", numLicencia=" + numLicencia + ", telefono=" + telefono
					+ ", Ciudad=" + Ciudad + ", fechaInscripcion=" + fechaInscripcion + ", club=" + club + ", concurso="
					+ null + "]";
		} else {
			string = "Piloto [idPiloto=" + idPiloto + ", nombre=" + nombre + ", apellido=" + apellido + ", email="
					+ email + ", password=" + password + ", numLicencia=" + numLicencia + ", telefono=" + telefono
					+ ", Ciudad=" + Ciudad + ", fechaInscripcion=" + fechaInscripcion + ", club=" + club + ", concurso="
					+ concurso + "]+ \n";
		}

		return string;
	}

}
