package com.develop.loginov.mytarget.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private final OnItemClickListener onItemClickListener;

    public TargetAdapter(@NonNull final List<Target> targets,
                         @NonNull final OnItemClickListener onItemClickListener) {
        this.targets = targets;
        this.onItemClickListener = onItemClickListener;
        Collections.sort(targets, (t1, t2) -> -Long.compare(t1.getTime(), t2.getTime()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.item_target, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(targets.get(position));
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position,
                                                                                holder.itemView));
    }

    @Override
    public int getItemCount() {
        return targets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final String WILL_DONE = context.getResources().getString(R.string.will_done);
        private final String MAYBE_WILL_DONE = context.getResources().getString(R.string.maybe_will_done);
        private final String NOT_WILL_DONE = context.getResources().getString(R.string.not_will_done);
        private final int GREEN = context.getResources().getColor(R.color.green);
        private final int BLUE = context.getResources().getColor(R.color.blue);
        private final int RED = context.getResources().getColor(R.color.red);

        private final TextView textTarget;
        private final TextView textResult;
        private final TextView textDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTarget = itemView.findViewById(R.id.item_target__name);
            textResult = itemView.findViewById(R.id.item_target__result);
            textDate = itemView.findViewById(R.id.item_target__date);
        }

        private void bind(final Target target) {
            textTarget.setText(target.getName());

            switch (target.getWinner()){
                case 2:
                    textResult.setText(WILL_DONE);
                    textResult.setTextColor(BLUE);
                    break;
                case 4:
                    textResult.setText(MAYBE_WILL_DONE);
                    textResult.setTextColor(GREEN);
                    break;
                case 1:
                case 3:
                    textResult.setText(NOT_WILL_DONE);
                    textResult.setTextColor(RED);
                    break;
            }
            final CharSequence time = DateFormat.format("dd.MM.yyyy", target.getTime());
            textDate.setText(time);
        }
    }
}
