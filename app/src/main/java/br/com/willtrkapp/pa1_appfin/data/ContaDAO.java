package br.com.willtrkapp.pa1_appfin.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.willtrkapp.pa1_appfin.model.Conta;
import br.com.willtrkapp.pa1_appfin.util.Utilitarios;
import br.com.willtrkapp.pa1_appfin.view.ContaSaldo;

public class ContaDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContaDAO(Context context) {
        this.dbHelper=new SQLiteHelper(context);
    }

    public List<Conta> buscaTodasContas() {
        database = dbHelper.getReadableDatabase();
        List<Conta> contas = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.TB_CONTA_KEY_ID, SQLiteHelper.TB_CONTA_KEY_DESCR, SQLiteHelper.TB_CONTA_KEY_SALDO_INI };

        cursor = database.query(SQLiteHelper.DB_TABLE_CONTA, cols, null , null,
                null, null, SQLiteHelper.TB_CONTA_KEY_DESCR);

        while (cursor.moveToNext())
        {
            Conta conta = new Conta();
            conta.setId(cursor.getInt(0));
            conta.setDescr(cursor.getString(1));
            conta.setSaldoIni(cursor.getFloat(2));
            contas.add(conta);
        }
        cursor.close();

        database.close();
        return contas;
    }

    public float getSaldoAtualContas() {

        float totSaldoIni = 0, totValorTrans = 0;
        database = dbHelper.getReadableDatabase();

        //Saldo Inicial de todas as contas
        String salConta= "SELECT SUM(" + SQLiteHelper.TB_CONTA_KEY_SALDO_INI + ") FROM " + SQLiteHelper.DB_TABLE_CONTA + ";";
        Cursor cursorConta = database.rawQuery(salConta, null);
        if(cursorConta.moveToFirst())
            totSaldoIni = cursorConta.getFloat(0);

        cursorConta.close();

        Long dtTodayQuery = new Utilitarios().getCurrentUnixDate();
        //Total de todas as transações até a data de hoje
        String sqlTrans = "SELECT SUM(" + SQLiteHelper.TB_TRANSACAO_KEY_VALOR + ") FROM " + SQLiteHelper.DB_TABLE_TRANSACAO + " WHERE " + SQLiteHelper.DB_TABLE_TRANSACAO + "." + SQLiteHelper.TB_TRANSACAO_KEY_DATA_LIB + " <= " + dtTodayQuery + ";";
        Cursor cursorTrans = database.rawQuery(sqlTrans, null);
        if(cursorTrans.moveToFirst())
            totValorTrans = cursorTrans.getFloat(0);

        cursorTrans.close();

        return totSaldoIni + totValorTrans;
    }

    public List<ContaSaldo> buscaTodasContasComSaldoAtual()
    {
        database = dbHelper.getReadableDatabase();
        List<ContaSaldo> contas = new ArrayList<>();
        Long dtTodayQuery = new Utilitarios().getCurrentUnixDate();

        /*String sql = "SELECT " + SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_ID  +  ", " + SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_DESCR  + ", " + SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_SALDO_INI + ", " +
        SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_SALDO_INI + " + IFNULL(SUM (" + SQLiteHelper.DB_TABLE_TRANSACAO  + "." + SQLiteHelper.TB_TRANSACAO_KEY_VALOR +
        "), 0) FROM " + SQLiteHelper.DB_TABLE_CONTA + " LEFT JOIN " +  SQLiteHelper.DB_TABLE_TRANSACAO + " ON " +  SQLiteHelper.DB_TABLE_TRANSACAO  + "." + SQLiteHelper.TB_TRANSACAO_KEY_ID_CONTA + " = " + SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_ID +
        " WHERE " + SQLiteHelper.DB_TABLE_TRANSACAO + "." + SQLiteHelper.TB_TRANSACAO_KEY_DATA_LIB + " <= " + dtTodayQuery +
        " OR  " + SQLiteHelper.DB_TABLE_TRANSACAO + "." + SQLiteHelper.TB_TRANSACAO_KEY_ID + " IS NULL " +
        "  GROUP BY " + SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_ID + ";";*/

        String sql = "SELECT " + SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_ID  +  ", " + SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_DESCR  + ", " + SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_SALDO_INI + ", " +
                SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_SALDO_INI + " + IFNULL(SUM (" + SQLiteHelper.DB_TABLE_TRANSACAO  + "." + SQLiteHelper.TB_TRANSACAO_KEY_VALOR +
                "), 0) FROM " + SQLiteHelper.DB_TABLE_CONTA + " LEFT JOIN " +  SQLiteHelper.DB_TABLE_TRANSACAO + " ON " +  SQLiteHelper.DB_TABLE_TRANSACAO  + "." + SQLiteHelper.TB_TRANSACAO_KEY_ID_CONTA + " = " + SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_ID +
                " AND " + SQLiteHelper.DB_TABLE_TRANSACAO + "." + SQLiteHelper.TB_TRANSACAO_KEY_DATA_LIB + " <= " + dtTodayQuery +
                "  GROUP BY " + SQLiteHelper.DB_TABLE_CONTA + "." + SQLiteHelper.TB_CONTA_KEY_ID + ";";


        Log.v("LOG_FIN_PA1", "SQL: " + sql);


        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext())
        {
            ContaSaldo conta = new ContaSaldo();
            conta.setId(cursor.getLong(0));
            conta.setDescr(cursor.getString(1));
            conta.setSaldoIni(cursor.getFloat(2));
            conta.setSaldo(cursor.getFloat(3));
            contas.add(conta);
        }
        cursor.close();

        database.close();
        return contas;

    }

    public void salvaConta(Conta c) {

        Log.v("LOG_FIN_PA1", "hit salvaConta");
        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.TB_CONTA_KEY_DESCR, c.getDescr());
        values.put(SQLiteHelper.TB_CONTA_KEY_SALDO_INI, c.getSaldoIni());


        if (c.getId()>0)
        {

            database.update(SQLiteHelper.DB_TABLE_CONTA, values, SQLiteHelper.TB_CONTA_KEY_ID + "=" + c.getId(), null);
            Log.v("LOG_FIN_PA1", "fez update");
        }
        else
        {
            database.insert(SQLiteHelper.DB_TABLE_CONTA, null, values);
            Log.v("LOG_FIN_PA1", "fez insert");
        }


        database.close();

    }

    public void removeConta(Conta c) {
        database = dbHelper.getReadableDatabase();
        /*database.execSQL("PRAGMA foreign_keys=ON");*/
        database.delete(SQLiteHelper.DB_TABLE_CONTA, SQLiteHelper.TB_CONTA_KEY_ID + "=" + c.getId(), null);
    }
}
