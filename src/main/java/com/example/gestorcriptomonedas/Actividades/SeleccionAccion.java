package com.example.gestorcriptomonedas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestorcriptomonedas.R;

public class SeleccionAccion extends AppCompatActivity {

    private TextView usuario;
    private Button buttonCripto;
    private Button buttonMonedero;
    private Button buttonAtras;
    private Button buttonVender;
    private Intent intent;
    private String contraseña;
    private String Stringusuario;
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccionaccion);

        intent=getIntent();
        if (intent!=null && intent.hasExtra("NOMBRE_USUARIO") && intent.hasExtra("CONTRASEÑA") ){
            usuario= findViewById(R.id.textViewNombreUsuario);
            usuario.setText("Bienvenido," + intent.getStringExtra("NOMBRE_USUARIO").toUpperCase());
            contraseña=intent.getStringExtra("CONTRASEÑA");
            Stringusuario=intent.getStringExtra("NOMBRE_USUARIO");
        }

        buttonCripto = findViewById(R.id.botonVerCriptomonedas);
        buttonMonedero = findViewById(R.id.botonVerMonedero);
        buttonAtras = findViewById(R.id.botonAtras);
        buttonVender = findViewById(R.id.botonVenderCriptomonedas);
        imagen = findViewById(R.id.imagen);
        imagen.setImageResource(R.drawable.logo);


        buttonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intent = new Intent(SeleccionAccion.this, VenderActivity.class);
                intent.putExtra("NOMBRE_USUARIO", Stringusuario);
                intent.putExtra("CONTRASEÑA", contraseña);
               startActivity(intent);
            }
        });

        buttonCripto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SeleccionAccion.this, CriptomonedasActivity.class);
                intent.putExtra("NOMBRE_USUARIO", Stringusuario);
                intent.putExtra("CONTRASEÑA", contraseña);
                startActivity(intent);
            }
        });

        buttonMonedero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SeleccionAccion.this, MonederoActivity.class);
                startActivity(intent);
            }
        });


    }

}
