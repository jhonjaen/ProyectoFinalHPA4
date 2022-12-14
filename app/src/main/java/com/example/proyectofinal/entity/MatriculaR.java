package com.example.proyectofinal.entity;

public class MatriculaR{
	private int estudianteId;
	private String updatedAt;
	private String createdAt;
	private int id;
	private int grupoAsignaturaId;
	private String titulo, mensaje;

	public String getTitulo() {
		return titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public int getStatus() {
		return status;
	}

	private int status;

	public int getEstudianteId(){
		return estudianteId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public int getGrupoAsignaturaId(){
		return grupoAsignaturaId;
	}
}
