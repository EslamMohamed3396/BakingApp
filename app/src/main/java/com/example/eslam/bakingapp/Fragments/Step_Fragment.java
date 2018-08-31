package com.example.eslam.bakingapp.Fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eslam.bakingapp.Activies.Steps_Details;
import com.example.eslam.bakingapp.Adapters.Ingredients_Adapter;
import com.example.eslam.bakingapp.Adapters.Steps_Adapters;
import com.example.eslam.bakingapp.AppExecutor.AppExecutor;
import com.example.eslam.bakingapp.DataBase.DataBaseBaking;
import com.example.eslam.bakingapp.Model.Ingredients;
import com.example.eslam.bakingapp.Model.Recipe;
import com.example.eslam.bakingapp.Model.Step;
import com.example.eslam.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Step_Fragment extends Fragment implements Steps_Adapters.StepsAdapterOnClick {

    private static final String INTENT_KEY_RECIPE = "recipe";
    private static final String INTENT_KEY_STEPS = "steps";
    private static final String INTENT_KEY_STEPS_VIDEO = "steps_video";
    private static final String INTENT_KEY_STEPS_DESCRIPTION = "steps_desc";
    private static final String INTENT_KEY_NAME = "name";
    private static final String INTENT_KEY_ID = "id";
    private static final String IS_TABLET = "tablet";
    @BindView(R.id.rc_ingredients)
    RecyclerView mRecyclerViewIngredients;
    @BindView(R.id.rc_steps)
    RecyclerView mRecyclerViewSteps;
    @BindView(R.id.fbtn_favorite)
    FloatingActionButton mFloatingActionButton;
    private Unbinder unbinder;
    private DataBaseBaking mDataBaseBaking;
    private LinearLayoutManager mLayoutManager;
    private Ingredients_Adapter mAdapter;
    private Steps_Adapters mStepsAdapters;
    private List<Ingredients> mIngredientsList;
    private List<Step> mStepList;
    private int mId;
    private String mName;
    private boolean isFavorite;
    private Toast mToast;
    private boolean isTablet;

    private OnClickCallback callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.step_fragment, container, false);

        unbinder = ButterKnife.bind(this, rootview);


        mId = getArguments().getInt(INTENT_KEY_ID);

        mName = getArguments().getString(INTENT_KEY_NAME);

        isTablet = getArguments().getBoolean(IS_TABLET);

        mDataBaseBaking = DataBaseBaking.getINSTANCE(getContext().getApplicationContext());

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFav();
            }
        });

        getIngredients();
        getSteps();
        isFav();

        return rootview;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnClickCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepsClickListener");
        }
    }

    private void isFav() {
        LiveData<List<Recipe>> loadAllRecipeByID = mDataBaseBaking.daoBaking().loadAllRecipeByID(mId);
        loadAllRecipeByID.observe(getActivity(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (recipes.isEmpty()) {
                    isFavorite = true;
                    mFloatingActionButton.setImageResource(R.drawable.ic_favorite_border);

                } else {
                    isFavorite = false;
                    mFloatingActionButton.setImageResource(R.drawable.ic_favorite);
                }
            }
        });

    }

    private void addFav() {
        if (isFavorite) {
            final Recipe recipe = new Recipe(mId, mName, mIngredientsList, mStepList);
            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDataBaseBaking.daoBaking().insertRecipe(recipe);
                }
            });
            mFloatingActionButton.setImageResource(R.drawable.ic_favorite);
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getContext(), getResources().getString(R.string.add_fav), Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getContext(), getResources().getString(R.string.added_before), Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    private void getIngredients() {

        mAdapter = new Ingredients_Adapter(getActivity(), new ArrayList<Ingredients>());

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerViewIngredients.setLayoutManager(mLayoutManager);

        mRecyclerViewIngredients.setAdapter(mAdapter);

        mIngredientsList = getArguments().getParcelableArrayList(INTENT_KEY_RECIPE);

        mAdapter.setIngredientsList(mIngredientsList);
    }

    private void getSteps() {

        mStepsAdapters = new Steps_Adapters(mStepList, this);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerViewSteps.setLayoutManager(mLayoutManager);

        mRecyclerViewSteps.setAdapter(mStepsAdapters);

        mStepList = getArguments().getParcelableArrayList(INTENT_KEY_STEPS);

        mStepsAdapters.setSteps(mStepList);
    }

    @Override
    public void onClick(int clickPosition) {

        if (isTablet) {
            callback.onClick(mStepList.get(clickPosition).getVideoURL(), mStepList.get(clickPosition).getDescription());
        } else {
            Intent intent = new Intent(getContext(), Steps_Details.class);
            intent.putExtra(INTENT_KEY_STEPS_VIDEO, mStepList.get(clickPosition).getVideoURL());
            intent.putExtra(INTENT_KEY_STEPS_DESCRIPTION, mStepList.get(clickPosition).getDescription());
            intent.putExtra(INTENT_KEY_ID, mStepList.get(clickPosition).getId());
            startActivity(intent);
        }
    }


    public interface OnClickCallback {
        void onClick(String index, String desc);
    }
}
