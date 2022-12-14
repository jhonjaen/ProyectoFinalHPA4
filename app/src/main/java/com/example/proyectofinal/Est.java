package com.example.proyectofinal;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.Util.ConnectionRest;
import com.example.proyectofinal.api.ServiceApi;
import com.example.proyectofinal.entity.AsistenciaE;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Est extends AppCompatActivity {

    Button btn_ver_asistencia;
    EditText edt_id_estudiante,grupo_ver;
    ListView lst_estudiantes_asistencia;
    List<String> asistencias = new ArrayList<String>();
    ArrayAdapter adaptador = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        grupo_ver = (EditText)findViewById(R.id.grupo_ver);
        btn_ver_asistencia = (Button)findViewById(R.id.btn_id_estudiante);
        edt_id_estudiante = (EditText)findViewById(R.id.edt_id_estudiante);

        lst_estudiantes_asistencia = (ListView)findViewById(R.id.lst_asistencia_estudiante);

        btn_ver_asistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_id_estudiante.getText().toString().equals("")){
                    edt_id_estudiante.setText("");
                    Toast.makeText(Est.this, "POR FAVOR, INTRODUZCA UN ID", Toast.LENGTH_LONG).show();
                }
                cargarAsistencia();
            }
        });
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, asistencias);
        lst_estudiantes_asistencia.setAdapter(adaptador);

    }

    public void cargarAsistencia(){
        String edt_id_estudiante_v = edt_id_estudiante.getText().toString();
        int estudiante_id = Integer.parseInt(edt_id_estudiante_v);
        int grup =Integer.parseInt(grupo_ver.getText().toString());

        ServiceApi api = ConnectionRest.getConnetion().create(ServiceApi.class);
        Call<List<AsistenciaE>> call = api.asistenciaEstudiante(grup, estudiante_id);

        call.enqueue(new Callback<List<AsistenciaE>>() {
            @Override
            public void onResponse(Call<List<AsistenciaE>> call, Response<List<AsistenciaE>> response) {
                List<AsistenciaE> lista = response.body();
                for (AsistenciaE x:lista){
                    asistencias.add("fecha : "+x.getFecha() +" estudianteId: "+x.getEstudianteId()+" updatedAt: "+ x.getUpdatedAt() + " hora: "+ x.getHora()+ " estadoAsistenciaId: "+ x.getEstadoAsistenciaId()+ " createdAt: "+ x.getCreatedAt()+ " id: "+ x.getId()+ " grupoAsignaturaId: "+ x.getGrupoAsignaturaId());
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AsistenciaE>> call, Throwable t) {

            }
        });
    }



}