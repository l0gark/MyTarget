package com.develop.ain.mindsoul.dialog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.develop.ain.mindsoul.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

public class HelpAnswerDialog extends BottomSheetDialogFragment {
    private static final String TYPE_ARG = "TYPE";
    private String hint;
    private boolean clearText;
    private static final Map<Integer, Integer> prevHints;


    static {
        prevHints = new HashMap<>();
        prevHints.put(R.array.hints1, -1);
        prevHints.put(R.array.hints2, -1);
        prevHints.put(R.array.hints3, -1);
        prevHints.put(R.array.hints4, -1);
    }


    public static HelpAnswerDialog newInstance(final int resourceId) {
        Bundle args = new Bundle();
        args.putInt(TYPE_ARG, resourceId);
        final HelpAnswerDialog fragment = new HelpAnswerDialog();
        fragment.setClearText(false);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        int resourceId = getArguments().getInt(TYPE_ARG);
        if (isClearText()) {
            hint = getResources().getString(resourceId);
        } else {
            if (prevHints.get(resourceId) != null) {
                final String[] hints = getResources().getStringArray(resourceId);
                int index = (prevHints.get(resourceId) + 1) % hints.length;
                prevHints.put(resourceId, index);
                hint = hints[index];
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_help_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView textView = (TextView) view;
        if (hint != null) {
            if (isClearText()) {
                textView.setText(hint);
            } else {
                textView.append(hint);
            }
        }
    }

    public void setClearText(final boolean clearText) {
        this.clearText = clearText;
    }

    private boolean isClearText() {
        return clearText;
    }
}
