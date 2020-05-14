package model;

public class Admin {

	private int idAdmin;

	private String usuario;
	private String password;

	public Admin(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
	}

	public Admin() {

	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Admin [usuario=" + usuario + ", password=" + password + "]";
	}

}
