package com.example.eslam.bakingapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eslam.bakingapp.Model.Step;
import com.example.eslam.bakingapp.Adapters.Steps_Adapters.ItemViewHolder;
import com.example.eslam.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Steps_Adapters extends RecyclerView.Adapter<ItemViewHolder> {
    private final StepsAdapterOnClick stepsAdapterOnClick;
    private List<Step> mStepsList;

    public Steps_Adapters(List<Step> mStepsList, StepsAdapterOnClick stepsAdapterOnClick) {
        this.mStepsList = mStepsList;
        this.stepsAdapterOnClick = stepsAdapterOnClick;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_items, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Step step = mStepsList.get(position);
        holder.tv_desc.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mStepsList == null) {
            return 0;
        }
        return mStepsList.size();
    }

    public void setSteps(List<Step> mStepList) {
        this.mStepsList = mStepList;
    }

    public interface StepsAdapterOnClick {
        void onClick(int clickPosition);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_short_desc)
        TextView tv_desc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            stepsAdapterOnClick.onClick(adapterPosition);
        }
    }
}
