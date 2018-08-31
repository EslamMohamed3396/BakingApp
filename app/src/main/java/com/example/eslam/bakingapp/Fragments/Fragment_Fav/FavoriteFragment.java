package com.example.eslam.bakingapp.Fragments.Fragment_Fav;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eslam.bakingapp.Activies.Favorite;
import com.example.eslam.bakingapp.Adapters.Adapter_Favorite.Adapter_Home_Fav;
import com.example.eslam.bakingapp.DataBase.DataBaseBaking;
import com.example.eslam.bakingapp.Model.Recipe;
import com.example.eslam.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteFragment extends Fragment implements Adapter_Home_Fav.RecipeAdapterOnClick {

    private static final String INTENT_KEY_RECIPE = "recipe";
    private static final String INTENT_KEY_STEPS = "steps";
    private static final String INTENT_KEY_ID = "id";
    @BindView(R.id.rc_home_fav)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Adapter_Home_Fav mAdapter_home_fav;
    private DataBaseBaking mDataBaseBaking;
    private Toast mToast;
    private List<Recipe> mRecipeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.recycler_home_fav, container, false);

        ButterKnife.bind(this, rootview);

        mDataBaseBaking = DataBaseBaking.getINSTANCE(getContext().getApplicationContext());


        getFav();
        return rootview;
    }

    private void getRecipe() {

        mAdapter_home_fav = new Adapter_Home_Fav(this, new ArrayList<Recipe>());

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter_home_fav);

    }

    private void getFav() {
        getRecipe();
        LiveData<List<Recipe>> data = mDataBaseBaking.daoBaking().loadAllRecipe();
        data.observe(getActivity(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (!recipes.isEmpty()) {
                    mRecipeList = recipes;
                    mAdapter_home_fav.setRecipeList(mRecipeList);
                }
//                else {
//                    if (mToast != null) {
//                        mToast.cancel();
//                    }
//                    mAdapter_home_fav.clear();
//                    mToast = Toast.makeText(getContext(), getActivity().getResources().getString(R.string.empty), Toast.LENGTH_SHORT);
//                    mToast.show();
//
//                }
            }
        });

    }

    @Override
    public void onClick(int clickPosition) {
        Intent intent = new Intent(getContext(), Favorite.class);
        intent.putParcelableArrayListExtra(INTENT_KEY_RECIPE, (ArrayList<? extends Parcelable>) mRecipeList.get(clickPosition).getIngredients());
        intent.putParcelableArrayListExtra(INTENT_KEY_STEPS, (ArrayList<? extends Parcelable>) mRecipeList.get(clickPosition).getSteps());
        intent.putExtra(INTENT_KEY_ID, mRecipeList.get(clickPosition).getId());
        startActivity(intent);
    }
}
