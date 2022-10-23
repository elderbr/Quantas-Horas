package br.com.elderbr.android.quantashoras.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import br.com.elderbr.android.quantashoras.R;

public class MessegeDialogAlert {

    private Activity myActivity;
    private AlertDialog alertDialog;

    public MessegeDialogAlert(Activity myActivity) {
        this.myActivity = myActivity;
    }

    public void startMessege(@NotNull String msg){

        LayoutInflater inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_messege_dialog, null);

        TextView message = view.findViewById(R.id.msgTextTv);
        message.setText(msg);

        Button button = view.findViewById(R.id.msgBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAlert();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(myActivity);
        builder.setView(view);
        builder.setCancelable(true);

        alertDialog = builder.create();
        alertDialog.setIcon(R.mipmap.ic_relogio);
        alertDialog.show();
    }

    public void dismissAlert(){
        if(alertDialog == null){
            throw new RuntimeException("O não foi criado o Alerta!");
        }
        alertDialog.dismiss();
    }



}
