package br.com.willtrkapp.pa1_appfin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.com.willtrkapp.pa1_appfin.R;
import br.com.willtrkapp.pa1_appfin.data.ContaDAO;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {

    private FloatingActionMenu faMenu;
    private FloatingActionButton fabNvConta, fabNvDespesa, fabNvCredito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabNvConta = (FloatingActionButton) findViewById(R.id.fabNvConta);
        fabNvCredito = (FloatingActionButton) findViewById(R.id.fabNvCredito);
        fabNvDespesa = (FloatingActionButton) findViewById(R.id.fabNvDespesa);
        faMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        inicializaEventosFloatingMenu();


        ContaDAO conta = new ContaDAO(this);
        conta.buscaTodasContas();


    }

    private void inicializaEventosFloatingMenu()
    {
        //Controlando abertura e fechamento do menu flutuante
/*        faMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                *//*if (opened) {
                    showToast("Menu aberto");
                } else {
                    showToast("Menu fechado");
                }*//*
            }
        });*/

        //Fecha o menu em caso de click fora do floatingbutton
        faMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (faMenu.isOpened()) {
                    faMenu.close(true);
                }
            }
        });

        //Controla os clicks individuais de cada floating button
        fabNvConta.setOnClickListener(onFloatButtonClick());
        fabNvDespesa.setOnClickListener(onFloatButtonClick());
        fabNvCredito.setOnClickListener(onFloatButtonClick());
    }

    private View.OnClickListener onFloatButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fabNvConta) {
                    showToast("Nova conta");
                } else if (view == fabNvDespesa) {
                    showToast("Novo debito");
                } else if (view == fabNvCredito) {
                    showToast("Novo credito");
                }
                faMenu.close(true);
            }
        };
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
