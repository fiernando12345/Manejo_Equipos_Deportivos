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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Editar_Equipo extends AppCompatActivity {


    TextView Nombre, Rama, PTG, PTP, SG, SP, PNG, PNP;
    EditText EditPO, EditPR;
    ToggleButton GANPER;
    String SNombre, SRama;
    int SPTG, SPTP, SSG, SSP, SPNG, SPNP, CuantosSetsSon, SetsGanados;
    Spinner spnEditar, spnCuantosSets, spnSetsGanados;
    Button Borrar, RJuego;
    ArrayList<Integer> tressets= new ArrayList<>();
    ArrayList<Integer> cincosets= new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__equipo);
        LeerBD();
        ArrayList<Integer> cuantossets= new ArrayList<>();
        cuantossets.add(3);
        cuantossets.add(5);
        tressets.add(2);
        tressets.add(3);
        cincosets.add(3);
        cincosets.add(4);
        cincosets.add(5);
        spnCuantosSets= findViewById(R.id.spnSets);
        spnSetsGanados= findViewById(R.id.spnSets_Ganados);
        spnCuantosSets.setAdapter(new ArrayAdapter<Integer>(this, R.layout.lista_editar, cuantossets));
        spnSetsGanados.setAdapter(new ArrayAdapter<Integer>(this, R.layout.lista_editar, tressets));
        spnCuantosSets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        spnSetsGanados.setAdapter(new ArrayAdapter<Integer>(Editar_Equipo.this, R.layout.lista_editar, tressets));
                        break;
                    case 1:
                        spnSetsGanados.setAdapter(new ArrayAdapter<Integer>(Editar_Equipo.this, R.layout.lista_editar, cincosets));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Nombre= findViewById(R.id.lblNombre);
        Rama= findViewById(R.id.lblRama);
        PTG= findViewById(R.id.lblPartidos_Ganados);
        PTP= findViewById(R.id.lblPartidos_Perdidos);
        SG= findViewById(R.id.lblSets_Ganados);
        SP= findViewById(R.id.lblSets_Perdidos);
        PNG= findViewById(R.id.lblPuntos_Favor);
        PNP= findViewById(R.id.lblPuntos_Contra);
        Nombre.setText(SNombre);
        Rama.setText("Rama " + SRama);
        PTG.setText("Partidos Ganados: " + SPTG);
        PTP.setText("Partidos Perdidos: " + SPTP);
        PNG.setText("Puntos Obtenidos: " + SPNG);
        PNP.setText("Puntos Contra: " + SPNP);
        SG.setText("Sets Ganados: " + SSG);
        SP.setText("Sets Perdidos: " + SSP);
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
            spnCuantosSets.setEnabled(false);
            spnSetsGanados.setEnabled(false);
        }else{
            RJuego.setEnabled(true);
            EditPO.setEnabled(true);
            EditPR.setEnabled(true);
            GANPER.setEnabled(true);
            spnCuantosSets.setEnabled(true);
            spnSetsGanados.setEnabled(true);
        }
        RJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditPO.getText().toString().length()==0 || EditPR.getText().toString().length()==0){
                    Toast.makeText(Editar_Equipo.this, "Introduce bien los datos", Toast.LENGTH_SHORT).show();
                }else{
                    CuantosSetsSon=Integer.parseInt(spnCuantosSets.getSelectedItem().toString());
                    SetsGanados=Integer.parseInt(spnSetsGanados.getSelectedItem().toString());
                    if(Integer.parseInt(spnCuantosSets.getSelectedItem().toString())==3){
                        CuantosSetsSon=2;
                    }else{
                        CuantosSetsSon=3;
                    }
                    String rival= String.valueOf(spnEditar.getSelectedItem());

                    int PuntosO= Integer.parseInt(EditPO.getText().toString());
                    int PuntosR= Integer.parseInt(EditPR.getText().toString());

                    int PuntosOFINAL= PuntosO + SPNG;
                    int PuntosRFINAL= PuntosR + SPNP;
                    BaseHelper baseHelper = new BaseHelper(Editar_Equipo.this, "Torneos", null, 1);
                    SQLiteDatabase db = baseHelper.getWritableDatabase();
                    if(GANPER.isChecked()){
                        SPTG++;
                        //Suma partido ganado a nativo
                        db.execSQL("Update Equipos Set Partidos_Ganados = " + SPTG + " where Nombre = '" + SNombre + "'");

                        if(SetsGanados==CuantosSetsSon){
                            SSG= SSG + SetsGanados;
                        }else{
                            SSG= SSG + CuantosSetsSon;
                            SSP= SSP + (SetsGanados - CuantosSetsSon);
                        }
                        db.execSQL("Update Equipos Set Sets_Ganados = " + SSG + " where Nombre = '" + SNombre + "'");
                        db.execSQL("Update Equipos Set Sets_Perdidos = " + SSP + " where Nombre = '" + SNombre + "'");
                    }else{
                        SPTP++;
                        //Suma partido perdido a nativo
                        db.execSQL("Update Equipos Set Partidos_Perdidos = " + SPTP + " where Nombre = '" + SNombre + "'");

                        if(SetsGanados==CuantosSetsSon){
                            SSP= SSP + SetsGanados;
                        }else{
                            SSG= SSG + ( SetsGanados - CuantosSetsSon );
                            SSP= SSP + CuantosSetsSon;
                        }
                        db.execSQL("Update Equipos Set Sets_Ganados = " + SSG + " where Nombre = '" + SNombre + "'");
                        db.execSQL("Update Equipos Set Sets_Perdidos = " + SSP + " where Nombre = '" + SNombre + "'");
                    }
                    //Suma puntos a favor
                    db.execSQL("Update Equipos Set Puntos_Favor = " + PuntosOFINAL + " where Nombre = '" + SNombre + "'");
                    //Suma puntos en contra
                    db.execSQL("Update Equipos Set Puntos_Contra = " + PuntosRFINAL + " where Nombre = '" + SNombre + "'");

                    int partidosganadosrival=0;
                    int partidosperdidosrival=0;
                    int setsganadosrival=0;
                    int setsperdidosrival=0;
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
                                    setsganadosrival= c.getInt(6);
                                    setsperdidosrival= c.getInt(7);
                                }
                            }while (c.moveToNext());
                        }
                    }

                    if(GANPER.isChecked()){
                        partidosperdidosrival++;
                        //Suma partido perdido a rival
                        db.execSQL("Update Equipos Set Partidos_Perdidos = " + partidosperdidosrival + " where Nombre = '" + rival + "'");

                        if(SetsGanados==CuantosSetsSon){
                            setsperdidosrival= setsperdidosrival + SetsGanados;
                        }else{
                            setsganadosrival= setsganadosrival + (SetsGanados - CuantosSetsSon);
                            setsperdidosrival= setsperdidosrival + CuantosSetsSon;
                        }

                    }else{
                        partidosganadosrival++;
                        //Suma partido ganado a rival
                        db.execSQL("Update Equipos Set Partidos_Ganados = " + partidosganadosrival + " where Nombre = '" + rival + "'");

                        if(SetsGanados==CuantosSetsSon){
                            setsganadosrival= setsganadosrival + SetsGanados;
                        }else {
                            setsganadosrival = setsganadosrival + CuantosSetsSon;
                            setsperdidosrival = setsperdidosrival + (SetsGanados - CuantosSetsSon);
                        }
                    }
                    db.execSQL("Update Equipos Set Sets_Ganados = " + setsganadosrival + " where Nombre = '" + rival + "'");
                    db.execSQL("Update Equipos Set Sets_Perdidos = " + setsperdidosrival + " where Nombre = '" + rival + "'");
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
        BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
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
                        SSG= c.getInt(6);
                        SSP=c.getInt(7);
                    }
                }while (c.moveToNext());
            }
        }
    }


    public void LlenarVS(){
        BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
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
        BaseHelper baseHelper = new BaseHelper(this, "Torneos", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        db.execSQL("delete from Equipos where Nombre = '" + SNombre + "'");
        Toast.makeText(this, "Se ha eliminado el equipo " + SNombre, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
