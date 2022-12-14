package com.example.proyectofinal.entity;

public class Datauser{
	public Datauser(String cedula, String apellido, String correo, String contrasena, String nombre) {
		this.cedula = cedula;
		this.apellido = apellido;
		this.correo = correo;
		this.contrasena = contrasena;
		this.nombre = nombre;
	}

	private String cedula;
	private String apellido;
	private String correo;
	private String contrasena;
	private String nombre;

	public String getCedula(){
		return cedula;
	}

	public String getApellido(){
		return apellido;
	}

	public String getCorreo(){
		return correo;
	}

	public String getContrasena(){
		return contrasena;
	}

	public String getNombre(){
		return nombre;
	}
}
