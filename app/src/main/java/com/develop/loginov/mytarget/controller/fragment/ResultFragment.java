package com.develop.loginov.mytarget.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.adapter.BoldArrayAdapter;

public class ResultFragment extends Fragment {
    private ListView listView;

    public ResultFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.fragment_result__list);
    }


    public void setResults(final String[] objects, final boolean[] boldObjects) {
        if (getContext() == null) {
            return;
        }

        final BoldArrayAdapter adapter = new BoldArrayAdapter(getContext(),
                                                              R.layout.item_answer,
                                                              objects,
                                                              boldObjects);
        listView.setAdapter(adapter);
    }
}
