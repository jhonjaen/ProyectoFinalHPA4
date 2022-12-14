package com.example.proyectofinal.entity;

import lombok.Data;

@Data
public class AsistenciaR{
	private String titulo;

	public String getTitulo() {
		return titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public int getStatus() {
		return status;
	}

	private String mensaje;
	private int status;
}