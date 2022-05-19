package com.example.expending.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.expending.R;

public class DialogPersonalizado extends DialogFragment
{
    //deifinamos las variables
    Idioma idioma;
    final int SPANISH = 1;
    final int ENGLISH = 2;
    final int FRENCH = 3;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        idioma = (Idioma) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final String[] items = {"Español", "Inglés", "Francés"};
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        ventana.setTitle("Elige el idioma de la aplicación");
        ventana.setIcon(R.mipmap.logo);

        ventana.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getActivity(), "Idioma: " + items[i], Toast.LENGTH_SHORT).show();
                if (items[i] == "Español"){
                    idioma.IdiomaSeleccionado(SPANISH);
                    //Toast.makeText(getActivity(), SPANISH + "", Toast.LENGTH_SHORT).show();
                } else if(items[i] == "Inglés"){
                    idioma.IdiomaSeleccionado(ENGLISH);
                    //Toast.makeText(getActivity(), ENGLISH + "", Toast.LENGTH_SHORT).show();
                } else if(items[i] == "Francés") {
                    idioma.IdiomaSeleccionado(FRENCH);
                }
            }
        });
        AlertDialog ad = ventana.create();
        return ad;
    }

    public interface Idioma
    {
        public void IdiomaSeleccionado(int idioma);
    }
}
