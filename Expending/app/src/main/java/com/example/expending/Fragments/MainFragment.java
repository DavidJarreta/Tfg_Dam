package com.example.expending.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expending.AdminSQL;
import com.example.expending.Albaran;
import com.example.expending.R;

import java.util.ArrayList;

public class MainFragment extends Fragment
{

    ArrayList<Albaran> listaAlbaranes= new ArrayList<>();
    AdminSQL conexion;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        conexion = new AdminSQL(getContext(), "expending", null, 5);

        guardarDatos();

        return view;
    }

    public void guardarDatos() {

        SQLiteDatabase db = conexion.getReadableDatabase();
        Albaran albaran = null;
        Cursor c = db.rawQuery("select * from albaranes;", null);

        while(c.moveToNext()){
            albaran = new Albaran();
            albaran.setId(c.getInt(0));
            albaran.setEstado_albaran(c.getString(1));
            albaran.setFecha(c.getString(2));
            albaran.setDinero(c.getDouble(3));
            albaran.setContador(c.getInt(4));
            albaran.setId_usuario(c.getInt(5));
            albaran.setId_maquina(c.getInt(6));
            listaAlbaranes.add(albaran);
        }
    }
}
