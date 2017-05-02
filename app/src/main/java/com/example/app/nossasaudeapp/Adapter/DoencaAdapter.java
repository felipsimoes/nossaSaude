package com.example.app.nossasaudeapp.Adapter;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Doenca;
import com.example.app.nossasaudeapp.data.Medico;

public class DoencaAdapter extends RealmBaseAdapter<Doenca> implements ListAdapter {

    private static class ViewHolder {
        TextView name;
    }

    public DoencaAdapter(@Nullable OrderedRealmCollection<Doenca> data) {
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
        final Doenca doenca = adapterData.get(position);
        viewHolder.name.setText(doenca.getNome());
        return convertView;
    }
}
