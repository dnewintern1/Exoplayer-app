package com.base.exoplayerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    PlayerView playerView;
    private ExoPlayer mSimpleExoPlayer;

    private FloatingActionButton myfab;

    private  String[] music_URL = new String[] {"https://opengameart.org/sites/default/files/Ove%20Melaa%20-%20High%20Stakes%2CLow%20Chances_1.mp3",
           "https://opengameart.org/sites/default/files/Enterin%20The%20Skies.mp3",
    "https://opengameart.org/sites/default/files/Ove%20Melaa%20-%20Hero%20Within.mp3"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.playerView);
        myfab = findViewById(R.id.myfab);

        myfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupPermission();
            }
        });


        mSimpleExoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(mSimpleExoPlayer);

        for(String music_url : music_URL){
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(music_url));

            mSimpleExoPlayer.addMediaItem(mediaItem);

        }

        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.play();
    }

    private void setupPermission(){

        Dexter.withContext(this)
                .withPermissions(
                       Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()){
                            downloadTheCurrentMusic(mSimpleExoPlayer.getCurrentMediaItem().localConfiguration.uri.toString());
                        }
                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void downloadTheCurrentMusic(String musicURLstring){

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(musicURLstring);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        String musicName = musicURLstring.substring(musicURLstring.lastIndexOf("/") + 1);
        request.setTitle(musicName);
        request.setVisibleInDownloadsUi(true);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,uri.getLastPathSegment());
        downloadManager.enqueue(request);
    }
}