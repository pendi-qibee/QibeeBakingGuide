package id.qibee.edu.qibeebakingguide.view;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.qibee.edu.qibeebakingguide.R;
import id.qibee.edu.qibeebakingguide.model.StepModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepFragment2 extends Fragment {

    private static final String BUNDLE_STEP = "bundle_step";
    private static final String CURRENT_POSITION = "current_position";
    private static final String CURRENT_WINDOW = "current_window";

    @BindView(R.id.step_video_view_2)
    SimpleExoPlayerView stepPlayerView;

    @BindView(R.id.text_step_instructions_2)
    TextView textStepInstructions;

    @BindView(R.id.image_step_thumbnail_2)
    ImageView imageStepThumbnail;

    private SimpleExoPlayer stepExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;

    private StepModel step;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;

    private long position;

    int currentWindow;

    boolean playWhenReady;

    public RecipeStepFragment2() {
        // Required empty public constructor
    }

    public static RecipeStepFragment2 newInstance(StepModel step) {
        RecipeStepFragment2 recipeStepFragment = new RecipeStepFragment2();
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_STEP, step);
        recipeStepFragment.setArguments(args);
        return recipeStepFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            step = getArguments().getParcelable(BUNDLE_STEP);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CURRENT_POSITION, position);
        outState.putInt(CURRENT_WINDOW, currentWindow);
        Log.d("position onSaveInstance", ""+position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe_step_2, container, false);
        ButterKnife.bind(this, view);

        //sample
        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getActivity(), "BakingGuide"), (TransferListener<? super DataSource>) bandwidthMeter);
        window = new Timeline.Window();
//sample-end

        if(savedInstanceState != null) {
            position = savedInstanceState.getLong(CURRENT_POSITION);
            Log.d("position onCreateView", ""+position);
        }

        if (!step.getVideoURL().isEmpty() && step.getVideoURL() != null) {
            stepPlayerView.setVisibility(View.VISIBLE);
            initializeMediaSession();
            initializePlayer();

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                    && !getResources().getBoolean(R.bool.isTablet)) {
                hideSystemUI();

                stepPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                stepPlayerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                textStepInstructions.setVisibility(View.INVISIBLE);
            } else {
                textStepInstructions.setVisibility(View.VISIBLE);

            }
        } else {
            stepPlayerView.setVisibility(View.GONE);
        }

        if (!step.getThumbnailURL().isEmpty()) {
            Glide.with(getContext())
                    .load(step.getThumbnailURL())
                    .into(imageStepThumbnail);
        }

        textStepInstructions.setText(step.getDescription());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(CURRENT_POSITION);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
        }
    }

    private void hideSystemUI() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void initializeMediaSession() {
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(getContext(),"media");
        mediaSessionCompat.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                        | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        );

        mediaSessionCompat.setMediaButtonReceiver(null);

        PlaybackStateCompat.Builder builder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY
                                | PlaybackStateCompat.ACTION_PAUSE
                                | PlaybackStateCompat.ACTION_PLAY_PAUSE
                                | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                );

        mediaSessionCompat.setPlaybackState(builder.build());
        mediaSessionCompat.setCallback(new MediaSessionCompat.Callback() {



            @Override
            public void onPlay() {
                stepExoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
//                super.onPause();
                stepExoPlayer.setPlayWhenReady(false);
            }


            @Override
            public void onSkipToPrevious() {
//                super.onSkipToPrevious();
                stepExoPlayer.seekTo(0);
            }


        });

        mediaSessionCompat.setActive(true);
    }

    private void initializePlayer() {
        if (stepExoPlayer == null) {
            stepPlayerView.requestFocus();
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            trackSelector = new DefaultTrackSelector(factory);
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            stepExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);



            stepPlayerView.setPlayer(stepExoPlayer);
            stepExoPlayer.setPlayWhenReady(shouldAutoPlay);
            stepExoPlayer.seekTo(currentWindow, position);




            //Todo Media Source
            MediaSource mediaSource = new ExtractorMediaSource(
                    Uri.parse(step.getVideoURL()),
                    mediaDataSourceFactory,
                    extractorsFactory,
                    null,
                    null
            );

            stepExoPlayer.prepare(mediaSource);
        }
    }

    private void releasePlayer() {
        if (stepExoPlayer != null) {
            position = stepExoPlayer.getCurrentPosition();
            currentWindow = stepExoPlayer.getCurrentWindowIndex();
            shouldAutoPlay = stepExoPlayer.getPlayWhenReady();

            stepExoPlayer.stop();
            stepExoPlayer.release();
            stepExoPlayer = null;
            trackSelector = null;

            Log.d("position releasePlayer", ""+position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || stepExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}
