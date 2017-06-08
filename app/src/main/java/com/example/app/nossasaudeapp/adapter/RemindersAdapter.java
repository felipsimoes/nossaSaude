package com.example.app.nossasaudeapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.activities.ConsultaViewActivity;
import com.example.app.nossasaudeapp.activities.ExameViewActivity;
import com.example.app.nossasaudeapp.activities.MedicamentoActivity;
import com.example.app.nossasaudeapp.activities.MedicamentoViewActivity;
import com.example.app.nossasaudeapp.data.Consulta;
import com.example.app.nossasaudeapp.data.Exame;
import com.example.app.nossasaudeapp.data.Medicamento;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.MyViewHolder> {

    RealmResults<Medicamento> medicamentos;
    RealmResults<Exame> exames;
    RealmResults<Consulta> consultas;
    List<RealmObject> list = new ArrayList<>();

    RealmChangeListener realmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView iconeReminder;
        public LinearLayout linearItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.reminder_name);
            iconeReminder = (ImageView) itemView.findViewById(R.id.imgViewIconeReminder);
            linearItem = (LinearLayout) itemView.findViewById(R.id.linearItemReminder);
        }
    }

    public RemindersAdapter(Realm realm) {
        this.medicamentos = realm.where(Medicamento.class).findAll();
        this.exames = realm.where(Exame.class).findAll();
        this.consultas = realm.where(Consulta.class).findAll();

        this.medicamentos.addChangeListener(realmChangeListener);
        this.exames.addChangeListener(realmChangeListener);
        this.consultas.addChangeListener(realmChangeListener);

        list.addAll(medicamentos);
        list.addAll(exames);
        list.addAll(consultas);
    }

    private <T extends RealmModel> int getResultSize(RealmResults<T> results) {
        return ((results == null) || (!results.isValid()) || (results.isValid() && !results.isLoaded()) ? 0 : results.size());
    }

    @Override
    public RemindersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_reminders, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RealmObject realmObject = list.get(position);

        if (realmObject instanceof Medicamento) {
            final Medicamento medicamento = (Medicamento) realmObject;
            holder.linearItem.setBackgroundResource(R.color.colorMedicamento);
            holder.name.setText(medicamento.getNome());
            holder.iconeReminder.setImageResource(R.mipmap.remedio);
            holder.linearItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MedicamentoViewActivity.class);
                    intent.putExtra("NOTIFICATION_ID", medicamento.getId());
                    v.getContext().startActivity(intent);
                }
            });
        } else if (realmObject instanceof Exame) {
            final Exame exame = (Exame) realmObject;
            holder.linearItem.setBackgroundResource(R.color.colorExame);
            holder.name.setText(exame.getNome());
            holder.iconeReminder.setImageResource(R.mipmap.exame);
            holder.linearItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ExameViewActivity.class);
                    intent.putExtra("NOTIFICATION_ID", exame.getId());
                    v.getContext().startActivity(intent);
                }
            });
        } else if (realmObject instanceof Consulta) {
            final Consulta consulta = (Consulta) realmObject;
            holder.linearItem.setBackgroundResource(R.color.colorConsulta);
            holder.name.setText(consulta.getNome());
            holder.iconeReminder.setImageResource(R.mipmap.consultas);
            holder.linearItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ConsultaViewActivity.class);
                    intent.putExtra("NOTIFICATION_ID", consulta.getId());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getResultSize(medicamentos) + getResultSize(exames) + getResultSize(consultas);
    }

    @Override
    public int getItemViewType(int position) {
        int retorno = 1;

        RealmObject object = list.get(position);
        if (object instanceof Medicamento) {
            retorno = 1;
        } else if (object instanceof Exame) {
            retorno = 2;
        } else if (object instanceof Consulta) {
            retorno = 3;
        }
        return retorno;
    }

}
