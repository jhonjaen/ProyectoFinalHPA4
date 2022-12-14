package com.example.proyectofinal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.proyectofinal.api.ServiceApi;
import com.example.proyectofinal.entity.Datauser;
import com.example.proyectofinal.entity.ResultadoR;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class registro extends AppCompatActivity {
EditText nombre, cedula, apellido, contrasena,correo;

Button btn;

    private OkHttpClient.Builder httpClientBuilder;
    private TextView textViewResult;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.ik();


    }
    public void ik(){
    nombre=(EditText)findViewById(R.id.nomnw);
    cedula=(EditText)findViewById(R.id.cednw);
    apellido=(EditText)findViewById(R.id.apenw);
    contrasena=(EditText)findViewById(R.id.passnw);
    correo=(EditText)findViewById(R.id.corrnw);
    btn=(Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               enviarWs(nombre.getText().toString(),apellido.getText().toString(),cedula.getText().toString(),correo.getText().toString(),contrasena.getText().toString());
                nombre.setText("");
                apellido.setText("");
                cedula.setText("");
                correo.setText("");
                contrasena.setText("");
            }
        });
    }

    private void enviarWs( String nombre,  String apellido,  String cedula, String correo, String contrasena) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://asistencia-upn43.ondigitalocean.app/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);

        Datauser datauser = new Datauser(cedula,apellido,correo,contrasena, nombre);
        Call<ResultadoR> call = serviceApi.registrar(datauser);

        call.enqueue(new Callback<ResultadoR>() {
            @Override
            public void onResponse(Call<ResultadoR> call, Response<ResultadoR> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                ResultadoR resultadoR = response.body();

                String content = "";
                content += "Title: " + resultadoR.getTitle() + "\n\n";
                Toast.makeText(registro.this, ""+content, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ResultadoR> call, Throwable t) {
                Toast.makeText(registro.this, "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }




}