package Util;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Hibernate.controladorHibernatePiloto;
import model.Manga;
import model.Piloto;

public class MailUtil {
	private String smtp = "smtp.office365.com";
	private final static String USERNAME = "yjrememberme@outlook.com";
	private final static String PASSWORD = "20130215.";

	final boolean debug;

	public MailUtil() {
		this.debug = true;
	}

	// gmail, 163, qq, outlook, hotmail, icloud,gmx
	public String obtenerSMTP(String servidor) {
		if (servidor.equalsIgnoreCase("gmail")) {
			smtp = "stmp.gmail.com";
		} else {
			if (servidor.equalsIgnoreCase("outlook")) {
				smtp = "stmp.Office365.com";
			}
		}

		return smtp;
	}

	Session createSSLSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", this.smtp); // SMTP主机名
		props.put("mail.smtp.port", "465"); // 主机端口号
		props.put("mail.smtp.auth", "true"); // 是否需要用户认证
		// Iniciar SSL:
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.port", "465");
		Session session = Session.getInstance(props, new Authenticator() {
			// 用户名+口令认证:
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MailUtil.USERNAME, MailUtil.PASSWORD);
			}
		});
		session.setDebug(this.debug); // 显示调试信息
		return session;
	}

	public Session createTLSSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", this.smtp);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MailUtil.USERNAME, MailUtil.PASSWORD);

			}
		});
		session.setDebug(this.debug);
		return session;
	}

	Session createInsecureSession(String host, String username, String password) {
		Properties props = new Properties();
		props.put("mail.smtp.host", this.smtp); // SMTP主机名
		props.put("mail.smtp.port", "25"); // 主机端口号
		props.put("mail.smtp.auth", "true"); // 是否需要用户认证
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MailUtil.USERNAME, MailUtil.PASSWORD);
			}
		});
		session.setDebug(this.debug); // 显示调试信息
		return session;
	}

	public static Message createActivacionCodeMessage(Session session, String to, String codigoVerfiacion) {
		MimeMessage message = new MimeMessage(session);
		final String subject = "Código de Verificación de la Aplicación Piloto";
		try {
			message.setFrom(new InternetAddress(USERNAME));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject, "UTF-8");
			String body = "Hola, buenas, \n tu código de verificación es : " + codigoVerfiacion
					+ "\n \n \n Departamento Piloto";
			message.setText(body, "UTF-8");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}

	public static Message enviarContrasena(Session session, String to) {
		MimeMessage message = new MimeMessage(session);
		final String subject = "Recuperar la Contraseña";
		try {
			message.setFrom(new InternetAddress(USERNAME));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject, "UTF-8");
			String body = "Hola, buenas, \n tu contraseña es : "
					+ controladorHibernatePiloto.obtenerPilotoPorEmail(to).getPassword()
					+ "\n \n \n Departamento Piloto";
			message.setText(body, "UTF-8");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}

	public static Message enviarConfirmacionRegistracionMessage(Session session, String to, String passwd) {
		MimeMessage message = new MimeMessage(session);
		final String subject = "Registrar en Proyecto Piloto";
		try {
			message.setFrom(new InternetAddress(USERNAME));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject, "UTF-8");
			String body = "Bienvenida al proyecto Piloto : \n" + "tu usuario es : " + to + "\n" + "tu contraseña es : "
					+ passwd + "\n" + "\n \n \n Departamento Piloto";
			message.setText(body, "UTF-8");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}

	public static Message enviarMensajeClasificacion(Session session, Piloto piloto, List<Manga> listaMangas) {
		MimeMessage message = new MimeMessage(session);
		final String subject = "La clasificación Final del concurso";
		int puntuacionTotal = 0;
		try {
			message.setFrom(new InternetAddress(USERNAME));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(piloto.getEmail()));
			message.setSubject(subject, "UTF-8");
			
			String body = "Hola, buenas. \n" + "Aquí te envíamos la calificación del concurso:  \n \n \n";
			body += "Concurso : " + listaMangas.get(0).getConcurso().getNombreConcurso()+ "\n";
			for (int i = 0; i < listaMangas.size(); i++) {
				body += "Tiempo de Manga " + i + " : " + listaMangas.get(i).getTiempo() +"\n";
				body += "Altura de Manga " + i + " : " + listaMangas.get(i).getAltura() + "\n";
				body += "Aterrizaje de Manga " + i + " : " + listaMangas.get(i).getAterrizaje() + "\n";
				body += "Punto de Manga " + i + " : " + listaMangas.get(i).getPuntos() + "\n \n \n " ;
				puntuacionTotal = listaMangas.get(i).getPuntosFinal() + puntuacionTotal;	
			}
			
			body += "Puntuación Total : " + puntuacionTotal + " \n \n \n";
			body += "Gracias por tu participación, espero en el siguiente concurso \n \n \n " + " Gracias. ";
			
			message.setText(body, "UTF-8");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}
	
	public static Message enviarMensajeContactar(Session session, String email, String titulo, String mensaje) {
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(USERNAME));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(USERNAME));
			message.setSubject("Cliente : " + email + " " + titulo, "UTF-8");
			
			message.setText(mensaje, "UTF-8");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}

}
