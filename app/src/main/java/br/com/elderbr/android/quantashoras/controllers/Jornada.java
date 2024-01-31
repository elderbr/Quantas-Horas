package br.com.elderbr.android.quantashoras.controllers;

import android.content.Context;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import br.com.elderbr.android.quantashoras.Conexao;
import br.com.elderbr.android.quantashoras.models.Hora;
import br.com.elderbr.android.quantashoras.models.Horario;

public class Jornada {

    private Horario horario;
    private String hora;
    private Hora entrada;
    private Hora saida;

    private Hora refeicaoEntrada;
    private Hora refeicaoSaida;
    private Hora refeicao = new Hora(0, 0);

    private Hora tempo = new Hora(0, 0);
    private Hora jornada = new Hora(0, 0);
    private Hora extra = new Hora(0, 0);
    private Hora fechamento = new Hora(0, 0);

    // DIFERENCIA DE HORAS
    private Conexao conexao;
    private Hora normal;
    private Hora horaMaxima;

    // Extra
    private Hora extra1 = new Hora(0, 0);
    private Hora extra2 = new Hora(0, 0);
    private Hora extraTotal = new Hora(0, 0);


    public Jornada(Context context) {
        conexao = new Conexao(context);
        horario = conexao.select();
        if(horario == null){
            normal = new Hora(7,0);
            horaMaxima = new Hora(10,40);
        }else{
            normal = new Hora(horario.getJornada());
            horaMaxima = new Hora(horario.getHoraMaxima());
        }
    }

    public Hora setEntrada(@NotNull EditText entradaEt) {
        entrada = null;
        hora = entradaEt.getText().toString().trim();
        if (hora.length() >= 3 && hora.contains(":")) {
            entrada = new Hora(hora);
        }
        return entrada;
    }

    /*********************************************
     *
     *              REFEIÇÃO
     *
     *********************************************/
    public Hora setRefeicaoInicio(@NotNull EditText refeicaoInicioEt) {
        refeicaoEntrada = null;
        hora = refeicaoInicioEt.getText().toString().trim();
        if (hora.length() >= 3 && hora.contains(":")) {
            refeicaoEntrada = new Hora(hora);
        }
        return refeicaoEntrada;
    }

    public Hora setRefeicaoFim(@NotNull EditText refeicaoFimEt) {
        refeicaoSaida = null;
        hora = refeicaoFimEt.getText().toString().trim();
        if (hora.length() >= 3 && hora.contains(":")) {
            refeicaoSaida = new Hora(hora);
        }
        return refeicaoSaida;
    }

    /***
     * Faz o calculo do tempo de refeição subtraindo a saída da refeição menos a entrada da refeição
     * verifica se os atributos refeição entrada e saída foram inicializada
     */
    public void refeicaoCalculo() {
        refeicao = new Hora(0, 0);
        if (refeicaoEntrada != null && refeicaoSaida != null) {
            refeicao = new Hora(refeicaoSaida);
            refeicao.subtrair(refeicaoEntrada);
        }
    }

    public Hora setSaida(@NotNull EditText saidaEt) {
        saida = null;
        hora = saidaEt.getText().toString().trim();
        if (hora.length() >= 3 && hora.contains(":")) {
            saida = new Hora(hora);
        }
        return saida;
    }

    public Hora getSaida() {
        return saida;
    }

    public Hora setTempo(@NotNull EditText tempoEt) {
        tempo = new Hora(0, 0);
        hora = tempoEt.getText().toString().trim();
        if (hora.length() >= 3 && hora.contains(":")) {
            tempo = new Hora(hora);
        }
        return tempo;
    }

    public Hora getJornada() {
        jornada = new Hora(0, 0);
        if (entrada != null && saida != null) {

            horario = conexao.select();
            if(horario == null){
                normal = new Hora(7,0);
                horaMaxima = new Hora(10,40);
            }else{
                normal = new Hora(horario.getJornada());
                horaMaxima = new Hora(horario.getHoraMaxima());
            }

            jornada = new Hora(saida);

            refeicaoCalculo();// Calcula o tempo de refeição

            jornada.subtrair(entrada);
            jornada.subtrair(refeicao);
            jornada.somar(tempo);

            somaExtra();
        }
        return jornada;
    }

    public Hora somaExtra() {
        extra = new Hora(0, 0);
        if (entrada != null && saida != null && jornada.getDoubleHora() > normal.getDoubleHora()) {
            extra = new Hora(jornada);
            extra.subtrair(normal);
        }
        return extra;
    }

    public String toExtra() {
        somaExtra();
        if(extra.getDoubleHora()>0) {
            return extra.toHoras();
        }
        return "";
    }

    public Hora getFechamento() {
        fechamento = new Hora(0, 0);
        if (entrada != null && saida != null) {
            fechamento = new Hora(saida);
            fechamento.somar(tempo);
        }
        return fechamento;
    }

    /*********************************************
     *
     *              SOMA HORA
     *
     *********************************************/
    public void setExtra1(@NotNull EditText editText) {
        String hora = editText.getText().toString().trim();
        extra1 = new Hora(extra);
        if (hora.length() >= 3 && hora.contains(":")) {
            extra1 = new Hora(hora);
        }
    }

    public String toExtra1() {
        if (extra.getDoubleHora() > 0) {
            extra1 = new Hora(extra);
        }
        if (extra1.getDoubleHora() > 0) {
            return extra1.toHoras();
        }
        return "";
    }

    public void setExtra2(@NotNull EditText editText) {
        String hora = editText.getText().toString().trim();
        extra2 = new Hora(0, 0);
        if (hora.length() >= 3 && hora.contains(":")) {
            extra2 = new Hora(hora);
        }
    }

    public String toExtraTotal() {
        extraTotal = new Hora(0, 0);
        if (extra1.getDoubleHora() > 0 && extra2.getDoubleHora() > 0) {
            extraTotal.somar(extra1).somar(extra2);
        }
        return extraTotal.toHoras();
    }

    /***
     * Fechamento das horas trabalhadas
     * Soma a entrada com a jornada normal de trabalho, se o tempo de refeição for adicionado
     * calcula a jornada de trabalho somando com o tempo parado de refeição
     */
    public void getFecharBtn() {
        if (entrada != null) {// Verifica se a entrada foi digita
            saida = new Hora(entrada);
            saida.somar(normal);
            refeicaoCalculo();// Calcula o tempo de refeição
            if (refeicao.getDoubleHora() > 0) {
                saida.somar(refeicao);
            }
            somaExtra();
        } else {
            saida = new Hora(0, 0);
        }
        fechamento = new Hora(saida);
    }

}
