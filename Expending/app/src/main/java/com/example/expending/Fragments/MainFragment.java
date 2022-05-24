package com.example.expending.Fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.expending.AdaptadorAlbaran;
import com.example.expending.AdminSQL;
import com.example.expending.Albaran;
import com.example.expending.R;
import java.util.ArrayList;

public class MainFragment extends Fragment implements SearchView.OnQueryTextListener, AdaptadorAlbaran.OnDatosListener
{

    ArrayList<Albaran> listaAlbaranes= new ArrayList<>();
    AdminSQL conexion;
    RecyclerView recyclerView;
    AdaptadorAlbaran adapter;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recycler);

        conexion = new AdminSQL(getContext(), "expending", null, 5);

        guardarDatos();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdaptadorAlbaran(listaAlbaranes, getContext(), (AdaptadorAlbaran.OnDatosListener) this);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        //LISTENER PARA EL BUSCADOR
        searchView.setOnQueryTextListener(this);
        return view;
    }

    public void guardarDatos() {

        SQLiteDatabase db = conexion.getReadableDatabase();
        Albaran albaran = null;
        Cursor c = db.rawQuery("select a.id_albaran, a.estado_albaran, a.fecha, a.dinero_recaudado, a.contador," +
                "u.id_usuario, u.nombre, m.id_maquina, m.nombre_empresa from albaranes a join usuarios u " +
                "on a.id_usuario = u.id_usuario join maquinas m on a.id_maquina = m.id_maquina", null);

        while(c.moveToNext()){
            albaran = new Albaran();
            albaran.setId(c.getInt(0));
            albaran.setEstado_albaran(c.getString(1));
            albaran.setFecha(c.getString(2));
            albaran.setDinero(c.getDouble(3));
            albaran.setContador(c.getInt(4));
            albaran.setId_usuario(c.getInt(5));
            albaran.setNombre_usuario(c.getString(6));
            albaran.setId_maquina(c.getInt(7));
            albaran.setNombre_empresa(c.getString(8));
            listaAlbaranes.add(albaran);
        }
    }

    //METODOS PARA EL BUSCADOR
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }

    //MÉTODOS DE INTERFAZ
    @Override
    public void onDatosBorrar(int posicion) {
        int id = listaAlbaranes.get(posicion).getId();
        //Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();

        SQLiteDatabase db = conexion.getWritableDatabase();
        db.execSQL("delete from albaranes where id_albaran = " + id);
        db.execSQL("delete from albaran_alimento where id_albaran = " + id);
        //db.close();
        listaAlbaranes.remove(posicion);
        adapter.notifyDataSetChanged();
    }
}
