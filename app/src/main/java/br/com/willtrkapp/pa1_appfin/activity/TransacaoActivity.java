package br.com.willtrkapp.pa1_appfin.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private TransacaoDAO transacaoDAO;
    private int natureza; //1= Credito 2= Debito
    private List<Categoria> categorias;
    private List<Conta> contas;
    private Spinner categoriaTransSpinner, contaTransSpinner, periodoRepeticoesSpinner;
    private long idCategoriaTransSelecionada, idContaTransSelecionada;
    private EditText editTextDtTrans;
    private CheckBox checkBoxRepeticoes;
    private NumberPicker numberPickerRepeticoes;

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

        //Data da transacao
        editTextDtTrans = (EditText) findViewById(R.id.editTexDtTrans);

        periodoRepeticoesSpinner = (Spinner) findViewById(R.id.spinnerPeriodoRepeticoes);
        numberPickerRepeticoes = (NumberPicker) findViewById(R.id.numberPickerRepeticoes);

        numberPickerRepeticoes.setMaxValue(100);
        numberPickerRepeticoes.setMinValue(2);
        numberPickerRepeticoes.setWrapSelectorWheel(true);

        checkBoxRepeticoes = (CheckBox) findViewById(R.id.checkBoxRepeticoes);


        //Crregando categorias
        categoriaTransSpinner = findViewById(R.id.spinnerCategoriaTrans);
        contaTransSpinner = findViewById(R.id.spinnerContaTrans);

        setUpCalendar();
        setUpChkRepeticoes();
        setUpCategoriaTrans();
        setUpContaTrans();


        transacaoDAO = new TransacaoDAO(this);
    }

    private void setUpChkRepeticoes()
    {
        checkBoxRepeticoes.setOnClickListener(new View.OnClickListener()  {

              @Override
              public void onClick(View v) {
                  if(checkBoxRepeticoes.isChecked())
                      ((LinearLayout)findViewById(R.id.linearLayoutRepeticoes)).setVisibility(View.VISIBLE);
                  else
                      ((LinearLayout)findViewById(R.id.linearLayoutRepeticoes)).setVisibility(View.GONE);

              }
          }
        );
    }

    private void setUpCalendar()
    {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DecimalFormat df = new DecimalFormat("00");
        editTextDtTrans.setText(df.format(dayOfMonth)  + "/" + df.format(month + 1 )+ "/" + year);

        editTextDtTrans.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TransacaoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                DecimalFormat df = new DecimalFormat("00");
                                editTextDtTrans.setText(df.format(day)  + "/" + df.format(month + 1 )+ "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();

            }
        });
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
                /*delete();*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void salvar()
    {
        if(idContaTransSelecionada != 0)
        {
            String strValor = ((EditText) findViewById(R.id.editTextValorTrans)).getText().toString();
            if(strValor != null && !strValor.isEmpty())
            {
                String descr = ((EditText) findViewById(R.id.editTextDescrTrans)).getText().toString();
                float valor = Float.valueOf(strValor);
                if(natureza != 1)
                    valor = valor * -1;

                Date dtToday = null, dtInicial = null;
                int count = 0;
                int numRepeticoes = 1;

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                try {
                    dtToday = formatter.parse(formatter.format(new Date()));
                    dtInicial = formatter.parse(editTextDtTrans.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }





                if(checkBoxRepeticoes.isChecked())
                    numRepeticoes = numberPickerRepeticoes.getValue();

                Log.v("LOG_FIN_PA1", "Vai repetir: " + numRepeticoes);

                do {

                    Transacao transacao = new Transacao();
                    transacao.setNatureza(natureza);
                    transacao.setDescricao(descr);
                    transacao.setIdCategoria(idCategoriaTransSelecionada);
                    transacao.setIdConta(idContaTransSelecionada);
                    transacao.setDtIns(dtToday);
                    transacao.setDtLib(getDtLib(dtInicial, count));
                    transacao.setValor(valor);
                    transacaoDAO.salvaTransacao(transacao);
                    Log.v("LOG_FIN_PA1", "Data Lib: " + formatter2.format(transacao.getDtLib()));
                    count++;
                }while (count < numRepeticoes);



                Intent resultIntent = new Intent();
                resultIntent.setAction(natureza == 1 ? "Crédito inserido" : "Débito inserido");
                setResult(RESULT_OK, resultIntent);
                finish();
            }
            else
                Toast.makeText(this, R.string.digite_o_valor_antes, Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, R.string.nenhuma_conta_selecionada_cadastre_uma_conta_antes, Toast.LENGTH_SHORT).show();
    }

    private Date getDtLib(Date dtSelecionada, int iteracao)
    {
        Date dataRepeticao = dtSelecionada;
        Calendar c = Calendar.getInstance();
        c.setTime(dataRepeticao);

        switch (periodoRepeticoesSpinner.getSelectedItemPosition())
        {
            case 0: //Diario
                c.add(Calendar.DATE, iteracao);
                break;

            case 1://Semanal
                c.add(Calendar.DATE, iteracao * 7);
                break;

            case 2://Mensal
                c.add(Calendar.MONTH, iteracao);
                break;

            case 3:
                c.add(Calendar.YEAR, iteracao);
                break;//Anual

            default:
                break;

        }

        dataRepeticao = c.getTime();


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataRepeticao = formatter.parse(formatter.format(dataRepeticao));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dataRepeticao;
    }
}
