package com.example.eslam.bakingapp.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eslam.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Video_Fragment extends Fragment {

    private static final String INTENT_KEY_STEPS_VIDEO = "steps_video";
    private static final String INTENT_KEY_STEPS_DESCRIPTION = "steps_desc";
    private static final String SAVED_PLAYER_POSITION = "player";
    private static final String SAVED_PLAYER_READY = "ready";
    private long pausePosition;
    private boolean mGetWehnReady;
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.exo_video)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tv_desc)
    TextView mDescription;
    private Unbinder unbinder;
    private SimpleExoPlayer mExoPlayer;
    private String mVideoUrl;
    private String mDesc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.video_fragment, container, false);

        unbinder = ButterKnife.bind(this, rootview);
        mVideoUrl = getArguments().getString(INTENT_KEY_STEPS_VIDEO);
        mDesc = getArguments().getString(INTENT_KEY_STEPS_DESCRIPTION);
        mDescription.setText(mDesc);
        if (mVideoUrl != null && !mVideoUrl.equals("")) {
            mPlayerView.requestFocus();
            initializePlayer(Uri.parse(mVideoUrl));
        } else {
            mPlayerView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No Video", Toast.LENGTH_SHORT).show();
        }
        return rootview;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {

            pausePosition = mExoPlayer.getCurrentPosition();
            mGetWehnReady = mExoPlayer.getPlayWhenReady();
            outState.putLong(SAVED_PLAYER_POSITION, mExoPlayer.getCurrentPosition());
            outState.putBoolean(SAVED_PLAYER_READY, mExoPlayer.getPlayWhenReady());
        } catch (Exception e) {
            Log.e(TAG, "exoplayer error" + e.toString());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            pausePosition = savedInstanceState.getLong(SAVED_PLAYER_POSITION);
            mGetWehnReady = savedInstanceState.getBoolean(SAVED_PLAYER_READY);
            if (pausePosition != -1)
                mExoPlayer.seekTo(pausePosition);
            else
                mExoPlayer.seekTo(0);

            mExoPlayer.setPlayWhenReady(mGetWehnReady);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initializePlayer(Uri mediaUri) {

        if (mExoPlayer == null) {
            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                String userAgent = Util.getUserAgent(getContext(), "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                mPlayerView.setPlayer(mExoPlayer);
                if (pausePosition != -1)
                    mExoPlayer.seekTo(pausePosition);
                else
                    mExoPlayer.seekTo(0);

                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(mGetWehnReady);

            } catch (Exception e) {
                Log.e(TAG, "exoplayer error" + e.toString());
            }
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null)
            pausePosition = mExoPlayer.getCurrentPosition();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }
}
