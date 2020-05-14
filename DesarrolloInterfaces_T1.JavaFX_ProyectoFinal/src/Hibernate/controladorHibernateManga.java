package Hibernate;

import java.util.ArrayList;


import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import Util.Utilidades;
import model.Concurso;
import model.Manga;
import model.Piloto;

public class controladorHibernateManga {

	public static boolean insertarGrupoManga(Concurso concurso, Piloto piloto, int grupo) {
		Session session = null;
		boolean insertar = false;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			for (int i = 1; i <= 6; i++) {
				Manga manga = new Manga(i, grupo, 0, 0, 0, 0, 0, 0, concurso, piloto);
				int mangaId = (int) session.save(manga);
				if (mangaId > 0) {
					insertar = true;
					System.out.println(insertar);
				}
			}
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

		return insertar;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Manga> obtener_listaManga_ConcursoPiloto(Concurso concurso, Piloto piloto) {
		Session session = null;
		List<Manga> listaMangas = new ArrayList<Manga>();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createQuery("From Manga Where idConcurso =: idConcurso And idPiloto =: idPiloto");
			query.setParameter("idConcurso", concurso.getIdConcurso());
			query.setParameter("idPiloto", piloto.getIdPiloto());
			listaMangas = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		}finally {
			if (session != null) {
				session.close();
			}
		}

		return listaMangas;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Manga> obtener_ListMangas_concurso_lista(Concurso concurso, List<Piloto> lista) {
		Session session = null;
		List<Manga> list = new ArrayList<>();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			for (int i = 0 ; i<lista.size(); i++) {
				Query query = session.createQuery("From Manga Where idConcurso =: idConcurso and idPiloto =: idPiloto");
				query.setParameter("idConcurso", concurso.getIdConcurso());
				query.setParameter("idPiloto", lista.get(i).getIdPiloto());
				list = query.list();

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


	@SuppressWarnings("unchecked")
	public static List<Manga> update_ListaMangas(Concurso concurso, Piloto piloto) {
		Session session = null;
		List<Manga> listaMangas = new ArrayList<Manga>();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From Manga Where idConcurso =: idConcurso And idPiloto =: idPiloto");
			query.setParameter("idConcurso", concurso.getIdConcurso());
			query.setParameter("idPiloto", piloto.getIdPiloto());
			listaMangas = query.list();

			for (int i = 0; i < listaMangas.size(); i++) {
				listaMangas.get(i).setConcurso(null);
				listaMangas.get(i).setPiloto(null);
				session.update(listaMangas.get(i));
			}
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listaMangas;
	}

	@SuppressWarnings("unchecked")
	public static List<Manga> update_manga_Concurso(Concurso concurso) {
		Session session = null;
		List<Manga> listaMangas = new ArrayList<Manga>();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From Manga Where idConcurso =: idConcurso");
			query.setParameter("idConcurso", concurso.getIdConcurso());
			listaMangas = query.list();

			for (int i = 0; i < listaMangas.size(); i++) {
				listaMangas.get(i).setConcurso(null);
				listaMangas.get(i).setPiloto(null);
				session.update(listaMangas.get(i));
			}
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listaMangas;
	}

	public static void delete_manga_PorConcursoPiloto(Concurso concurso, Piloto piloto) {
		Session session = null;
		List<Manga> listaMangas = new ArrayList<Manga>();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			listaMangas = update_ListaMangas(concurso, piloto);

			for (int i = 0; i < listaMangas.size(); i++) {
				session.delete(listaMangas.get(i));
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static void delete_manga_ListaManga(List<Manga> listaManga) {
		Session session = null;
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			for (int i = 0; i < listaManga.size(); i++) {
				session.delete(listaManga.get(i));
			}
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}


	public static List<Manga> update_puntosMangas(List<Manga> listaMangas) {
		Session session = null;
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			for (int i = 0; i < listaMangas.size(); i++) {
				session.update(listaMangas.get(i));
			}
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return listaMangas;
	}

	public static List<Manga> update_manga_puntosFinal(List<Manga> listaMangas, int posicion) {
		Session session = null;
		int punto = 0;
		final int puntoAsignado = 1000;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			for (int i = 0; i < listaMangas.size(); i++) {
				if (i == posicion) {
					listaMangas.get(i).setPuntosFinal(1000);
				} else {
					punto = listaMangas.get(i).getPuntos() * puntoAsignado / listaMangas.get(posicion).getPuntos();
					listaMangas.get(i).setPuntosFinal(punto);
				}

				session.update(listaMangas.get(i));
			}

			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listaMangas;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Manga> obtener_mangas_Concurso(Concurso concurso) {
		Session session = null;
		List<Manga> listaList = new ArrayList<>();
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("From Manga Where idConcurso =: idConcurso");
			query.setParameter("idConcurso", concurso.getIdConcurso());
			listaList = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listaList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Manga> obtener_manga_puntuacion(Concurso concurso, int numManga) {
		Session session = null;

		List<Manga> list = new ArrayList<>();
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createQuery("From Manga Where idConcurso =: idConcurso and numManga =: numManga");

			query.setParameter("idConcurso", concurso.getIdConcurso());
			query.setParameter("numManga", numManga);

			list = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	
	//Obtener el piloto dentro de la manga a través del concurso.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Piloto> obtener_piloto_PorConcurso_EnManga(Concurso concurso) {
		Session session = null;
		List<Integer> lista = new ArrayList<>();
		List<Piloto> listaPilotos = new ArrayList<>();
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session
					.createQuery("Select distinct piloto.idPiloto From Manga m  WHERE idConcurso =:idConcurso");
			query.setParameter("idConcurso", concurso.getIdConcurso());
			lista = query.list();

			for (int i = 0; i < lista.size(); i++) {
				Piloto piloto = session.load(Piloto.class, lista.get(i));
				listaPilotos.add(piloto);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return listaPilotos;
	}

	@SuppressWarnings("unchecked")
	public static List<Long> obtener_GrupoManga_PorConcurso(Concurso concurso) {
		List<Long> listaGrupo = new ArrayList<Long>();
		Session session = null;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery(
					"Select count(distinct idPiloto) from Manga where idConcurso =: idConcurso group by grupo");
			query.setParameter("idConcurso", concurso.getIdConcurso());
			listaGrupo = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listaGrupo;
	}
	

}
