package br.com.elderbr.android.quantashoras;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Hora{

    private Calendar data = Calendar.getInstance(new Locale("pt","BR"));
    SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    public Hora() {
        data = Calendar.getInstance(new Locale("pt","BR"));
        data.set(Calendar.DAY_OF_YEAR, 1);
    }

    public Hora(int hora, int minutos) {
        data = Calendar.getInstance(new Locale("pt","BR"));
        data.set(Calendar.DAY_OF_YEAR, 1);
        data.set(Calendar.HOUR_OF_DAY, hora);
        data.set(Calendar.MINUTE, minutos);
    }

    public Hora setHora(int hora){
        data.set(Calendar.HOUR_OF_DAY, hora);
        return this;
    }

    public Hora setHora(int hora, int minutos){
        data.set(Calendar.MINUTE, minutos);
        data.set(Calendar.HOUR_OF_DAY, hora);
        return this;
    }

    public Hora setHora(String hora){
        parse(hora);
        return this;
    }

    public Hora setHora(Hora hora){
        data.set(Calendar.DAY_OF_YEAR, 1);
        data.set(Calendar.HOUR_OF_DAY, hora.getHora());
        data.set(Calendar.MINUTE, hora.getMinutos());
        return this;
    }

    public int getHora(){
        return data.get(Calendar.HOUR_OF_DAY);
    }

    public Hora setMinutos(int minutos){
        data.set(Calendar.MINUTE, minutos);
        return this;
    }

    public Hora subHoraMinuto(int hora, int minutos){
        data.add(Calendar.MINUTE, -minutos);
        data.add(Calendar.HOUR, -hora);
        return this;
    }

    public double getDoubleHora(){
        int hora = data.get(Calendar.HOUR_OF_DAY);
        int minutos = data.get(Calendar.MINUTE);
        if(minutos<10){
            return Double.parseDouble(hora+".0"+minutos);
        }
        return Double.parseDouble(hora+"."+minutos);
    }

    public int getMinutos(){
        return data.get(Calendar.MINUTE);
    }

    public Hora subtrair(Hora hora){
        if(getHora() < hora.getHora()){
            hora.addDia(1);
        }
        data.add(Calendar.MINUTE, -hora.getMinutos());
        data.add(Calendar.HOUR_OF_DAY, -hora.getHora());
        return this;
    }

    public Hora subtrair(int hora, int minutos){
        data.add(Calendar.MINUTE, -minutos);
        data.add(Calendar.HOUR_OF_DAY, -hora);
        return this;
    }

    public Hora somar(Hora hora){
        data.add(Calendar.MINUTE, hora.getMinutos());
        data.add(Calendar.HOUR_OF_DAY, hora.getHora());
        return this;
    }


    public String toHoras(){
        return format.format(data.getTime());
    }


    public Hora parse(String hora){
        data.set(Calendar.HOUR_OF_DAY, 0);
        data.set(Calendar.MINUTE, 0);
        if(hora == null || !hora.contains(":")){
            return this;
        }

        String[] hrs = hora.split(":");

        if(hrs[0] == null || hrs[1] == null){
            return this;
        }

        int h = Integer.parseInt(hrs[0]);
        int m = Integer.parseInt(hrs[1]);

        data.set(Calendar.HOUR_OF_DAY, h);
        data.set(Calendar.MINUTE, m);

        return this;
    }

    public int getDiaAno(){
        return data.get(Calendar.DAY_OF_YEAR);
    }

    public String toDia(){
        return ""+data.get(Calendar.DAY_OF_MONTH);
    }

    public Hora addDia(int dia){
        data.add(Calendar.DAY_OF_YEAR, dia);
        return this;
    }



}
