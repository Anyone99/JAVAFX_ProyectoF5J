package Hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import Util.Utilidades;
import model.Concurso;
import model.ListaEspera;
import model.Piloto;

public class controladorHibernateListaEspera {

	public static ListaEspera saveListaEspera(Concurso concurso, Piloto piloto) {
		Session session = null;
		ListaEspera listaEspera = new ListaEspera();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			listaEspera.setConcurso(concurso);
			listaEspera.setPiloto(piloto);

			int id = (int) session.save(listaEspera);
			System.out.println("Lista de Espera : " + id);

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return listaEspera;
	}

	public static boolean comprobacion_ListaEspera(Piloto piloto) {
		Session session = null;
		boolean espera = false;
		ListaEspera listaEspera = new ListaEspera();
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From ListaEspera Where idPiloto =: idPiloto");
			query.setParameter("idPiloto", piloto.getIdPiloto());
			listaEspera = (ListaEspera) query.uniqueResult();

			if (listaEspera != null) {
				espera = true;
			} else {
				espera = false;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return espera;
	}

	public static ListaEspera obtener_listaEspera_byPiloto(Piloto piloto) {
		Session session = null;
		ListaEspera listaEspera = new ListaEspera();
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From ListaEspera Where idPiloto =: idPiloto");
			query.setParameter("idPiloto", piloto.getIdPiloto());
			listaEspera = (ListaEspera) query.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listaEspera;

	}

	@SuppressWarnings("unchecked")
	public static List<ListaEspera> obtener_listaEspera_byConcurso(Concurso concurso) {
		Session session = null;
		List<ListaEspera> list = new ArrayList<ListaEspera>();
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From ListaEspera Where Concurso =: Concurso");
			list = query.list();
			if (list.isEmpty()) {
				list = null;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;

	}

}
