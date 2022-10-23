package br.com.elderbr.android.quantashoras.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class Msg {

    private static String tag = "Script";

    public static void Log(String msg) {
        Log.i(tag, msg);
    }

    public static void Log(@NotNull String msg, @NotNull Class classe) {
        Log.i(tag, msg + " - classe: " + classe.getSimpleName());
    }

    public static void Erro(@NotNull String msg) {
        Log.e(tag, msg);
    }

    public static void Erro(@NotNull String msg, @NotNull Exception exception) {
        Log.e(tag, msg + "\nErro: " + exception.getMessage());
    }

    public static void AVISO(Context myContext, @NotNull String msg) {
        Toast.makeText(myContext, msg, Toast.LENGTH_LONG).show();
        Log(msg);
    }

}
