package br.com.elderbr.android.quantashoras.controllers;

import android.content.Context;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import br.com.elderbr.android.quantashoras.Conexao;
import br.com.elderbr.android.quantashoras.MainActivity;
import br.com.elderbr.android.quantashoras.models.Hora;
import br.com.elderbr.android.quantashoras.models.Horario;
import br.com.elderbr.android.quantashoras.utils.Msg;

public class HoraController {

    private Conexao conexao = new Conexao(null);
    private Horario horario;
    private Hora jornadaNormal;
    private Hora horaMaxima;
    private String hora;

    public HoraController(Context context) {
        conexao = new Conexao(context);
    }

    public void setJornada(@NotNull EditText editText) throws Exception {
        jornadaNormal = null;
        hora = editText.getText().toString().trim();
        if (hora.length() >= 3 && hora.contains(":")) {
            jornadaNormal = new Hora(hora);
            if (jornadaNormal.getDoubleHora() < 4) {
                jornadaNormal = null;
                throw new Exception("A jornada normal não pode ser menor que 4 horas!");
            }
        }
    }

    public void setHoraMaxima(@NotNull EditText editText) throws Exception {
        horaMaxima = null;
        hora = editText.getText().toString().trim();
        if (hora.length() >= 3 && hora.contains(":")) {
            horaMaxima = new Hora(hora);
            if (horaMaxima.getDoubleHora() < 4) {
                horaMaxima = null;
                throw new Exception("A hora máxima de hora extra não pode ser menor que 4 horas!");
            }
        }
    }

    public long addHora() throws Exception {
        if(jornadaNormal == null){
            throw new Exception("Digite a jornada normal de trabalho!");
        }
        if(horaMaxima == null){
            throw new Exception("Digite a hora máxima de hora extra!");
        }
        horario = new Horario();
        horario.setJornadaNormal(jornadaNormal);
        horario.setHoraMaxima(horaMaxima);
        return conexao.insert(horario);
    }

    public long updateHora() throws Exception {
        if(jornadaNormal == null){
            throw new Exception("Digite a jornada normal de trabalho!");
        }
        if(horaMaxima == null){
            throw new Exception("Digite a hora máxima de hora extra!");
        }
        horario = new Horario();
        horario.setJornadaNormal(jornadaNormal);
        horario.setHoraMaxima(horaMaxima);
        return conexao.update(horario);
    }

    public int addUpdate() throws Exception {
        if(conexao.select() == null){
            Msg.Log("Se foi adicionado >> "+addHora(), getClass());
            return 1;
        }else{
            updateHora();
            return 2;
        }
    }
}
