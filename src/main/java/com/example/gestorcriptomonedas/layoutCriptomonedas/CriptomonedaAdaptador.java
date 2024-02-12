package com.example.gestorcriptomonedas.layoutCriptomonedas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gestorcriptomonedas.Entidades.Criptomonedas;
import com.example.gestorcriptomonedas.R;

import java.util.List;

public class CriptomonedaAdaptador extends ArrayAdapter<Criptomonedas> {

    private static int alto = 50;
    private static int ancho = 50;
    private Context context;
    private List<Criptomonedas> criptomonedasList;
    private int selectedPosition = -1; // Posición seleccionada

    public CriptomonedaAdaptador(Context context, List<Criptomonedas> criptomonedasList) {
        super(context, R.layout.list_item_cripto, criptomonedasList);
        this.context = context;
        this.criptomonedasList = criptomonedasList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_cripto, null);
        }
        Criptomonedas criptomoneda = criptomonedasList.get(position);
        if (criptomoneda != null) {
            TextView textViewNombre = view.findViewById(R.id.textViewNombre);
            TextView textViewPrecio = view.findViewById(R.id.textViewPrecio);
            ImageView imagenView = view.findViewById(R.id.image);

            textViewNombre.setText(criptomoneda.getNombre());
            textViewPrecio.setText(String.valueOf(criptomoneda.getPrecio()));

            byte[] imagenBytes = criptomoneda.getImagen();
            if (imagenBytes != null) {
                Bitmap bitmapRedimensionado = redimensionarImagen(imagenBytes, ancho, alto);
                imagenView.setImageBitmap(bitmapRedimensionado);
            }

            // Resaltar el elemento seleccionado
            if (position == selectedPosition) {
                view.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
            } else {
                view.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            }
        }
        return view;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    public Criptomonedas getSelectedCriptomoneda() {
        if (selectedPosition != -1 && selectedPosition < criptomonedasList.size()) {
            return criptomonedasList.get(selectedPosition);
        }
        return null;
    }

    private Bitmap redimensionarImagen(byte[] imagenBytes, int nuevoAncho, int nuevoAlto) {
        if (imagenBytes != null && imagenBytes.length > 0) {
            Bitmap bitmapOriginal = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
            if (bitmapOriginal != null) {
                return Bitmap.createScaledBitmap(bitmapOriginal, nuevoAncho, nuevoAlto, true);
            }
        }
        return null; // Retorna null si hay algún problema con la imagen
    }
}
