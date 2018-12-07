package br.com.willtrkapp.pa1_appfin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.willtrkapp.pa1_appfin.R;
import br.com.willtrkapp.pa1_appfin.model.Conta;

public class ContaAdapter extends RecyclerView.Adapter<ContaAdapter.ContaViewHolder> {

    private static List<Conta> contas;
    private Context context;


    private static ItemClickListener clickListener;

    public ContaAdapter(List<Conta> contas, Context context) {
        this.contas = contas;
        this.context = context;
    }

    @Override
    public ContaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.contato_celula, viewGroup, false);
        return new ContaViewHolder(view);
    }


    @Override
    public void onBindViewHolder( ContaViewHolder contaViewHolder, int i) {
        contaViewHolder.desr.setText(contas.get(i).getDescr());
        contaViewHolder.saldo.setText(String.format("%.2f", contas.get(i).getSaldoIni()));

    }

    @Override
    public int getItemCount() {
        return contas.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    public class ContaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView desr;
        final TextView saldo;

        ContaViewHolder(View view) {
            super(view);

            desr = (TextView)view.findViewById(R.id.descrContaCelula);
            saldo = (TextView)view.findViewById(R.id.saldoContaCelula);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
