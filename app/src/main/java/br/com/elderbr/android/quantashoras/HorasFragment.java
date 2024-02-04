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

import br.com.elderbr.android.quantashoras.controllers.HoraController;
import br.com.elderbr.android.quantashoras.controllers.MessegeDialogAlert;

public class HorasFragment extends Fragment {

    private Context myContext;
    private EditText etMaxima, etJornada, etPercurso;
    private Button bt_salvar;

    private HoraController horaController;

    private MessegeDialogAlert messege;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_horas, container, false);


        myContext = view.getContext();
        messege = new MessegeDialogAlert(new MainActivity());

        // EditText
        etMaxima = view.findViewById(R.id.et_hora_maxima);
        etJornada = view.findViewById(R.id.jornadaNormalEt);
        etPercurso = view.findViewById(R.id.etPercurso);

        // Controlador
        horaController = new HoraController(view.getContext());
        horaController.carrega(etJornada, etMaxima, etPercurso);

        // Botão
        bt_salvar = view.findViewById(R.id.bt_salvar);

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
        // Inflate the layout for this fragment
        return view;
    }
}