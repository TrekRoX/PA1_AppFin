package br.com.willtrkapp.pa1_appfin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.willtrkapp.pa1_appfin.R;
import br.com.willtrkapp.pa1_appfin.data.CategoriaDAO;
import br.com.willtrkapp.pa1_appfin.data.ContaDAO;
import br.com.willtrkapp.pa1_appfin.data.TransacaoDAO;
import br.com.willtrkapp.pa1_appfin.model.Categoria;
import br.com.willtrkapp.pa1_appfin.model.Conta;
import br.com.willtrkapp.pa1_appfin.model.Transacao;

public class TransacaoActivity extends AppCompatActivity {
    private Transacao transacao;
    private TransacaoDAO transacaoDAO;
    private int natureza; //1= Credito 2= Debito
    private List<Categoria> categorias;
    private List<Conta> contas;
    private Spinner categoriaTransSpinner, contaTransSpinner;
    private long idCategoriaTransSelecionada, idContaTransSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacao);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTransacao);
        setSupportActionBar(toolbar);

        //Identificando o modo da tela
        if(getIntent().getAction() == "Novo Crédito")
            natureza = 1;
        else
            natureza = 2;

        setTitle(getIntent().getAction());


        //Crregando categorias
        categoriaTransSpinner = findViewById(R.id.spinnerCategoriaTrans);
        contaTransSpinner = findViewById(R.id.spinnerContaTrans);

        setUpCategoriaTrans();
        setUpContaTrans();


        transacaoDAO = new TransacaoDAO(this);
    }

    private void setUpCategoriaTrans()
    {
        categorias = new CategoriaDAO(this).buscaTodasCategorias();


        ArrayAdapter<Categoria> spinnerArrayAdapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item,  categorias); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoriaTransSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                idCategoriaTransSelecionada = ((Categoria)parentView.getItemAtPosition(position)).getId();
                Log.v("LOG_FIN_PA1", "Selecionou categoria de id " + idCategoriaTransSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        categoriaTransSpinner.setAdapter(spinnerArrayAdapter);


    }

    private void setUpContaTrans()
    {
        contas = new ContaDAO(this).buscaTodasContas();
        ArrayAdapter<Conta> spinnerArrayAdapter = new ArrayAdapter<Conta>(this, android.R.layout.simple_spinner_item, contas); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        contaTransSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                idContaTransSelecionada = ((Conta)parentView.getItemAtPosition(position)).getId();
                Log.v("LOG_FIN_PA1", "Selecionou conta de id " + idContaTransSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        contaTransSpinner.setAdapter(spinnerArrayAdapter);
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
        String descr = ((EditText) findViewById(R.id.editTextDescrTrans)).getText().toString();
        float valor = Float.valueOf(((EditText) findViewById(R.id.editTextValorTrans)).getText().toString());
        Date dtToday = null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            dtToday = formatter.parse(formatter.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.v("LOG_FIN_PA1", "Data: " + formatter2.format(dtToday));

        if (transacao ==null)
            transacao = new Transacao();

        transacao.setNatureza(natureza);
        transacao.setDescricao(descr);
        transacao.setIdCategoria(idCategoriaTransSelecionada);
        transacao.setIdConta(idContaTransSelecionada);
        transacao.setDtIns(dtToday);
        transacao.setDtLib(dtToday);


        if(natureza != 1)
            valor = valor * -1;

        transacao.setValor(valor);

        transacaoDAO.salvaTransacao(transacao);
        Intent resultIntent = new Intent();
        resultIntent.setAction(transacao.getNatureza() == 1 ? "Crédito inserido" : "Débito inserido");
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
