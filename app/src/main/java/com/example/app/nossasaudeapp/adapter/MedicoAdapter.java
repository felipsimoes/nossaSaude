package com.example.app.nossasaudeapp.adapter;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Medico;

public class MedicoAdapter extends RealmBaseAdapter<Medico> implements ListAdapter {

    private static class ViewHolder {
        TextView name;
        TextView name2;
    }

    public MedicoAdapter(@Nullable OrderedRealmCollection<Medico> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_medico, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.name = (TextView) convertView.findViewById(R.id.name);
        viewHolder.name2 = (TextView) convertView.findViewById(R.id.name2);
        convertView.setTag(viewHolder);
        final Medico medico = adapterData.get(position);
        viewHolder.name.setText(medico.getNome());
        viewHolder.name2.setText(medico.getEspecializacao());
        return convertView;
    }
}
