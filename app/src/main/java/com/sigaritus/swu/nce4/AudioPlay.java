package com.sigaritus.swu.nce4;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Administrator on 2015/6/28.
 */
public class AudioPlay {

    private  MediaPlayer mMediaPlayer;

    private static AudioPlay instance;

    private AudioPlay(){
//        mMediaPlayer = new MediaPlayer();
//        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public static AudioPlay getInstance(){
        if (instance==null){
            instance = new AudioPlay();
        }
        return instance;
    }


    public void play(String url){
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();// might take long! (for buffering, etc)
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
