package br.com.elderbr.android.quantashoras;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import br.com.elderbr.android.quantashoras.controllers.ChegadaController;

public class ChegadaFragment extends Fragment {

    private View view;
    private Context myContext;

    private EditText etTpSaida, etTpPercurso, etTpChegada, etTpPrevisaoSaida,
            etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida,
            etCatraca1, etCatraca2, etCatracaTotal;
    private Button btnCalcular;

    private ChegadaController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chegada, container, false);
        myContext = inflater.getContext();
        init();

        etTpSaida.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try {
                    controller.calcularTp(etTpSaida, etTpPercurso, etTpChegada, etTpPrevisaoSaida, etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        });

        etTpPercurso.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try {
                    controller.calcularTp(etTpSaida, etTpPercurso, etTpChegada, etTpPrevisaoSaida, etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        });

        etTsSaida.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try {
                    controller.calcularTs(etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida);
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
                    controller.calcularTs(etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        });


        /** CATRACA **/
        etCatraca1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                controller.calcularCatraca(etCatraca1, etCatraca2, etCatracaTotal);
                return false;
            }
        });
        etCatraca2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                controller.calcularCatraca(etCatraca1, etCatraca2, etCatracaTotal);
                return false;
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    controller.calcular(etTpSaida, etTpPercurso, etTpChegada, etTpPrevisaoSaida, etTsSaida, etTsPercurso, etTsChegada, etTsPrevisaoSaida);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return view;
    }

    private void init() {

        // Primeiro lugar
        etTpSaida = view.findViewById(R.id.etTpSaida);
        etTpPercurso = view.findViewById(R.id.etTpPercurso);
        etTpChegada = view.findViewById(R.id.etTpChegada);
        etTpPrevisaoSaida = view.findViewById(R.id.etTpPrevisaoSaida);

        // Primeiro lugar
        etTsSaida = view.findViewById(R.id.etTsSaida);
        etTsPercurso = view.findViewById(R.id.etTsPercurso);
        etTsChegada = view.findViewById(R.id.etTsChegada);
        etTsPrevisaoSaida = view.findViewById(R.id.etTsPrevisaoSaida);

        // Catraca
        etCatraca1 = view.findViewById(R.id.etCatraca1);
        etCatraca2 = view.findViewById(R.id.etCatraca2);
        etCatracaTotal = view.findViewById(R.id.etCatracaTotal);

        // Botão Calcular
        btnCalcular = view.findViewById(R.id.btnChegar);

        // Carrega informações do banco
        controller = new ChegadaController(myContext);
        controller.carrega(etTpPercurso, etTsPercurso);

    }


}