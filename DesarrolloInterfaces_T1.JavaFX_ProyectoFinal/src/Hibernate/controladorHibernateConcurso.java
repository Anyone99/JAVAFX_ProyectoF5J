package Hibernate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import Util.Utilidades;
import model.Concurso;
import model.Manga;
import model.Piloto;

public class controladorHibernateConcurso {

	public static Concurso anadirConcurso(Concurso concurso) {
		Session session = null;
		Concurso saveConcurso = new Concurso();
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			int id = (Integer) session.save(concurso);
			session.getTransaction().commit();
			saveConcurso = obtenerConcursoPorId(id);

		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return saveConcurso;

	}

	public static Concurso updateConcurso(Concurso concurso) {
		Session session = null;
		Concurso modConcurso = new Concurso();
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(concurso);
			modConcurso = concurso;

			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return modConcurso;
	}

	public static void eliminarConcurso(Concurso concurso) {
		Session session = null;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			List<Manga> listaManga = controladorHibernateManga.obtener_mangas_Concurso(concurso);
			if (listaManga != null) {
				listaManga = controladorHibernateManga.update_manga_Concurso(concurso);
				controladorHibernateManga.delete_manga_ListaManga(listaManga);
			} else {

			}

			List<Piloto> listaPilotos = controladorHibernatePiloto.obtenerPilotoPorConcurso(concurso);

			if (listaPilotos != null) {
				controladorHibernatePiloto.updateListaPiloto(listaPilotos);
			}

			session.delete(concurso);

			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Concurso> obtenerListaConcursos() {
		Session session = null;
		List<Concurso> listaConcursos = new ArrayList<Concurso>();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			listaConcursos = session.createQuery("From Concurso order by fechaConcurso asc").list();
		} catch (HibernateException e) {
			System.err.println(e);
		}
		return listaConcursos;
	}

	public static Concurso obtenerConcursoPorId(int id) {
		Session session = null;
		Concurso concurso = new Concurso();
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			concurso = session.load(Concurso.class, id);

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return concurso;
	}

	@SuppressWarnings("rawtypes")
	public static boolean comprobarFechaFinRegistroConcurso(Concurso concurso, LocalDate fechaActual) {
		Session session = null;
		Concurso selectedConcurso = new Concurso();
		boolean ok = false;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("From Concurso Where idConcurso =: idConcurso");
			query.setParameter("idConcurso", concurso.getIdConcurso());
			selectedConcurso = (Concurso) query.uniqueResult();
			// si la fecha se apunta antes de 2 días.
			if (fechaActual.isBefore(selectedConcurso.getFinRegistroConcurso().minusDays(2))) {
				ok = false;
			} else {
				// si la fecha de actual es después de la fecha fin de registro.
				if (fechaActual.isAfter(selectedConcurso.getFinRegistroConcurso())) {
					ok = false;
				} else {
					ok = true;
				}
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return ok;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Integer> distinctFechaConcursos() {
		Session session = null;
		List<Integer> listaFecha = new ArrayList<Integer>();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("Select distinct year(fechaConcurso) From Concurso");
			listaFecha = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listaFecha;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Concurso> obtenerConcursosPorDistinctFecha(int anoConcurso) {
		Session session = null;
		List<Concurso> listaConcursos = new ArrayList<>();
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("From Concurso where year(fechaConcurso) =: anoConcurso");
			query.setParameter("anoConcurso", anoConcurso);
			listaConcursos = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listaConcursos;
	}

}
