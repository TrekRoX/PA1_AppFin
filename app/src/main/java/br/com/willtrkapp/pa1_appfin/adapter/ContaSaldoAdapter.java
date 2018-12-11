package br.com.willtrkapp.pa1_appfin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.willtrkapp.pa1_appfin.R;
import br.com.willtrkapp.pa1_appfin.view.ContaSaldo;

public class ContaSaldoAdapter extends RecyclerView.Adapter<ContaSaldoAdapter.ContaSaldoViewHolder> {

    private static List<ContaSaldo> contas;
    private Context context;

    private static ContaSaldoAdapter.ItemClickListener clickListener;

    public ContaSaldoAdapter(List<ContaSaldo> contas, Context context) {
        this.contas = contas;
        this.context = context;
    }


    @Override
    public ContaSaldoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.contato_celula, viewGroup, false);
        return new ContaSaldoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContaSaldoViewHolder contaSaldoViewHolder, int i) {
        contaSaldoViewHolder.desr.setText(contas.get(i).getDescr());
        contaSaldoViewHolder.saldo.setText(String.format("%.2f", contas.get(i).getSaldo()));

    }
    @Override
    public int getItemCount() {
        return contas.size();
    }

    public void setClickListener(ContaSaldoAdapter.ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    public class ContaSaldoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView desr;
        final TextView saldo;

        ContaSaldoViewHolder(View view) {
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
