package com.example.gestorcriptomonedas.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.gestorcriptomonedas.CONSTANTES.TipoOperacion;
import com.example.gestorcriptomonedas.Entidades.Criptomonedas;
import com.example.gestorcriptomonedas.Entidades.Transacciones;
import com.example.gestorcriptomonedas.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class BaseDeDatos extends SQLiteOpenHelper {


    private static final String NOMBRE_BBDD = "gestor";
    private static final int VERSION_BB_DD = 1;

    //Tabla criptomonedas
    private static final String TABLA_CRIPTO = "criptomonedas";
    private static final String ID_CRIPTO = "id_cripto";
    private static final String NOMBRE_CRIPTO = "nombre_cripto";
    private static final String PRECIO_CRIPTO = "precio_cripto";
    private static final String IMAGEN_CRIPTO = "imagen";

    //Tabla usuarios
    private static String TABLA_USUARIOS = "usuarios";
    private static String ID_USUARIO = "id_usuario";
    private static String NOMBRE_USUARIO = "nombre";
    private static String USUARIO_LOGIN = "usuario";
    private static String PASSWORD = "contraseña";
    private static String SALDO_USUARIO = "saldo";


    //Tabla transacciones
    private static String TABLA_TRANSACCIONES = "transacciones";
    private static String ID_TRANSACCION = "id_transaccion";
    private static String ID_USUARIO_T = "idusuario";
    private static String ID_CRIPTOMONEDA_T = "idcripto";
    private static String TIPOOPERACION = "tipo"; //Compra o venta
    private static String CANTIDAD = "cantidad";
    private static String PRECIOUNITARIO = "preciounitario";
    private static String FECHACOMRA = "fecha";

//Aplicamos el patrón de diseño Singleton
    private static BaseDeDatos instance;

    private Context context;
    private BaseDeDatos(Context context) {
        super(context, NOMBRE_BBDD, null, VERSION_BB_DD);
        this.context=context;
    }

    public static synchronized BaseDeDatos getInstance(Context context) {
        if (instance == null) {
            instance = new BaseDeDatos(context.getApplicationContext());
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String quearyCrearTablaCripto="CREATE TABLE " + TABLA_CRIPTO
                + "(" + ID_CRIPTO + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                NOMBRE_CRIPTO + " TEXT," + PRECIO_CRIPTO + " DOUBLE," +
                IMAGEN_CRIPTO + " BLOB)";
        db.execSQL(quearyCrearTablaCripto);

        String quearyCrearTablaUsuarios="CREATE TABLE " + TABLA_USUARIOS
                + "(" + ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                NOMBRE_USUARIO + " TEXT," + USUARIO_LOGIN + " TEXT," +
                PASSWORD + " TEXT," + SALDO_USUARIO + " DOUBLE)";
        db.execSQL(quearyCrearTablaUsuarios);

        String quearyCrearTablaTransacciones="CREATE TABLE " + TABLA_TRANSACCIONES
                + "("+ ID_TRANSACCION + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ID_USUARIO_T + " INTEGER," + ID_CRIPTOMONEDA_T + " INTEGER,"
                + TIPOOPERACION + " TEXT," + CANTIDAD + " DOUBLE," +
                PRECIOUNITARIO + " DOUBLE," + FECHACOMRA + " DATE,"+
                "FOREIGN KEY(" + ID_USUARIO_T + ") REFERENCES " +TABLA_USUARIOS+" ("
                + ID_USUARIO+"),"+
                "FOREIGN KEY(" + ID_CRIPTOMONEDA_T + ") REFERENCES " +TABLA_CRIPTO+" ("
                + ID_CRIPTO+"));";
        db.execSQL(quearyCrearTablaTransacciones);
        //insertarUsuario(db, "admin", "admin", "admin", 10000.0);
        Random ran = new Random();

     //Precios randoms entre 100 y 1000 por defecto
        insertarCriptomonedas(db, "Bitcoin", Double.valueOf(ran.nextInt(901) + 100), convertirDrawableToBytes(context, R.drawable.bitcoin));
        insertarCriptomonedas(db,"Ethereum ", Double.valueOf(ran.nextInt(901) + 100), convertirDrawableToBytes(context, R.drawable.ethereum));
        insertarCriptomonedas(db,"Cardano",Double.valueOf(ran.nextInt(901) + 100), convertirDrawableToBytes(context, R.drawable.cardano));
        insertarCriptomonedas(db, "Tether", Double.valueOf(ran.nextInt(901) + 100), convertirDrawableToBytes(context, R.drawable.tether));
        insertarCriptomonedas(db,"Dogecoin", Double.valueOf(ran.nextInt(901) + 100), convertirDrawableToBytes(context, R.drawable.dogecoin));

    }



    private byte[] convertirDrawableToBytes(Context context, int drawableId) {
        Resources resources = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void insertarCriptomonedas(SQLiteDatabase db, String nombre, double Precio, byte[] imagen){
        ContentValues valores = new ContentValues();
        valores.put(NOMBRE_CRIPTO, nombre);
        valores.put(PRECIO_CRIPTO, Precio);
        valores.put(IMAGEN_CRIPTO, imagen);
        db.insert(TABLA_CRIPTO, null, valores);
    }

    private void insertarUsuario(SQLiteDatabase db, String nombre, String usuario, String password, double saldo){
        ContentValues values = new ContentValues();
        values.put(NOMBRE_USUARIO, nombre);
        values.put(USUARIO_LOGIN, usuario);
        values.put(PASSWORD, password);
        values.put(SALDO_USUARIO, saldo);
        db.insert(TABLA_USUARIOS, null, values);
    }

    public List<Criptomonedas> getCriptomonedas() {
        List<Criptomonedas> lista = new ArrayList<Criptomonedas>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String consulta = "SELECT * FROM " + TABLA_CRIPTO;
            cursor = db.rawQuery(consulta, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Criptomonedas c = new Criptomonedas(
                            cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_CRIPTO)),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(PRECIO_CRIPTO))
                    );
                    byte[] imagenBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(IMAGEN_CRIPTO));
                    c.setImagen(imagenBytes);
                    lista.add(c);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // Cerrar la base de datos solo si no está cerrada ya
            if (db.isOpen()) {
                db.close();
            }
        }
        return lista;
    }
    public boolean crearTransaccion(Transacciones transacciones){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ID_USUARIO_T, transacciones.getId_usuario());
        valores.put(ID_CRIPTOMONEDA_T, transacciones.getId_criptomoneda());
        valores.put(TIPOOPERACION, transacciones.getTipoOperacion().toString());
        valores.put(CANTIDAD, transacciones.getCantidad());
        valores.put(PRECIOUNITARIO, transacciones.getPrecio_unitario());
        valores.put(FECHACOMRA, transacciones.getFecha().getTime());
        long resultado = db.insert(TABLA_TRANSACCIONES, null, valores);
        if(resultado!=-1){
            //Ha ido bien
            return  true;
        }
        //Si no se insertó:
            return false;
    }

    public boolean comprobarCredenciales(String usuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String consulta = "SELECT * FROM " + TABLA_USUARIOS +
                    " WHERE " + USUARIO_LOGIN + " = ? AND " +
                    PASSWORD + " = ?";
            cursor = db.rawQuery(consulta, new String[]{usuario, password});
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }



