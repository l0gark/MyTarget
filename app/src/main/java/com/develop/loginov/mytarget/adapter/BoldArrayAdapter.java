package com.develop.loginov.mytarget.adapter;

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

import com.develop.loginov.mytarget.R;

public class BoldArrayAdapter extends ArrayAdapter<String> {
    private boolean[] boldObjects;

    public BoldArrayAdapter(@NonNull Context context, int resource, @NonNull String[] objects,
                            boolean[] boldObjects) {
        super(context, resource, objects);
        this.boldObjects = boldObjects;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.answer_item,
                                                                             null,
                                                                             false);
        view.setText(getItem(position));
        if (boldObjects[position]) {
            view.setTypeface(null, Typeface.BOLD);
        }
        return view;
    }

}
