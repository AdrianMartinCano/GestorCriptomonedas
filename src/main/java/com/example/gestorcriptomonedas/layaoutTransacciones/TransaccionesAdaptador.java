package com.example.gestorcriptomonedas.layaoutTransacciones;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gestorcriptomonedas.CONSTANTES.TipoOperacion;
import com.example.gestorcriptomonedas.Entidades.Transacciones;
import com.example.gestorcriptomonedas.R;
import com.example.gestorcriptomonedas.bbdd.BaseDeDatos;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TransaccionesAdaptador extends ArrayAdapter<Transacciones> {

    private Context context;
    private List<Transacciones> transaccionesList;
    private int selectedPosition = -1; // Posición seleccionada
    private static int ancho = 100;
    private static int alto = 100;
    private BaseDeDatos bbdd;

    public TransaccionesAdaptador(Context context, List<Transacciones> transaccionesList) {
        super(context, R.layout.list_item_transaccion, transaccionesList);
        this.context = context;
        this.transaccionesList = transaccionesList;
        bbdd=BaseDeDatos.getInstance(this.getContext());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_transaccion, null);
        }

        Transacciones transaccion = transaccionesList.get(position);
        if (transaccion != null) {

        ImageView ImageViewImagen = view.findViewById(R.id.imageViewCriptomoneda);
        TextView TextViewTipo = view.findViewById(R.id.textViewTipo);
        TextView textViewCantidad = view.findViewById(R.id.textViewCantidad);
        TextView textViewPrecio = view.findViewById(R.id.textViewPrecio);
        TextView textViewFecha = view.findViewById(R.id.textViewFecha);



        //Mostramos los campos:
            TextViewTipo.setText(Html.fromHtml("<b>Tipo:</b> " + transaccion.getTipoOperacion().toString()));
            textViewCantidad.setText(Html.fromHtml("<b>Cantidad:</b> " + String.valueOf(transaccion.getCantidad())));
            textViewPrecio.setText(Html.fromHtml("<b>Precio:</b> " + transaccion.getPrecio_unitario()));
            Date fechaTransaccion = transaccion.getFecha();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            textViewFecha.setText(Html.fromHtml("<b>Fecha:</b> " + formatoFecha.format(fechaTransaccion)));

            String tipoOperacion = transaccion.getTipoOperacion().toString();
            SpannableString spannableString = new SpannableString(tipoOperacion);

            if ("Compra".equalsIgnoreCase(tipoOperacion)) {
                spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(android.R.color.holo_green_light)),
                        0, tipoOperacion.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if ("Venta".equalsIgnoreCase(tipoOperacion)) {
                spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(android.R.color.holo_red_light)),
                        0, tipoOperacion.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            TextViewTipo.setText(spannableString);
            byte[] imagenBytes = bbdd.getImagenCriptomoneda(transaccion.getId_criptomoneda());
            //Falta redimensionar la imagen y montarla
            if (imagenBytes != null) {
                Bitmap bitmapRedimensionado = redimensionarImagen(imagenBytes, ancho, alto);
                ImageViewImagen.setImageBitmap(bitmapRedimensionado);
            }


        }

        if (position == selectedPosition) {
            view.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
        } else {
            view.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }

        return view;
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
