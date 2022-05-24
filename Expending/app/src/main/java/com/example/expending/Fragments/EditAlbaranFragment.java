package com.example.expending.Fragments;

import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.expending.AdminSQL;
import com.example.expending.Albaran;
import com.example.expending.Alimento;
import com.example.expending.Maquina;
import com.example.expending.R;
import com.example.expending.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EditAlbaranFragment extends Fragment
{

    Spinner spinner_usuario_edit, spinner_maquina_edit, spinner_alimento_edit;
    EditText et_contador_edit, et_dinero_edit, et_fecha_edit, et_estado_edit, et_cantidad_edit;
    Button btn_edit;
    AdminSQL conexion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editar_albaran_fragment, container, false);

        spinner_usuario_edit = view.findViewById(R.id.spinner_edit_usuario);
        spinner_maquina_edit = view.findViewById(R.id.spinner_edit_maquina);
        spinner_alimento_edit = view.findViewById(R.id.spinner_edit_ali);
        et_contador_edit = view.findViewById(R.id.et_edit_contador);
        et_dinero_edit = view.findViewById(R.id.et_edit_dinero);
        et_fecha_edit = view.findViewById(R.id.et_edit_fecha);
        et_estado_edit = view.findViewById(R.id.et_edit_estado);
        et_cantidad_edit = view.findViewById(R.id.et_edit_cantidad);
        btn_edit = view.findViewById(R.id.btn_edit_albaran);

        conexion = new AdminSQL(getContext(), "expending", null, 5);

        //datos spinners
        //spinner maquinas
        List<Maquina> listaMaquinas = llenarSpinnerMaquinas();
        ArrayAdapter<Maquina> arrayAdapterMaquinas = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaMaquinas);
        spinner_maquina_edit.setAdapter(arrayAdapterMaquinas);

        //spinner usuarios
        List<Usuario> listaUsuario = llenarSpinnerUsuarios();
        ArrayAdapter<Usuario> arrayAdapterUsuarios = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaUsuario);
        spinner_usuario_edit.setAdapter(arrayAdapterUsuarios);

        //spinner alimentos
        List<Alimento> listaAlimento = llenarSpinnerAlimentos();
        ArrayAdapter<Alimento> arrayAdapterAlimentos = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaAlimento);
        spinner_alimento_edit.setAdapter(arrayAdapterAlimentos);

        //crear objeto bundle para recibir el objeto enviado por argumentos
        Bundle objetoAlbaran = getArguments();
        Albaran albaran = null;

        //validacion para verificar si existen argumentos enviados para mostrarlos
        if(objetoAlbaran != null){
            albaran = (Albaran) objetoAlbaran.getSerializable("objeto");

        }

        return  view;
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
