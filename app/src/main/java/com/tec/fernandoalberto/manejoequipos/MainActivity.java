package com.tec.fernandoalberto.manejoequipos;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ListView Lista;
    TextView Titulo;

    public static String Equipo_Editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Lista= findViewById(R.id.Lista);
        Titulo= findViewById(R.id.txtEquipo);
        Cargar();

        Lista.setClickable(true);
        Lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Equipo_Editar = String.valueOf(Lista.getItemAtPosition(position));
                startActivity(new Intent(MainActivity.this, Editar_Equipo.class));
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, Agregar_Equipos.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_equipos_varoniles) {
            VerVaroniles();
            Titulo.setText("Todos los equipos Varoniles");
            return true;
        }
        if (id == R.id.action_equipos_femeniles) {
            VerFemeniles();
            Titulo.setText("Todos los equipos Femeniles");
            return true;
        }
        if (id == R.id.action_equipos_mixtos) {
            Titulo.setText("Todos los equipos");
            Cargar();
            return true;
        }
        if (id == R.id.action_lugares_varonil) {
            Titulo.setText("Lugares Rama Varonil");
            LugaresVaroniles();
            return true;
        }
        if (id == R.id.action_lugares_femenil) {
            Titulo.setText("Lugares Rama Femenil");
            LugaresFemeniles();
            return true;
        }
        if (id == R.id.action_Eliminar){
            if(Lista.getCount()!=0){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("¿Quiere eliminar todos los equipos?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Limpiar();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }else{
                Toast.makeText(this, "No hay equipos que eliminar", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Cargar(){
        BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            Cursor c= db.rawQuery("select * from Equipos", null);
            int cantidad= c.getCount();
            int i= 0;
            String[] arreglo= new String[cantidad];
            if(c.moveToFirst()){
                do{
                    String linea= c.getString(0);
                    arreglo[i]= linea;
                    i++;
                }while (c.moveToNext());
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<>(this, R.layout.lista_ittem, arreglo);
            ListView lista= (ListView) findViewById(R.id.Lista);
            lista.setAdapter(adapter);
        }
    }

    public void VerVaroniles(){
        BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            Cursor c= db.rawQuery("select * from Equipos where Rama = 'Varonil'", null);
            int cantidad= c.getCount();
            int i= 0;
            String[] arreglo= new String[cantidad];
            if(c.moveToFirst()){
                do{
                    String linea= c.getString(0);
                    arreglo[i]= linea;
                    i++;
                }while (c.moveToNext());
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<>(this, R.layout.lista_ittem, arreglo);
            ListView lista= (ListView) findViewById(R.id.Lista);
            lista.setAdapter(adapter);
        }
    }

    public void LugaresVaroniles(){
        ArrayList<Equipos> vatos= new ArrayList<>();
        BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            Cursor c= db.rawQuery("select * from Equipos where Rama = 'Varonil'", null);
            int cantidad= c.getCount();
            int i= 0;
            String[] arreglo= new String[cantidad];
            if(c.moveToFirst()){
                do{
                    vatos.add(new Equipos( c.getString(0), c.getString(1), c.getInt(2),c.getInt(3),c.getInt(4),c.getInt(5), c.getInt(6), c.getInt(7)));
                    String linea= c.getString(0);
                    arreglo[i]= linea;
                    i++;
                }while (c.moveToNext());
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<>(this, R.layout.lista_ittem, arreglo);
            ListView lista= (ListView) findViewById(R.id.Lista);
            lista.setAdapter(adapter);


            
            Collections.sort(vatos, new Comparator<Equipos>() {
                @Override
                public int compare(Equipos p1, Equipos p2) {
                    // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
                    return new Integer(p2.getPartidosGanados() - p2.getPartidosPerdidos()).compareTo(new Integer(p1.getPartidosGanados() - p1.getPartidosPerdidos()));
                }
            });

            String[] ordenados= new String[vatos.size()];

            for(int x=0; x<vatos.size(); x++){
                ordenados[x]= vatos.get(x).getNombre();
            }

            ArrayAdapter<String> adapterord= new ArrayAdapter<>(this, R.layout.lista_ittem, ordenados);
            ListView listaord= (ListView) findViewById(R.id.Lista);
            lista.setAdapter(adapterord);
        }
    }

    public void LugaresFemeniles(){
        ArrayList<Equipos> vatos= new ArrayList<>();
        BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            Cursor c= db.rawQuery("select * from Equipos where Rama = 'Femenil'", null);
            int cantidad= c.getCount();
            int i= 0;
            String[] arreglo= new String[cantidad];
            if(c.moveToFirst()){
                do{
                    vatos.add(new Equipos( c.getString(0), c.getString(1), c.getInt(2),c.getInt(3),c.getInt(4),c.getInt(5), c.getInt(6), c.getInt(7)));
                    String linea= c.getString(0);
                    arreglo[i]= linea;
                    i++;
                }while (c.moveToNext());
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<>(this, R.layout.lista_ittem, arreglo);
            ListView lista= (ListView) findViewById(R.id.Lista);
            lista.setAdapter(adapter);



            Collections.sort(vatos, new Comparator<Equipos>() {
                @Override
                public int compare(Equipos p1, Equipos p2) {
                    // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
                    return new Integer(p2.getPartidosGanados() - p2.getPartidosPerdidos()).compareTo(new Integer(p1.getPartidosGanados() - p1.getPartidosPerdidos()));
                }
            });

            String[] ordenados= new String[vatos.size()];

            for(int x=0; x<vatos.size(); x++){
                ordenados[x]= vatos.get(x).getNombre();
            }

            ArrayAdapter<String> adapterord= new ArrayAdapter<>(this, R.layout.lista_ittem, ordenados);
            ListView listaord= (ListView) findViewById(R.id.Lista);
            lista.setAdapter(adapterord);
        }
    }


    public void VerFemeniles(){
        BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            Cursor c= db.rawQuery("select * from Equipos where Rama = 'Femenil'", null);
            int cantidad= c.getCount();
            int i= 0;
            String[] arreglo= new String[cantidad];
            if(c.moveToFirst()){
                do{
                    String linea= c.getString(0);
                    arreglo[i]= linea;
                    i++;
                }while (c.moveToNext());
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<>(this, R.layout.lista_ittem, arreglo);
            ListView lista= (ListView) findViewById(R.id.Lista);
            lista.setAdapter(adapter);
        }
    }



    public void Limpiar(){
        BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        db.execSQL("delete from Equipos");
        Toast.makeText(this, "Tabla limpiada", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
