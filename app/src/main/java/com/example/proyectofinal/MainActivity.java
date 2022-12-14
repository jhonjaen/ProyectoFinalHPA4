package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.api.RetrofitApi;
import com.example.proyectofinal.model.Authorization.Credencial;
import com.example.proyectofinal.model.Authorization.Validacion;
import com.example.proyectofinal.model.Estudiantes.Estudiante;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private List<Estudiante> listaEstudiantes;
    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;
    private TextView textViewResult;
    EditText usuario,pass;

    Button sesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario=(EditText)findViewById(R.id.usuariox);
        pass=(EditText)findViewById(R.id.pass);

        textViewResult = findViewById(R.id.text_view_result);
        sesion=(Button)findViewById(R.id.sesion);
        sesion.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://asistencia-upn43.ondigitalocean.app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Credencial credencial= new Credencial(pass.getText().toString(), usuario.getText().toString());
        Call <Validacion> call = retrofitApi.login(credencial);

        call.enqueue(new Callback<Validacion>() {
            @Override
            public void onResponse(Call<Validacion> call, Response<Validacion> response) {
                if(!response.isSuccessful()){

                    //textViewResult.setText("Code: " + response.code());
                    if(response.code()==500){
                        startActivity(new Intent(MainActivity.this, Est.class));
                        return;
                    }
                    return;
                }

                Validacion validacion = response.body();

                String content = "";
                content += "Title: " + validacion.getTitle() + "\n\n";
                textViewResult.append(content);
                if(usuario.getText().toString().equals("danger@utp.ac.pa")){
                    startActivity(new Intent(MainActivity.this, Admin.class));
                }
             else   if(usuario.getText().toString().equals("zamorin@utp.ac.pa")){
                    startActivity(new Intent(MainActivity.this, profe.class));
                }
               else {
                    startActivity(new Intent(MainActivity.this, Est.class));
                }
            }

            @Override
            public void onFailure(Call<Validacion> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_LONG).show();


            }

        });

    }
});



/*        Call<List<Estudiante>> call = retrofitApi.getEstudiantes();

        call.enqueue(new Callback<List<Estudiante>>() {
            @Override
            public void onResponse(Call<List<Estudiante>> call, Response<List<Estudiante>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Estudiante> estudiantes = response.body();

                for (Estudiante estudiante : estudiantes){
                    String content = "";
                    content += "Nombre: " + estudiante.getNombre() + " " + estudiante.getApellido() + "\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Estudiante>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });*/

    }




}