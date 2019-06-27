package com.develop.loginov.mytarget.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.model.Target;

import java.util.Collections;
import java.util.List;


public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.ViewHolder> {
    private Context context;
    private final List<Target> targets;

    public TargetAdapter(final List<Target> targets) {
        this.targets = targets;
        Collections.sort(targets, (t1, t2) -> -Long.compare(t1.getTime(), t2.getTime()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.item_target, null, false);
        final ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                  ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(targets.get(position));
    }

    @Override
    public int getItemCount() {
        return targets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final String WILL_DONE = context.getResources().getString(R.string.will_done);
        private final String NOT_WILL_DONE = context.getResources().getString(R.string.not_will_done);
        private final int GREEN = context.getResources().getColor(R.color.green);
        private final int RED = context.getResources().getColor(R.color.red);

        private final TextView teztTarget;
        private final TextView textResult;
        private final TextView textDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teztTarget = itemView.findViewById(R.id.item_target__name);
            textResult = itemView.findViewById(R.id.item_target__result);
            textDate = itemView.findViewById(R.id.item_target__date);
        }

        private void bind(final Target target) {
            teztTarget.setText(target.getName());

            if (target.getProbability() > 0) {
                textResult.setText(WILL_DONE);
                textResult.setTextColor(GREEN);
            } else {
                textResult.setText(NOT_WILL_DONE);
                textResult.setTextColor(RED);
            }

            final CharSequence time = DateFormat.format("dd.MM.yyyy", target.getTime());
            textDate.setText(time);
        }
    }
}