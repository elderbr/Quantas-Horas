package br.com.elderbr.android.quantashoras.models;

import java.util.ArrayList;
import java.util.List;

public class Horario {

    private Hora jornada = new Hora(7,40);
    private Hora horaMaxima = new Hora(10,50);

    private Hora percurso = new Hora(0, 30);
    private List<Horario> listaHorario = new ArrayList<>();
    public static String[] columnName = new String[]{"jornada", "hora_maxima", "hr_percurso"};

    public Horario() {
    }

    public Hora getJornada() {
        return jornada;
    }

    public void setJornada(Hora jornada) {
        this.jornada = jornada;
    }

    public Hora getHoraMaxima() {
        return horaMaxima;
    }

    public void setHoraMaxima(Hora horaMaxima) {
        this.horaMaxima = horaMaxima;
    }

    public void setPercurso(Hora hora){
        this.percurso = hora;
    }

    public Hora getPercurso(){
        return percurso;
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

    public static String ColumnPercurso(){
        return columnName[2];
    }
}
