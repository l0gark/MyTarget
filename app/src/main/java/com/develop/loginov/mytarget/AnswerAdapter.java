package com.develop.loginov.mytarget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private String[] answers;

    public AnswerAdapter(String[] answers) {
        this.answers = answers;
    }

    public AnswerAdapter() {
        answers = new String[5];
        for (int i = 0; i < 5; i++) {
            answers[i] = "";
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item,
                                                                     null,
                                                                     false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position >= 5) {
            return;
        }
        holder.textView.setText(answers[position]);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setAnswers(String[] answers) {
        if (answers.length == 5) {
            this.answers = answers;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.simple_text_item__text);
        }
    }
}
