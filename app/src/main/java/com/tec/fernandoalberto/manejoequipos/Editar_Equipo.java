package com.tec.fernandoalberto.manejoequipos;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Editar_Equipo extends AppCompatActivity {


    TextView Nombre, Rama, PTG, PTP, PNG, PNP;
    EditText EditPO, EditPR;
    ToggleButton GANPER;
    String SNombre, SRama;
    int SPTG, SPTP, SPNG, SPNP;
    Spinner spnEditar;
    Button Borrar, RJuego;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__equipo);
        LeerBD();
        Nombre= findViewById(R.id.lblNombre);
        Rama= findViewById(R.id.lblRama);
        PTG= findViewById(R.id.lblPartidos_Ganados);
        PTP= findViewById(R.id.lblPartidos_Perdidos);
        PNG= findViewById(R.id.lblPuntos_Favor);
        PNP= findViewById(R.id.lblPuntos_Contra);
        Nombre.setText(SNombre);
        Rama.setText("Rama " + SRama);
        PTG.setText("Partidos Ganados: " + SPTG);
        PTP.setText("Partidos Perdidos: " + SPTP);
        PNG.setText("Puntos Obtenidos: " + SPNG);
        PNP.setText("Puntos Contra: " + SPNP);
        spnEditar= findViewById(R.id.spnVS);
        EditPO= findViewById(R.id.editPO);
        EditPR= findViewById(R.id.editPR);
        GANPER= findViewById(R.id.togleR);
        LlenarVS();
        Borrar= findViewById(R.id.Eliminar);
        RJuego= findViewById(R.id.btnRegistrarJuego);
        if(spnEditar.getCount()==0){
            RJuego.setEnabled(false);
            EditPO.setEnabled(false);
            EditPR.setEnabled(false);
            GANPER.setEnabled(false);
        }else{
            RJuego.setEnabled(true);
            EditPO.setEnabled(true);
            EditPR.setEnabled(true);
            GANPER.setEnabled(true);
        }
        RJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditPO.getText().toString().length()==0 || EditPR.getText().toString().length()==0){
                    Toast.makeText(Editar_Equipo.this, "Introduce bien los datos", Toast.LENGTH_SHORT).show();
                }else{
                    String rival= String.valueOf(spnEditar.getSelectedItem());

                    int PuntosO= Integer.parseInt(EditPO.getText().toString());
                    int PuntosR= Integer.parseInt(EditPR.getText().toString());
                    int PuntosOFINAL= PuntosO + SPNG;
                    int PuntosRFINAL= PuntosR + SPNP;
                    BaseHelper baseHelper = new BaseHelper(Editar_Equipo.this, "Torneo", null, 1);
                    SQLiteDatabase db = baseHelper.getWritableDatabase();
                    if(GANPER.isChecked()){
                        SPTG++;
                        //Suma partido ganado a nativo
                        db.execSQL("Update Equipos Set Partidos_Ganados = " + SPTG + " where Nombre = '" + SNombre + "'");
                    }else{
                        SPTP++;
                        //Suma partido perdido a nativo
                        db.execSQL("Update Equipos Set Partidos_Perdidos = " + SPTP + " where Nombre = '" + SNombre + "'");
                    }
                    //Suma puntos a favor
                    db.execSQL("Update Equipos Set Puntos_Favor = " + PuntosOFINAL + " where Nombre = '" + SNombre + "'");
                    //Suma puntos en contra
                    db.execSQL("Update Equipos Set Puntos_Contra = " + PuntosRFINAL + " where Nombre = '" + SNombre + "'");

                    int partidosganadosrival=0;
                    int partidosperdidosrival=0;
                    int puntosfavorrival=0;
                    int puntoscontrarival=0;
                    if (db != null) {
                        Cursor c= db.rawQuery("select * from Equipos", null);
                        if(c.moveToFirst()){
                            do{
                                if(c.getString(0).equals(rival)){
                                    partidosganadosrival= c.getInt(2);
                                    partidosperdidosrival= c.getInt(3);
                                    puntosfavorrival= c.getInt(4);
                                    puntoscontrarival= c.getInt(5);
                                }
                            }while (c.moveToNext());
                        }
                    }

                    if(GANPER.isChecked()){
                        partidosperdidosrival++;
                        //Suma partido perdido a rival
                        db.execSQL("Update Equipos Set Partidos_Perdidos = " + partidosperdidosrival + " where Nombre = '" + rival + "'");
                    }else{
                        partidosganadosrival++;
                        //Suma partido ganado a rival
                        db.execSQL("Update Equipos Set Partidos_Ganados = " + partidosganadosrival + " where Nombre = '" + rival + "'");
                    }

                    puntosfavorrival= puntosfavorrival + PuntosR;
                    //Suma puntos a favor rival
                    db.execSQL("Update Equipos Set Puntos_Favor = " + puntosfavorrival + " where Nombre = '" + rival + "'");
                    puntoscontrarival= puntoscontrarival + PuntosO;
                    //Suma puntos en contra rival
                    db.execSQL("Update Equipos Set Puntos_Contra = " + puntoscontrarival + " where Nombre = '" + rival + "'");
                    Toast.makeText(Editar_Equipo.this, "Partido finalizado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Editar_Equipo.this, MainActivity.class));
                }
            }
        });
        Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Editar_Equipo.this);
                builder.setMessage("¿Quiere eliminar este equipo?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Eliminar();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void LeerBD(){
        BaseHelper baseHelper = new BaseHelper(this, "Torneo", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            Cursor c= db.rawQuery("select * from Equipos", null);
            if(c.moveToFirst()){
                do{
                    if(c.getString(0).equals(MainActivity.Equipo_Editar)){
                        SNombre= c.getString(0);
                        SRama= c.getString(1);
                        SPTG= c.getInt(2);
                    SPTP= c.getInt(3);
                    SPNG= c.getInt(4);
                    SPNP= c.getInt(5);
                    }
                }while (c.moveToNext());
            }
        }
    }


    public void LlenarVS(){
        BaseHelper baseHelper = new BaseHelper(this, "Torneo", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            Cursor c= db.rawQuery("select * from Equipos", null);
            ArrayList<String> arreglo= new ArrayList<>();
            if(c.moveToFirst()){
                do{
                    if(c.getString(1).equals(SRama)){
                        if(SNombre.equals(c.getString(0))) {
                        }else{
                        arreglo.add(c.getString(0));
                    }
                    }
                }while (c.moveToNext());
            }
            spnEditar.setAdapter(new ArrayAdapter<String>(this, R.layout.lista_editar, arreglo));
        }
    }

    public void Eliminar(){
        BaseHelper baseHelper = new BaseHelper(this, "Torneo", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        db.execSQL("delete from Equipos where Nombre = '" + SNombre + "'");
        Toast.makeText(this, "Se ha eliminado este alumno", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
