package com.example.proyectofinal.model.Estudiantes;

public class Estudiante{
	private String fotoUrl;
	private String updatedAt;
	private String cedula;
	private String apellido;
	private String correo;
	private String createdAt;
	private int id;
	private String nombre;



	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getFotoUrl(){
		return fotoUrl;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getCedula(){
		return cedula;
	}

	public String getApellido(){
		return apellido;
	}

	public String getCorreo(){
		return correo;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getNombre(){
		return nombre;
	}
}
