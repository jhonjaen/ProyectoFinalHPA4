package com.example.proyectofinal.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Asistencia{
	private String fecha;
	@SerializedName("estudiante_id")
	private int estudianteId;
	private String hora;
	@SerializedName("estado_asistencia_id")
	private int estadoAsistenciaId;
	@SerializedName("grupo_asignatura_id")
	private int grupoAsignaturaId;

	public Asistencia(String fecha, int estudianteId, String hora, int estadoAsistenciaId, int grupoAsignaturaId) {
		this.fecha = fecha;
		this.estudianteId = estudianteId;
		this.hora = hora;
		this.estadoAsistenciaId = estadoAsistenciaId;
		this.grupoAsignaturaId = grupoAsignaturaId;
	}


}