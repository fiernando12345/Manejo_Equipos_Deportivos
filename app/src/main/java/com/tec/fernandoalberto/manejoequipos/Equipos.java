package com.tec.fernandoalberto.manejoequipos;

public class Equipos {
     private String Nombre;
    private String Rama;
    private int PartidosGanados;
    private int PartidosPerdidos;
    private int PuntosFavor;
    private int PuntosContra;
    private int SetsGanados;
    private int SetsPerdidos;

    public Equipos(String nombre, String rama, int partidosGanados, int partidosPerdidos, int puntosFavor, int puntosContra, int setsGanados, int setsPerdidos) {
        Nombre = nombre;
        Rama = rama;
        PartidosGanados = partidosGanados;
        PartidosPerdidos = partidosPerdidos;
        PuntosFavor = puntosFavor;
        PuntosContra = puntosContra;
        SetsGanados = setsGanados;
        SetsPerdidos = setsPerdidos;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getRama() {
        return Rama;
    }

    public void setRama(String rama) {
        Rama = rama;
    }

    public int getPartidosGanados() {
        return PartidosGanados;
    }

    public void setPartidosGanados(int partidosGanados) {
        PartidosGanados = partidosGanados;
    }

    public int getPartidosPerdidos() {
        return PartidosPerdidos;
    }

    public void setPartidosPerdidos(int partidosPerdidos) {
        PartidosPerdidos = partidosPerdidos;
    }

    public int getPuntosFavor() {
        return PuntosFavor;
    }

    public void setPuntosFavor(int puntosFavor) {
        PuntosFavor = puntosFavor;
    }

    public int getPuntosContra() {
        return PuntosContra;
    }

    public void setPuntosContra(int puntosContra) {
        PuntosContra = puntosContra;
    }

    public int getSetsGanados() {
        return SetsGanados;
    }

    public void setSetsGanados(int setsGanados) {
        SetsGanados = setsGanados;
    }

    public int getSetsPerdidos() {
        return SetsPerdidos;
    }

    public void setSetsPerdidos(int setsPerdidos) {
        SetsPerdidos = setsPerdidos;
    }
}
