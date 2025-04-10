package com.rmp.memoria.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.rmp.memoria.R;

import java.util.ArrayList;

/**
 * Created by Viktor-Ruff
 * Date: 24.01.2024
 * Time: 16:20
 */
public class RecordsAdapter extends ArrayAdapter<String> {


    ArrayList<String> recordsCollection;
    Context context;

    public RecordsAdapter(@NonNull Context context, ArrayList<String> recordsCollection) {
        super(context, R.layout.item_record, recordsCollection);
        this.context = context;
        this.recordsCollection = recordsCollection;
    }


    private class ViewHolder {
        TextView tvRecord;

        public ViewHolder(View view) {
            tvRecord = view.findViewById(R.id.tv_record_text);
        }
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_record, null, true);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvRecord.setText(recordsCollection.get(position));

        return convertView;

        
    }
}
