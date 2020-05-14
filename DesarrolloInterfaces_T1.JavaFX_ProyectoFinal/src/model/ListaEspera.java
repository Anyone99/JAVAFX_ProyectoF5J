package model;

public class ListaEspera {
	private int idEspera;
	private Piloto piloto;
	private Concurso concurso;

	public ListaEspera(Piloto piloto, Concurso concurso) {
		this.piloto = piloto;
		this.concurso = concurso;

	}

	public ListaEspera() {

	}

	public int getIdEspera() {
		return idEspera;
	}

	public void setIdEspera(int idEspera) {
		this.idEspera = idEspera;
	}

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public Concurso getConcurso() {
		return concurso;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
	}

	@Override
	public String toString() {
		return "ListaEspera [idEspera=" + idEspera + ", piloto=" + piloto + ", concurso=" + concurso + "]";
	}

}
