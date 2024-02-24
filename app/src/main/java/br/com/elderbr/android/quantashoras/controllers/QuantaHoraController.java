package br.com.elderbr.android.quantashoras.controllers;

import android.content.Context;
import android.widget.EditText;

import java.time.LocalTime;

import br.com.elderbr.android.quantashoras.Conexao;
import br.com.elderbr.android.quantashoras.models.Hora;
import br.com.elderbr.android.quantashoras.models.Horario;
import br.com.elderbr.android.quantashoras.utils.Msg;

public class QuantaHoraController {

    private Conexao conexao;
    private Horario horario;
    private Hora hrEntrada = new Hora(0, 0);
    private Hora hrSaida = new Hora(0, 0);
    private Hora hrTempo = new Hora(0, 0);
    private Hora hrHora = new Hora(0, 0);
    private Hora hrExtra = new Hora(0, 0);
    private Hora hrNoturno = new Hora(0, 0);
    private Hora hrSobrando = new Hora(0, 0);
    private Hora hrFechamento = new Hora(0, 0);
    private Hora hrNormal = new Hora(7, 50);
    private Hora hrMaxima = new Hora(10, 50);

    public QuantaHoraController() {
    }

    public QuantaHoraController(Context context) {
        conexao = new Conexao(context);
        horario = conexao.select();

        hrNormal = horario.getJornada();
        hrMaxima = horario.getHoraMaxima();

    }

    public Hora getHora() {
        return hrHora;
    }

    public String toHora() {
        return hrHora.toHoras();
    }

    public Hora getExtra() {
        return hrExtra;
    }

    public String toExtra() {
        return hrExtra.toHoras();
    }

    public Hora getNoturno() {
        return hrNoturno;
    }

    public String toNoturno() {
        return hrNoturno.toHoras();
    }

    public Hora getSobrando() {
        return hrSobrando;
    }

    public String toSobrando() {
        return hrSobrando.toHoras();
    }

    public Hora getFechamento() {
        return hrFechamento;
    }

    public String toFechamento() {
        return hrFechamento.toHoras();
    }

    public void calcular(EditText etEntrada, EditText etSaida, EditText etTempo,
                         EditText etHora, EditText etExtra, EditText etNoturno,
                         EditText etDevendo, EditText etFechamento) {

        try {
            String entrada = etEntrada.getText().toString().trim();
            String saida = etSaida.getText().toString().trim();
            String tempo = etTempo.getText().toString().trim();

            // Pegando a entrada
            hrEntrada = new Hora(0, 0);
            if (entrada.isBlank() || !entrada.contains(":") || entrada.split(":").length < 2) {
                hrEntrada.setHora(0, 0);
                setEdits(etHora, etExtra, etNoturno, etDevendo, etFechamento);
                return;
            }
            hrEntrada.setHora(entrada);

            // Pegando a saída
            hrSaida = new Hora(0, 0);
            if (saida.isBlank() || !saida.contains(":") || saida.split(":").length < 2) {
                hrSaida.setHora(0, 0);
                setEdits(etHora, etExtra, etNoturno, etDevendo, etFechamento);
                return;
            }
            hrSaida.setHora(saida);

            // Se o tempo conter hora
            hrTempo = new Hora(0, 0);
            if (tempo.isBlank() || !tempo.contains(":")) {
                hrTempo = new Hora(0, 0);
            } else {
                hrTempo.setHora(tempo);
            }

            // Calculando o fechamento
            fechamento();
            // Calculando as horas trabalhadas
            trabalhada();
            // Calculando a hora extra
            extra();
            // Calculando o adicional noturno
            noturno();
            // Calculando horas na casa
            sobrando();

            etFechamento.setText(hrFechamento.toHoras());
            etHora.setText(hrHora.toHoras());
            etExtra.setText(hrExtra.toHoras());
            etNoturno.setText(hrNoturno.toHoras());
            etDevendo.setText(hrSobrando.toHoras());


        } catch (Exception e) {
            Msg.Erro("Erro ao calcular horas trabalhadas: " + e.getMessage());
        }

    }

    public void calcular(Hora entrada, Hora saida, Hora tempo) {

        hrEntrada = new Hora(entrada);
        hrSaida = new Hora(saida);
        hrTempo = new Hora(tempo);

        // Calculando o fechamento
        fechamento();

        // Calculando as horas trabalhadas
        trabalhada();

        // Calculando a hora extra
        extra();

        // Calculando o adicional noturno
        noturno();

        // Calculando horas na casa
        sobrando();
    }

    private void fechamento() {
        hrFechamento = new Hora(hrSaida);
        hrFechamento.somar(hrTempo);
        if (hrEntrada.getDoubleHora() > hrFechamento.getDoubleHora()) {
            hrFechamento.addDia(1);
        }
    }

    private void trabalhada() {
        hrHora = new Hora(hrFechamento);
        hrHora.subtrair(hrEntrada);
        if (hrHora.getDoubleHora() > hrMaxima.getDoubleHora()) {
            sobrando();
            hrFechamento.subtrair(hrSobrando);
        }
    }

    private void extra() {
        hrExtra = new Hora(0, 0);
        if (hrHora.getDoubleHora() > hrNormal.getDoubleHora()) {
            hrExtra = new Hora(hrHora);
            hrExtra.subtrair(hrNormal);
        }
    }

