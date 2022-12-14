package com.example.proyectofinal.model.Authorization;

public class Credencial {
	public Credencial(String contrasena, String usuario) {
		this.contrasena = contrasena;
		this.usuario = usuario;
	}

	private String contrasena;
	private String usuario;

	public String getContrasena(){
		return contrasena;
	}

	public String getUsuario(){
		return usuario;
	}
}
