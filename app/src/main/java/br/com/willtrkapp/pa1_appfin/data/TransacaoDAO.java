package br.com.willtrkapp.pa1_appfin.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.willtrkapp.pa1_appfin.model.Transacao;

public class TransacaoDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public TransacaoDAO(Context context) {
        this.dbHelper=new SQLiteHelper(context);
    }

    public List<Transacao> buscaTodasTransacaoes()
    {
        database=dbHelper.getReadableDatabase();
        List<Transacao> transacoes = new ArrayList<>();

        Cursor cursor;

        String[] cols = new String[] {SQLiteHelper.TB_TRANSACAO_KEY_ID, SQLiteHelper.TB_TRANSACAO_KEY_ID_CONTA, SQLiteHelper.TB_TRANSACAO_KEY_ID_CATEGORIA, SQLiteHelper.TB_TRANSACAO_KEY_VALOR,
           SQLiteHelper.TB_TRANSACAO_KEY_DESCR, SQLiteHelper.TB_TRANSACAO_KEY_NATUREZA };

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

            transacoes.add(trans);
        }
        cursor.close();

        database.close();
        return transacoes;
    }
}
