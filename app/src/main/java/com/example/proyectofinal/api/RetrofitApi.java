package com.example.proyectofinal.api;

import com.example.proyectofinal.model.Authorization.Credencial;
import com.example.proyectofinal.model.Authorization.Validacion;
import com.example.proyectofinal.model.Estudiantes.Estudiante;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApi {

    @GET("estudiantes/all")
    Call <List<Estudiante>> getEstudiantes();

    @POST("login")
    Call<Validacion> login(@Body Credencial credencial);
}
