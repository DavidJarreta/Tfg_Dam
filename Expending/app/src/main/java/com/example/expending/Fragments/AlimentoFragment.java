package com.example.expending.Fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
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

import com.example.expending.R;

import java.util.Locale;

public class AlimentoFragment extends Fragment
{

    EditText et_nombre;
    EditText et_precio;
    Button btn_add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alimento_fragment, container, false);

        et_nombre = view.findViewById(R.id.et_nombre_alimento);
        et_precio = view.findViewById(R.id.et_precio_alimento);
        btn_add = view.findViewById(R.id.btn_add_alimento);

        return view;
    }
}
