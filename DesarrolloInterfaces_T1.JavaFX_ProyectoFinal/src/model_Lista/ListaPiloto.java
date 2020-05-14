package model_Lista;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.Piloto;

@XmlRootElement(name = "pilotos")
public class ListaPiloto {

	private List<Piloto> lista = new ArrayList<>();

	@XmlElement(name = "piloto")

	public List<Piloto> getLista() {
		return lista;
	}

	public void setLista(List<Piloto> lista) {
		this.lista = lista;
	}

}
