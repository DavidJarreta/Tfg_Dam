package com.example.expending;

import android.content.Context;
import android.provider.AlarmClock;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AdaptadorAlbaran extends RecyclerView.Adapter<AdaptadorAlbaran.MiContenedor>
        implements View.OnClickListener
{

    ArrayList<Albaran> listaAlbaranes;
    ArrayList<Albaran> listaOriginal;
    Context contexto;
    OnDatosListener onDatosListener;

    //variable listener para el metodo on click
    private View.OnClickListener listener;

    public AdaptadorAlbaran(ArrayList<Albaran> listaAlbaranes, Context contexto, OnDatosListener onDatosListener) {
        this.listaAlbaranes = listaAlbaranes;
        this.contexto = contexto;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaAlbaranes);
        this.onDatosListener = onDatosListener;
    }

    @Override
    public MiContenedor onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_albaran, parent, false);
        view.setOnClickListener(this);
        return new MiContenedor(view);
    }

    //listener
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener !=null){
            listener.onClick(view);
        }
    }

    public void onBindViewHolder(MiContenedor holder, int position)
    {
        holder.tv_id_albaran.setText("Id albarán: " + listaAlbaranes.get(position).getId());
        holder.tv_estado_albaran.setText("Estado: " + listaAlbaranes.get(position).getEstado_albaran());
        holder.tv_contador.setText("Contador: " + listaAlbaranes.get(position).getContador());
        holder.tv_dinero.setText("Dinero: " + listaAlbaranes.get(position).getDinero() + " euros");
        holder.tv_id_usuario.setText("Id usuario: " + listaAlbaranes.get(position).getId_usuario());
        holder.tv_id_maquina.setText("Id máquina: " + listaAlbaranes.get(position).getId_maquina());
        holder.tv_fecha.setText("Fecha: " + listaAlbaranes.get(position).getFecha());
        holder.tv_usuario.setText("Usuario: " + listaAlbaranes.get(position).getNombre_usuario());
        holder.tv_nombre_empresa.setText("Empresa: " + listaAlbaranes.get(position).getNombre_empresa());
    }

    @Override
    public int getItemCount() {
        return listaAlbaranes.size();
    }

    //METODO PARA FILTRAR EN EL BUSCADOR
    public void filtrado(String fecha){
        int longitud = fecha.length();
        if(longitud == 0){
            listaAlbaranes.clear();
            listaAlbaranes.addAll(listaOriginal);
        } else {
            //VERSION RARA DE BUSCAR
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Albaran> collecion = listaAlbaranes.stream().filter(i -> i.getFecha().
                        toLowerCase(Locale.ROOT).contains(fecha.toLowerCase(Locale.ROOT)))
                        .collect(Collectors.toList());
                listaAlbaranes.clear();
                listaAlbaranes.addAll(collecion);

            } else { //SI NO TIENE LA VERSION DE ANDROID ANTERIOR HACE ESTA OTRA MANERA DIFERENTE DE BUSCAR
                for(Albaran a: listaOriginal){
                    if(a.getFecha().toLowerCase(Locale.ROOT).contains(fecha.toLowerCase(Locale.ROOT))){
                        listaAlbaranes.add(a);
                    }
                }
            }
        }
        notifyDataSetChanged(); //REFRESCAMOS
    }

    public class MiContenedor extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener{

        TextView tv_id_albaran, tv_estado_albaran, tv_contador, tv_fecha, tv_dinero, tv_id_usuario, tv_id_maquina;
        TextView tv_nombre_empresa, tv_usuario;

        public MiContenedor(@NonNull View itemView) {
            super(itemView);
            tv_id_albaran = itemView.findViewById(R.id.i_id_albaran);
            tv_estado_albaran = itemView.findViewById(R.id.i_estado);
            tv_contador = itemView.findViewById(R.id.i_contador);
            tv_fecha = itemView.findViewById(R.id.i_fecha);
            tv_dinero = itemView.findViewById(R.id.i_dinero);
            tv_id_usuario = itemView.findViewById(R.id.i_id_usuario);
            tv_id_maquina = itemView.findViewById(R.id.i_id_maquina);
            tv_usuario = itemView.findViewById(R.id.i_nombre_usuario);
            tv_nombre_empresa = itemView.findViewById(R.id.i_empresa);
            itemView.setOnCreateContextMenuListener(this);
        }

        //metodo que crea las opciones del context menu
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo)
        {
            MenuItem itemBorrar = contextMenu.add(Menu.NONE, 2, 2, "Borrar");
            itemBorrar.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            onDatosListener.onDatosBorrar(getAdapterPosition()); //le pasamos a la interfaz la posicion
            return true;
        }
    }

    public interface OnDatosListener
    {
        void onDatosBorrar(int posicion);
    }
}
