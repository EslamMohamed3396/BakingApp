package com.example.eslam.bakingapp.Activies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.eslam.bakingapp.Fragments.Fragment_Fav.Details_Recipe_Fragment;
import com.example.eslam.bakingapp.Model.Ingredients;
import com.example.eslam.bakingapp.Model.Step;
import com.example.eslam.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity {
    private static final String INTENT_KEY_RECIPE = "recipe";
    private static final String INTENT_KEY_STEPS = "steps";
    private static final String INTENT_KEY_ID = "id";
    private Intent intent;
    private List<Ingredients> mIngredientsList;
    private List<Step> mSteps;
    private int mId;
    private Fragment mFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        intent = getIntent();
        mFragment = new Details_Recipe_Fragment();
        if (savedInstanceState == null) {
            setFragment(mFragment);
        }

        intentToFragment();
    }

    private void intentToFragment() {
        if (intent != null && !intent.equals("")) {
            mIngredientsList = intent.getParcelableArrayListExtra(INTENT_KEY_RECIPE);
            mSteps = intent.getParcelableArrayListExtra(INTENT_KEY_STEPS);
            mId = intent.getIntExtra(INTENT_KEY_ID, 0);

        }
        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelableArrayList(INTENT_KEY_RECIPE, (ArrayList<? extends Parcelable>) mIngredientsList);
        recipeBundle.putParcelableArrayList(INTENT_KEY_STEPS, (ArrayList<? extends Parcelable>) mSteps);
        recipeBundle.putInt(INTENT_KEY_ID, mId);
        mFragment.setArguments(recipeBundle);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setFragment(Fragment mFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_fav_content, mFragment);
        transaction.commit();
    }
}
