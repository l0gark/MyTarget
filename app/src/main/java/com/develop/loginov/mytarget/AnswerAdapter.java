package com.develop.loginov.mytarget;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private static final int COUNT_ANSWERS = 5;
    private String[] answers;
    private Context context;

    public AnswerAdapter(String[] answers) {
        this.answers = answers;
    }

    public AnswerAdapter() {
        answers = new String[COUNT_ANSWERS];
        for (int i = 0; i < COUNT_ANSWERS; i++) {
            answers[i] = "";
        }
    }

    public String[] getAnswers() {
        return answers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.answer_item, null, false);
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
        holder.textView.setText(answers[position]);


        holder.editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                holder.changeText(position);
                return true;
            }
            return false;
        });
        holder.button.setOnClickListener(v -> holder.changeText(position));
        holder.textView.setOnClickListener(v -> holder.viewSwitcher.showNext());

        final Animation slideInLeft = AnimationUtils.loadAnimation(context,
                                                                   android.R.anim.slide_in_left);
        final Animation slideOutRight = AnimationUtils.loadAnimation(context,
                                                                     android.R.anim.slide_out_right);
        holder.viewSwitcher.setInAnimation(slideInLeft);
        holder.viewSwitcher.setOutAnimation(slideOutRight);
    }

    @Override
    public int getItemCount() {
        return COUNT_ANSWERS;
    }

    public void setAnswers(String[] answers) {
        if (answers.length == COUNT_ANSWERS) {
            this.answers = answers;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewSwitcher viewSwitcher;
        TextView textView;
        EditText editText;
        Button button;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.simple_text_item__text);
            viewSwitcher = itemView.findViewById(R.id.answer_item__view_switcher);
            editText = itemView.findViewById(R.id.answer_item__edit);
            button = itemView.findViewById(R.id.answer_item__button_ok);
        }


        void changeText(int position) {
            String text = editText.getText().toString();
            if (TextUtils.isEmpty(text)) {
                return;
            }
            answers[position] = text;
            textView.setText(text);
            viewSwitcher.showNext();
        }
    }
}
