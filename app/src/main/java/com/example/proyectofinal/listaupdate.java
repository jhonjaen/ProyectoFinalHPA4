package com.example.proyectofinal;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.proyectofinal.Util.ConnectionRest;
import com.example.proyectofinal.api.ServiceApi;
import com.example.proyectofinal.entity.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listaupdate extends AppCompatActivity {

    String url="https://asistencia-upn43.ondigitalocean.app/api/estudiantes/all";
    List<String> data= new ArrayList<String>();
    ListView lstd;
    ArrayAdapter adapatador = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaupdate);


         lstd = (ListView) findViewById(R.id.listak);
        adapatador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        lstd.setAdapter(adapatador);
        cargaUsuarios();

    }
    public void cargaUsuarios(){
        ServiceApi api = ConnectionRest.getConnetion().create(ServiceApi.class);

        Call<List<User>> call = api.listaUsuarios();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> lista = response.body();
                for(User x:lista){

                    data.add("ID: "+x.getId() +" Creado: "+x.getCreated_at()+" Acualizado: "+ x.getUpdated_at());
                }
                adapatador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {}
        });

    }

}