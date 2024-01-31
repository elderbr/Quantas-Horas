package br.com.elderbr.android.quantashoras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.elderbr.android.quantashoras.models.Hora;
import br.com.elderbr.android.quantashoras.models.Horario;
import br.com.elderbr.android.quantashoras.utils.Msg;

public class Conexao extends SQLiteOpenHelper {

    public static String BANCO = "quantahora";
    public static int VERSAO = 2;

    private String sql;
    private SQLiteDatabase db;
    private Cursor curso;

    public Conexao(Context context) {
        super((context == null ? MainActivity.myContext : context), BANCO, null, VERSAO);

        if (select() == null) {
            updateBanco();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "CREATE TABLE IF NOT EXISTS hora (id INTEGER PRIMARY KEY AUTOINCREMENT, jornada TEXT NOT NULL, hora_maxima TEXT NOT NULL, hr_percurso TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void updateBanco() {
        db = super.getWritableDatabase();
        try {
            sql = "DROP TABLE IF EXISTS hora";
            db.execSQL(sql);
        } catch (SQLException e) {
            Msg.Erro("Erro ao deletar a tabela!", e);
        } finally {
            db.close();
        }
        onCreate(super.getWritableDatabase());
        insert(new Horario());
    }

    public long insert(Horario horario) {
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Horario.ColumnJornadaNormal(), horario.getJornada().toHoras());
            values.put(Horario.ColumnHoraMaxima(), horario.getHoraMaxima().toHoras());
            values.put(Horario.ColumnPercurso(), horario.getPercurso().toHoras());
            return db.insert("hora", null, values);
        } catch (SQLException e) {
            Msg.Erro("Erro ao adicionar hora!\nErro: " + e.getMessage());
            return 0;
        }
    }

    public int update(Horario horario) {
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Horario.ColumnJornadaNormal(), horario.getJornada().toHoras());
            values.put(Horario.ColumnHoraMaxima(), horario.getHoraMaxima().toHoras());
            values.put(Horario.ColumnPercurso(), horario.getPercurso().toHoras());
            return db.update("hora", values, null, null);
        } catch (SQLException e) {
            Msg.Erro("Erro ao atualizar!", e);
            return 0;
        } finally {
            db.close();
        }
    }

    public Horario select() {
        try {
            db = super.getReadableDatabase();
            curso = db.query("hora", Horario.columnName, null, null, null, null, null);
            while (curso.moveToNext()) {
                Horario horario = new Horario();
                horario.setJornada(new Hora(curso.getString(0)));
                horario.setHoraMaxima(new Hora(curso.getString(1)));
                horario.setPercurso(new Hora(curso.getString(2)));
                return horario;
            }
            curso.close();
        } catch (SQLException e) {
            Msg.Erro("Erro ao buscar a hora!", e);
        }
        return null;
    }
}
