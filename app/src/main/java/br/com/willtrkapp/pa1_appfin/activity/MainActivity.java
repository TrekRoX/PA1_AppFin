package br.com.willtrkapp.pa1_appfin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.willtrkapp.pa1_appfin.R;
import br.com.willtrkapp.pa1_appfin.data.ContaDAO;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContaDAO conta = new ContaDAO(this);
        conta.buscaTodasContas();


    }
}
