package br.com.elderbr.android.quantashoras;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.elderbr.android.quantashoras.controllers.ChegadaController;
import br.com.elderbr.android.quantashoras.controllers.MessegeDialogAlert;
import br.com.elderbr.android.quantashoras.models.Chegada;
import br.com.elderbr.android.quantashoras.models.Hora;

public class ChegadaActivity extends AppCompatActivity {

    private Chegada chegada = new Chegada();
    private ChegadaController chegadaCtrl = new ChegadaController();
    private EditText etTpSaida, etTpPercurso, etTpChegada, etTpPrevisaoSaida;
    private EditText etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida;

    private Button btnCalcular;

    MessegeDialogAlert message = new MessegeDialogAlert(ChegadaActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chegada);

        // Campos lugar 1
        etTpSaida = findViewById(R.id.etTpSaida);
        etTpPercurso = findViewById(R.id.etTpPercurso);
        etTpChegada = findViewById(R.id.etTpChegada);
        etTpPrevisaoSaida = findViewById(R.id.etTpPrevisaoSaida);

        // Campos lugar 2
        etTsSaida = findViewById(R.id.etTsSaida);
        etTsPercurso = findViewById(R.id.etTsPercurso);
        etTsChegada = findViewById(R.id.etTsChegada);
        etTsPrevisaoSaida = findViewById(R.id.etTsPrevisaoSaida);

        btnCalcular = findViewById(R.id.btnChegar);

        etTpSaida.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try {
                    chegadaCtrl.calcularTp(etTpSaida, etTpPercurso, etTpChegada, etTpPrevisaoSaida, etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida);
                } catch (Exception e) {
                    message.startMessege(e.getMessage());
                }
                return false;
            }
        });

        etTpPercurso.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try {
                    chegadaCtrl.calcularTp(etTpSaida, etTpPercurso, etTpChegada, etTpPrevisaoSaida, etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        });


        /** Local secundario **/
        etTsSaida.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try {
                    chegadaCtrl.calcularTs(etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        });

        etTsPercurso.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try {
                    chegadaCtrl.calcularTs(etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    chegadaCtrl.calcular(etTpSaida, etTpPercurso, etTpChegada, etTpPrevisaoSaida, etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
}