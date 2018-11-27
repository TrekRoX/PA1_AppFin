package br.com.willtrkapp.pa1_appfin.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pa1appfin.db";

    static final String DB_TABLE_CONTA = "contas";
    static final String TB_CONTA_KEY_ID = "id";
    static final String TB_CONTA_KEY_DESCR = "descr";
    static final String TB_CONTA_KEY_SALDO_INI = "saldoIni";
    static final String TB_CONTA_KEY_SALDO = "saldo";


    private static final String DATABASE_CREATE = "CREATE TABLE "+ DB_TABLE_CONTA +" (" +
            TB_CONTA_KEY_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TB_CONTA_KEY_DESCR + " TEXT NOT NULL, " +
            TB_CONTA_KEY_SALDO_INI + " TEXT, "  +
            TB_CONTA_KEY_SALDO + " TEXT)";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
