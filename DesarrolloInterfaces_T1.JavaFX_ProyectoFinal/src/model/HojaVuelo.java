package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="HojaVuelo")
public class HojaVuelo {
	@XmlElement(name="concurso")
	private Concurso concurso = new Concurso();
	@XmlElement(name="piloto")
	private Piloto piloto = new Piloto();
	@XmlElementWrapper(name="listaMangas")
	@XmlElement(name="listaManga")
	private List<Manga> listaMangas = new ArrayList<>();
	
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
	public List<Manga> getListaMangas() {
		return listaMangas;
	}
	public void setListaMangas(List<Manga> listaMangas) {
		this.listaMangas = listaMangas;
	}
	
	
	
	

	



}
