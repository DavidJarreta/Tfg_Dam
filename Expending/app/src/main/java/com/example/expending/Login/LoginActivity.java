package com.example.expending.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expending.AdminSQL;
import com.example.expending.MainActivity;
import com.example.expending.R;

public class LoginActivity extends AppCompatActivity {

    Button btn_login, btn_register;
    EditText et_email_l, et_pass_l;
    AdminSQL conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        et_email_l = findViewById(R.id.et_email_l);
        et_pass_l = findViewById(R.id.et_pass_l);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        //iniciamos conexion
        conexion = new AdminSQL(this, "expending", null, 5);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email_l.getText().toString();
                String pass = et_pass_l.getText().toString();

                SQLiteDatabase BDD = conexion.getReadableDatabase();
                //ContentValues registro = new ContentValues();
                Cursor fila = BDD.rawQuery("select email, contrasena, tipo_usuario from usuarios where email= '" + email + "';", null);
                String emailConsulta = null;
                String passConsulta = null;
                String tipo_usuario = null;

                if (fila.moveToFirst()){
                    emailConsulta = fila.getString(0);
                    passConsulta = fila.getString(1);
                    tipo_usuario = fila.getString(2);

                    if (emailConsulta.equals(email) && passConsulta.equals(pass)) {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle b = new Bundle();
                        b.putString("TIPO", tipo_usuario);
                        i.putExtras(b);
                        startActivity(i);
                        et_email_l.setText("");
                        et_pass_l.setText("");
                    } else {
                        Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario no existente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}