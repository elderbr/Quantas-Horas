package br.com.elderbr.android.quantashoras;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import br.com.elderbr.android.quantashoras.controllers.HoraController;
import br.com.elderbr.android.quantashoras.controllers.QuantaHoraController;

public class QuantaHoraFragment extends Fragment {
    private View view;
    private EditText etEntrada, etSaida, etTempo, etHora, etDevendo, etFechamento,
            etSoma1, etSoma2, etSomaTotal,
            etSubtrair1, etSubtrair2, etSutrairTotal;
    private Button btnCalcular, btnLimpar;

    private QuantaHoraController quantaHoraController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quanta_hora, container, false);

        etEntrada = view.findViewById(R.id.etEntrada);
        etSaida = view.findViewById(R.id.etSaida);
        etTempo = view.findViewById(R.id.etTempo);
        etHora = view.findViewById(R.id.etHoraTrabalhada);
        etDevendo = view.findViewById(R.id.etDevendo);
        etFechamento = view.findViewById(R.id.etFechamento);

        // Somar hora
        etSoma1 = view.findViewById(R.id.etHoraSoma01);
        etSoma2 = view.findViewById(R.id.etHoraSoma02);
        etSomaTotal = view.findViewById(R.id.etTotalSoma01);

        // Subtrair hora
        etSubtrair1 = view.findViewById(R.id.etHoraSubtrair01);
        etSubtrair2 = view.findViewById(R.id.etHoraSubtrair02);
        etSutrairTotal = view.findViewById(R.id.etTotalSubtrair01);

        btnCalcular = view.findViewById(R.id.btnTrabalhada);
        btnLimpar = view.findViewById(R.id.btnLimpar);

        quantaHoraController = new QuantaHoraController(inflater.getContext());

        etEntrada.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                quantaHoraController.calcular(etEntrada, etSaida, etTempo, etHora, etDevendo, etFechamento);
                return false;
            }
        });

        etSaida.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                quantaHoraController.calcular(etEntrada, etSaida, etTempo, etHora, etDevendo, etFechamento);
                return false;
            }
        });

        etTempo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                quantaHoraController.calcular(etEntrada, etSaida, etTempo, etHora, etDevendo, etFechamento);
                return false;
            }
        });

        etSoma1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                quantaHoraController.somar(etSoma1, etSoma2, etSomaTotal);
                return false;
            }
        });

        etSoma2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                quantaHoraController.somar(etSoma1, etSoma2, etSomaTotal);
                return false;
            }
        });

        etSubtrair1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                quantaHoraController.subtrair(etSubtrair1, etSubtrair2, etSutrairTotal);
                return false;
            }
        });

        etSubtrair2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                quantaHoraController.subtrair(etSubtrair1, etSubtrair2, etSutrairTotal);
                return false;
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantaHoraController.limpar(etEntrada, etSaida, etTempo, etHora, etDevendo, etFechamento,
                        etSoma1, etSoma2, etSomaTotal, etSubtrair1, etSubtrair2, etSutrairTotal);
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantaHoraController.btnCalcular(etEntrada, etSaida, etTempo, etHora, etDevendo, etFechamento);
            }
        });


        return view;
    }
}