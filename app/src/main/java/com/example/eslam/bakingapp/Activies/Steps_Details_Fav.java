package com.example.eslam.bakingapp.Activies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.eslam.bakingapp.Fragments.Fragment_Fav.Video_Fragment_Fav;
import com.example.eslam.bakingapp.R;

public class Steps_Details_Fav extends AppCompatActivity {
    private static final String INTENT_KEY_STEPS_VIDEO = "steps_video";
    private static final String INTENT_KEY_STEPS_DESCRIPTION = "steps_desc";
    private Intent mIntent;
    private Fragment mFragment = null;
    private String mStepsVideo;
    private String mStepsDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps__details__fav);
        mIntent = getIntent();
        mFragment = new Video_Fragment_Fav();
        if (savedInstanceState == null) {
            setFragment(mFragment);
        }
        intentToFragment();
    }

    private void intentToFragment() {
        if (mIntent != null && !mIntent.equals("")) {
            mStepsVideo = mIntent.getStringExtra(INTENT_KEY_STEPS_VIDEO);
            mStepsDesc = mIntent.getStringExtra(INTENT_KEY_STEPS_DESCRIPTION);
        }
        Bundle videoBundle = new Bundle();
        videoBundle.putString(INTENT_KEY_STEPS_VIDEO, mStepsVideo);
        videoBundle.putString(INTENT_KEY_STEPS_DESCRIPTION, mStepsDesc);
        mFragment.setArguments(videoBundle);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setFragment(Fragment mFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_video_fav, mFragment);
        transaction.commit();
    }
}
