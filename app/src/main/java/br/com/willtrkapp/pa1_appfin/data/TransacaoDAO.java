package br.com.willtrkapp.pa1_appfin.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.willtrkapp.pa1_appfin.model.Transacao;

public class TransacaoDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public TransacaoDAO(Context context) {
        this.dbHelper=new SQLiteHelper(context);
    }

    public List<Transacao> buscaTodasTransacaoes() {
        database=dbHelper.getReadableDatabase();
        List<Transacao> transacoes = new ArrayList<>();

        Cursor cursor;

        String[] cols = new String[] {SQLiteHelper.TB_TRANSACAO_KEY_ID, SQLiteHelper.TB_TRANSACAO_KEY_ID_CONTA, SQLiteHelper.TB_TRANSACAO_KEY_ID_CATEGORIA, SQLiteHelper.TB_TRANSACAO_KEY_VALOR,
           SQLiteHelper.TB_TRANSACAO_KEY_DESCR, SQLiteHelper.TB_TRANSACAO_KEY_NATUREZA, SQLiteHelper.TB_TRANSACAO_KEY_DATA_INS, SQLiteHelper.TB_TRANSACAO_KEY_DATA_LIB };

        cursor = database.query(SQLiteHelper.DB_TABLE_TRANSACAO, cols, null , null,
                null, null, SQLiteHelper.TB_TRANSACAO_KEY_DESCR);

        while (cursor.moveToNext())
        {
            Transacao trans = new Transacao();
            trans.setId(cursor.getInt(0));
            trans.setIdConta(cursor.getInt(1));
            trans.setIdCategoria(cursor.getInt(2));
            trans.setValor(cursor.getFloat(3));
            trans.setDescricao(cursor.getString(4));
            trans.setNatureza(cursor.getInt(5));
            trans.setDtIns(new Date(cursor.getLong(6)));
            trans.setDtLib(new Date(cursor.getLong(7)));

            transacoes.add(trans);
        }
        cursor.close();

        database.close();
        return transacoes;
    }

    public void salvaTransacao(Transacao trans) {

        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.TB_TRANSACAO_KEY_ID_CONTA, trans.getIdConta());
        values.put(SQLiteHelper.TB_TRANSACAO_KEY_ID_CATEGORIA, trans.getIdCategoria());
        values.put(SQLiteHelper.TB_TRANSACAO_KEY_VALOR, trans.getValor());
        values.put(SQLiteHelper.TB_TRANSACAO_KEY_DESCR, trans.getDescricao());
        values.put(SQLiteHelper.TB_TRANSACAO_KEY_NATUREZA, trans.getNatureza());
        values.put(SQLiteHelper.TB_TRANSACAO_KEY_DATA_INS, trans.getDtIns().getTime() / 1000); //TESTAR
        values.put(SQLiteHelper.TB_TRANSACAO_KEY_DATA_LIB, trans.getDtLib().getTime() / 1000); //TESTAR



        if (trans.getId()>0)
            database.update(SQLiteHelper.DB_TABLE_TRANSACAO, values, SQLiteHelper.TB_TRANSACAO_KEY_ID + "="
                    + trans.getId(), null);
        else
            database.insert(SQLiteHelper.DB_TABLE_TRANSACAO, null, values);

        database.close();

    }

    public void removeTransacao(Transacao trans) {
        dbHelper.getReadableDatabase().delete(SQLiteHelper.DB_TABLE_TRANSACAO, SQLiteHelper.TB_TRANSACAO_KEY_ID + "=" + trans.getId(), null);
    }
}
