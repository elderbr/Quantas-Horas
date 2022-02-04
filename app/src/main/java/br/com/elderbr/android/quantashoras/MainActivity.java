package br.com.elderbr.android.quantashoras;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private Hora hr_entrada = new Hora(0, 0);
    private Hora hr_saida = new Hora(0, 0);
    private Hora hr_tempo = new Hora(0, 0);
    private Hora hr_hora = new Hora(0, 0);
    private Hora hr_devendo = new Hora(0, 0);
    private Hora hr_fechamento = new Hora(0, 0);

    // Horas Extra para Casa
    private Hora hr_hora1 = new Hora(0, 0);
    private Hora hr_hora2 = new Hora(0, 0);
    private Hora hr_total = new Hora(0, 0);

    // HORAS NA CASA
    private Hora hr_casa = new Hora(0, 0);
    private Hora hr_usada = new Hora(0, 0);
    private Hora hr_casaRestante = new Hora(0, 0);


    private String entrada, saida, tempo, hora1, hora2, casa, usada, restante, total;


    private EditText entradaEt, saidaEt, tempoEt, horaEt, devendoEt, fechamentoEt, hora1_Et, hora2_Et, totalEt, casaHoraEt, casaUsadaEt, casaRestanteEt;

    private Button limparBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Conexao conexao = new Conexao(this);
        instanciando();

        entradaEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                hr_entrada = new Hora(0, 0);
                entrada = entradaEt.getText().toString().trim();
                if (entrada.length() > 3 && entrada.contains(":")) {
                    hr_entrada.parse(entrada);
                }
                horaTrabalhada();
                return false;
            }
        });

        saidaEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                hr_saida = new Hora(0, 0);
                saida = saidaEt.getText().toString().trim();
                if (saida.length() > 3 && saida.contains(":")) {
                    hr_saida.parse(saida);
                }
                horaTrabalhada();
                return false;
            }
        });

        tempoEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tempo = tempoEt.getText().toString().trim();
                if (tempo.length() > 3 && tempo.contains(":")) {
                    hr_tempo.parse(tempo);
                } else {
                    hr_tempo.setHora(0, 0);
                }
                horaTrabalhada();
                return false;
            }
        });

        hora1_Et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                hora1 = hora1_Et.getText().toString().trim();
                if (hora1.length() > 3 && hora1.contains(":")) {
                    hr_hora1.parse(hora1);
                } else {
                    hr_hora1.setHora(0, 0);
                }
                somaHoras();
                return false;
            }
        });

        hora2_Et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                hora2 = hora2_Et.getText().toString().trim();
                if (hora2.length() > 3 && hora2.contains(":")) {
                    hr_hora2.parse(hora2);
                } else {
                    hr_hora2.setHora(0, 0);
                }
                somaHoras();
                return false;
            }
        });

        casaHoraEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                casa = casaHoraEt.getText().toString().trim();
                horaTrabalhada();
                return false;
            }
        });

        limparBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instanciando();
                entradaEt.requestFocus();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_hora, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_hora:
                startActivity(new Intent(MainActivity.this, HorasActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void horaTrabalhada() {

        // INSTANCIANDO HORAS
        hr_hora = new Hora(0, 0);
        hr_fechamento = new Hora(0, 0);
        hr_devendo = new Hora(0, 0);

        //HORA NA CASA
        hr_casa = new Hora(0, 0);
        hr_usada = new Hora(0, 0);
        hr_casaRestante = new Hora(0, 0);

        // VALIDANDO A HORA DA CASA
        if (casa != null && casa.length() > 3 && casa.contains(":")) {
            hr_casa.parse(casa);
        }

        // VERIFICA SE A ENTRADA E A SAÍDA CONTEM HORA VALIDA
        if (entrada != null && entrada.length() > 3 && saida != null && saida.length() > 3) {

            // HORA É IGUAL A SAÍDA MAIS O TEMPO MENOS A ENTRADA
            hr_hora.setHora(hr_saida);
            hr_hora.somar(hr_tempo);
            hr_hora.somar(hr_casa);

            // QUANTIDADE DE HORA É IGUAL A SAÍDA MENOS A ENTRADA
            hr_hora.subtrair(hr_entrada);

            // HORA DO FECHAMENTO É IGUAL A PARADA MAIS O TEMPO
            hr_fechamento.setHora(hr_saida);// FECHAMENTO É IGUAL A SAÍDA
            hr_fechamento.somar(hr_tempo);// SOMANDO COM TEMPO
            hr_fechamento.somar(hr_casa);// SOMANDO COM A HORA DA CASA

            if (hr_hora.getDoubleHora() > 11.45) {// SE A HORA TRABALHADA FOR MAIOR QUE 11:45

                // DEVENDO É IGUAL A HORA MENOS 11:45
                hr_devendo.setHora(hr_hora);
                hr_devendo.subtrair(11, 45);

            } else {
                hr_devendo.setHora(0, 0);
            }
            hr_fechamento.subtrair(hr_devendo);// FECHAMENTO MENOS A HORA QUE ESTA DEVENDO

            // IMPRIMINDO NAS EDITTEXT
            horaEt.setText(hr_hora.toHoras());
            devendoEt.setText(hr_devendo.toHoras());
            fechamentoEt.setText(hr_fechamento.toHoras());
            hora1_Et.setText(hr_devendo.toHoras());

            // HORAS EXTRAS
            hora1_Et.setText(hr_devendo.toHoras());

        } else {
            horaEt.setText("00:00");
            devendoEt.setText("00:00");
            totalEt.setText("00:00");
        }

        // HORA NA CASA É O VALOR DA HORA NA CASA MENOS A HORA DEVENDO SOBRA O RESTANTE DE HORAS NA CASA
        if (hr_casa.getDoubleHora() > 0) {

            hr_usada.setHora(hr_casa);
            hr_usada.subtrair(hr_devendo);

            hr_casaRestante.setHora(hr_casa);
            hr_casaRestante.subtrair(hr_usada);
        }

        casaUsadaEt.setText(hr_usada.toHoras());
        casaRestanteEt.setText(hr_casaRestante.toHoras());

        somaHoras();
    }

    /***
     * SOMA AS HORAS NA CASA
     * HORA QUE JÁ TEM MAIS A HORA DO DIA
     */
    private void somaHoras() {

        hr_hora1 = new Hora(0, 0);
        hr_hora2 = new Hora(0, 0);
        hr_total = new Hora(0, 0);

        if (hr_devendo.getDoubleHora() > 0) {
            hora1_Et.setText(hr_devendo.toHoras());
        } else {
            hora1_Et.setText("");
        }

        hora1 = hora1_Et.getText().toString().trim();
        hora2 = hora2_Et.getText().toString().trim();

        hr_total.setHora(0, 0);

        if (hora1.length() > 3 && hora1.contains(":") && hora2.length() > 3 && hora2.contains(":")) {
            hr_hora1.setHora(hora1);
            hr_hora2.setHora(hora2);

            hr_total.somar(hr_hora1);
            hr_total.somar(hr_hora2);
        }
        totalEt.setText(hr_total.toHoras());
    }

    private void instanciando() {

        entradaEt = (EditText) findViewById(R.id.entradaEt);
        entrada = null;
        saidaEt = (EditText) findViewById(R.id.saidaEt);
        saida = null;
        tempoEt = (EditText) findViewById(R.id.tempoEt);
        tempo = null;
        horaEt = (EditText) findViewById(R.id.horaEt);
        devendoEt = findViewById(R.id.devendoEt);
        fechamentoEt = findViewById(R.id.fechamentoEt);
        limparBtn = (Button) findViewById(R.id.limparBtn);

        // HORAS EXTRAS
        hora1_Et = findViewById(R.id.et_hora_01);
        hora1 = null;
        hora2_Et = findViewById(R.id.et_hora_02);
        hora2 = null;
        totalEt = findViewById(R.id.et_hora_03);

        // HORAS NA CASA
        casaHoraEt = findViewById(R.id.etHoraCasa);
        casa = null;
        casaUsadaEt = findViewById(R.id.etHoraCasaUsada);
        casaRestanteEt = findViewById(R.id.etHoraCasaRestante);

        // HORAS TRABALHADAS
        hr_entrada = new Hora(0, 0);
        hr_saida = new Hora(0, 0);
        hr_hora = new Hora(0, 0);
        hr_tempo = new Hora(0, 0);
        hr_fechamento = new Hora(0, 0);
        hr_devendo = new Hora(0, 0);

        //EXTRA NA CASA
        hr_hora1 = new Hora(0, 0);
        hr_hora2 = new Hora(0, 0);
        hr_total = new Hora(0, 0);

        //HORA NA CASA
        hr_casa = new Hora(0, 0);
        hr_usada = new Hora(0, 0);
        hr_casaRestante = new Hora(0, 0);
        casa = null;

        entradaEt.setText("");
        saidaEt.setText("");
        tempoEt.setText("");
        horaEt.setText("00:00");
        devendoEt.setText("00:00");
        fechamentoEt.setText("00:00");

        hora1_Et.setText("");
        hora2_Et.setText("");
        totalEt.setText("00:00");

        casaHoraEt.setText("");
        casaUsadaEt.setText("00:00");
        casaRestanteEt.setText("00:00");

    }

}