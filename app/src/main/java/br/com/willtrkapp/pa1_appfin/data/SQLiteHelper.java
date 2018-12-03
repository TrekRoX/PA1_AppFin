package br.com.willtrkapp.pa1_appfin.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.net.PortUnreachableException;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pa1appfin.db";

    //Tabela contas
    static final String DB_TABLE_CONTA = "contas";
    static final String TB_CONTA_KEY_ID = "id";
    static final String TB_CONTA_KEY_DESCR = "descr";
    static final String TB_CONTA_KEY_SALDO_INI = "saldoIni";
    static final String TB_CONTA_KEY_SALDO = "saldo";

    //Tabela categorias
    static final String DB_TABLE_CATEGORIA = "categorias";
    static final String TB_CATEGORIA_KEY_ID = "id";
    static final String TB_CATEGORIA_KEY_DESCR = "descr";

    //Tabela trnasacoes
    static final String DB_TABLE_TRANSACAO = "transacoes";
    static final String TB_TRANSACAO_KEY_ID = "id";
    static final String TB_TRANSACAO_KEY_ID_CONTA = "idConta";
    static final String TB_TRANSACAO_KEY_ID_CATEGORIA = "idCategoria";
    static final String TB_TRANSACAO_KEY_VALOR = "valor";
    static final String TB_TRANSACAO_KEY_DESCR = "descr";
    static final String TB_TRANSACAO_KEY_NATUREZA = "natureza";


    private static final String DB_CREATE_TB_CONTA = "CREATE TABLE "+ DB_TABLE_CONTA +" (" +
            TB_CONTA_KEY_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TB_CONTA_KEY_DESCR + " TEXT NOT NULL, " +
            TB_CONTA_KEY_SALDO_INI + " REAL, "  +
            TB_CONTA_KEY_SALDO + " REAL); ";

    private static final String DB_CREATE_TB_CATEGORIA = "CREATE TABLE "+ DB_TABLE_CATEGORIA +" (" +
            TB_CATEGORIA_KEY_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TB_CATEGORIA_KEY_DESCR + " TEXT NOT NULL); ";

    private static final String DB_CREATE_TB_TRANCACAO = "CREATE TABLE "+ DB_TABLE_TRANSACAO +" (" +
            TB_TRANSACAO_KEY_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TB_TRANSACAO_KEY_ID_CONTA  +  " INTEGER NOT NULL, " +
            TB_TRANSACAO_KEY_ID_CATEGORIA  +  " INTEGER NOT NULL, " +
            TB_TRANSACAO_KEY_VALOR + " REAL NOT NULL, "  +
            TB_TRANSACAO_KEY_DESCR + " TEXT NOT NULL, "  +
            TB_TRANSACAO_KEY_NATUREZA + " INTEGER NOT NULL, " + //1 = CREDITO 2 = DEBITO
            " FOREIGN KEY (" + TB_TRANSACAO_KEY_ID_CONTA + ") REFERENCES " + DB_TABLE_CONTA  +"(" + TB_CONTA_KEY_ID + " ), " +
            " FOREIGN KEY (" + TB_TRANSACAO_KEY_ID_CATEGORIA + ") REFERENCES " + DB_TABLE_CATEGORIA  +"(" + TB_CATEGORIA_KEY_ID + ")); ";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("LOG_FIN_PA1", "hit upgrade");
        db.execSQL(DB_CREATE_TB_CONTA);
        Log.v("LOG_FIN_PA1", "Criou conta");
        db.execSQL(DB_CREATE_TB_CATEGORIA);
        Log.v("LOG_FIN_PA1", "Criou categoria");
        db.execSQL(DB_CREATE_TB_TRANCACAO);
        Log.v("LOG_FIN_PA1", "Criou transacao");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
