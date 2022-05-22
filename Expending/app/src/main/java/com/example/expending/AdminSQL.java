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
                "precio double)");

        sql.execSQL("create table albaranes(id_albaran integer primary key autoincrement, estado_albaran text not null," +
                "fecha text, dinero_recaudado double, contador integer,  id_usuario integer,id_maquina integer," +
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

        sql.execSQL("create table incidencias(id_incidencia integer primary key autoincrement, fecha_incidencia text," +
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
        sql.execSQL("insert into maquinas(nombre_empresa, latitud, longitud) values (" +
                "'Bopepor S.L', '41.837821', '-1.310012')");
        sql.execSQL("insert into maquinas(nombre_empresa, latitud, longitud) values (" +
                "'Persianas Tauste', '41.907878', '-1.249410')");
        sql.execSQL("insert into maquinas(nombre_empresa, latitud, longitud) values (" +
                "'Polideportivo Murchante', '42.031778', '-1.651113')");
        sql.execSQL("insert into maquinas(nombre_empresa, latitud, longitud) values (" +
                "'Ayuntamiento Mallen', '41.899971', '-1.420459')");
        sql.execSQL("insert into maquinas(nombre_empresa, latitud, longitud) values (" +
                "'Casa de cultura', '42.466570', '-2.445540')");
        sql.execSQL("insert into maquinas(nombre_empresa, latitud, longitud) values (" +
                "'Academia Calatayud', '41.352900', '-1.630606')");
        sql.execSQL("insert into maquinas(nombre_empresa, latitud, longitud) values (" +
                "'Instituto Pedrola', '41.784137', '-1.223012')");
        sql.execSQL("insert into maquinas(nombre_empresa, latitud, longitud) values (" +
                "'Serveo S.L Figueruelas', '41.765597', '-1.181376')");

        //ALIMENTOS
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Kinder bueno', '1.1')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Cocacola', '0.6')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Rufles', '1.2')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Palmera chocolate', '1.6')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Fanta naranja', '0.6')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Patantas fritas', '0.7')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Gominolas', '1.1')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Chaskis', '0.9')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Cocacolo 500', '1.3')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Maiz', '0.5')");
        sql.execSQL("insert into alimentos(nombre, precio) values (" +
                "'Papadelta', '0.45')");

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
