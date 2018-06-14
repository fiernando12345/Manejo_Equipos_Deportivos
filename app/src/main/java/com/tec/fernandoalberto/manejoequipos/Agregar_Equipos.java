package com.tec.fernandoalberto.manejoequipos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Agregar_Equipos extends AppCompatActivity {

    Spinner Rama;
    EditText txtNombre;
    Button Agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__equipos);

        Rama = findViewById(R.id.spnRama);
        txtNombre = findViewById(R.id.txtNombreEquipo);
        String[] operaciones = {"Varonil","Femenil"};
        Rama.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, operaciones));
        Agregar= findViewById(R.id.btnAgregar);

        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comprobar();
            }
        });
    }

    public void Comprobar(){
        if ((txtNombre.getText().toString().length() == 0)) {
            Toast.makeText(this, "Campos incmpletos", Toast.LENGTH_SHORT).show();
        }else{
            boolean repetido = false;
            BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
            SQLiteDatabase db = baseHelper.getWritableDatabase();
            if (db != null) {
                Cursor c = db.rawQuery("select * from Equipos", null);
                int i = 0;
                if (c.moveToFirst()) {
                    do {
                        if ((txtNombre.getText().toString().equals(c.getString(0)))) {
                            repetido = true;
                        }
                        i++;
                    } while (c.moveToNext());
                }
            }
            if (repetido) {
                txtNombre.setText("");
                Toast.makeText(this, "El nombre insertado ya están en uso", Toast.LENGTH_SHORT).show();
            } else {
                GuardarDatos();
            }
        }
    }

    public void GuardarDatos() {
        String Nombre = txtNombre.getText().toString();
        String rama = Rama.getSelectedItem().toString();

        BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            ContentValues registronuevo = new ContentValues();
            registronuevo.put("Nombre", Nombre);
            registronuevo.put("Rama", rama);
            registronuevo.put("Partidos_Ganados", 0);
            registronuevo.put("Partidos_Perdidos", 0);
            registronuevo.put("Puntos_Favor", 0);
            registronuevo.put("Puntos_Contra", 0);
            registronuevo.put("Sets_Ganados", 0);
            registronuevo.put("Sets_Perdidos", 0);
            long i = db.insert("Equipos", null, registronuevo);
            if (i > 0) {
                Toast.makeText(this, "Registro Insertado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Agregar_Equipos.this, MainActivity.class));
            }
        }
    }
}
