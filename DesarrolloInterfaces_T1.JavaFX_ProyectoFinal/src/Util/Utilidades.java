package Util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Utilidades {
	// Factoria de sesi锟絥 para crear objeto sesi锟絥 a partir de XML
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {

		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			sessionFactory = configuration.buildSessionFactory();
		} catch (HibernateException he) {
			System.out.println("Session Factory creation failure");
			throw he;
		}
		return sessionFactory;
	}

	/*
	 * M锟絫odo est锟絫ico (Fachada) para crear la factor锟絘 de sesiones
	 */

	/**
	 * Capturar las excepciones que suelen aparecer.
	 * @param h
	 */
	public static void exceptionHibernate(HibernateException h) {

		if (h.getCause() instanceof org.hibernate.InstantiationException) {
			System.err.println(h + " : No hay defecto de constructor por Entity" + h.getLocalizedMessage());
		} else {
			if (h.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				System.out.println(h + " : Duplicado el numero primary key" + h.getLocalizedMessage());
			} else {
				if (h.getCause() instanceof org.hibernate.exception.SQLGrammarException) {
					System.err.println(h + " :could not execute query" + h.getLocalizedMessage());
				}else {
					if (h.getCause() instanceof org.hibernate.MappingException) {
						System.err.println(h + " : No se conoce el Entity " + "\r\n " + h.getLocalizedMessage());
					}else {
						System.err.println(h + h.getLocalizedMessage());
					}
				}
			}
		}

		if (h.getCause() instanceof NullPointerException) {
			System.err.println(h + " : No hay datos " + h.getLocalizedMessage());
		}
		
		

	}

}
