package Util;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacion {
	/**
	 * Comprobar el email si es valido.
	 * 
	 * @param email
	 * @return si es valido o no
	 */
	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String patternEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(patternEmail);
		final Matcher mat = pattern.matcher(email);

		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	// '/^(\+34|0034|34)?[\s|\-|\.]?[6-9][\s|\-|\.]?([0-9][\s|\-|\.]?){8}$/'
	public static boolean validationTelefono(String telefono) {
		boolean valido = true;
		final String patternTelefono = "^(\\+34|0034|34)?[\\s|\\-|\\.]?[6-9][\\s|\\-|\\.]?([0-9][\\s|\\-|\\.]?){8}$";
		final Pattern pattern = Pattern.compile(patternTelefono);
		final Matcher matcher = pattern.matcher(telefono);

		if (!matcher.find()) {
			valido = false;
		}

		return valido;

	}

	public static boolean comprobarDifFecha(Calendar endDate, Calendar nowDate) {
		boolean valido;
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// Obtener la diferencia del tiempo.
		long diff = endDate.getTime().getTime() - nowDate.getTime().getTime();

		// calcular dia.
		long day = diff / nd;
		// calcular hora
		long hour = diff % nd / nh;
		// Calcular minutp
		long min = diff % nd % nh / nm;
		// long sec = diff % nd % nh % nm / ns;

		if (day > 0) {
			valido = false;
		} else {
			if (hour > 0) {
				valido = false;
			} else {
				if (min > 59) {
					valido = false;
				} else {
					valido = true;
				}
			}
		}
		return valido;
	}

	/*
	 * Method --> Convertir el tiempo de segundo en forma de hora, minuto y segundo.
	 */
	public static String convertirTiempo(int segundo) {
		int h, s, m;
		final int hora = 3600;
		final int minuto = 60;
		h = segundo / hora;

		segundo = segundo % hora;

		m = segundo / minuto;

		s = segundo % minuto;

		return h + " : " + m + " : " + s;
	}

}