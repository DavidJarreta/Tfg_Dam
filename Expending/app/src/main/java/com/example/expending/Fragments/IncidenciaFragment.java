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
import com.example.expending.Maquina;
import com.example.expending.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IncidenciaFragment extends Fragment
{

    Spinner spinner_maquina, spinner_gravedad;
    EditText et_descrip, et_fecha_inci;
    Button btn_add_inci;
    long fecha_incidencia;
    AdminSQL conexion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incidencia_fragment, container, false);

        spinner_maquina = view.findViewById(R.id.spinner_maquina_in);
        spinner_gravedad = view.findViewById(R.id.spinner_incidencia);
        et_descrip = view.findViewById(R.id.et_descripcion);
        et_fecha_inci = view.findViewById(R.id.et_fecha_inci);
        btn_add_inci = view.findViewById(R.id.btn_add_inci);

        String dia = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        et_fecha_inci.setText(dia);

        conexion = new AdminSQL(getContext(), "expending", null, 5);

        List<Maquina> listaMaquinas = llenarSpinnerMaquinas();
        ArrayAdapter<Maquina> arrayAdapter = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaMaquinas);

        spinner_maquina.setAdapter(arrayAdapter);

        btn_add_inci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maquina = spinner_maquina.getSelectedItem().toString();
                String gravedad = spinner_gravedad.getSelectedItem().toString();
                String descrip = et_descrip.getText().toString();
                String fecha = et_fecha_inci.getText().toString();

                String[] parts = maquina.split(",");
                String id = parts[0];
                Integer idMaquina = Integer.valueOf(id);

                SQLiteDatabase BaseDeDatos = conexion.getWritableDatabase();
                ContentValues registro = new ContentValues();

                if (!descrip.isEmpty() && !fecha.isEmpty()){
                    if (fecha.contains("/")){
                        registro.put("fecha_incidencia", fecha);
                        registro.put("descripcion", descrip);
                        registro.put("gravedad", gravedad);
                        registro.put("id_maquina", idMaquina);

                        BaseDeDatos.insert("incidencias", "id", registro);
                        //BaseDeDatos.close();

                        et_descrip.setText("");
                        et_fecha_inci.setText("");
                        Toast.makeText(getContext(), "Incidencia creada", Toast.LENGTH_SHORT).show();

                    } else {
                        et_fecha_inci.setText("");
                        Toast.makeText(getContext(), "Escribe bien la fecha", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Rellena los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

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
}
