package br.com.elderbr.android.quantashoras;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    Hora entrada = new Hora();
    Hora saida = new Hora();
    Hora tempo = new Hora();
    Hora hora = new Hora();
    Hora devendo = new Hora();
    Hora fechamento = new Hora();
    Hora hr_hora1 = new Hora();
    Hora hr_hora2 = new Hora();
    Hora hr_total = new Hora();
    String hora1, hora2, total;


    EditText entradaEt, saidaEt, tempoEt, horaEt, devendoEt, fechamentoEt,hora1_Et,hora2_Et,totalEt;

    Button limparBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entradaEt = (EditText) findViewById(R.id.entradaEt);
        saidaEt = (EditText) findViewById(R.id.saidaEt);
        tempoEt = (EditText) findViewById(R.id.tempoEt);
        horaEt = (EditText) findViewById(R.id.horaEt);
        devendoEt = findViewById(R.id.devendoEt);
        fechamentoEt = findViewById(R.id.fechamentoEt);
        limparBtn = (Button) findViewById(R.id.limparBtn);

        hora1_Et = findViewById(R.id.et_hora_01);
        hora2_Et = findViewById(R.id.et_hora_02);
        totalEt = findViewById(R.id.et_hora_03);

        // Entrada
        entradaEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(entradaEt.getText().length()>3){
                    if(entradaEt.getText().toString().length()>3){
                        entrada.setHora(entradaEt.getText().toString());
                    }
                }
                horasTrab();
                return false;
            }
        });

        // Ao sair do campo entrada calcula
        entradaEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==false){
                    if(entradaEt.getText().toString().length()>3){
                        entrada.setHora(entradaEt.getText().toString());
                        horasTrab();
                    }
                }
            }
        });

        // Saída
        saidaEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==false && saidaEt.getText().toString().length()>3){
                    saida.setHora(saidaEt.getText().toString());
                    horasTrab();
                }
            }
        });
        saidaEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(saidaEt.getText().toString().length()>3){
                    saida.setHora(saidaEt.getText().toString());
                }
                horasTrab();
                return false;
            }
        });

        // TEMPO
        tempoEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus==false && tempoEt.getText().toString().length()>3){
                    tempo.setHora(tempoEt.getText().toString());
                    horasTrab();
                }
            }
        });
        tempoEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(tempoEt.getText().toString().length()==0||tempoEt.getText().toString().length()>3){
                    tempo.setHora(tempoEt.getText().toString());
                    horasTrab();
                }
                return false;
            }
        });

        hora1_Et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                hora1 = hora1_Et.getText().toString();
                hora2 = hora2_Et.getText().toString();

                if(hora1.contains(":") && hora1.length() > 3 && hora2.contains(":") && hora2.length() > 3){

                    hr_hora1.parse(hora1);
                    hr_hora2.parse(hora2);

                    hr_total.setHora("00:00");
                    hr_total.somar(hr_hora1);
                    hr_total.somar(hr_hora2);
                    totalEt.setText(hr_total.toHoras());

                }else{
                    totalEt.setText("00:00");
                }
                return false;
            }
        });

        hora2_Et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                somar();
                return false;
            }
        });

        limparBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entradaEt.setText("");
                saidaEt.setText("");
                horaEt.setText("00:00");
                devendoEt.setText("00:00");
                fechamentoEt.setText("00:00");
                hora1_Et.setText("");
                hora2_Et.setText("");
                totalEt.setText("00:00");
                entradaEt.requestFocus();
            }
        });
    }

    private void horasTrab(){

        if(entradaEt.getText().toString().length()>3&&saidaEt.getText().toString().length()>3){

            entrada.setHora(entradaEt.getText().toString());
            saida.setHora(saidaEt.getText().toString());

            // SETA A HORA TRABALHADA E FECHAMENTO
            hora.setHora(saida);
            fechamento.setHora(saida);

            // SE NO CAMPO TEMPO ESTIVER PREENCHIDO
            if(tempoEt.getText().toString().length()>3){
                tempo.setHora(tempoEt.getText().toString());// PEGA A HORA DO TEMPO
            }else{
                tempo.setHora("00:00");
            }

            hora.somar(tempo);// SOMA A HORA TRABALHADA MAIS O TEMPO
            fechamento.somar(tempo);// SOMA A HORA TRABALHADA MAIS O TEMPO

            hora.subtrair(entrada);// FAZ O CALCULO DA SAIDA MAIS O TEMPO MENOS E ENTRADA

            // SE A HORA FORA MAIOR QUE 11:45
            if(hora.getDoubleHora()>11.45){

                devendo.setHora(hora);
                devendo.subHoraMinuto(11,45);

                fechamento.subtrair(devendo); //Fechamento menos o valor que está devendo

                //SOMA A HORA 1 COM A Hora 2
                hr_hora1.setHora(devendo);
                hora1_Et.setText(hr_hora1.toHoras());
                somar();// Hora 1 + hora 2

            }else{
                devendo.setHora("00:00");
            }
        }else{
            hora.setHora("00:00");
            fechamento.setHora("00:00");
            devendo.setHora("00:00");
        }

        horaEt.setText(hora.toHoras());
        devendoEt.setText(devendo.toHoras());
        fechamentoEt.setText(fechamento.toHoras());

    }

    private void somar(){
        hora1 = hora1_Et.getText().toString();
        hora2 = hora2_Et.getText().toString();

        if(hora1.contains(":") && hora1.length() > 3 && hora2.contains(":") && hora2.length() > 3){

            hr_hora1.parse(hora1);
            hr_hora2.parse(hora2);

            hr_total.setHora("00:00");
            hr_total.somar(hr_hora1);
            hr_total.somar(hr_hora2);
            totalEt.setText(hr_total.toHoras());

        }else{
            totalEt.setText("00:00");
        }
    }

}