package com.example.gestorcriptomonedas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestorcriptomonedas.Entidades.Transacciones;
import com.example.gestorcriptomonedas.R;
import com.example.gestorcriptomonedas.bbdd.BaseDeDatos;
import com.example.gestorcriptomonedas.layaoutTransacciones.TransaccionesAdaptador;

import java.util.List;

public class MonederoActivity  extends AppCompatActivity {

    private BaseDeDatos bbdd;
    private TransaccionesAdaptador transaccionesAdaptador;
    private List<Transacciones> transaccionesList;
    private Intent intent;
    private String usuario;
    private String contraseña;
    private ListView listviewTransacciones;

    private Button buttonAtras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monedero_activity);
        listviewTransacciones = findViewById(R.id.ListView);

        buttonAtras= findViewById(R.id.botonAtras);

        buttonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bbdd = BaseDeDatos.getInstance(this);
        transaccionesList = bbdd.getTransacciones();
        transaccionesAdaptador = new TransaccionesAdaptador(this, transaccionesList);
        listviewTransacciones.setAdapter(transaccionesAdaptador);
        intent=getIntent();
        listviewTransacciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if(intent!=null && intent.hasExtra("NOMBRE_USUARIO") && intent.hasExtra("CONTRASEÑA")){
            usuario=intent.getStringExtra("NOMBRE_USUARIO");
            contraseña=intent.getStringExtra("CONTRASEÑA");
        }

    }
}
