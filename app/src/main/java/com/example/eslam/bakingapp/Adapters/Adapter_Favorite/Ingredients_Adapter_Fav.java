package com.example.eslam.bakingapp.Adapters.Adapter_Favorite;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eslam.bakingapp.Model.Ingredients;

import com.example.eslam.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Ingredients_Adapter_Fav extends RecyclerView.Adapter<Ingredients_Adapter_Fav.ItemViewHolder> {

    private List<Ingredients> ingredientsList;
    private Context context;

    public Ingredients_Adapter_Fav(Context context, List<Ingredients> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_fav_ingredients, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.tv_quantity.setText(String.valueOf(ingredientsList.get(position).getQuantity()));
        holder.tv_measure.setText(ingredientsList.get(position).getMeasure());
        holder.tv_ingredients.setText(ingredientsList.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        if (ingredientsList == null) {
            return 0;
        }
        return ingredientsList.size();
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ingredients_fav)
        TextView tv_ingredients;
        @BindView(R.id.tv_quantity_fav)
        TextView tv_quantity;
        @BindView(R.id.tv_measure_fav)
        TextView tv_measure;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
