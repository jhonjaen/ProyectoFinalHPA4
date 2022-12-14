package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class profe extends AppCompatActivity {

    private Button btnpasar, btnver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profe);

        btnpasar = (Button) findViewById(R.id.btnpasarlista);
        btnver = (Button) findViewById(R.id.btnverlista);
        btnpasar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (profe.this, PasarLista.class);
                startActivity(i);
                Toast.makeText(profe.this, "aqui", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void Verlista(View v)
    {
        Intent i = new Intent (this, ListaAsistencia.class);
        startActivity(i);

    }
}