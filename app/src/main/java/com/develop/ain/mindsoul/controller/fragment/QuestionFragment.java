package com.develop.ain.mindsoul.controller.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.develop.ain.mindsoul.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QuestionFragment extends Fragment {
    private FloatingActionButton fab;
    private String question;
    private boolean enabled;

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        question = "Hello, i'm empty!";
        if (context != null && attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                                                                   R.styleable.QuestionFragment);
            if (typedArray.hasValue(R.styleable.QuestionFragment_name)) {
                question = typedArray.getString(R.styleable.QuestionFragment_name);
            }
            typedArray.recycle();
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView textQuestion = view.findViewById(R.id.fragment_question__name);
        fab = view.findViewById(R.id.fragment_question__fab);

        textQuestion.setText(question);
    }

    public void setOnClickListener(@NonNull final View.OnClickListener onClickListener) {
        if (fab != null) {
            fab.setOnClickListener(onClickListener);
        }
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setDone(boolean done){
        if(getContext() == null){
            return;
        }
        if(done){
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green)));
            fab.setImageResource(R.drawable.ic_ok);
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.gray)));
            fab.setImageResource(android.R.drawable.ic_input_add);
        }
    }

}
