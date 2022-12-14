package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {
    Button ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        this.inicio();
    }


    public void inicio() {
        ed=(Button) findViewById(R.id.ed);
    }

    public void nnfc(View view){

        startActivity(new Intent(this, cambium.class));


    }
    public void regist(View v){ startActivity(new Intent(this, registro.class));}

    public void listup(View v){startActivity(new Intent(this, listaupdate.class));}

    public void Delete(View v){startActivity(new Intent(this, Borrar.class));}

    public void matricula(View v){startActivity(new Intent(this, matricula.class));}

    public void salir(View v){startActivity(new Intent(this, MainActivity.class));}
}