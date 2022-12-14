package com.example.proyectofinal.entity;

public class Enviarlist{
	public Enviarlist(int grupoAsignaturaId) {
		this.grupoAsignaturaId = grupoAsignaturaId;
	}

	private int grupoAsignaturaId;

	public int getGrupoAsignaturaId(){
		return grupoAsignaturaId;
	}
}
