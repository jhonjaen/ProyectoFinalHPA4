package com.example.proyectofinal.Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionRest {

    private static Retrofit retrofit = null;
    private static final String  RUTA_API="https://asistencia-upn43.ondigitalocean.app";

    public static Retrofit getConnetion(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(RUTA_API).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
