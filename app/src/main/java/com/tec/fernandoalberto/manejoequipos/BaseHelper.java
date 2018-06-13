package com.tec.fernandoalberto.manejoequipos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseHelper extends SQLiteOpenHelper{

    String tabla= "CREATE TABLE Equipos(Nombre Text, Rama Text, Partidos_Ganados INTEGER, Partidos_Perdidos INTEGER, Puntos_Favor INTEGER, Puntos_Contra INTEGER)";

    public BaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
