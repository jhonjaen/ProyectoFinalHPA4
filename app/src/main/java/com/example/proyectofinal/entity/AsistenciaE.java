package com.example.proyectofinal.entity;

import com.google.gson.annotations.SerializedName;

public class AsistenciaE {
	private String fecha;
	@SerializedName("estudiante_id")
	private int estudianteId;
	@SerializedName("updated_at")
	private String updatedAt;
	private String hora;
	@SerializedName("estado_asistencia_id")
	private int estadoAsistenciaId;
	@SerializedName("created_at")
	private String createdAt;
	private int id;
	@SerializedName("grupo_asignatura_id")
	private int grupoAsignaturaId;

	public String getFecha(){
		return fecha;
	}

	public int getEstudianteId(){
		return estudianteId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getHora(){
		return hora;
	}

	public int getEstadoAsistenciaId(){
		return estadoAsistenciaId;
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
