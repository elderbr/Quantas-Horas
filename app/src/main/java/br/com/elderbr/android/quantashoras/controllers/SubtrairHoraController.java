package br.com.elderbr.android.quantashoras.controllers;

import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import br.com.elderbr.android.quantashoras.models.Hora;

public class SubtrairHoraController {

    private Hora hora_01;
    private Hora hora_02;
    private Hora total = new Hora(0,0);
    private String hora;

    public SubtrairHoraController() {
    }

    public void setHora01(@NotNull EditText editText) throws Exception{
        hora_01 = null;
        hora = editText.getText().toString().trim();
        if(hora.split(":").length == 2){
            hora_01 = new Hora(hora);
        }
    }
    public void setHora02(@NotNull EditText editText) throws Exception{
        hora_02 = null;
        hora = editText.getText().toString().trim();
        if(hora.split(":").length == 2){
            hora_02 = new Hora(hora);
        }
    }

    public String toTotal() {
        total = new Hora(0,0);
        if(hora_01 != null && hora_02 != null) {
            total = new Hora(hora_01);
            total.subtrair(hora_02);
        }
        return total.toHoras();
    }
}