    private void noturno() {

        Hora fechamento = new Hora(hrSaida);
        fechamento.somar(hrTempo);
        if (hrEntrada.getDoubleHora() > fechamento.getDoubleHora()) {
            fechamento.addDia(1);
        }

        hrNoturno = new Hora(0, 0);

        // Entrada maior que 22
        if (hrEntrada.getDoubleHora() >= 22) {
            if (fechamento.getDoubleHora() >= 22 || fechamento.getDoubleHora() <= 5) {
                hrNoturno.setHora(fechamento);
            } else {
                hrNoturno.setHora("05:00");
            }
            hrNoturno.subtrair(hrEntrada);
            return;
        }

        // Entrada menor ou igual a 5
        if (hrEntrada.getDoubleHora() <= 5) {
            if (fechamento.getDoubleHora() >= 5) {
                hrNoturno.setHora(5, 0);
            } else {
                hrNoturno.setHora(fechamento);
            }
            hrNoturno.subtrair(hrEntrada);
            return;
        }
        if (hrEntrada.getDoubleHora() > 5 && hrEntrada.getDoubleHora() <= 22) {
            if (fechamento.getDoubleHora() >= 22 || fechamento.getDoubleHora() <= 5) {
                hrNoturno.setHora(fechamento);
                hrNoturno.subtrair(22, 0);
                return;
            }
            if (fechamento.getDoubleHora() >= 5) {
                hrNoturno = new Hora(7,0);
            }
        }

    }

    private void sobrando() {
        hrSobrando = new Hora(0, 0);
        if (hrHora.getDoubleHora() > hrMaxima.getDoubleHora()) {
            hrSobrando = new Hora(hrHora);
            hrSobrando.subtrair(hrMaxima);
        }
    }

    public void limpar(EditText etEntrada, EditText etSaida, EditText etTempo, EditText etHora, EditText etDevendo, EditText etFechamento,
                       EditText etSoma1, EditText etSoma2, EditText etSomaTotal,
                       EditText etSubtrair1, EditText etSubtrair2, EditText etSubtrairTotal) {
        etEntrada.setText("");
        etSaida.setText("");
        etTempo.setText("00:00");
        etHora.setText("00:00");
        etDevendo.setText("00:00");
        etFechamento.setText("00:00");

        // EditText que soma
        etSoma1.setText("");
        etSoma2.setText("");
        etSomaTotal.setText("00:00");

        // EditText que subtrai
        etSubtrair1.setText("");
        etSubtrair2.setText("");
        etSubtrairTotal.setText("00:00");

        // Da foco do EditText entrada
        etEntrada.requestFocus();
    }

    public void btnCalcular(EditText etEntrada, EditText etSaida, EditText etTempo, EditText etHora, EditText etDevendo, EditText etFechamento) {

        try {
            String entrada = etEntrada.getText().toString().trim();
            String saida = etSaida.getText().toString().trim();
            String tempo = etTempo.getText().toString().trim();

            // Pegando a entrada
            if (entrada.isBlank() || !entrada.contains(":")) {
                hrEntrada = new Hora();
                etEntrada.setText(hrEntrada.toHoras());
            } else {
                hrEntrada.setHora(entrada);
            }


            hrSaida = new Hora(hrEntrada);
            hrSaida.somar(horario.getJornada());
            etSaida.setText(hrSaida.toHoras());

            hrHora.setHora(hrSaida);
            hrHora.subtrair(hrEntrada);
            etHora.setText(hrHora.toHoras());

            etTempo.setText("00:00");
            etDevendo.setText("00:00");
            etFechamento.setText(hrSaida.toHoras());

            etEntrada.requestFocus();

        } catch (Exception e) {
            Msg.Erro("Erro ao calcular horas trabalhadas: " + e.getMessage());
        }

    }

    public void somar(EditText etHora1, EditText etHora2, EditText etTotal) {

        try {
            String hora1 = etHora1.getText().toString().trim();
            String hora2 = etHora2.getText().toString().trim();

            Hora total = new Hora(0, 0);

            if (hora1.isBlank() || !hora1.contains(":")) {
                return;
            }

            if (hora2.isBlank() || !hora2.contains(":")) {
                return;
            }

            total.setHora(hora1);
            total.somar(hora2);
            etTotal.setText(total.toHoras());
        } catch (Exception e) {
            etTotal.setText("00:00");
        }

    }

    public void subtrair(EditText etHora1, EditText etHora2, EditText etTotal) {

        try {

            String hora1 = etHora1.getText().toString().trim();
            String hora2 = etHora2.getText().toString().trim();

            Hora total = new Hora(0, 0);

            if (hora1.isBlank() || !hora1.contains(":")) {
                return;
            }

            if (hora2.isBlank() || !hora2.contains(":")) {
                return;
            }

            total.setHora(hora1);
            total.subtrair(hora2);
            etTotal.setText(total.toHoras());
        } catch (Exception e) {
            etTotal.setText("00:00");
        }

    }


    private void setEdits(EditText etHora, EditText etExtra, EditText etNoturno, EditText etDevendo, EditText etFechamento) {
        etHora.setText("00:00");
        etExtra.setText("00:00");
        etNoturno.setText("00:00");
        etDevendo.setText("00:00");
        etFechamento.setText("00:00");
    }
}
