package br.com.elderbr.android.quantashoras;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.elderbr.android.quantashoras.controllers.HoraController;
import br.com.elderbr.android.quantashoras.controllers.MessegeDialogAlert;
import br.com.elderbr.android.quantashoras.models.Hora;
import br.com.elderbr.android.quantashoras.models.Horario;
import br.com.elderbr.android.quantashoras.utils.Msg;

public class HorasActivity extends AppCompatActivity {

    private Context myContext;
    private EditText horaMaximaEt;
    private EditText jornadaNormalEt;
    private Button bt_salvar;

    // Horas
    private Hora jornada;

    private Conexao conexao;
    private HoraController horaController;

    private MessegeDialogAlert messege;
    private Horario horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas);

        myContext = this;
        horaController = new HoraController(this);
        messege = new MessegeDialogAlert(HorasActivity.this);

        // EditText
        horaMaximaEt = findViewById(R.id.et_hora_maxima);
        jornadaNormalEt = findViewById(R.id.jornadaNormalEt);

        // Botão
        bt_salvar = findViewById(R.id.bt_salvar);

        conexao = new Conexao(HorasActivity.this);
        horario = conexao.select();
        if(horario != null){
            jornadaNormalEt.setText(horario.getJornadaNormal().toHoras());
            horaMaximaEt.setText(horario.getHoraMaxima().toHoras());
        }

        jornadaNormalEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    horaController.setJornada(jornadaNormalEt);
                } catch (Exception e) {
                    messege.startMessege(e.getMessage());
                    jornadaNormalEt.setSelection(0, jornadaNormalEt.getText().toString().length());
                }
                return false;
            }
        });

        horaMaximaEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    horaController.setHoraMaxima(horaMaximaEt);
                } catch (Exception e) {
                    messege.startMessege(e.getMessage());
                    horaMaximaEt.setSelection(0, horaMaximaEt.getText().toString().length());
                }
                return false;
            }
        });

        bt_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    horaController.setJornada(jornadaNormalEt);
                    horaController.setHoraMaxima(horaMaximaEt);
                    if(horaController.addUpdate() == 1){
                        messege.startMessege("Hora adicionada com sucesso!");
                    }else{
                        messege.startMessege("Hora atualizada com sucesso!");
                    }
                } catch (Exception e) {
                    messege.startMessege(e.getMessage());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}