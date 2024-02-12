package com.example.gestorcriptomonedas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestorcriptomonedas.R;
import com.example.gestorcriptomonedas.bbdd.BaseDeDatos;

public class MainActivity extends AppCompatActivity {

    private BaseDeDatos bbdd;


   private EditText editTextPassword ;
    private EditText editTextUsuario;
    private Button boton;

    private Button botonCrearCuenta;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bbdd = BaseDeDatos.getInstance(this);

        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        botonCrearCuenta = findViewById(R.id.buttonCrearCuenta);

        botonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, CrearCuenta.class);
                startActivity(intent);
            }
        });




        boton = findViewById(R.id.buttonIniciarSesion);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bbdd.comprobarCredenciales(editTextUsuario.getText().toString(),editTextPassword.getText().toString() )){
                    intent = new Intent(MainActivity.this, SeleccionAccion.class);
                    intent.putExtra("NOMBRE_USUARIO", editTextUsuario.getText().toString());
                    intent.putExtra("CONTRASEÑA", editTextPassword.getText().toString());
                    startActivity(intent);
                }else {
                Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecto" , Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Limpiar los campos al reanudar la actividad
        editTextUsuario.setText("");
        editTextPassword.setText("");
    }
}