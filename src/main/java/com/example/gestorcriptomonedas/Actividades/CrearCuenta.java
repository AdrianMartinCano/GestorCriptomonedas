package com.example.gestorcriptomonedas.Actividades;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestorcriptomonedas.R;
import com.example.gestorcriptomonedas.bbdd.BaseDeDatos;

public class CrearCuenta extends AppCompatActivity {

private Button botonAceptar;
private Button botonCancelar;
private BaseDeDatos bbdd;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_cuenta); // Reemplaza "activity_nueva_actividad" con el nombre de tu layout XML

        botonAceptar = findViewById(R.id.buttonAceptar);
        botonCancelar= findViewById(R.id.buttonCancelar);
        bbdd = BaseDeDatos.getInstance(this);
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = ((EditText) findViewById(R.id.editTextNombre)).getText().toString();
                String usuario = ((EditText) findViewById(R.id.editTextUsuario)).getText().toString();
                String password = ((EditText) findViewById(R.id.editTextContraseña)).getText().toString();
                double saldo = Double.parseDouble(((EditText) findViewById(R.id.editTextSaldo)).getText().toString());
                bbdd.agregarUsuario(nombre, usuario, password, saldo);
                Toast.makeText(CrearCuenta.this, "Se ha creado la cuenta correctamente", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}

