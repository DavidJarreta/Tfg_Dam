package com.example.expending.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.expending.R;
import com.example.expending.Usuario;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class IncidenciaFragment extends Fragment
{

    Spinner spinner_usuario, spinner_gravedad;
    EditText et_descrip, et_fecha_inci;
    Button btn_add_inci;
    AdminSQL conexion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incidencia_fragment, container, false);

        spinner_usuario = view.findViewById(R.id.spinner_usuario_in);
        spinner_gravedad = view.findViewById(R.id.spinner_incidencia);
        et_descrip = view.findViewById(R.id.et_descripcion);
        et_fecha_inci = view.findViewById(R.id.et_fecha_inci);
        btn_add_inci = view.findViewById(R.id.btn_add_inci);

        conexion = new AdminSQL(getContext(), "expending", null, 4);

        List<Usuario> listaUsuarios = llenarSpinnerUsuarios();
        ArrayAdapter<Usuario> arrayAdapter = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaUsuarios);

        spinner_usuario.setAdapter(arrayAdapter);

        return view;
    }

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
        List<Usuario> listaUsers = new ArrayList<>();
        Cursor c = mostrarUsuarios();

        if(c != null){
            if(c.moveToFirst()){
                do {
                    Usuario u = new Usuario();
                    //u.setId(c.getInt(c.getColumnIndex("id")));
                    u.setNombre(c.getString(c.getColumnIndex("nombre")));
                    listaUsers.add(u);
                } while (c.moveToNext());
            }
        }
        return listaUsers;
    }
}
