package com.example.app.nossasaudeapp;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.app.nossasaudeapp.data.Medicamento;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class MedicamentosAdapter extends RealmBaseAdapter<Medicamento> implements ListAdapter {

    private static class ViewHolder {
        TextView name;
    }
    public MedicamentosAdapter(@Nullable OrderedRealmCollection<Medicamento> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        convertView.setTag(viewHolder);
        final Medicamento medicamento = adapterData.get(position);
        viewHolder.name.setText(medicamento.getNome());
        return convertView;
    }
}
