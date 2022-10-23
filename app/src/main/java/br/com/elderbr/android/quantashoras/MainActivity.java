package br.com.elderbr.android.quantashoras;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.elderbr.android.quantashoras.controllers.Jornada;
import br.com.elderbr.android.quantashoras.controllers.SubtrairHoraController;
import br.com.elderbr.android.quantashoras.models.Hora;
import br.com.elderbr.android.quantashoras.utils.Msg;

public class MainActivity extends AppCompatActivity {

    public static Context myContext;

    // HORA MAXIMA TRABALHADA
    private Hora hr_maxima = new Hora();

    private EditText entradaEt, refInicioET, refFimEt, saidaEt, tempoEt, horaEt, extraEt,
            fechamentoEt, hora1_Et, hora2_Et, totalEt;

    // Subtrair Hora
    private EditText subtrair_hora1Et, subtrair_hora2Et, subtrair_horaToal;
    private SubtrairHoraController subtrairHoraController = new SubtrairHoraController();

    private TextView msgTv;

    private Button limparBtn;
    private Button fecharBtn;
    private Jornada jornada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myContext = this;
        instanciando();

        // INICIO DA JORANDA
        entradaEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    editEvent();
                } catch (Exception e) {
                    msgTv.setText(e.getMessage());
                    msgTv.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        // INICIO DA REFEIÇÃO
        refInicioET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    editEvent();
                } catch (Exception e) {
                    msgTv.setText(e.getMessage());
                    msgTv.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        // FIM DA REFEIÇÃO
        refFimEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    editEvent();
                } catch (Exception e) {
                    msgTv.setText(e.getMessage());
                    msgTv.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        // FIM DA JORNADA
        saidaEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    editEvent();
                } catch (Exception e) {
                    msgTv.setText(e.getMessage());
                    msgTv.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        // TEMPO PARA PONTO FINAL OU GARAGEM
        tempoEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    editEvent();
                } catch (Exception e) {
                    msgTv.setText(e.getMessage());
                    msgTv.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        hora1_Et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    jornada.setExtra1(hora1_Et);
                    jornada.setExtra2(hora2_Et);
                    totalEt.setText(jornada.toExtraTotal());
                }catch (Exception e){
                    msgTv.setText(e.getMessage());
                    msgTv.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        hora2_Et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    jornada.setExtra1(hora1_Et);
                    jornada.setExtra2(hora2_Et);
                    totalEt.setText(jornada.toExtraTotal());
                } catch (Exception e) {
                    Msg.AVISO(myContext, e.getMessage());
                }
                return false;
            }
        });

        /*****************************************************
         *
         *              SUBTRAIR HORA
         *
         ******************************************************/
        subtrair_hora1Et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    msgTv.setVisibility(View.GONE);
                    subtrairHoraController.setHora01(subtrair_hora1Et);
                    subtrairHoraController.setHora02(subtrair_hora2Et);
                    subtrair_horaToal.setText(subtrairHoraController.toTotal());
                } catch (Exception e) {
                    msgTv.setText(e.getMessage());
                    msgTv.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        subtrair_hora2Et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    msgTv.setVisibility(View.GONE);
                    subtrairHoraController.setHora01(subtrair_hora1Et);
                    subtrairHoraController.setHora02(subtrair_hora2Et);
                    subtrair_horaToal.setText(subtrairHoraController.toTotal());
                } catch (Exception e) {
                    msgTv.setText(e.getMessage());
                    msgTv.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        fecharBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jornada.setRefeicaoInicio(refInicioET);
                jornada.setRefeicaoFim(refFimEt);
                jornada.getFecharBtn();
                saidaEt.setText(jornada.getSaida().toHoras());
                horaEt.setText(jornada.getJornada().toHoras());
                extraEt.setText(jornada.toExtra());
                fechamentoEt.setText(jornada.getFechamento().toHoras());
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
        switch (item.getItemId()) {
            case R.id.menu_hora:
                startActivity(new Intent(MainActivity.this, HorasActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void instanciando() {

        entradaEt = (EditText) findViewById(R.id.entradaEt);
        refInicioET = (EditText) findViewById(R.id.refInicioEt);
        refFimEt = (EditText) findViewById(R.id.refFimEt);
        saidaEt = (EditText) findViewById(R.id.saidaEt);
        tempoEt = (EditText) findViewById(R.id.tempoEt);
        horaEt = (EditText) findViewById(R.id.horaEt);
        extraEt = findViewById(R.id.extraEt);
        fechamentoEt = findViewById(R.id.fechamentoEt);
        fecharBtn = (Button) findViewById(R.id.fecharBtn);
        limparBtn = (Button) findViewById(R.id.limparBtn);

        // Mensagem
        msgTv = findViewById(R.id.msgTv);

        // SOMAR HORA
        hora1_Et = findViewById(R.id.et_hora_01);
        hora2_Et = findViewById(R.id.et_hora_02);
        totalEt = findViewById(R.id.et_hora_03);
        hora1_Et.setText("");
        hora2_Et.setText("");
        totalEt.setText("00:00");

        // SUBTRAIR HORA
        subtrair_hora1Et = findViewById(R.id.subtrair_hora1Et);
        subtrair_hora2Et = findViewById(R.id.subtrair_hora2Et);
        subtrair_horaToal = findViewById(R.id.subtrair_totalEt);
        subtrair_hora1Et.setText("");
        subtrair_hora2Et.setText("");
        subtrair_horaToal.setText("00:00");

        entradaEt.setText("");
        refInicioET.setText("");
        refFimEt.setText("");
        saidaEt.setText("");
        tempoEt.setText("");
        horaEt.setText("00:00");
        extraEt.setText("00:00");
        fechamentoEt.setText("00:00");

        jornada = new Jornada(myContext);
    }

    private void editEvent(){
        msgTv.setVisibility(View.GONE);
        jornada.setEntrada(entradaEt);
        jornada.setRefeicaoInicio(refInicioET);
        jornada.setRefeicaoFim(refFimEt);
        jornada.setSaida(saidaEt);
        jornada.setTempo(tempoEt);
        jornada.setExtra1(hora1_Et);
        jornada.setExtra2(hora2_Et);
        showEvent();
    }
    private void showEvent(){
        horaEt.setText(jornada.getJornada().toHoras());
        extraEt.setText(jornada.toExtra());
        fechamentoEt.setText(jornada.getFechamento().toHoras());
        hora1_Et.setText(jornada.toExtra());
    }

}