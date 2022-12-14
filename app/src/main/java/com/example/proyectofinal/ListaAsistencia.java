package com.example.proyectofinal;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.proyectofinal.Util.ConnectionRest;
import com.example.proyectofinal.api.ServiceApi;
import com.example.proyectofinal.entity.Oblist;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaAsistencia extends AppCompatActivity {

    ArrayList<String> asistencia = new ArrayList<String>();
    ListView lstasist = null;
    ArrayAdapter adaptador = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_asistencia);

        lstasist = (ListView) findViewById(R.id.lstAsistencia);
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, asistencia);
        lstasist.setAdapter(adaptador);

       Cargarlista();
    }

    public void Cargarlista(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://asistencia-upn43.ondigitalocean.app/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       ServiceApi api = retrofit.create(ServiceApi.class);
       /* Call<List<Oblist>> call = api.asistencias(2);

        call.enqueue(new Callback<List<Oblist>>() {
            @Override
            public void onResponse(Call<List<Oblist>> call, Response<List<Oblist>> response) {
                List<Oblist> lista = response.body();
                for (Oblist x:lista){
                    asistencia.add("fecha : "+x.getFecha() +" estudianteId: "+x.getEstudianteId()+" updatedAt: "+ x.getUpdatedAt() + " hora: "+ x.getHora()+ " estadoAsistenciaId: "+ x.getEstadoAsistenciaId()+ " createdAt: "+ x.getCreatedAt()+ " id: "+ x.getId()+ " grupoAsignaturaId: "+ x.getGrupoAsignaturaId());
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Oblist>> call, Throwable t) {

            }
        });*/
        Call<List<Oblist>> call = api.asistencias(2);

        call.enqueue(new Callback<List<Oblist>>() {
            @Override
            public void onResponse(Call<List<Oblist>> call, Response<List<Oblist>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(ListaAsistencia.this, "error", Toast.LENGTH_LONG).show();
                    return;
                }



                List<Oblist> lista = response.body();
                for (Oblist x:lista){
                    asistencia.add("fecha : "+x.getFecha() +" estudianteId: "+x.getEstudianteId()+" updatedAt: "+ x.getUpdatedAt() + " hora: "+ x.getHora()+ " estadoAsistenciaId: "+ x.getEstadoAsistenciaId()+ " createdAt: "+ x.getCreatedAt()+ " id: "+ x.getId()+ " grupoAsignaturaId: "+ x.getGrupoAsignaturaId());
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Oblist>> call, Throwable t) {

            }
        });



    }
}