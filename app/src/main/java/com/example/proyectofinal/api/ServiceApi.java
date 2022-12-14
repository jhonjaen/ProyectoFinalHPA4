package com.example.proyectofinal.api;


import com.example.proyectofinal.entity.Asistencia;
import com.example.proyectofinal.entity.AsistenciaE;
import com.example.proyectofinal.entity.AsistenciaR;
import com.example.proyectofinal.entity.Datauser;
import com.example.proyectofinal.entity.MatriculaR;
import com.example.proyectofinal.entity.Matriculaenv;
import com.example.proyectofinal.entity.Oblist;
import com.example.proyectofinal.entity.ResultadoR;
import com.example.proyectofinal.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceApi {

    @GET("/api/estudiantes/all")
    public abstract  Call<List<User>> listaUsuarios();

    @POST("registrar")
    Call<ResultadoR> registrar(@Body Datauser datauser);

    @POST("matricular")
    Call<MatriculaR> matricular(@Body Matriculaenv matriculaenv);

   // @GET("/estudiantes/asistencia/1")
   // public abstract Call<List<user>> listaAsistencia();

    @POST("estudiante/asistencia")
    Call<AsistenciaR> Enviarasistencia(@Body Asistencia asistencia);

    @GET("/api/estudiante/asistencia/{gaId}/{eId}")
    public  abstract  Call<List<AsistenciaE>> asistenciaEstudiante(@Path("gaId") int groupID, @Path("eId") int studentID);

    @GET("/api/estudiantes/asistencia/{gaId}/")
    public  abstract  Call<List<Oblist>> asistencias(@Path("gaId") int groupID);
}
