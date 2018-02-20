package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.common.Common;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.HashMap;
import java.util.Map;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

public class VideoPlayActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final String SCREEN_LABEL = "Video Play Screen";
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;
    private YouTubePlayer player;
    private String videoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player_activity);
        if (null != getIntent() && null != getIntent().getExtras()) {
            videoString = getIntent().getExtras().getString(AppConstants.YOUTUBE_VIDEO_CODE);
        }
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(AppConstants.YOUTUBE_DEVELOPER_KEY, this);
        playerStateChangeListener = new MyPlayerStateChangeListener();
        playbackEventListener = new MyPlaybackEventListener();
        ((SheroesApplication) getApplication()).trackScreenView(getString(R.string.ID_VEDIO_PLAYER_SCREEN));

        HashMap<String, Object> properties = new EventProperty.Builder().url(videoString).build();
        AnalyticsManager.trackScreenView(SCREEN_LABEL, properties);
        AnalyticsManager.trackScreenView(SCREEN_LABEL);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.player = player;
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        if (!wasRestored) {
            if (StringUtil.isNotNullOrEmptyString(videoString)) {
                if (videoString.contains(AppConstants.YOUTUBE_VIDEO_CODE)) {
                    String[] youTubeUrl = videoString.split(AppConstants.BACK_SLASH);
                    if (youTubeUrl.length > 1) {
                        if (StringUtil.isNotNullOrEmptyString(youTubeUrl[youTubeUrl.length - 1])) {
                                player.loadVideo(youTubeUrl[youTubeUrl.length - 1]);
                                //  player.cueVideo(youTubeUrl[1]); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
                        }
                    } else {
                        showMessage(getString(R.string.ID_YOU_TUBE_VIDEO_NOT_PLAYING));
                        finish();
                    }
                }else if (videoString.contains(AppConstants.MOBILE_YOUTUBE_VIDEO_CODE)) {
                        String[] youTubeUrl = videoString.split(AppConstants.BACK_SLASH);
                        if (youTubeUrl.length > 1) {
                            String youtubeIdSet=youTubeUrl[youTubeUrl.length - 1];
                            if (StringUtil.isNotNullOrEmptyString(youtubeIdSet)) {
                                String[] youTubeId = youtubeIdSet.split(AppConstants.EQUAL_SIGN);
                                if(StringUtil.isNotNullOrEmptyString(youTubeId[youTubeId.length-1]))
                                {
                                    player.loadVideo(youTubeId[youTubeId.length-1]);
                                }
                            }
                        } else {
                            showMessage(getString(R.string.ID_YOU_TUBE_VIDEO_NOT_PLAYING));
                            finish();
                        }
                    }
                else
                {
                    String[] youTubeUrl = videoString.split(AppConstants.BACK_SLASH);
                    if (youTubeUrl.length > 1) {
                        String youtubeIdSet=youTubeUrl[youTubeUrl.length - 1];
                        if (StringUtil.isNotNullOrEmptyString(youtubeIdSet)) {
                            String[] youTubeId = youtubeIdSet.split(AppConstants.EQUAL_SIGN);
                            if(youTubeId!=null && youTubeId.length > 0 && StringUtil.isNotNullOrEmptyString(youTubeId[1]))
                            {
                                if(youTubeId[1].contains(AppConstants.AND_SIGN)) {
                                    String[] id = youTubeId[1].split(AppConstants.AND_SIGN);
                                    if(StringUtil.isNotNullOrEmptyString(id[0])) {
                                        player.loadVideo(id[0]);
                                    }
                                }else
                                {
                                    player.loadVideo(youTubeId[1]);
                                }
                            }
                        }
                    } else {
                        showMessage(getString(R.string.ID_YOU_TUBE_VIDEO_NOT_PLAYING));
                        finish();
                    }
                }
            }


        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(AppConstants.YOUTUBE_DEVELOPER_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().
        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().
        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.
        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null) {
            player.release();
        }
        player = null;
        finish();
    }
}
