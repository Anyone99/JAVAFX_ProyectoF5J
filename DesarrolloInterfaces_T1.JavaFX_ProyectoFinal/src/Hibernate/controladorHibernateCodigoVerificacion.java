package Hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import Util.Utilidades;
import model.CodigoVerificacion;

public class controladorHibernateCodigoVerificacion {

	public static CodigoVerificacion add_codigoVerf_usuario(String email, String codigo, Calendar tiempoApuntado) {
		Session session = null;
		CodigoVerificacion codigoVerificacion = new CodigoVerificacion(email, codigo, tiempoApuntado);

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			String id = (String) session.save(codigoVerificacion);
			System.out.println(id);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return codigoVerificacion;
	}

	public static CodigoVerificacion obtener_codigoVerf_usuario(String codigo, String email) {
		Session session = null;
		CodigoVerificacion codigoVerificacion = new CodigoVerificacion();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session
					.createQuery("From CodigoVerificacion Where codigoVerificacion =: codigo and email =: email");
			query.setParameter("codigo", codigo);
			query.setParameter("email", email);
			codigoVerificacion = (CodigoVerificacion) query.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return codigoVerificacion;
	}
	
	public static CodigoVerificacion obtener_usuario(String email) {
		Session session = null;
		CodigoVerificacion codigoVerificacion = new CodigoVerificacion();

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session
					.createQuery("From CodigoVerificacion Where email =: email");
			query.setParameter("email", email);
			codigoVerificacion = (CodigoVerificacion) query.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return codigoVerificacion;
	}

	public static boolean delete_codigoVerf_usuario(String email) {
		Session session = null;
		CodigoVerificacion codigoVerificacion = new CodigoVerificacion();
		boolean eliminado = false;

		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			codigoVerificacion.setEmail(email);
			session.delete(codigoVerificacion);
			session.getTransaction().commit();
			eliminado = true;

		} catch (HibernateException e) {
			e.printStackTrace();
			eliminado = false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return eliminado;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<CodigoVerificacion> obtener_CodigoVerificacion() {
		Session session = null;
		List<CodigoVerificacion> listaCodigoVerificacions = new ArrayList<CodigoVerificacion>();
		
		try {
			session = Utilidades.getSessionFactory().openSession();
			session.beginTransaction();
			
			Query query = session.createQuery("From CodigoVerificacion");
			listaCodigoVerificacions = query.list();
			
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally {
			if (session != null) {
				session.close();
			}
		}
		return listaCodigoVerificacions;
		
	}

}
