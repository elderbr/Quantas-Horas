package br.com.elderbr.android.quantashoras.controllers;

import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import br.com.elderbr.android.quantashoras.models.Chegada;
import br.com.elderbr.android.quantashoras.models.Hora;

public class ChegadaController {
    private Chegada principal = new Chegada();
    private Chegada secundario = new Chegada();

    private final String HORA_DEFAULT = "00:00";

    public ChegadaController() {
    }

    public void setSaida(@NotNull EditText editText) throws Exception {
        String hr = editText.getText().toString();

        // Verificar se a hora digita é maior que 2 caracteres e se possui 2 pontos
        if (hr.length() > 2 && hr.contains(":")) {

        }
    }

    public void calcularTp(EditText etTpSaida, EditText etTpPercurso, EditText etTpChegada, EditText etTpPrevisaoSaida, EditText etTsSaida, EditText etTsPercurso, EditText etTsChegada, EditText etTsPrevisaoSaida) throws Exception {
        String tpSaida = etTpSaida.getText().toString();
        String tpPercurso = etTpPercurso.getText().toString();

        // Verificar se a hora digita é maior que 2 caracteres e se possui 2 pontos
        if (tpSaida.length() > 2 && tpSaida.contains(":")) {
            principal.setSaida(tpSaida);
        } else {
            principal.setSaida(HORA_DEFAULT);
            return;
        }

        if (tpPercurso.length() > 2 && tpPercurso.contains(":")) {
            principal.setPercurso(tpPercurso);
        } else {
            principal.setPercurso(HORA_DEFAULT);
            etTpChegada.setText(HORA_DEFAULT);
            etTpPrevisaoSaida.setText(HORA_DEFAULT);
            return;
        }

        principal.calcula();
        etTpChegada.setText(principal.toChegada());
        etTpPrevisaoSaida.setText(principal.toPrevisaoSaida());

        // Secundario
        String tsPercurso = etTsPercurso.getText().toString();
        secundario.setSaida(principal.getPrevisaSaida());
        etTsSaida.setText(secundario.getSaida().toHoras());

        if (tsPercurso.length() < 3 && !tsPercurso.contains(":")) {
            etTsPercurso.setText(principal.getPercurso().toHoras());
        }
        secundario.setPercurso(tsPercurso);
        secundario.calcula();
        etTsChegada.setText(secundario.toChegada());
        etTsPrevisaoSaida.setText(secundario.toPrevisaoSaida());

    }

    public void calcularTs(EditText etTsSaida, EditText etTsPercurso, EditText etTsChegada, EditText etTsPrevisaoSaida) throws Exception {
        String tsSaida = etTsSaida.getText().toString();
        String tsPercurso = etTsPercurso.getText().toString();

        if (tsSaida.length() > 2 && tsSaida.contains(":")) {
            secundario.setSaida(tsSaida);
        } else {
            secundario.setSaida(new Hora(0, 0));
        }

        if (tsPercurso.length() > 2 && tsPercurso.contains(":")) {
            secundario.setPercurso(tsPercurso);
        } else {
            secundario.setPercurso(new Hora(0, 0));
            return;
        }

        secundario.calcula();
        etTsChegada.setText(secundario.toChegada());
        etTsPrevisaoSaida.setText(secundario.toPrevisaoSaida());
    }

    public void calcular(EditText etTpSaida, EditText etTpPercurso, EditText etTpChegada, EditText etTpPrevisaoSaida, EditText etTsSaida, EditText etTsPercurso, EditText etTsChegada, EditText etTsPrevisaoSaida) throws Exception {
        String tpSaida = etTpSaida.getText().toString();
        String tpPercurso = etTpPercurso.getText().toString();

        // Verificar se a hora digita é maior que 2 caracteres e se possui 2 pontos
        if (tpSaida.length() > 2 && tpSaida.contains(":")) {
            principal.setSaida(new Hora(tpSaida));
        } else {
            principal.setSaida(new Hora());
            etTpSaida.setText(principal.getSaida().toHoras());
        }

        // Percurso
        if (tpPercurso.length() > 2 && tpPercurso.contains(":")) {
            principal.setPercurso(tpPercurso);
        } else {
            return;
        }
        principal.calcula();
        etTpChegada.setText(principal.toChegada());
        etTpPrevisaoSaida.setText(principal.toPrevisaoSaida());

        // Calculando Secundario
        String tsSaida = etTsSaida.getText().toString();
        String tsPercurso = etTsPercurso.getText().toString();

        if (tsSaida.length() > 2 && tsSaida.contains(":")) {
            secundario.setSaida(tsSaida);
        } else {
            if (principal.getPrevisaSaida().getDoubleHora() > 0) {
                secundario.setSaida(principal.getPrevisaSaida());
                etTsSaida.setText(principal.toPrevisaoSaida());
            } else {
                return;
            }
        }

        if (tsPercurso.length() > 2 && tsPercurso.contains(":")) {
            secundario.setPercurso(tsPercurso);
        } else {
            secundario.setPercurso(principal.getPercurso());
        }

        secundario.setSaida(principal.getPrevisaSaida());
        secundario.setPercurso(principal.getPercurso());
        secundario.calcula();
        etTsChegada.setText(secundario.toChegada());
        etTsPrevisaoSaida.setText(secundario.toPrevisaoSaida());
    }
}
