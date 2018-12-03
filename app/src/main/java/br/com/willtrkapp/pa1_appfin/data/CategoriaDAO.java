package br.com.willtrkapp.pa1_appfin.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.willtrkapp.pa1_appfin.model.Categoria;

public class CategoriaDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public CategoriaDAO(Context context) {
        this.dbHelper=new SQLiteHelper(context);
    }

    public List<Categoria> buscaTodasContas()
    {
        database=dbHelper.getReadableDatabase();
        List<Categoria> categorias = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.TB_CATEGORIA_KEY_ID, SQLiteHelper.TB_CATEGORIA_KEY_DESCR };

        cursor = database.query(SQLiteHelper.DB_TABLE_CATEGORIA, cols, null , null,
                null, null, SQLiteHelper.TB_CATEGORIA_KEY_DESCR);

        while (cursor.moveToNext())
        {
            Categoria categ = new Categoria();
            categ.setId(cursor.getInt(0));
            categ.setDescr(cursor.getString(1));

            categorias.add(categ);
        }
        cursor.close();

        database.close();
        return categorias;
    }
}
