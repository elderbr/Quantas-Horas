package br.com.elderbr.android.quantashoras;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HorasActivity extends AppCompatActivity {

    private EditText et_hora_maxima;
    private Button bt_salvar;

    private Conexao conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas);

        et_hora_maxima = findViewById(R.id.et_hora_maxima);
        bt_salvar = findViewById(R.id.bt_salvar);

        conexao = new Conexao(this);
        et_hora_maxima.setText(conexao.select().toHoras());

        bt_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hr = et_hora_maxima.getText().toString().trim();
                Hora hora = new Hora(0, 0);

                if (hr.length() > 3 && hr.contains(":")) {
                    hora.parse(hr);
                    if (conexao.update(hora) > 0) {
                        Toast.makeText(HorasActivity.this, "Hora Salva", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HorasActivity.this, "Erro ao salvar a Hora!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HorasActivity.this, "Digite uma hora valida!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}