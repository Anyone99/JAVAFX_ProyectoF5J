package model;

public class Manga {
	private int idManga;
	private int numManga;
	private int grupo;
	private int tiempo;
	private int aterrizaje;
	private int altura;
	private int penalizaciones;
	private int puntos;
	private int puntosFinal;
	private Concurso concurso;
	private Piloto piloto;

	public Manga(int numManga, int grupo, int tiempo, int aterrizaje, int altura, int penalizaciones, int puntos,
			int puntosFinal, Concurso concurso, Piloto piloto) {
		super();
		this.numManga = numManga;
		this.grupo = grupo;
		this.tiempo = tiempo;
		this.aterrizaje = aterrizaje;
		this.altura = altura;
		this.penalizaciones = penalizaciones;
		this.puntos = puntos;
		this.puntosFinal = puntosFinal;
		this.concurso = concurso;
		this.piloto = piloto;
	}

	public Manga() {

	}

	public int getIdManga() {
		return idManga;
	}

	public void setIdManga(int idManga) {
		this.idManga = idManga;
	}

	public int getNumManga() {
		return numManga;
	}

	public void setNumManga(int numManga) {
		this.numManga = numManga;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public int getAterrizaje() {
		return aterrizaje;
	}

	public void setAterrizaje(int aterrizaje) {
		this.aterrizaje = aterrizaje;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getPenalizaciones() {
		return penalizaciones;
	}

	public void setPenalizaciones(int penalizaciones) {
		this.penalizaciones = penalizaciones;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getPuntosFinal() {
		return puntosFinal;
	}

	public void setPuntosFinal(int puntosFinal) {
		this.puntosFinal = puntosFinal;
	}

	public Concurso getConcurso() {
		return concurso;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	@Override
	public String toString() {
		return "Manga [idManga=" + idManga + ", numManga=" + numManga + ", grupo=" + grupo + ", tiempo=" + tiempo
				+ ", aterrizaje=" + aterrizaje + ", altura=" + altura + ", penalizaciones=" + penalizaciones
				+ ", puntos=" + puntos + ", puntosFinal=" + puntosFinal + ", concurso=" + concurso + ", piloto="
				+ piloto + "] " + "\n";
	}

}
