package br.com.willtrkapp.pa1_appfin.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.willtrkapp.pa1_appfin.model.Conta;

public class ContaDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContaDAO(Context context) {
        this.dbHelper=new SQLiteHelper(context);
    }

    public List<Conta> buscaTodasContas()
    {
        database=dbHelper.getReadableDatabase();
        List<Conta> contas = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.TB_CONTA_KEY_ID, SQLiteHelper.TB_CONTA_KEY_DESCR, SQLiteHelper.TB_CONTA_KEY_SALDO_INI, SQLiteHelper.TB_CONTA_KEY_SALDO };

        cursor = database.query(SQLiteHelper.DB_TABLE_CONTA, cols, null , null,
                null, null, SQLiteHelper.TB_CONTA_KEY_DESCR);

        while (cursor.moveToNext())
        {
            Conta conta = new Conta();
            conta.setId(cursor.getInt(0));
            conta.setDescr(cursor.getString(1));
            conta.setSaldoIni(cursor.getFloat(2));
            conta.setSaldo(cursor.getFloat(3));
            contas.add(conta);
        }
        cursor.close();

        database.close();
        return contas;
    }

    public float getSaldoContas()
    {
        float saldo = 0;
        database = dbHelper.getReadableDatabase();
        String sql= "SELECT SUM(" + SQLiteHelper.TB_CONTA_KEY_SALDO_INI + ") FROM " + SQLiteHelper.DB_TABLE_CONTA + ";";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst())
            saldo = cursor.getFloat(0);

        cursor.close();

        return saldo;
    }
}
