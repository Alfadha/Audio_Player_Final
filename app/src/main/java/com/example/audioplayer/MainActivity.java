package com.example.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button play,stop,next,previous,random;

    final MediaPlayer mediaPlayer = new MediaPlayer();
    ArrayList<Musik> MusicList= new ArrayList();
    int nowPlaying = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (Button)findViewById(R.id.play);
        stop = (Button)findViewById(R.id.stop);
        next = (Button)findViewById(R.id.next);
        previous = (Button)findViewById(R.id.previous);
        random = (Button)findViewById(R.id.random);

        MusicList.add(new Musik(getUri(R.raw.girls_like_you), "Maroon 5 - Girls Like You"));
        MusicList.add(new Musik(getUri(R.raw.jang_ganggu), ""));
        MusicList.add(new Musik(getUri(R.raw.tulus_hatihati), "Tulus - Hati-Hati"));
        MusicList.add(new Musik(getUri(R.raw.powfu_deathbed), "Powfu - death bed"));
        MusicList.add(new Musik(getUri(R.raw.until_found),"Alexis - until i found"));
        init();
        try {
            loadMusic(MusicList.get(nowPlaying));
        } catch (IOException e) {
            e.printStackTrace();
        }


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomMusik();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMusic();
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.seekTo(0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NextMusik();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviousMusik();
            }
        });
    }
    private void init() {
        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            NextMusik();
        });
    }


    private void loadMusic(Musik music) throws IOException {
        mediaPlayer.reset();
        mediaPlayer.setDataSource(getApplicationContext(), music.getUri());
        mediaPlayer.prepare();
    }

    private void PreviousMusik() {
        if (nowPlaying == 0) {
            throw new IndexOutOfBoundsException();
        }
        stopMusic();
        nowPlaying -= 1;

        try {
            loadMusic(MusicList.get(nowPlaying));
        } catch (IOException e) {
            e.printStackTrace();
        }

        play();
    }

    private void randomMusik() {
        stopMusic();
        Random random = new Random();
        nowPlaying = random.nextInt(5-0)+0;
        try {
            loadMusic(MusicList.get(nowPlaying));
        } catch (IOException e) {
            e.printStackTrace();
        }

        play();
    }

    private void NextMusik() {
        if (nowPlaying == MusicList.size() - 1) {
            throw new IndexOutOfBoundsException();
        }

        stopMusic();
        nowPlaying += 1;

        try {
            loadMusic(MusicList.get(nowPlaying));
        } catch (IOException e) {
            e.printStackTrace();
        }
        play();
    }

    private void play() {
        mediaPlayer.start();
    }
    private void stopMusic() {
        mediaPlayer.stop();
    }


    private Uri getUri(int rawId) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + rawId);
    }
}