package com.example.expending;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQL extends SQLiteOpenHelper
{

    public AdminSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sql) {

        sql.execSQL("create table usuarios(id_usuario integer primary key autoincrement, nombre text not null," +
                "email text not null, contrasena text not null, tipo_usuario text not null)");

        sql.execSQL("create table maquinas(id_maquina integer primary key autoincrement, nombre_empresa text not null," +
                "latitud text not null, longitud text not null)");

        sql.execSQL("create table alimentos(id_alimento integer primary key autoincrement, nombre text not null," +
                "precio float)");

        sql.execSQL("create table albaranes(id_albaran integer primary key autoincrement, estado_albaran text not null," +
                "fecha long, dinero_recaudado float, contador integer, id_usuario integer, id_maquina integer," +
                "foreign key(id_usuario) references usuarios(id_usuario)" +
                "on delete cascade on update cascade," +
                "foreign key(id_maquina) references maquinas(id_maquina)" +
                "on delete cascade on update cascade)");

        sql.execSQL("create table existencia_maquina(id_existencia integer primary key autoincrement, cantidad integer not null," +
                "id_maquina integer, id_alimento integer," +
                "foreign key(id_maquina) references maquinas(id_maquina)" +
                "on delete cascade on update cascade," +
                "foreign key(id_alimento) references alimentos(id_alimento)" +
                "on delete cascade on update cascade)");

        sql.execSQL("create table incidencias(id_incidencia integer primary key autoincrement, fecha_incidencia long," +
                "descripcion text not null, gravedad text not null, id_maquina integer," +
                "foreign key(id_maquina) references maquinas(id_maquina)" +
                "on delete cascade on update cascade)");

        sql.execSQL("create table albaran_alimento(id_albaran_alimento integer primary key autoincrement, id_albaran integer," +
                "id_alimento integer, cantidad integer," +
                "foreign key(id_albaran) references albaranes(id_albaran)" +
                "on delete cascade on update cascade," +
                "foreign key(id_alimento) references alimentos(id_alimentos)" +
                "on delete cascade on update cascade)");

        //DATOS INICIALES DE MAQUINAS Y ALIMENTOS
        //MAQUINAS
        sql.execSQL("insert into maquinas(nombre_empresa, latitud, longitud) values (" +
                "'Idea Consulting', '41.643923', '-0.885696')");
        sql.execSQL("insert into maquinas(nombre_empresa, latitud, longitud) values (" +
                "'Integra estrategia y tecnologia', '41.649727', '-0.898420')");

        //ALIMENTOS
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Kinder bueno', '1.1')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Cocacola', '0.6')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sql, int i, int i1) {
        sql.execSQL("DROP TABLE IF EXISTS usuarios");
        sql.execSQL("DROP TABLE IF EXISTS maquinas");
        sql.execSQL("DROP TABLE IF EXISTS alimentos");
        sql.execSQL("DROP TABLE IF EXISTS albaranes");
        sql.execSQL("DROP TABLE IF EXISTS existencia_maquina");
        sql.execSQL("DROP TABLE IF EXISTS incidencias");
        sql.execSQL("DROP TABLE IF EXISTS albaran_alimento");
        onCreate(sql);
    }
}
