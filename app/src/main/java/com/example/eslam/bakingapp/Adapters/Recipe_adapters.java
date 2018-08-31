package com.example.eslam.bakingapp.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.eslam.bakingapp.Adapters.Recipe_adapters.ItemViewHolder;

import com.example.eslam.bakingapp.Model.Recipe;
import com.example.eslam.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Recipe_adapters extends RecyclerView.Adapter<ItemViewHolder> {
    private final RecipeAdapterOnClick recipeAdapterOnClick;
    private List<Recipe> recipeList;

    public Recipe_adapters(RecipeAdapterOnClick recipeAdapterOnClick, List<Recipe> recipeList) {
        this.recipeAdapterOnClick = recipeAdapterOnClick;
        this.recipeList = recipeList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_content_home, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.tv_name.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if (recipeList == null) {
            return 0;
        }
        return recipeList.size();
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public interface RecipeAdapterOnClick {
        void onClick(int clickPosition);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.name)
        TextView tv_name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            recipeAdapterOnClick.onClick(adapterPosition);
        }
    }
}
