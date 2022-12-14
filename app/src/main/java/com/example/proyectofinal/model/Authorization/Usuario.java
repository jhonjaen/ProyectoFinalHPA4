package com.example.proyectofinal.model.Authorization;

public class Usuario{
	private String apellidos;
	private int role;
	private String updatedAt;
	private int docenteId;
	private String cedula;
	private int active;
	private String createdAt;
	private Object emailVerifiedAt;
	private int id;
	private String email;
	private String nombres;

	public String getApellidos(){
		return apellidos;
	}

	public int getRole(){
		return role;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getDocenteId(){
		return docenteId;
	}

	public String getCedula(){
		return cedula;
	}

	public int getActive(){
		return active;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public Object getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getNombres(){
		return nombres;
	}
}
