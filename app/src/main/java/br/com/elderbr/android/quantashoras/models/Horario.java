package br.com.elderbr.android.quantashoras.models;

import java.util.ArrayList;
import java.util.List;

public class Horario {

    private Hora jornadaNormal;
    private Hora horaMaxima;
    private List<Horario> listaHorario = new ArrayList<>();
    public static String[] columnName = new String[]{"jornada", "hora_maxima"};

    public Horario() {
    }

    public Hora getJornadaNormal() {
        return jornadaNormal;
    }

    public void setJornadaNormal(Hora jornadaNormal) {
        this.jornadaNormal = jornadaNormal;
    }

    public Hora getHoraMaxima() {
        return horaMaxima;
    }

    public void setHoraMaxima(Hora horaMaxima) {
        this.horaMaxima = horaMaxima;
    }

    public Horario addHorario(Horario horario){
        listaHorario.add(horario);
        return this;
    }

    public List<Horario> getListaHorario() {
        return listaHorario;
    }

    public static String ColumnJornadaNormal(){
        return columnName[0];
    }

    public static String ColumnHoraMaxima(){
        return columnName[1];
    }
}
