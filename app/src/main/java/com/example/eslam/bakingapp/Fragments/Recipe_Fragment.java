package com.example.eslam.bakingapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eslam.bakingapp.Activies.Details_Recipe;
import com.example.eslam.bakingapp.Adapters.Recipe_adapters;
import com.example.eslam.bakingapp.Adapters.Recipe_adapters.RecipeAdapterOnClick;
import com.example.eslam.bakingapp.Model.Recipe;
import com.example.eslam.bakingapp.Network.APIinterface.RecipeInterface;
import com.example.eslam.bakingapp.Network.ApiClient.ApiClient;
import com.example.eslam.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recipe_Fragment extends Fragment implements RecipeAdapterOnClick {

    private static final String INTENT_KEY_RECIPE = "recipe";
    private static final String INTENT_KEY_STEPS = "steps";
    private static final String INTENT_KEY_NAME = "name";
    private static final String INTENT_KEY_ID = "id";
    @BindView(R.id.rc_home_recipe)
    RecyclerView mRecyclerView;
    private Recipe_adapters mRecipe_adapters;
    private Unbinder unbinder;
    private RecipeInterface mRecipeInterface;
    private LinearLayoutManager mLayoutManager;
    private List<Recipe> mRecipeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.recipe_home_recycler, container, false);
        unbinder = ButterKnife.bind(this, rootview);

        getRecipe();

        return rootview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getRecipe() {

        mRecipe_adapters = new Recipe_adapters(this, new ArrayList<Recipe>());

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mRecipe_adapters);

        getDataFromApi();
    }

    private void getDataFromApi() {
        mRecipeInterface = ApiClient.getApiClient().create(RecipeInterface.class);
        Call<List<Recipe>> list = mRecipeInterface.recipelist();
        list.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                mRecipeList = response.body();
                mRecipe_adapters.setRecipeList(mRecipeList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v("url", "" + t.getMessage());
            }
        });

    }


    @Override
    public void onClick(int clickPosition) {
        Intent intent = new Intent(getContext(), Details_Recipe.class);
        intent.putParcelableArrayListExtra(INTENT_KEY_RECIPE, (ArrayList<? extends Parcelable>) mRecipeList.get(clickPosition).getIngredients());
        intent.putParcelableArrayListExtra(INTENT_KEY_STEPS, (ArrayList<? extends Parcelable>) mRecipeList.get(clickPosition).getSteps());
        intent.putExtra(INTENT_KEY_NAME, mRecipeList.get(clickPosition).getName());
        intent.putExtra(INTENT_KEY_ID, mRecipeList.get(clickPosition).getId());
        startActivity(intent);

    }
}
