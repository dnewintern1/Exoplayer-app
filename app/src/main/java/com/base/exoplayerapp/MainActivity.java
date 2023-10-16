package com.base.exoplayerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    PlayerView playerView;
    private ExoPlayer mSimpleExoPlayer;

    private  String[] music_URL = new String[] {"https://opengameart.org/sites/default/files/Ove%20Melaa%20-%20High%20Stakes%2CLow%20Chances_1.mp3",
           "https://opengameart.org/sites/default/files/Enterin%20The%20Skies.mp3",
    "https://opengameart.org/sites/default/files/Ove%20Melaa%20-%20Hero%20Within.mp3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.playerView);

        mSimpleExoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(mSimpleExoPlayer);

        for(String music_url : music_URL){
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(music_url));

            mSimpleExoPlayer.addMediaItem(mediaItem);

        }

        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.play();





    }
}