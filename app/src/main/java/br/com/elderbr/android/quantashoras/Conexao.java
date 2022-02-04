package br.com.elderbr.android.quantashoras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {

    private Context context;

    private String sql;
    private SQLiteDatabase db;
    private Cursor curso;
    private ContentValues values;

    private Hora hora;


    public Conexao(@Nullable Context context) {
        super(context, "quantahora", null, 1);
        this.context = context;

        if(select().getDoubleHora()<1){
            insert(new Hora(11,45));
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "CREATE TABLE IF NOT EXISTS hora (id INTEGER PRIMARY KEY AUTOINCREMENT, hora TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(Hora hora) {
        try {
            db = this.getWritableDatabase();
            values = new ContentValues();
            values.put("hora", hora.toHoras());
            return db.insert("hora", null, values);
        } catch (SQLException e) {
            Log.i("Script", "Erro ao adicionar >> " + e.getMessage());
            return 0;
        }
    }

    public int update(Hora hora) {
        try {
            db = this.getWritableDatabase();
            values = new ContentValues();
            values.put("hora", hora.toHoras());
            return db.update("hora", values, "id = 1", null);
        } catch (SQLException e) {
            Log.i("Script", "Erro ao atualizar >> " + e.getMessage());
            return 0;
        }
    }

    public Hora select() {
        hora = new Hora();
        hora.setHora(0, 0);

        try {
            db = super.getReadableDatabase();
            curso = db.rawQuery("SELECT * FROM hora", null);
            while (curso.moveToNext()) {
                hora.parse(curso.getString(1));
            }
            curso.close();
        } catch (SQLException e) {
            Log.i("Script", "Erro: " + e.getMessage());
        }
        return hora;
    }
}
