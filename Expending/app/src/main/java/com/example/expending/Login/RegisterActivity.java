package com.example.expending.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expending.R;

public class RegisterActivity extends AppCompatActivity
{
    EditText et_nombre_r, et_email_r, et_pass_r, et_confirm_pass_r;
    Button btn_crear, btn_volver;
    Spinner spinner_rol;
    ProgressDialog progressDialog;

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
