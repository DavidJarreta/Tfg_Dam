package com.example.expending;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Console;
import java.util.ArrayList;

public class UbicacionActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    Button btn_volver;
    Toolbar toolbar;
    ArrayList<Maquina> listaMaquinas = new ArrayList<>();

    AdminSQL conexion = new AdminSQL(this, "expending", null, 5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubicacion_activity);
        btn_volver = findViewById(R.id.btn_volver);
        toolbar = findViewById(R.id.toolbar);

        //menú
        setSupportActionBar(toolbar); //pone el toolbar en el action bar

        //mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        double lati = 0;
        double longi = 0;

        SQLiteDatabase db = conexion.getReadableDatabase();
        Maquina m = null;
        Cursor c = db.rawQuery("select * from maquinas;", null);
        while(c.moveToNext()){
            m = new Maquina();
            m.setNombre_empresa(c.getString(1));
            m.setLatitud(c.getString(2));
            m.setLongitud(c.getString(3));
            //listaMaquinas.add(m);

            lati = Double.parseDouble(m.getLatitud());
            longi = Double.parseDouble(m.getLongitud());

            LatLng ubi = new LatLng(lati, longi);
            mMap.addMarker(new MarkerOptions()
                    .position(ubi)
                    .title(m.getNombre_empresa()));
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubi,6));
        }


        /*LatLng sydney = new LatLng(40.42428047564802, -3.7076996553623554);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Madrid"));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,6));*/
    }

    public void guardarDatos(){

        SQLiteDatabase db = conexion.getReadableDatabase();
        Maquina m = null;
        Cursor c = db.rawQuery("select * from maquinas;", null);

        while(c.moveToNext()){
            m = new Maquina();
            m.setNombre_empresa(c.getString(1));
            m.setLatitud(c.getString(2));
            m.setLongitud(c.getString(3));
            listaMaquinas.add(m);
        }
        c.close();
    }
}
