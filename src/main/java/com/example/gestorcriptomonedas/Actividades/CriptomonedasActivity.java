package com.example.gestorcriptomonedas.Actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestorcriptomonedas.CONSTANTES.TipoOperacion;
import com.example.gestorcriptomonedas.Entidades.Transacciones;
import com.example.gestorcriptomonedas.R;
import com.example.gestorcriptomonedas.bbdd.BaseDeDatos;
import com.example.gestorcriptomonedas.layoutCriptomonedas.CriptomonedaAdaptador;
import com.example.gestorcriptomonedas.Entidades.Criptomonedas;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CriptomonedasActivity extends AppCompatActivity {

    private ListView listViewCriptomonedas;
    private CriptomonedaAdaptador criptomonedaAdaptador;
    private List<Criptomonedas> criptomonedasList;
    private BaseDeDatos bbdd;
    private Button botonAtras;
    private static int ancho = 100;
    private static int alto = 100;
    private Intent intent;
    private String usuario;
    private String contraseña;
    private static int anchoPopUp=1000;
    private static int altoPopup=1000;
    private String nombreCriptomoneda;
    private Double precioCriptomoneda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criptomonedas_activity);

        bbdd = BaseDeDatos.getInstance(this);
        criptomonedasList = bbdd.getCriptomonedas();
        criptomonedaAdaptador = new CriptomonedaAdaptador(this, criptomonedasList);
        intent=getIntent();
        if(intent!=null && intent.hasExtra("NOMBRE_USUARIO") && intent.hasExtra("CONTRASEÑA")){
            usuario=intent.getStringExtra("NOMBRE_USUARIO");
            contraseña=intent.getStringExtra("CONTRASEÑA");
        }

        listViewCriptomonedas = findViewById(R.id.listViewCrypto);
        listViewCriptomonedas.setAdapter(criptomonedaAdaptador);
        listViewCriptomonedas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewCriptomonedas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nombreCriptomoneda = criptomonedasList.get(position).getNombre();
                precioCriptomoneda = criptomonedasList.get(position).getPrecio();
                byte[] imagenBytes = bbdd.getImagenCriptomoneda(nombreCriptomoneda);
                if (imagenBytes != null) {
                    Bitmap bitmapRedimensionado = redimensionarImagen(imagenBytes, ancho, alto);
                    //https://chat.openai.com/share/6884d78b-7811-4d12-be1b-6901053e0513
                    mostrarPopup(imagenBytes);
                }
            }
        });

        botonAtras = findViewById(R.id.botonAtras);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mostrarPopup(byte[] imagenBytes) {
        PopupWindow popupWindow = new PopupWindow(CriptomonedasActivity.this);
        View popupView = getLayoutInflater().inflate(R.layout.pop_up_layout, null);
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(anchoPopUp);
        popupWindow.setHeight(altoPopup);
        popupWindow.setFocusable(true);
        ImageView imageViewPopup = popupView.findViewById(R.id.imageViewPopup);

        Button botonAceptarPopUp = popupView.findViewById(R.id.botonAceptarPopUp);
        Button botonCancelarPopUp = popupView.findViewById(R.id.botonCancelarPopUp);
        EditText numeroCriptosEditText = popupView.findViewById(R.id.numeroCriptosEditTextPopUp);
        botonAceptarPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manejar el evento del botón "Aceptar" aquí

                bbdd=BaseDeDatos.getInstance(CriptomonedasActivity.this);
                //Ahora falta añadir la transacción a la tabla
                //Tenemos que sacar el ID del usuario;
                int id_usuario= bbdd.idUsuario(usuario, contraseña);
                int id_cripto= bbdd.idCriptomoneda(nombreCriptomoneda);
                Date fecha = Date.valueOf(LocalDate.now().toString());

                //Creamos el objeto de la transaccion
                Transacciones t = new Transacciones(id_usuario, id_cripto, TipoOperacion.COMPRA,
                    Integer.parseInt(numeroCriptosEditText.getText().toString()), precioCriptomoneda, fecha );
//llamamos al método
                if(bbdd.crearTransaccion(t)){
                    Toast.makeText(CriptomonedasActivity.this, "Compra realizada correctamente", Toast.LENGTH_LONG ).show();
                }else {
                    Toast.makeText(CriptomonedasActivity.this, "Ha ocurrido algún error", Toast.LENGTH_LONG ).show();
                }

                popupWindow.dismiss(); // Cerrar el PopupWindow después de realizar las acciones
            }
        });

        botonCancelarPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Popup", "Cancelar");
                popupWindow.dismiss(); // Cerrar el PopupWindow al hacer clic en Cancelar
            }
        });
        if (imagenBytes != null) {
            Bitmap bitmapRedimensionado = redimensionarImagen(imagenBytes, ancho, alto);
            imageViewPopup.setImageBitmap(bitmapRedimensionado);
        }
        // Muestra el PopupWindow en una ubicación específica
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private Bitmap redimensionarImagen(byte[] imagenBytes, int nuevoAncho, int nuevoAlto) {
        if (imagenBytes != null && imagenBytes.length > 0) {
            Bitmap bitmapOriginal = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
            if (bitmapOriginal != null) {
                int originalAncho = bitmapOriginal.getWidth();
                int originalAlto = bitmapOriginal.getHeight();

                // Calcula la proporción para mantener la relación de aspecto
                float proporcion = (float) nuevoAncho / originalAncho;

                // Redimensiona la imagen
                return Bitmap.createScaledBitmap(bitmapOriginal, nuevoAncho, Math.round(originalAlto * proporcion), true);
            }
        }
        return null; // Retorna null si hay algún problema con la imagen
    }
}
