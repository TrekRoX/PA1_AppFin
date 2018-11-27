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
        List<Conta> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_ID, SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL, SQLiteHelper.KEY_FAVORITO,
                SQLiteHelper.KEY_FONE2, SQLiteHelper.KEY_DTNASC };

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, null , null,
                null, null, SQLiteHelper.KEY_NAME);

        while (cursor.moveToNext())
        {
            Conta conta = new Conta();
            conta.setId(cursor.getInt(0));
            conta.setDescr(cursor.getString(1));
            conta.setSaldoIni(cursor.getFloat(2));
            conta.setSaldo(cursor.getFloat(3));
            contatos.add(conta);
        }
        cursor.close();

        database.close();
        return contatos;
    }
}
