package com.example.eslam.bakingapp.Fragments.Fragment_Fav;

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

import com.example.eslam.bakingapp.Activies.Steps_Details_Fav;
import com.example.eslam.bakingapp.Adapters.Adapter_Favorite.Ingredients_Adapter_Fav;
import com.example.eslam.bakingapp.Adapters.Adapter_Favorite.Steps_Adapters_Fav;
import com.example.eslam.bakingapp.AppExecutor.AppExecutor;
import com.example.eslam.bakingapp.DataBase.DataBaseBaking;
import com.example.eslam.bakingapp.Model.Ingredients;
import com.example.eslam.bakingapp.Model.Step;
import com.example.eslam.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Details_Recipe_Fragment extends Fragment implements Steps_Adapters_Fav.StepsAdapterOnClick {

    private static final String INTENT_KEY_RECIPE = "recipe";
    private static final String INTENT_KEY_STEPS = "steps";
    private static final String INTENT_KEY_STEPS_VIDEO = "steps_video";
    private static final String INTENT_KEY_STEPS_DESCRIPTION = "steps_desc";
    private static final String INTENT_KEY_ID = "id";
    @BindView(R.id.rc_ingredients_fav)
    RecyclerView mRecyclerViewIngredients;
    @BindView(R.id.rc_steps_fav)
    RecyclerView mRecyclerViewSteps;
    @BindView(R.id.fbtn_favorite_fav)
    FloatingActionButton mFloatingActionButton;
    private DataBaseBaking mDataBaseBaking;
    private LinearLayoutManager mLayoutManager;
    private Ingredients_Adapter_Fav mAdapter;
    private Steps_Adapters_Fav mStepsAdapters;
    private List<Ingredients> mIngredientsList;
    private List<Step> mStepList;
    private int mId;
    private Toast mToast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.steps_fargment_fav, container, false);

        ButterKnife.bind(this, rootview);

        mId = getArguments().getInt(INTENT_KEY_ID);

        mDataBaseBaking = DataBaseBaking.getINSTANCE(getContext().getApplicationContext());

        mFloatingActionButton.setImageResource(R.drawable.ic_favorite);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFav();

                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(getContext(), getResources().getString(R.string.delete_fav), Toast.LENGTH_SHORT);
                mToast.show();
                getActivity().finish();
            }
        });

        getIngredients();

        getSteps();

        return rootview;

    }

    private void getIngredients() {

        mAdapter = new Ingredients_Adapter_Fav(getActivity(), new ArrayList<Ingredients>());

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerViewIngredients.setLayoutManager(mLayoutManager);

        mRecyclerViewIngredients.setAdapter(mAdapter);

        mIngredientsList = getArguments().getParcelableArrayList(INTENT_KEY_RECIPE);

        mAdapter.setIngredientsList(mIngredientsList);
    }

    private void getSteps() {

        mStepsAdapters = new Steps_Adapters_Fav(new ArrayList<Step>(), this);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerViewSteps.setLayoutManager(mLayoutManager);

        mRecyclerViewSteps.setAdapter(mStepsAdapters);

        mStepList = getArguments().getParcelableArrayList(INTENT_KEY_STEPS);

        mStepsAdapters.setSteps(mStepList);
    }

    private void deleteFav() {
        mFloatingActionButton.setImageResource(R.drawable.ic_favorite_border);

        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDataBaseBaking.daoBaking().deleteRecipeById(mId);
            }
        });
    }

    @Override
    public void onClick(int clickPosition) {
        Intent intent = new Intent(getContext(), Steps_Details_Fav.class);
        intent.putExtra(INTENT_KEY_STEPS_VIDEO, mStepList.get(clickPosition).getVideoURL());
        intent.putExtra(INTENT_KEY_STEPS_DESCRIPTION, mStepList.get(clickPosition).getDescription());
        intent.putExtra(INTENT_KEY_ID, mStepList.get(clickPosition).getId());
        startActivity(intent);
    }
}
