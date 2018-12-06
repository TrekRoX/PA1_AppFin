package br.com.willtrkapp.pa1_appfin.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import br.com.willtrkapp.pa1_appfin.R;
import br.com.willtrkapp.pa1_appfin.adapter.ContaAdapter;
import br.com.willtrkapp.pa1_appfin.data.ContaDAO;
import br.com.willtrkapp.pa1_appfin.model.Conta;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private ContaDAO contaDAO;
    private FloatingActionMenu faMenu;
    private FloatingActionButton fabNvConta, fabNvDespesa, fabNvCredito;

    private RecyclerView recyclerView;

    private List<Conta> contas = new ArrayList<>();
    private ContaAdapter adapter;


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            /*String query = intent.getStringExtra(SearchManager.QUERY);
            searchView.clearFocus();
            updateUI(query);*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        handleIntent(intent);

        contaDAO= new ContaDAO(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.contas_recycler_view);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        adapter = new ContaAdapter(contas, this);
        recyclerView.setAdapter(adapter);

        setupRecyclerView();

        fabNvConta = (FloatingActionButton) findViewById(R.id.fabNvConta);
        fabNvCredito = (FloatingActionButton) findViewById(R.id.fabNvCredito);
        fabNvDespesa = (FloatingActionButton) findViewById(R.id.fabNvDespesa);
        faMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        inicializaEventosFloatingMenu();

        updateUI();

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
                    Intent i = new Intent(getApplicationContext(), ContaActivity.class);
                    startActivityForResult(i, 1);
                } else if (view == fabNvDespesa) {
                    showToast("Novo debito");
                } else if (view == fabNvCredito) {
                    showToast("Novo credito");
                }
                faMenu.close(true);
            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1)
            if (resultCode == RESULT_OK) {
                showToast(getResources().getString(R.string.conta_adicionada));
                //   adapter.notifyItemInserted(adapter.getItemCount());
                updateUI();
            }



/*        if (requestCode == 2) {
            if (resultCode == RESULT_OK)
                showSnackBar(getResources().getString(R.string.contato_alterado));
            if (resultCode == 3)
                showSnackBar(getResources().getString(R.string.contato_apagado));



            updateUI(null);
        }*/
    }


    private void updateUI()
    {

        contas.clear();

        contas.addAll(contaDAO.buscaTodasContas());
        /*empty.setText(getResources().getString(R.string.lista_contas_vazia));*/
        /*fab.show();*/


        recyclerView.getAdapter().notifyDataSetChanged();

        if (recyclerView.getAdapter().getItemCount()==0)
            /*empty.setVisibility(View.VISIBLE);*/
            showToast(getResources().getString(R.string.lista_contas_vazia));
        /*else*/
            /*empty.setVisibility(View.GONE);*/

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    private void setupRecyclerView() {

        adapter.setClickListener(new ContaAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.v("LOG_FIN_PA1", "Hit onItemClick");

                final Conta conta = contas.get(position);
                Intent i = new Intent(getApplicationContext(), ContaActivity.class);
                i.putExtra("conta", conta);
                startActivityForResult(i, 2);
            }
        });




        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if (swipeDir == ItemTouchHelper.RIGHT) {
                    Conta conta = contas.get(viewHolder.getAdapterPosition());
                    contaDAO.removeConta(conta);
                    contas.remove(viewHolder.getAdapterPosition());
                    recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
                    /*showSnackBar(getResources().getString(R.string.contato_apagado));*/
                    showToast(getResources().getString(R.string.conta_apagada));

                }
            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                Paint p = new Paint();
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(ContextCompat.getColor(getBaseContext(), R.color.colorDelete));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_account_remove);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }



        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
}
