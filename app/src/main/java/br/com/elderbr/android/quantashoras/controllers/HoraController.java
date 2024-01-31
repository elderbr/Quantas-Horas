package br.com.elderbr.android.quantashoras.controllers;

import android.content.Context;
import android.widget.EditText;

import br.com.elderbr.android.quantashoras.Conexao;
import br.com.elderbr.android.quantashoras.models.Hora;
import br.com.elderbr.android.quantashoras.models.Horario;

public class HoraController {

    private Conexao conexao;
    private Horario horario;
    private Hora hrJornada = new Hora(0, 0);
    private Hora hrMaxima = new Hora(0, 0);
    private Hora hrPercurso = new Hora(0, 0);
    private String hora;

    public HoraController(Context context) {
        conexao = new Conexao(context);
        horario = conexao.select();
    }

    public void setHoraNormal(EditText editText) {
        hora = editText.getText().toString().trim();
        if (hora.length() < 3 && !hora.contains(":")) {
            return;
        }
        hrJornada = new Hora(hora);
    }

    public void setMaxima(EditText editText) {
        hora = editText.getText().toString().trim();
        if (hora.length() < 3 && !hora.contains(":")) {
            return;
        }
        hrMaxima = new Hora(hora);
    }

    public void setPercurso(EditText editText) {
        hora = editText.getText().toString().trim();
        if (hora.length() < 3 && !hora.contains(":")) {
            return;
        }
        hrPercurso = new Hora(hora);
    }

    public void carrega(EditText etJornada, EditText etMaximo, EditText etPercurso){
        horario = conexao.select();
        etJornada.setText(horario.getJornada().toHoras());
        etMaximo.setText(horario.getHoraMaxima().toHoras());
        etPercurso.setText(horario.getPercurso().toHoras());
    }

    public boolean save() {
        boolean save = false;
        if (hrJornada.getDoubleHora() > 0 && !horario.getJornada().equals(hrJornada)) {
            horario.setJornada(hrJornada);
            save = true;
        }
        if (hrMaxima.getDoubleHora() > 0 && !horario.getHoraMaxima().equals(hrMaxima)) {
            horario.setHoraMaxima(hrMaxima);
            save = true;
        }
        if (hrPercurso.getDoubleHora() > 0 && !horario.getPercurso().equals(hrPercurso)) {
            horario.setPercurso(hrPercurso);
            save = true;
        }
        if (save) {
            return (conexao.update(horario) > 0);
        }
        return false;
    }
}
