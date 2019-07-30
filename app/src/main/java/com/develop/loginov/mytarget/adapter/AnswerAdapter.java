package com.develop.loginov.mytarget.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.develop.loginov.mytarget.R;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    public static int text = 0;

    private static final int COUNT_ANSWERS = 5;
    private String[] answers;

    public AnswerAdapter() {
        answers = new String[COUNT_ANSWERS];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer,
                                                                     null,
                                                                     false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position >= COUNT_ANSWERS) {
            return;
        }
        if (answers[position] != null) {
            holder.editText.setText(answers[position]);
        }

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                holder.changeText(position);
            }

            @Override
            public void afterTextChanged(Editable s) {
                holder.changeText(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return COUNT_ANSWERS;
    }

    public void setAnswers(@NonNull final String[] answers) {
        if (answers.length == COUNT_ANSWERS) {
            this.answers = answers;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final EditText editText;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.item_answer__edit);
        }

        void changeText(final int position) {
            final String text = editText.getText().toString();
            if (!TextUtils.isEmpty(text)) {
                answers[position] = text;
            }
        }
    }
}
