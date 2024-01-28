package br.com.elderbr.android.quantashoras.models;

public class Chegada {

    private Hora saida = new Hora();
    private Hora percurso = new Hora(0,0);
    private Hora chegada = new Hora(0,0);
    private Hora previsaSaida = new Hora(0,0);

    public Chegada() {
    }

    public Hora getSaida() {
        return saida;
    }

    public void setSaida(Hora saida) {
        this.saida = saida;
    }

    public void setSaida(String saida) {
        this.saida = new Hora(saida);
    }

    public Hora getPercurso() {
        return percurso;
    }

    public void setPercurso(Hora percurso) {
        this.percurso = percurso;
    }

    public void setPercurso(String percurso) {
        this.percurso = new Hora(percurso);
    }

    public Hora getChegada() {
        chegada = new Hora(saida);
        chegada.somar(percurso);
        return chegada;
    }

    public String toChegada(){
        return chegada.toHoras();
    }

    public Hora getPrevisaSaida() {
        previsaSaida = new Hora(chegada);
        previsaSaida.somar("00:05");
        return previsaSaida;
    }

    public String toPrevisaoSaida(){
        return previsaSaida.toHoras();
    }

    public void calcula(){
        chegada = new Hora(saida);
        chegada.somar(percurso);
        previsaSaida = new Hora(chegada);
        previsaSaida.somar("00:05");
    }


}
