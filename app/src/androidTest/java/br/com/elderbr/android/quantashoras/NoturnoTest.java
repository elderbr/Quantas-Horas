package br.com.elderbr.android.quantashoras;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import br.com.elderbr.android.quantashoras.controllers.QuantaHoraController;
import br.com.elderbr.android.quantashoras.models.Hora;

public class NoturnoTest extends TestCase {

    Hora entrada;
    Hora saida;
    Hora tempo;

    private QuantaHoraController controller = new QuantaHoraController();

    @Before
    public void setup(){

    }

    @Test
    public void testEntradaMenorQue22SaidaMenorQue5() {
        entrada = new Hora(21,0);
        saida = new Hora(4,30);
        tempo = new Hora(1,0);

        controller.calcular(entrada, saida, tempo);
        // Horas Trabalhadas
        assertEquals("08:30", controller.toHora());
        // Extras
        assertEquals("00:40", controller.toExtra());
        // Adicional Noturno
        assertEquals("07:00", controller.toNoturno());
        // Fechamento das horas
        assertEquals("05:30", controller.toFechamento());
        // Sobrando
        assertEquals("00:00", controller.toSobrando());
    }

    @Test
    public void testEntradaMenorQue22SaidaMaiorQue5() {
        entrada = new Hora(21,0);
        saida = new Hora(8,0);
        tempo = new Hora(1,0);

        controller.calcular(entrada, saida, tempo);
        // Horas Trabalhadas
        assertEquals("12:00", controller.toHora());
        // Extras
        assertEquals("04:10", controller.toExtra());
        // Adicional Noturno
        assertEquals("07:00", controller.toNoturno());
        // Fechamento das horas
        assertEquals("07:50", controller.toFechamento());
        // Sobrando
        assertEquals("01:10", controller.toSobrando());
    }

    @Test
    public void testEntradaMaiorQue22SaidaMenorQue5() {
        entrada = new Hora(22,30);
        saida = new Hora(4,30);
        tempo = new Hora(1,0);

        controller.calcular(entrada, saida, tempo);
        // Horas Trabalhadas
        assertEquals("07:00", controller.toHora());
        // Extras
        assertEquals("00:00", controller.toExtra());
        // Adicional Noturno
        assertEquals("06:30", controller.toNoturno());
        // Fechamento das horas
        assertEquals("05:30", controller.toFechamento());
        // Sobrando
        assertEquals("00:00", controller.toSobrando());
    }

    @Test
    public void testEntradaMaiorQue22SaidaMaiorQue5() {
        entrada = new Hora(22,30);
        saida = new Hora(9,0);
        tempo = new Hora(1,0);

        controller.calcular(entrada, saida, tempo);
        // Horas Trabalhadas
        assertEquals("11:30", controller.toHora());
        // Extras
        assertEquals("03:40", controller.toExtra());
        // Adicional Noturno
        assertEquals("06:30", controller.toNoturno());
        // Fechamento das horas
        assertEquals("09:20", controller.toFechamento());
        // Sobrando
        assertEquals("00:40", controller.toSobrando());
    }

    @Test
    public void testEntradaMenorQue5SaidaMaiorQue5() {
        entrada = new Hora(3,0);
        saida = new Hora(12,0);
        tempo = new Hora(1,0);

        controller.calcular(entrada, saida, tempo);
        // Horas Trabalhadas
        assertEquals("10:00", controller.toHora());
        // Extras
        assertEquals("02:10", controller.toExtra());
        // Adicional Noturno
        assertEquals("02:00", controller.toNoturno());
        // Fechamento das horas
        assertEquals("13:00", controller.toFechamento());
        // Sobrando
        assertEquals("00:00", controller.toSobrando());
    }

    @Test
    public void testEntradaMenorQue5SaidaMenorQue5() {
        entrada = new Hora(0,0);
        saida = new Hora(4,0);
        tempo = new Hora(1,0);

        controller.calcular(entrada, saida, tempo);
        // Horas Trabalhadas
        assertEquals("05:00", controller.toHora());
        // Extras
        assertEquals("00:00", controller.toExtra());
        // Adicional Noturno
        assertEquals("05:00", controller.toNoturno());
        // Fechamento das horas
        assertEquals("05:00", controller.toFechamento());
        // Sobrando
        assertEquals("00:00", controller.toSobrando());
    }

    @Test
    public void testEntradaMaiorQue5SaidaMenorQue22() {
        entrada = new Hora(13,0);
        saida = new Hora(21,0);
        tempo = new Hora(1,0);

        controller.calcular(entrada, saida, tempo);
        // Horas Trabalhadas
        assertEquals("09:00", controller.toHora());
        // Extras
        assertEquals("01:10", controller.toExtra());
        // Adicional Noturno
        assertEquals("00:00", controller.toNoturno());
        // Fechamento das horas
        assertEquals("22:00", controller.toFechamento());
        // Sobrando
        assertEquals("00:00", controller.toSobrando());
    }

    @Test
    public void testEntradaMaiorQue5SaidaMaiorQue22() {
        entrada = new Hora(13,0);
        saida = new Hora(23,0);
        tempo = new Hora(1,0);

        controller.calcular(entrada, saida, tempo);
        // Horas Trabalhadas
        assertEquals("11:00", controller.toHora());
        // Extras
        assertEquals("03:10", controller.toExtra());
        // Adicional Noturno
        assertEquals("02:00", controller.toNoturno());
        // Fechamento das horas
        assertEquals("23:50", controller.toFechamento());
        // Sobrando
        assertEquals("00:10", controller.toSobrando());
    }

}
