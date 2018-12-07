package br.com.willtrkapp.pa1_appfin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.willtrkapp.pa1_appfin.R;
import br.com.willtrkapp.pa1_appfin.data.ContaDAO;
import br.com.willtrkapp.pa1_appfin.data.TransacaoDAO;
import br.com.willtrkapp.pa1_appfin.model.Conta;
import br.com.willtrkapp.pa1_appfin.model.Transacao;

public class TransacaoActivity extends AppCompatActivity {
    private Transacao transacao;
    private TransacaoDAO transacaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTransacao);
        setSupportActionBar(toolbar);


        setTitle(getIntent().getAction());

        if (getIntent().hasExtra("transacao"))
        {
            this.transacao = (Transacao) getIntent().getSerializableExtra("transacao");
            //EditText descrText = (EditText)findViewById(R.id.editTextDescricao);
            //descrText.setText(conta.getDescr());

            //EditText saldoIniText = (EditText)findViewById(R.id.editTextSaldoInicial);
            //saldoIniText.setText(String.format("%.2f", conta.getSaldoIni()));

            int pos =transacao.getDescricao().indexOf(" ");
            if (pos==-1)
                pos=transacao.getDescricao().length();
            setTitle(transacao.getDescricao().substring(0,pos));
        }
        transacaoDAO = new TransacaoDAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("transacao"))
        {
            MenuItem item = menu.findItem(R.id.deleteMenu);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarMenu:
                salvar();
                return true;
            case R.id.deleteMenu:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void delete()
    {
        transacaoDAO.removeTransacao(transacao);
        Intent resultIntent = new Intent();
        setResult(3,resultIntent);
        finish();
    }

    private void salvar()
    {
        //String descr = ((EditText) findViewById(R.id.editTextDescricao)).getText().toString();
        //float saldoInicial = Float.valueOf(((EditText) findViewById(R.id.editTextSaldoInicial)).getText().toString());

        if (transacao ==null)
            transacao = new Transacao();


        //conta.setDescr(descr);
        //conta.setSaldoIni(saldoInicial);

        //transacaoDAO.salvaTransacao(transacao);
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
