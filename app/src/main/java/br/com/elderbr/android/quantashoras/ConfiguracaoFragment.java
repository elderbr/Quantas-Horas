package br.com.elderbr.android.quantashoras;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import br.com.elderbr.android.quantashoras.controllers.HoraController;
import br.com.elderbr.android.quantashoras.utils.Msg;

public class ConfiguracaoFragment extends Fragment {


    private EditText etHoraNormal, etHoraMaxima, etPercurso;
    private Button btnSave;

    private View view;
    private Context context;

    private HoraController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_configuracao, container, false);
        context = inflater.getContext();

        init();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                controller.setHoraNormal(etHoraNormal);
                controller.setMaxima(etHoraMaxima);
                controller.setPercurso(etPercurso);

                if(controller.save()){
                    Msg.AVISO(context,"Horas alteradas com sucesso!");
                }else{
                    Msg.AVISO(context,"Não houve alterção!");
                }
            }
        });


        return view;
    }

    private void init() {
        etHoraNormal = view.findViewById(R.id.etHoraNormal);
        etHoraMaxima = view.findViewById(R.id.etHoraMaxima);
        etPercurso = view.findViewById(R.id.etPercurso);
        btnSave = view.findViewById(R.id.btnSaveConfig);

        // Controler
        controller = new HoraController(context);
        controller.carrega(etHoraNormal, etHoraMaxima, etPercurso);
    }
}