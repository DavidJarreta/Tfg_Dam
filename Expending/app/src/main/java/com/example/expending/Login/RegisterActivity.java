package com.example.expending.Login;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expending.AdminSQL;
import com.example.expending.MainActivity;
import com.example.expending.R;

import java.nio.charset.StandardCharsets;

public class RegisterActivity extends AppCompatActivity
{
    EditText et_nombre_r, et_email_r, et_pass_r, et_confirm_pass_r;
    Button btn_crear, btn_volver;
    Spinner spinner_rol;
    ProgressDialog progressDialog;
    //conexion
    AdminSQL conex = new AdminSQL(RegisterActivity.this, "expending", null, 4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        et_nombre_r = findViewById(R.id.et_name_r);
        et_email_r = findViewById(R.id.et_email_r);
        et_pass_r = findViewById(R.id.et_pass_r);
        et_confirm_pass_r = findViewById(R.id.et_confirmPass_r);
        btn_crear = findViewById(R.id.btn_create_r);
        btn_volver = findViewById(R.id.btn_back_r);
        spinner_rol = findViewById(R.id.spinner_user_r);

        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cogemos los valores
                String nombre = et_nombre_r.getText().toString();
                String email = et_email_r.getText().toString();
                String pass = et_pass_r.getText().toString();
                String confirmPass = et_confirm_pass_r.getText().toString();
                String rol = spinner_rol.getSelectedItem().toString();

                SQLiteDatabase BaseDeDatos = conex.getWritableDatabase();
                ContentValues registro = new ContentValues();

                if (!nombre.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !confirmPass.isEmpty()){
                    if(pass.equals(confirmPass)){
                        //Progresdialog
                        progressDialog = new ProgressDialog(RegisterActivity.this);
                        progressDialog.setMessage("Creando..."); // Setting Message
                        progressDialog.setTitle("Creando usuario"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(2500);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                        }).start();

                        registro.put("nombre", nombre);
                        registro.put("email", email);
                        registro.put("contrasena", pass);
                        registro.put("tipo_usuario", rol);

                        BaseDeDatos.insert("usuarios", "id", registro);
                        //BaseDeDatos.close();

                        //vaciamos los datos
                        et_nombre_r.setText("");
                        et_email_r.setText("");
                        et_pass_r.setText("");
                        et_confirm_pass_r.setText("");

                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);

                    } else {
                        et_pass_r.setText("");
                        et_confirm_pass_r.setText("");
                        Toast.makeText(RegisterActivity.this, "Confirmación de contraseña erronea", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Llena los campos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
