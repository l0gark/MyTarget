package com.develop.loginov.mytarget.dialog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.develop.loginov.mytarget.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class HelpAnswerDialog extends BottomSheetDialogFragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_help_answer, container, false);
    }
}
