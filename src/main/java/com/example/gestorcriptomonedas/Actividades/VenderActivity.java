package com.example.gestorcriptomonedas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestorcriptomonedas.CONSTANTES.TipoOperacion;
import com.example.gestorcriptomonedas.Entidades.Transacciones;
import com.example.gestorcriptomonedas.R;
import com.example.gestorcriptomonedas.bbdd.BaseDeDatos;
import com.example.gestorcriptomonedas.layaoutTransacciones.TransaccionesAdaptador;

import java.util.List;


public class VenderActivity extends AppCompatActivity {

    private ListView listviewTransacciones;
    private Button buttonAtras;
    private BaseDeDatos bbdd;
    private List<Transacciones> transaccionesList;
    private TransaccionesAdaptador transaccionesAdaptador;
    private Intent intent;
    private String usuario;
    private String contraseña;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monedero_activity);
        listviewTransacciones = findViewById(R.id.ListView);
        buttonAtras= findViewById(R.id.botonAtras);
        intent=getIntent();
        if(intent!=null && intent.hasExtra("NOMBRE_USUARIO") && intent.hasExtra("CONTRASEÑA")){
            usuario=intent.getStringExtra("NOMBRE_USUARIO");
            contraseña=intent.getStringExtra("CONTRASEÑA");
        }
        buttonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bbdd = BaseDeDatos.getInstance(this);
        //Primero hay que sacar el id del usuario
        int idUsuario = bbdd.idUsuario(usuario, contraseña);
        //Llamamos al método que devuelve la lista de acciones de ese usuario
        transaccionesList = bbdd.getListaVenta(idUsuario);
        transaccionesAdaptador = new TransaccionesAdaptador(this, transaccionesList);
        listviewTransacciones.setAdapter(transaccionesAdaptador);
        listviewTransacciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listviewTransacciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Transacciones transaccionVenta = new Transacciones(
                        transaccionesList.get(position).getId(),
                        transaccionesList.get(position).getId_usuario(),
                        transaccionesList.get(position).getId_criptomoneda(),
                        TipoOperacion.VENTA,
                        transaccionesList.get(position).getCantidad(),
                        transaccionesList.get(position).getPrecio_unitario(),
                        transaccionesList.get(position).getFecha()
                );

                Transacciones transaccionCompra = new Transacciones(
                        transaccionesList.get(position).getId(),
                        transaccionesList.get(position).getId_usuario(),
                        transaccionesList.get(position).getId_criptomoneda(),
                        TipoOperacion.COMPRA,
                        transaccionesList.get(position).getCantidad(),
                        transaccionesList.get(position).getPrecio_unitario(),
                        transaccionesList.get(position).getFecha()
                );
                //Faltaría ahora borrar la transaccion de compra

                if(bbdd.crearTransaccion(transaccionVenta)){
                    if(bbdd.deleteTransaccion(transaccionCompra)){
                        Toast.makeText(VenderActivity.this, "Venta realizada correctamente", Toast.LENGTH_LONG ).show();
                    }
                }else {
                    Toast.makeText(VenderActivity.this, "Ha ocurrido algún error", Toast.LENGTH_LONG ).show();
                }



            }
        });
    }
}
