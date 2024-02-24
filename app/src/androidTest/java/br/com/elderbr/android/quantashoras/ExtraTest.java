package br.com.elderbr.android.quantashoras;

import junit.framework.TestCase;

import org.junit.Test;

import br.com.elderbr.android.quantashoras.controllers.QuantaHoraController;
import br.com.elderbr.android.quantashoras.models.Hora;

public class ExtraTest extends TestCase {

    private Hora entrada;
    private Hora saida;
    private Hora tempo;

    private QuantaHoraController controller = new QuantaHoraController();

    @Test
    public void testHoraTrabalhadaMaiorQue10hora50min(){
        entrada = new Hora(0,0);
        saida = new Hora(10,30);
        tempo = new Hora(1,0);
        controller.calcular(entrada, saida, tempo);

        assertEquals("10:50", controller.toFechamento());
        assertEquals("03:40", controller.toExtra());
    }

    @Test
    public void testHoraTrabalhadaMaiorQue7hora50min(){
        entrada = new Hora(0,0);
        saida = new Hora(8,0);
        tempo = new Hora(1,0);
        controller.calcular(entrada, saida, tempo);

        assertEquals("01:10", controller.toExtra());
        assertEquals("09:00", controller.toFechamento());
    }

    @Test
    public void testHoraTrabalhadaMenorQue7hora50min(){
        entrada = new Hora(0,0);
        saida = new Hora(6,0);
        tempo = new Hora(1,0);
        controller.calcular(entrada, saida, tempo);

        assertEquals("00:00", controller.toExtra());
        assertEquals("07:00", controller.toFechamento());
    }

}
