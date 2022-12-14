package com.example.proyectofinal.entity;

import com.google.gson.annotations.SerializedName;

public class Matriculaenv{
	public Matriculaenv(int estudianteId, int grupoAsignaturaId) {
		this.estudianteId = estudianteId;
		this.grupoAsignaturaId = grupoAsignaturaId;
	}
	@SerializedName("estudiante_id")
	private int estudianteId;
	@SerializedName("grupo_asignatura_id")
	private int grupoAsignaturaId;

	public int getEstudianteId(){
		return estudianteId;
	}

	public int getGrupoAsignaturaId(){
		return grupoAsignaturaId;
	}
}
