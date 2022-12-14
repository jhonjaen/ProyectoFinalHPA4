package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofinal.api.ServiceApi;
import com.example.proyectofinal.entity.Datauser;
import com.example.proyectofinal.entity.MatriculaR;
import com.example.proyectofinal.entity.Matriculaenv;
import com.example.proyectofinal.entity.ResultadoR;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class matricula extends AppCompatActivity {
Button btn2;
EditText idest,matid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);
        matriculainicio();
    }

    public void matriculainicio(){
        idest=(EditText)findViewById(R.id.idest);
        matid=(EditText) findViewById(R.id.idmat);
        btn2=(Button) findViewById(R.id.enviarmatricula);


 btn2.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
        int id=Integer.parseInt(idest.getText().toString());
        int mid=Integer.parseInt(matid.getText().toString());
        enviarmat(id,mid);
        idest.setText("");
        matid.setText("");

    }
});
        }

private void enviarmat( int id,  int mid) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://asistencia-upn43.ondigitalocean.app/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
        ServiceApi serviceApi = retrofit.create(ServiceApi.class);

       Matriculaenv matriculaenv = new Matriculaenv(id,mid);
        Call<MatriculaR> call = serviceApi.matricular(matriculaenv);

        call.enqueue(new Callback<MatriculaR>() {
            @Override
            public void onResponse(Call<MatriculaR> call, Response<MatriculaR> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(matricula.this,"ya existe",Toast.LENGTH_LONG).show();
                    return;
                }

                MatriculaR matriculaR = response.body();

                String content = "";
                content += "Title: " + matriculaR.getEstudianteId() + "\n\n";
                Toast.makeText(matricula.this, "Matriculado "+content, Toast.LENGTH_LONG).show();


        }
            @Override
            public void onFailure(Call<MatriculaR> call, Throwable t) {

            }
        });

}
}