//nombre, usuario,  contraseña, saldo
public void agregarUsuario(String nombre, String usuario, String password, double saldo) {
    SQLiteDatabase db = this.getWritableDatabase(); // Obtener instancia de la base de datos para escritura
    ContentValues valores = new ContentValues();
    valores.put(NOMBRE_USUARIO, nombre);
    valores.put(USUARIO_LOGIN, usuario);
    valores.put(PASSWORD, password);
    valores.put(SALDO_USUARIO, saldo);
    db.insert(TABLA_USUARIOS, null, valores);
    db.close();
}


    public byte[] getImagenCriptomoneda(int idCriptomoneda) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String consulta = "SELECT " + IMAGEN_CRIPTO + " FROM " + TABLA_CRIPTO +
                    " WHERE " + ID_CRIPTO + " = ?";
            cursor = db.rawQuery(consulta, new String[]{idCriptomoneda+""});

            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getBlob(cursor.getColumnIndexOrThrow(IMAGEN_CRIPTO));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db.isOpen()) {
                db.close();
            }
        }

        return null;
    }

    public byte[] getImagenCriptomoneda(String nombreCriptomoneda) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String consulta = "SELECT " + IMAGEN_CRIPTO + " FROM " + TABLA_CRIPTO +
                    " WHERE " + NOMBRE_CRIPTO + " = ?";
            cursor = db.rawQuery(consulta, new String[]{nombreCriptomoneda});

            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getBlob(cursor.getColumnIndexOrThrow(IMAGEN_CRIPTO));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db.isOpen()) {
                db.close();
            }
        }

        return null;
    }



    public int idUsuario(String usuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int idUsuario = -1; // Valor por defecto si no se encuentra el usuario

        try {
            String consulta = "SELECT " + ID_USUARIO + " FROM " + TABLA_USUARIOS +
                    " WHERE " + USUARIO_LOGIN + " = ? AND " +
                    PASSWORD + " = ?";
            cursor = db.rawQuery(consulta, new String[]{usuario, password});

            if (cursor.moveToFirst()) {
                idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow(ID_USUARIO));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return idUsuario;
    }


    public int idCriptomoneda(String nombreCriptomoneda) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int idCripto = -1; // Valor por defecto si no se encuentra el usuario

        try {
            String consulta = "SELECT " + ID_CRIPTO + " FROM " + TABLA_CRIPTO +
                    " WHERE " + NOMBRE_CRIPTO + " = ?";
            cursor = db.rawQuery(consulta, new String[]{nombreCriptomoneda});

            if (cursor.moveToFirst()) {
                idCripto = cursor.getInt(cursor.getColumnIndexOrThrow(ID_CRIPTO));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if(db.isOpen()){
            db.close();
            }
        }

        return idCripto;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

 public List<Transacciones> getListaVenta(int idUsuario) {
     List<Transacciones> lista = new ArrayList<>();
     SQLiteDatabase db = this.getReadableDatabase();
     Cursor cursor = null;

     try {
         String consulta = "SELECT * FROM " + TABLA_TRANSACCIONES + " WHERE " + ID_USUARIO_T + " = ? AND " + TIPOOPERACION + " = ?";
         String tipoOperacion = TipoOperacion.COMPRA.toString();
         cursor = db.rawQuery(consulta, new String[]{String.valueOf(idUsuario), tipoOperacion});

         if (cursor != null && cursor.moveToFirst()) {
             do {
                 Transacciones transaccion = new Transacciones(
                         cursor.getInt(cursor.getColumnIndexOrThrow(ID_TRANSACCION)),
                         cursor.getInt(cursor.getColumnIndexOrThrow(ID_USUARIO_T)),
                         cursor.getInt(cursor.getColumnIndexOrThrow(ID_CRIPTOMONEDA_T)),
                         TipoOperacion.COMPRA,
                         cursor.getInt(cursor.getColumnIndexOrThrow(CANTIDAD)),
                         cursor.getDouble(cursor.getColumnIndexOrThrow(PRECIOUNITARIO)),
                         new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FECHACOMRA)))
                 );
                 lista.add(transaccion);
             } while (cursor.moveToNext());
         }
     } finally {
         if (cursor != null) {
             cursor.close();
         }
         if (db.isOpen()) {
             db.close();
         }
     }
     return lista;
 }
    public boolean deleteTransaccion(Transacciones transacciones) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String whereClause  = ID_TRANSACCION + " = ?";

            String[] whereArgs = {String.valueOf(transacciones.getId())};
            Log.d("base de datossssss", "ID: " + String.valueOf(transacciones.getId()));
            int filasAfectadas = db.delete(TABLA_TRANSACCIONES, whereClause, whereArgs);
            if(filasAfectadas>0)
                return true;
            return false;

        } finally {

            if (db.isOpen()) {
                db.close();
            }
        }

    }

    public List<Transacciones> getTransacciones() {
        List<Transacciones> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String consulta = "SELECT * FROM " + TABLA_TRANSACCIONES;
            cursor = db.rawQuery(consulta, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Transacciones transaccion = new Transacciones(
                            cursor.getInt(cursor.getColumnIndexOrThrow(ID_TRANSACCION)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(ID_USUARIO_T)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(ID_CRIPTOMONEDA_T)),
                            TipoOperacion.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(TIPOOPERACION))),
                            cursor.getInt(cursor.getColumnIndexOrThrow(CANTIDAD)),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(PRECIOUNITARIO)),
                            new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FECHACOMRA)))
                    );
                    lista.add(transaccion);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db.isOpen()) {
                db.close();
            }
        }

        return lista;
    }

}