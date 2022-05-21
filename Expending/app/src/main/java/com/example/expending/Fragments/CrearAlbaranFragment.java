package com.example.expending.Fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expending.AdminSQL;
import com.example.expending.Alimento;
import com.example.expending.Maquina;
import com.example.expending.R;
import com.example.expending.Usuario;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CrearAlbaranFragment extends Fragment
{
    Spinner spinner_usuario, spinner_maquina, spinner_alimento;
    EditText et_contador, et_dinero, et_fecha, et_estado, et_cantidad;
    Button btn_añadir;
    AdminSQL conexion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.crear_albaran_fragment, container, false);

        spinner_usuario = view.findViewById(R.id.spinner_usuario_alba);
        spinner_maquina = view.findViewById(R.id.spinner_maquina_alba);
        spinner_alimento = view.findViewById(R.id.spinner_alimentos);
        et_contador = view.findViewById(R.id.et_contador);
        et_dinero = view.findViewById(R.id.et_dinero);
        et_fecha = view.findViewById(R.id.et_fecha_creacion);
        et_estado = view.findViewById(R.id.et_estado_maquina);
        et_cantidad = view.findViewById(R.id.et_cantidad_alimento);
        btn_añadir = view.findViewById(R.id.btn_add_alba);

        conexion = new AdminSQL(getContext(), "expending", null, 5);

        String dia = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        et_fecha.setText(dia);

        //spinner maquinas
        List<Maquina> listaMaquinas = llenarSpinnerMaquinas();
        ArrayAdapter<Maquina> arrayAdapterMaquinas = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaMaquinas);
        spinner_maquina.setAdapter(arrayAdapterMaquinas);

        //spinner usuarios
        List<Usuario> listaUsuario = llenarSpinnerUsuarios();
        ArrayAdapter<Usuario> arrayAdapterUsuarios = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaUsuario);
        spinner_usuario.setAdapter(arrayAdapterUsuarios);

        //spinner alimentos
        List<Alimento> listaAlimento = llenarSpinnerAlimentos();
        ArrayAdapter<Alimento> arrayAdapterAlimentos = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaAlimento);
        spinner_alimento.setAdapter(arrayAdapterAlimentos);

        btn_añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = spinner_usuario.getSelectedItem().toString();
                String maquina = spinner_maquina.getSelectedItem().toString();
                String alimento = spinner_alimento.getSelectedItem().toString();

                //parseamos el id del usuario a integer
                String[] partsUsuario = usuario.split(",");
                String idU = partsUsuario[0];
                Integer idUsuario = Integer.valueOf(idU);

                //parseamos el id de la maquina a integer
                String[] partsMaquina = maquina.split(",");
                String idM = partsMaquina[0];
                Integer idMaquina = Integer.valueOf(idM);

                //parseamos el id del alimento a integer
                String[] partsAlimento = alimento.split(",");
                String idA = partsAlimento[0];
                Integer idAlimento = Integer.valueOf(idA);

                String contador = et_contador.getText().toString();
                double dinero = Double.parseDouble(et_dinero.getText().toString());
                String fecha = et_fecha.getText().toString();
                String estado = et_estado.getText().toString();
                Integer cantidad =  Integer.valueOf(et_cantidad.getText().toString());

                SQLiteDatabase BaseDeDatos = conexion.getWritableDatabase();
                SQLiteDatabase BDD = conexion.getReadableDatabase();
                ContentValues registro = new ContentValues();

                if (!contador.isEmpty() && dinero != 0 && !fecha.isEmpty() && !estado.isEmpty() && cantidad != 0){
                    if(fecha.contains("/")){
                        //Insertamos en albaranes
                        registro.put("estado_albaran", estado);
                        registro.put("fecha", fecha);
                        registro.put("dinero_recaudado", dinero);
                        registro.put("contador", contador);
                        registro.put("id_usuario", idUsuario);
                        registro.put("id_maquina", idMaquina);

                        BaseDeDatos.insert("albaranes", "id", registro);
                        //BaseDeDatos.close();

                        et_estado.setText("");
                        et_fecha.setText("");
                        et_dinero.setText("");
                        et_contador.setText("");
                        et_cantidad.setText("");

                        //ALMACENAMOS EL ID DEL ALBARÁN CREADO PARA HACER LA INSERCIÓN EN LA TABLA ALBARAN_ALIMENTO
                        Cursor fila = BDD.rawQuery("select id_albaran from albaranes where estado_albaran= '" + estado + "';", null);
                        int id_albaran = 0;
                        if (fila.moveToFirst()){
                            id_albaran = fila.getInt(0);
                        }
                        ContentValues registro2 = new ContentValues();
                        registro2.put("id_albaran", id_albaran);
                        registro2.put("id_alimento", idAlimento);
                        registro2.put("cantidad", cantidad);
                        BaseDeDatos.insert("albaran_alimento", "id_albaran_alimento", registro2);

                        //INSERCIÓN EN EXISTENCIA MAQUINA
                        ContentValues registro3 = new ContentValues();
                        registro3.put("cantidad", cantidad);
                        registro3.put("id_maquina", idMaquina);
                        registro3.put("id_alimento", idAlimento);
                        BaseDeDatos.insert("existencia_maquina", "id_existencia", registro3);

                        Toast.makeText(getContext(), "Albarán creado", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Fecha incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Rellena los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    //METODOS PARA MAQUINAS
    public Cursor mostrarMaquinas(){
        try{
            SQLiteDatabase bd = conexion.getWritableDatabase();
            Cursor filas = bd.rawQuery("select * from maquinas", null);
            if (filas.moveToFirst()){
                return filas;
            } else {
                return  null;
            }
        } catch (Exception e){
            return null;
        }
    }

    @SuppressLint("Range")
    private List<Maquina> llenarSpinnerMaquinas(){
        List<Maquina> listaMaquina = new ArrayList<>();
        Cursor c = mostrarMaquinas();

        if(c != null){
            if(c.moveToFirst()){
                do {
                    Maquina m = new Maquina();
                    m.setId(c.getInt(c.getColumnIndex("id_maquina")));
                    m.setNombre_empresa(c.getString(c.getColumnIndex("nombre_empresa")));
                    listaMaquina.add(m);
                } while (c.moveToNext());
            }
        }
        return listaMaquina;
    }

    //METODOS USUARIOS
    public Cursor mostrarUsuarios(){
        try{
            SQLiteDatabase bd = conexion.getWritableDatabase();
            Cursor filas = bd.rawQuery("select * from usuarios", null);
            if (filas.moveToFirst()){
                return filas;
            } else {
                return  null;
            }
        } catch (Exception e){
            return null;
        }
    }

    @SuppressLint("Range")
    private List<Usuario> llenarSpinnerUsuarios(){
        List<Usuario> listaUsuario = new ArrayList<>();
        Cursor c = mostrarUsuarios();

        if(c != null){
            if(c.moveToFirst()){
                do {
                    Usuario u = new Usuario();
                    u.setId(c.getInt(c.getColumnIndex("id_usuario")));
                    u.setNombre(c.getString(c.getColumnIndex("nombre")));
                    listaUsuario.add(u);
                } while (c.moveToNext());
            }
        }
        return listaUsuario;
    }
    //METODOS USUARIOS
    public Cursor mostrarAlimentos(){
        try{
            SQLiteDatabase bd = conexion.getWritableDatabase();
            Cursor filas = bd.rawQuery("select * from alimentos", null);
            if (filas.moveToFirst()){
                return filas;
            } else {
                return  null;
            }
        } catch (Exception e){
            return null;
        }
    }

    @SuppressLint("Range")
    private List<Alimento> llenarSpinnerAlimentos(){
        List<Alimento> listaAlimento = new ArrayList<>();
        Cursor c = mostrarAlimentos();

        if(c != null){
            if(c.moveToFirst()){
                do {
                    Alimento a = new Alimento();
                    a.setId(c.getInt(c.getColumnIndex("id_alimento")));
                    a.setNombre(c.getString(c.getColumnIndex("nombre")));
                    listaAlimento.add(a);
                } while (c.moveToNext());
            }
        }
        return listaAlimento;
    }

}
