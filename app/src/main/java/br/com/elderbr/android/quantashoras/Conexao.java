package br.com.elderbr.android.quantashoras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {

    private Context context;

    private String sql;
    private SQLiteDatabase db;
    private Cursor curso;
    private ContentValues values;

    private Hora hora;


    public Conexao(@Nullable Context context) {
        super(context, "quantahora.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "CREATE TABLE IF NOT EXISTS hora (id INTEGER PRIMARY KEY AUTOINCREMENT, hora VARCHAR(5));";
        db.execSQL(sql);

        insert(new Hora(11, 45));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insert(Hora hora) {
        if (select().getDoubleHora() < 1.0) {
            db = this.getWritableDatabase();
            values = new ContentValues();
            values.put("hora", hora.toHoras());
            return (db.insert("hora", null, values) > 0 ? true : false);
        } else {
            return false;
        }
    }

    public int update(Hora hora) {
        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("hora", hora.toHoras());
        return db.update("hora", values, "id = 1", null);
    }

    public Hora select() {
        hora = new Hora();
        hora.setHora(0, 0);
        db = this.getReadableDatabase();
        try {
            curso = db.rawQuery("SELECT * FROM hora;", null);
            while (curso.moveToNext()) {
                hora.parse(curso.getString(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erro >> "+ e.getMessage(), Toast.LENGTH_LONG);
            Log.i("Script","Erro >> "+ e.getMessage());
        }
        return hora;
    }
}
