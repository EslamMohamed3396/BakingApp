package com.example.eslam.bakingapp.Activies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.eslam.bakingapp.Fragments.Step_Fragment;
import com.example.eslam.bakingapp.Fragments.Video_Fragment;
import com.example.eslam.bakingapp.HelperMethod.SharedPre;
import com.example.eslam.bakingapp.Model.Ingredients;
import com.example.eslam.bakingapp.Model.Step;
import com.example.eslam.bakingapp.R;

import java.util.ArrayList;
import java.util.List;


public class Details_Recipe extends AppCompatActivity implements Step_Fragment.OnClickCallback {

    private static final String INTENT_KEY_RECIPE = "recipe";
    private static final String INTENT_KEY_STEPS = "steps";
    private static final String INTENT_KEY_NAME = "name";
    private static final String INTENT_KEY_ID = "id";
    private static final String IS_TABLET = "tablet";
    private static final String INTENT_KEY_STEPS_VIDEO = "steps_video";
    private static final String INTENT_KEY_STEPS_DESCRIPTION = "steps_desc";

    private Intent intent;
    private List<Ingredients> mIngredientsList;
    private List<Step> mSteps;
    private int mId;
    private String mName;
    private Fragment mFragment = null;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__recipe);
        intent = getIntent();

        mFragment = new Step_Fragment();
        if (findViewById(R.id.linear_tablet) != null) {
            isTablet = true;
        } else {
            isTablet = false;
        }
        if (savedInstanceState == null) {
            setFragment(mFragment);
        }
        intentToFragment();
        setPref();
    }

    private void removeFragment() {
        getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
    }

    private void setPref() {
        List<Ingredients> list = SharedPre.getList(this);
        if (list != null) {
            SharedPre.ClearArrayList(this);
            SharedPre.saveArrayList(mIngredientsList, this);
        } else {
            SharedPre.saveArrayList(mIngredientsList, this);

        }
    }

    private void intentToFragment() {
        if (intent != null && !intent.equals("")) {
            mIngredientsList = intent.getParcelableArrayListExtra(INTENT_KEY_RECIPE);
            mSteps = intent.getParcelableArrayListExtra(INTENT_KEY_STEPS);
            mId = intent.getIntExtra(INTENT_KEY_ID, 0);
            mName = intent.getStringExtra(INTENT_KEY_NAME);
        }
        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelableArrayList(INTENT_KEY_RECIPE, (ArrayList<? extends Parcelable>) mIngredientsList);
        recipeBundle.putParcelableArrayList(INTENT_KEY_STEPS, (ArrayList<? extends Parcelable>) mSteps);
        recipeBundle.putString(INTENT_KEY_NAME, mName);
        recipeBundle.putInt(INTENT_KEY_ID, mId);
        recipeBundle.putBoolean(IS_TABLET, isTablet);
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
        transaction.add(R.id.content_step, mFragment);
        transaction.commit();
    }

    private void setFragmentVideo(Fragment mFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_video_tablet, mFragment);
        transaction.commit();
    }

    @Override
    public void onClick(String index, String desc) {
        if (isTablet) {
            mFragment = new Video_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString(INTENT_KEY_STEPS_VIDEO, index);
            bundle.putString(INTENT_KEY_STEPS_DESCRIPTION, desc);
            mFragment.setArguments(bundle);
            removeFragment();
            setFragmentVideo(mFragment);
        }
    }
}
