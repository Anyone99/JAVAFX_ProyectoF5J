package model;

import java.time.LocalDate;

public class Concurso {

	private int idConcurso;
	private String nombreConcurso;
	private String tipoConcurso;
	private LocalDate fechaConcurso;
	private String lugarConcurso;
	private LocalDate finRegistroConcurso;

	public Concurso(String nombreConcurso, String tipoConcurso, LocalDate fechaConcurso, String lugarConcurso,
			LocalDate finRegistroConcurso) {
		super();
		this.nombreConcurso = nombreConcurso;
		this.tipoConcurso = tipoConcurso;
		this.fechaConcurso = fechaConcurso;
		this.lugarConcurso = lugarConcurso;
		this.finRegistroConcurso = finRegistroConcurso;
	}

	public Concurso(int idConcurso, String nombreConcurso, String tipoConcurso, LocalDate fechaConcurso,
			String lugarConcurso, LocalDate finRegistroConcurso) {
		super();
		this.idConcurso = idConcurso;
		this.nombreConcurso = nombreConcurso;
		this.tipoConcurso = tipoConcurso;
		this.fechaConcurso = fechaConcurso;
		this.lugarConcurso = lugarConcurso;
		this.finRegistroConcurso = finRegistroConcurso;
	}

	public Concurso() {
	}

	public int getIdConcurso() {
		return idConcurso;
	}

	public void setIdConcurso(int idConcurso) {
		this.idConcurso = idConcurso;
	}

	public String getNombreConcurso() {
		return nombreConcurso;
	}

	public void setNombreConcurso(String nombreConcurso) {
		this.nombreConcurso = nombreConcurso;
	}

	public String getTipoConcurso() {
		return tipoConcurso;
	}

	public void setTipoConcurso(String tipoConcurso) {
		this.tipoConcurso = tipoConcurso;
	}

	public LocalDate getFechaConcurso() {
		return fechaConcurso;
	}

	public void setFechaConcurso(LocalDate fechaConcurso) {
		this.fechaConcurso = fechaConcurso;
	}

	public String getLugarConcurso() {
		return lugarConcurso;
	}

	public void setLugarConcurso(String lugarConcurso) {
		this.lugarConcurso = lugarConcurso;
	}

	public LocalDate getFinRegistroConcurso() {
		return finRegistroConcurso;
	}

	public void setFinRegistroConcurso(LocalDate finRegistroConcurso) {
		this.finRegistroConcurso = finRegistroConcurso;
	}

	@Override
	public String toString() {
		return "Concurso [idConcurso=" + idConcurso + ", nombreConcurso=" + nombreConcurso + ", tipoConcurso="
				+ tipoConcurso + ", fechaConcurso=" + fechaConcurso + ", lugarConcurso=" + lugarConcurso
				+ ", finRegistroConcurso=" + finRegistroConcurso + "]";
	}

}
