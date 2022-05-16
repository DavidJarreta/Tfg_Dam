package com.example.expending;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.expending.Fragments.AlimentoFragment;
import com.example.expending.Fragments.CrearAlbaranFragment;
import com.example.expending.Fragments.IncidenciaFragment;
import com.example.expending.Fragments.UbicacionMaquinasFragment;
import com.example.expending.Login.LoginActivity;
import com.example.expending.Login.RegisterActivity;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.navigation.NavigationView;
import com.example.expending.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    DrawerLayout drawerLayout; //todo lo que hay en el main.xml
    Toolbar toolbar;
    NavigationView navigationView; //todo lo que hay dentro del content_main
    ActionBarDrawerToggle actionBarDrawerToggle;

    //varaibles para cargar el fragmento
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //pone el toolbar en el action bar

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navegationView);

        //establecemos evento onclick al navigationView
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open , R.string.close); //los dos ultimos parametros los ha creado en string.xml

        //añade el drawer a drawerlayout (main.xml)
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true); //true para que se vea el icon del drawer
        actionBarDrawerToggle.syncState();

        //cargar fragmento albaranes
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new MainFragment());
        fragmentTransaction.commit();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        //habrá que coger el usuario que ha iniciado sesion para comprabar si es admin o trabajador
        //cuando guarde el rol se hará un if con las dos opciones y con restricciones en las opciones del menu

        switch (item.getItemId()){
            //TODO LO QUE PUEDE HACER UN ADMINISTRADOR
            case R.id.crear_albaran:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new CrearAlbaranFragment());
                fragmentTransaction.commit();
                break;
            case R.id.albaranes:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new MainFragment());
                fragmentTransaction.commit();
                break;
            case R.id.alimentos:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new AlimentoFragment());
                fragmentTransaction.commit();
                break;
            case R.id.incidencias:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new IncidenciaFragment());
                fragmentTransaction.commit();
                break;
            case R.id.ubicaciones:
                Intent i = new Intent(MainActivity.this, UbicacionActivity.class);
                startActivity(i);
                break;
            case R.id.configuracion:
                Toast.makeText(this, "configuracion", Toast.LENGTH_SHORT).show();
                break;

            //TODO LO QUE PUEDE HACER UN TRABAJADOR
            case R.id.crear_albaran_trabajador:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new CrearAlbaranFragment());
                fragmentTransaction.commit();
                break;
            case R.id.albaranes_trabajador:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new MainFragment());
                fragmentTransaction.commit();
                break;
            case R.id.incidencias_trabajador:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new IncidenciaFragment());
                fragmentTransaction.commit();
                break;
            case R.id.ubicaciones_trabajador:
                i = new Intent(MainActivity.this, UbicacionActivity.class);
                startActivity(i);
                break;
        }

        return false;
    }
}
