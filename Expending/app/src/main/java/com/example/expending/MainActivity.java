package com.example.expending;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import com.example.expending.Fragments.DialogPersonalizado;
import com.example.expending.Fragments.EditAlbaranFragment;
import com.example.expending.Fragments.IncidenciaFragment;
import com.google.android.material.navigation.NavigationView;
import com.example.expending.Fragments.MainFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        DialogPersonalizado.Idioma, iComunicaFragments
{
    DrawerLayout drawerLayout; //todo lo que hay en el main.xml
    Toolbar toolbar;
    NavigationView navigationView; //todo lo que hay dentro del content_main
    ActionBarDrawerToggle actionBarDrawerToggle;

    //varaibles para cargar el fragmento
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    EditAlbaranFragment editAlbaranFragment;

    //parametros para cambiar de idioma
    final int SPANISH = 1;
    final int ENGLISH = 2;
    final int FRENCH = 3;

    int idiomaSeccionado = 1;
    String tipo_usuario = null;
    AdminSQL conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);

        //iniciamos conexion
        conexion = new AdminSQL(this, "expending", null, 5);

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

        Intent i = this.getIntent();
        Bundle b = i.getExtras();
        if(b != null) {
            String tipo = b.getString("TIPO");
            this.tipo_usuario = tipo;
        }

        //cargar fragmento crear albaran
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new CrearAlbaranFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        if (this.tipo_usuario.equals("Empleado")){
            //EMPLEADO
            switch (item.getItemId()) {
                case R.id.crear_albaran:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new CrearAlbaranFragment());
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
                    FragmentManager fm = getSupportFragmentManager();
                    DialogPersonalizado dp = new DialogPersonalizado();
                    dp.show(fm, "tag");
                    break;
                case R.id.albaranes:
                    Toast.makeText(this, "Se necesitan permisos", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.alimentos:
                    Toast.makeText(this, "Se necesitan permisos", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.cerrar_sesion:
                    finish();
                    Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (this.tipo_usuario.equals("Administrador")){
            switch (item.getItemId()){
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
                case R.id.crear_albaran:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new CrearAlbaranFragment());
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
                    FragmentManager fm = getSupportFragmentManager();
                    DialogPersonalizado dp = new DialogPersonalizado();
                    dp.show(fm, "tag");
                    break;
                case R.id.cerrar_sesion:
                    finish();
                    Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        return false;
    }

    @Override
    public void IdiomaSeleccionado(int idioma) {
        this.idiomaSeccionado = idioma;
        //Toast.makeText(this, idiomaSeccionado + "", Toast.LENGTH_SHORT).show();
        String language = "";
        switch (idioma) {
            case SPANISH:
                language = "es";
                break;
            case ENGLISH:
                language = "en";
                break;
            case FRENCH:
                language = "fr";
                break;
        }
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = this.getResources();
        Configuration c = resources.getConfiguration();
        c.locale = locale;
        resources.updateConfiguration(c, resources.getDisplayMetrics());

        //VOLVERMOS A HACER LO QUE HAY EN EL ONCREATE PARA ACTUALIZAR TODA LA MAINACTIVITY
        setContentView(R.layout.main_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navegationView);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open , R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true); //true para que se vea el icon del drawer
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new CrearAlbaranFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void enviarAlbaran(Albaran albaran) {
        editAlbaranFragment = new EditAlbaranFragment();

        //objeto bundle para pasar los datos
        Bundle bundleEnvio = new Bundle();

        //enviamos el objeto que esta llegando con serializable
        bundleEnvio.putSerializable("objeto", albaran);
        editAlbaranFragment.setArguments(bundleEnvio);

        //abrimos el fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, editAlbaranFragment );
        fragmentTransaction.addToBackStack(null); //sirve para volver al recyclerview
        fragmentTransaction.commit();
    }
}
