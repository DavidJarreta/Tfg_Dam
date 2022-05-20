package com.example.expending.Fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expending.AdminSQL;
import com.example.expending.R;

public class AlimentoFragment extends Fragment
{

    EditText et_nombre;
    EditText et_precio;
    Button btn_add;
    AdminSQL conexion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alimento_fragment, container, false);

        et_nombre = view.findViewById(R.id.et_nombre_ali);
        et_precio = view.findViewById(R.id.et_precio_ali);
        btn_add = view.findViewById(R.id.btn_add_inci);

        conexion = new AdminSQL(getContext(), "expending", null, 4);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = et_nombre.getText().toString();
                String precio = et_precio.getText().toString();

                SQLiteDatabase BaseDeDatos = conexion.getWritableDatabase();
                ContentValues registro = new ContentValues();

                if (!nombre.isEmpty() && !precio.isEmpty()){

                    double precioToDouble = Double.parseDouble(precio);
                    registro.put("nombre", nombre);
                    registro.put("precio", precioToDouble);

                    BaseDeDatos.insert("alimentos", "id", registro);
                    //BaseDeDatos.close();

                    et_nombre.setText("");
                    et_precio.setText("");
                    Toast.makeText(getContext(), "Alimento creado", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
