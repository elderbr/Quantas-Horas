package br.com.elderbr.android.quantashoras;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.elderbr.android.quantashoras.controllers.HoraController;
import br.com.elderbr.android.quantashoras.controllers.MessegeDialogAlert;

public class HorasActivity extends AppCompatActivity {

    private Context myContext;
    private EditText etMaxima, etJornada, etPercurso;
    private Button bt_salvar;

    private HoraController horaController;

    private MessegeDialogAlert messege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas);

        myContext = this;
        messege = new MessegeDialogAlert(HorasActivity.this);

        // EditText
        etMaxima = findViewById(R.id.et_hora_maxima);
        etJornada = findViewById(R.id.jornadaNormalEt);
        etPercurso = findViewById(R.id.etPercurso);

        // Controlador
        horaController = new HoraController(this);
        horaController.carrega(etJornada, etMaxima, etPercurso);

        // Botão
        bt_salvar = findViewById(R.id.bt_salvar);

        etJornada.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                horaController.setHoraNormal(etJornada);
                return false;
            }
        });

        etMaxima.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                horaController.setMaxima(etMaxima);
                return false;
            }
        });

        etPercurso.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                horaController.setPercurso(etPercurso);
                return false;
            }
        });

        bt_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horaController.save()) {
                    messege.startMessege("Horas atualizadas com sucesso!");
                } else {
                    messege.startMessege("Não houve alterações!");
                }
            }
        });
    }
}