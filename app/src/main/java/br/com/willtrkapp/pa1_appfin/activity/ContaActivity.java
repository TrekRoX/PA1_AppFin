package br.com.willtrkapp.pa1_appfin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.willtrkapp.pa1_appfin.R;
import br.com.willtrkapp.pa1_appfin.data.ContaDAO;
import br.com.willtrkapp.pa1_appfin.model.Conta;


public class ContaActivity extends AppCompatActivity {
    private Conta conta;
    private ContaDAO contaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("conta"))
        {
            this.conta = (Conta) getIntent().getSerializableExtra("conta");
            EditText descrText = (EditText)findViewById(R.id.editTextDescricao);
            descrText.setText(conta.getDescr());

            EditText saldoIniText = (EditText)findViewById(R.id.editTextSaldoInicial);
            saldoIniText.setText(String.format("%.2f", conta.getSaldoIni()));

            int pos =conta.getDescr().indexOf(" ");
            if (pos==-1)
                pos=conta.getDescr().length();
            setTitle(conta.getDescr().substring(0,pos));
        }
        contaDAO = new ContaDAO(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("conta"))
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
        contaDAO.removeConta(conta);
        Intent resultIntent = new Intent();
        setResult(3,resultIntent);
        finish();
    }

    private void salvar()
    {
        String descr = ((EditText) findViewById(R.id.editTextDescricao)).getText().toString();
        float saldoInicial = Float.valueOf(((EditText) findViewById(R.id.editTextSaldoInicial)).getText().toString());

        if (conta==null)
            conta = new Conta();


        conta.setDescr(descr);
        conta.setSaldoIni(saldoInicial);

        contaDAO.salvaConta(conta);
        Intent resultIntent = new Intent();
        setResult(RESULT_OK,resultIntent);
        finish();
    }

}
