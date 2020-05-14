package model_Lista;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.Concurso;

@XmlRootElement(name = "Concurso")
public class ListaConcurso {

	private List<Concurso> lista = new ArrayList<>();

	@XmlElement(name = "concurso")
	public List<Concurso> getLista() {
		return lista;
	}
	
	
	public void setLista(List<Concurso> lista) {
		this.lista = lista;
	}

}
