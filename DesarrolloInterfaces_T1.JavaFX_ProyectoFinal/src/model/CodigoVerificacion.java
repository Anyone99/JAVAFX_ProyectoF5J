package model;

import java.util.Calendar;

public class CodigoVerificacion {
	private String email;
	private String codigoVerificacion;
	private Calendar tiempoApuntado;

	public CodigoVerificacion(String email, String codigoVerificacion, Calendar tiempoApuntado) {
		super();
		this.email = email;
		this.codigoVerificacion = codigoVerificacion;
		this.tiempoApuntado = tiempoApuntado;
	}
	
	public CodigoVerificacion() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodigoVerificacion() {
		return codigoVerificacion;
	}

	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	public Calendar getTiempoApuntado() {
		return tiempoApuntado;
	}

	public void setTiempoApuntado(Calendar tiempoApuntado) {
		this.tiempoApuntado = tiempoApuntado;
	}

	@Override
	public String toString() {
		return "CodigoVerificacion [email=" + email + ", codigoVerificacion=" + codigoVerificacion + ", tiempoApuntado="
				+ tiempoApuntado + "]";
	}

}
