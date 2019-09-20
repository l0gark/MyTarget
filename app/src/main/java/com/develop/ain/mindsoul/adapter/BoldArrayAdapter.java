package com.develop.ain.mindsoul.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.develop.ain.mindsoul.R;

public class BoldArrayAdapter extends ArrayAdapter<String> {
    private int[] boldObjects;

    public BoldArrayAdapter(@NonNull Context context, int resource, @NonNull String[] objects,
                            int[] boldObjects) {
        super(context, resource, objects);
        this.boldObjects = boldObjects;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_text,
                                                                             null,
                                                                             false);
        view.setText(getItem(position));
        if (boldObjects[position] > 0) {
            view.setTypeface(null, Typeface.BOLD);
        }

        switch (boldObjects[position]) {
            case 1:
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.v_z, 0);
                break;
            case 2:
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vv_z, 0);
                break;
            case 3:
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vvv_z, 0);
                break;
            case 4:
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vvvv_z, 0);
                break;
            case 5:
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.vvvvv_z, 0);
                break;
            case 0:
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.v_s, 0);
                break;
        }
        return view;
    }

}
