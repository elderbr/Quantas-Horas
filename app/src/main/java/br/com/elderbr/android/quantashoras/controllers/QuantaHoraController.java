package br.com.elderbr.android.quantashoras.controllers;

import android.content.Context;
import android.widget.EditText;

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
    private Hora hrDevendo = new Hora(0, 0);
    private Hora hrFechamento = new Hora(0, 0);

    private Hora trabalhada = new Hora(0, 0);
    private Hora hrNormal = new Hora(0, 0);
    private Hora hrMaxima = new Hora(0, 0);

    public QuantaHoraController(Context context) {
        conexao = new Conexao(context);
        horario = conexao.select();

        hrNormal = horario.getJornada();
        hrMaxima = horario.getHoraMaxima();

    }

    public void calcular(EditText etEntrada, EditText etSaida, EditText etTempo, EditText etHora, EditText etDevendo, EditText etFechamento) {

        try {
            String entrada = etEntrada.getText().toString().trim();
            String saida = etSaida.getText().toString().trim();
            String tempo = etTempo.getText().toString().trim();

            // Pegando a entrada
            if (entrada.isBlank() || !entrada.contains(":")) {
                hrEntrada.setHora(0, 0);
                setEdits(etHora, etDevendo, etFechamento);
                return;
            }
            hrEntrada.setHora(entrada);

            // Pegando a saída
            if (saida.isBlank() || !saida.contains(":")) {
                hrSaida.setHora(0, 0);
                setEdits(etHora, etDevendo, etFechamento);
                return;
            }
            hrSaida.setHora(saida);


            // Calculando a hora trabalhada
            trabalhada.setHora(hrSaida);
            if (hrSaida.getDoubleHora() < hrEntrada.getDoubleHora()) {
                trabalhada.addDia(1);
            }

            // Se o tempo conter hora
            if (tempo.isBlank() || !tempo.contains(":")) {
                hrTempo = new Hora(0, 0);
            } else {
                hrTempo.setHora(tempo);
            }
            trabalhada.somar(hrTempo);

            // Fechamento da hora
            hrFechamento.setHora(saida);
            hrFechamento.somar(tempo);
            etFechamento.setText(hrFechamento.toHoras());


            // Subtraindo as horas
            trabalhada.subtrair(hrEntrada);
            if (trabalhada.getDoubleHora() > hrMaxima.getDoubleHora()) {

                hrDevendo = new Hora(trabalhada);
                hrFechamento.setHora(trabalhada);

                // Calculando quantidade horas devendo
                hrDevendo.subtrair(hrMaxima);
                etDevendo.setText(hrDevendo.toHoras());

                hrFechamento.subtrair(hrDevendo);
                etFechamento.setText(hrFechamento.toHoras());

            } else {
                etDevendo.setText("00:00");
            }
            hrHora.setHora(trabalhada);
            etHora.setText(hrHora.toHoras());

        } catch (Exception e) {
            Msg.Erro("Erro ao calcular horas trabalhadas: " + e.getMessage());
        }

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

            trabalhada.setHora(hrSaida);
            trabalhada.subtrair(hrEntrada);
            etHora.setText(trabalhada.toHoras());

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


    private void setEdits(EditText etHora, EditText etDevendo, EditText etFechamento) {
        etHora.setText("00:00");
        etDevendo.setText("00:00");
        etFechamento.setText("00:00");
    }
}
