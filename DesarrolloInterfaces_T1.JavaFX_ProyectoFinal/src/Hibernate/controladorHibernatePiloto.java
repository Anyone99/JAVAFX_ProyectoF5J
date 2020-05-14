package Hibernate;

import java.util.ArrayList;

import java.util.List;

import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import Util.Utilidades;
import model.Admin;
import model.Concurso;
import model.Piloto;

public class controladorHibernatePiloto {

	public static Piloto registrar(Piloto piloto) {
		Session session = null;
		Piloto registradoPiloto = new Piloto();
		int id = 0;
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			if (obtenerPilotoPorEmail(piloto.getEmail()) == null) {
				 id = (int) session.save(piloto);
			}
			session.getTransaction().commit();
			registradoPiloto = obtenerPilotoPorID(id);

		} catch (HibernateException h) {
			h.printStackTrace();
		} finally {
			if (session != null) {
				Utilidades.getSessionFactory().close();
			}
		}

		return registradoPiloto;

	}

	public static boolean logInUser(String email, String password) {
		Session session = null;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("FROM Piloto WHERE email =: email and password =: password");
			query.setParameter("email", email);
			query.setParameter("password", password);

			Piloto piloto = (Piloto) query.uniqueResult();

			if (piloto != null) {
				return true;
			} else {
				return false;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (session != null) {
				Utilidades.getSessionFactory().close();
			}
		}
	}

	/**
	 * Iniciar session de admin.
	 * 
	 * @param user
	 * @param password
	 * @return
	 */
	public static boolean logInAdmin(String user, String password) {
		Session session = null;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("FROM Admin WHERE usuario =: username and password =: password");
			query.setParameter("username", user);
			query.setParameter("password", password);
			Admin admin = (Admin) query.uniqueResult();
			if (admin != null) {
				return true;
			} else {
				return false;
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (session != null) {
				Utilidades.getSessionFactory().close();
			}
		}
	}

	/**
	 * Modificar el piloto seleccionado.
	 * 
	 * @param piloto
	 * @return
	 */
	public static Piloto updatePiloto(Piloto piloto) {
		Session session = null;
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(piloto);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return piloto;
	}

	/**
	 * Modificafr el piloto cuando apunta en un concurso.
	 * 
	 * @param piloto
	 * @param concurso
	 * @return
	 */
	public static Piloto inscribir_piloto_updateConcurso(Piloto piloto, Concurso concurso) {
		Session session = null;
		Piloto modfPiloto = new Piloto();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From Piloto where idPiloto =:idPiloto");
			query.setParameter("idPiloto", piloto.getIdPiloto());

			Piloto selectedPiloto = (Piloto) query.uniqueResult();

			// Si el piloto ya existe el concurso, entonces no se puede tener otro concurso
			// más.
			if (selectedPiloto.getConcurso() != null) {
				modfPiloto = null;
			} else {
				selectedPiloto.setConcurso(concurso);
				session.update(selectedPiloto);
				modfPiloto = selectedPiloto;
				session.getTransaction().commit();
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return modfPiloto;
	}

	/**
	 * Eliminar Piloto
	 * 
	 * @param piloto
	 */
	public static void deletePiloto(Piloto piloto) {
		Session session = null;
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(piloto);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static void updateListaPiloto(List<Piloto> listaPilotos) {
		Session session = null;
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			for (int i = 0; i < listaPilotos.size(); i++) {
				listaPilotos.get(i).setConcurso(null);
				session.update(listaPilotos.get(i));
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

	@SuppressWarnings("unchecked")
	public static List<Piloto> obtenerListaPiloto() {
		Session session = null;
		List<Piloto> listaPilotos = new ArrayList<>();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			listaPilotos = session.createQuery("From Piloto").list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listaPilotos;
	}

	/**
	 * Obtener Piloto por id
	 * 
	 * @param ID
	 * @return
	 */
	public static Piloto obtenerPilotoPorID(Integer ID) {
		Piloto piloto = new Piloto();
		Session session = null;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From Piloto Where idPiloto =: id");
			query.setParameter("id", ID);
			piloto = (Piloto) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return piloto;
	}

	/**
	 * Obtener piloto por Email
	 * 
	 * @param Email
	 * @return
	 */
	public static Piloto obtenerPilotoPorEmail(String email) {
		Piloto pilotoSelected = new Piloto();
		Session session = null;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From Piloto Where Email =: Email");
			query.setParameter("Email", email);

			pilotoSelected = (Piloto) query.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return pilotoSelected;
	}

	/**
	 * Obtener el listado de piloto por nombre
	 * 
	 * @param nombre
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Piloto> obtenerPilotoPorNombre(String nombre) {
		List<Piloto> listaPiloto = new ArrayList<Piloto>();
		Session session = null;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From Piloto Where Nombre =: Nombre");
			query.setParameter("Nombre", nombre);
			listaPiloto = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return listaPiloto;
	}

	/**
	 * Obtener el listado de piloto por Apellido.
	 * 
	 * @param Apellido
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Piloto> obtenerPilotoPorApellido(String Apellido) {
		List<Piloto> listaPiloto = new ArrayList<Piloto>();
		Session session = null;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From Piloto Where Apellido =: Apellido");
			query.setParameter("Apellido", Apellido);
			listaPiloto = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return listaPiloto;
	}

	/**
	 * Obtener el listado a través del telefono;
	 * 
	 * @param Telefono
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Piloto> obtenerPilotoPorTelefono(Integer Telefono) {
		List<Piloto> listaPiloto = new ArrayList<Piloto>();
		Session session = null;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From Piloto Where Telefono =: Telefono");
			query.setParameter("Telefono", Telefono);
			listaPiloto = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return listaPiloto;
	}

	/**
	 * Obtener el concurso de Piloto por Piloto
	 * 
	 * @param piloto
	 * @return
	 */
	public static Piloto obtenerConcursoPorPiloto(Piloto piloto) {
		Piloto selectPiloto = new Piloto();
		Session session = null;
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From Piloto Where Email =: Email");
			query.setParameter("Email", piloto.getEmail());
			selectPiloto = (Piloto) query.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return selectPiloto;
	}

	/**
	 * Obtener la lista de piloto por id de concurso.
	 * 
	 * @param concurso
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Piloto> obtenerPilotoPorConcurso(Concurso concurso) {
		List<Piloto> listaPilotos = new ArrayList<Piloto>();
		Session session = null;
		System.out.println("id Concurso : " + concurso.getIdConcurso());
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("From Piloto where idConcurso =: idConcurso");
			query.setParameter("idConcurso", concurso.getIdConcurso());
			listaPilotos = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listaPilotos;
	}

	public static Piloto obtenerPiloto_ConcursoYPiloto(Concurso concurso, Piloto piloto) {
		Piloto modPiloto = new Piloto();
		Session session = null;
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("From Piloto where idConcurso =: idConcurso AND idPiloto =: idPiloto");
			query.setParameter("idConcurso", concurso.getIdConcurso());
			query.setParameter("idPiloto", piloto.getIdPiloto());
			modPiloto = (Piloto) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return modPiloto;
	}

	public static Piloto deleteConcursoPiloto(Piloto piloto) {
		Session session = null;
		Piloto modPiloto = new Piloto();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			piloto.setConcurso(null);
			session.update(piloto);
			modPiloto = piloto;
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return modPiloto;

	}

	public static List<Piloto> updatePilotoIdConcurso(Concurso concurso) {
		Session session = null;
		List<Piloto> listaPilotos = obtenerPilotoPorConcurso(concurso);

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();

			for (int i = 0; i < listaPilotos.size(); i++) {
				listaPilotos.get(i).setConcurso(null);
				session.update(listaPilotos.get(i));
			}
			session.getTransaction().commit();

		} catch (HibernateException e) {
			// TODO: handle exception
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return listaPilotos;
	}

	@SuppressWarnings("rawtypes")
	public static boolean comprobarNumLicencia(int numLicencia) {
		Session session = null;
		boolean existe = false;
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("From Piloto Where numLicencia =: numLicencia");
			query.setParameter("numLicencia", numLicencia);
			Piloto piloto = (Piloto) query.uniqueResult();
			if (piloto == null) {
				existe = false;
			} else {
				existe = true;
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return existe;
	}

	@SuppressWarnings("rawtypes")
	public static long contarPilotoConcurso(Concurso concurso) {
		Session session = null;
		long count = 0;
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("Select count(p) From Piloto p Where idConcurso =: idConcurso");
			query.setParameter("idConcurso", concurso.getIdConcurso());
			count = (long) query.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return count;
	}

}
