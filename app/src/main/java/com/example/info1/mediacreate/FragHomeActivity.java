package com.example.info1.mediacreate;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class FragHomeActivity extends Fragment implements View.OnClickListener {
    static ImageButton btnPause, btnPlay, btnStop;
    static ImageView imgAlbum_play;
    static TextView tvSinger_play, tvTitle_play, tvProgress;
    SeekBar seekBar;
    View view;
    static RecyclerView recyclerView;
    MediaPlayer mediaPlayer;
    ArrayList<MyData> list = new ArrayList<MyData>();
//    static final String MP3_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
//        static final String MP3_PATH = "/storage/emulated/0/";
    MyAdapter myAdapter;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase;
    LinearLayoutManager linearLayoutManager;
    //    String selectedMP3;
    HashMap<Integer, String> map = new HashMap<>();
    boolean paused=false;
    int position;


    @SuppressLint("ValidFragment")
    public FragHomeActivity() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);

        mediaPlayer = new MediaPlayer();
        recyclerView = view.findViewById(R.id.recyclerView);
        btnPause = view.findViewById(R.id.btnPause);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnStop = view.findViewById(R.id.btnStop);
        imgAlbum_play = view.findViewById(R.id.imgAlbum_play);

        tvSinger_play = view.findViewById(R.id.tvSinger_play);
        tvTitle_play = view.findViewById(R.id.tvTitle_play);
        tvProgress = view.findViewById(R.id.tvProgress);

        seekBar = view.findViewById(R.id.seekBar);

        myDBHelper = new MyDBHelper(getActivity());
        Log.d("음악파일테스트1", "onCreateView");

        ActivityCompat.requestPermissions(getActivity(), new String[]
                {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        loadAlbum();

        btnPause.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);




        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(R.layout.item_layout, list);
        recyclerView.setAdapter(myAdapter);


        return view;

    }//end of onCreateView



    public void loadAlbum() {

        String[] Music_cursorColumns = new String[]{
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.ALBUM_ID,
        };

        /*ContentProvider는 모든 시스템의 정보들을 제공해주는 기능을 가지고 있다.
          단 uri방식으로 접근해야 한다.
        */

        Cursor Music_cursor = getActivity().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Music_cursorColumns, null, null, null);


        if (Music_cursor != null) { //커서가 널값이 아니면
            if (Music_cursor.moveToFirst()) {
                int album = Music_cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM); //앨범이름
                int title = Music_cursor.getColumnIndex(MediaStore.Audio.Media.TITLE); //이름
                int displayName = Music_cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME); //표시되는 이름
                int track = Music_cursor.getColumnIndex(MediaStore.Audio.Media.TRACK); //트랙
                int directory = Music_cursor.getColumnIndex(MediaStore.Audio.Media.DATA); //음악 경로
                int artist = Music_cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST); //아티스트
                int duration = Music_cursor.getColumnIndex(MediaStore.Audio.Media.DURATION); //실행시간
                int year = Music_cursor.getColumnIndex(MediaStore.Audio.Media.YEAR); //발매 년도
                int albumId = Music_cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID); //앨범아이디
                do {
                    String albumArt = ""; //앨범아트 주소를 담을 문자열
                    if (map.containsKey(Integer.parseInt(Music_cursor.getString(albumId)))) {
                        //맵이 앨범아이디와 동일한 항목을 가지고 있으면
                        albumArt = map.get(Integer.parseInt(Music_cursor.getString(albumId)));
                        //앨범아트 주소를 넣어줍니다
                    }

                    MyData myData = new MyData(
                            Music_cursor.getString(album),
                            Music_cursor.getString(title),
                            Music_cursor.getString(displayName),
                            Music_cursor.getString(track),
                            Music_cursor.getString(directory),
                            Music_cursor.getString(artist),
                            Music_cursor.getString(duration),
                            Music_cursor.getString(year),
                            Music_cursor.getString(albumId),
                            albumArt);

                    Log.d("음악파일테스트10", "onCreateView " + myData.getAlbum());
                    Log.d("음악파일테스트10", "onCreateView " + myData.getTitle());
                    Log.d("음악파일테스트10", "onCreateView " + myData.getDisplayName());
                    Log.d("음악파일테스트10", "onCreateView " + myData.getTrack());
                    Log.d("음악파일테스트10", "onCreateView " + myData.getDirectory());
                    Log.d("음악파일테스트10", "onCreateView " + myData.getArtist());
                    Log.d("음악파일테스트10", "onCreateView " + myData.getDuration());
                    Log.d("음악파일테스트10", "onCreateView " + myData.getYear());
                    Log.d("음악파일테스트10", "onCreateView " + myData.getAlbumArt());
                    list.add(myData);
                } while (Music_cursor.moveToNext());
            }
        }

        Music_cursor.close();

    }//end of loadAlbum();

    /*****************************************
     판규오빠
     */

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnPause:
//                Log.d("재생pause", "onClick");
//                mediaPlayer.pause();
//                paused = true;
//
//                btnPlay.setEnabled(true);
//                btnPause.setEnabled(false);
//                btnStop.setEnabled(true);
//
//
//                break;
//            case R.id.btnPlay:
//
//                if (paused) {
//                    Toast.makeText(getContext(), "재생", Toast.LENGTH_SHORT).show();
//                    mediaPlayer.start();
//                    paused = false;
//
//                    btnPlay.setEnabled(false);
//                    btnPause.setEnabled(true);
//                    btnStop.setEnabled(true);
//
//                } else {
//
//                    try {
//                        if(MyAdapter.selectedMP3_dataPath != null){
//                            mediaPlayer.setDataSource(MyAdapter.selectedMP3_dataPath);
//
//                        }else{
//                            mediaPlayer.setDataSource(MyAdapter.list.get(0).getDirectory());
//                            Toast.makeText(getContext(), "재생할 곡을 먼저 선택해주세요", Toast.LENGTH_SHORT).show();
//                        }
//
//                        mediaPlayer.prepare();
//                        mediaPlayer.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    btnPlay.setEnabled(false);
//                    btnPause.setEnabled(true);
//                    btnStop.setEnabled(true);
//
//                }
//                Thread thread = new Thread() {
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//
//                    @Override
//                    public void run() {
//                        if (mediaPlayer == null) {
//                            return;
//                        }
//
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                seekBar.setMax(mediaPlayer.getDuration());
//                                tvProgress.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
//                            }
//                        });
//
//                        while (mediaPlayer.isPlaying()) {
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
////                                        tvProgress.setText("진행시간 :"+simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
//                                }
//                            });
//                            SystemClock.sleep(200);
//                        }
//                    }
//                };
//                Log.d("재생5", "onClick");
//                thread.start();
//                Log.d("재생6", "onClick");
//
//
//                break;
//
//
//            case R.id.btnStop:
//                Log.d("재생stop", "onClick");
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                }
//                btnPlay.setEnabled(true);
//                btnStop.setEnabled(false);
//                btnPause.setEnabled(false);
//                seekBar.setProgress(0);
//                break;
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPause:
                position=mediaPlayer.getCurrentPosition();
                Log.d("재생pause1위치", "onClick"+position);
                mediaPlayer.pause();
                Log.d("재생pause2", "onClick");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer.isPlaying()) {
                            btnSetting(true, false, true);
                        } else {
                            btnSetting(false, true, true);
                        }
                    }
                });
                break;
            case R.id.btnPlay:

                Log.d("재생시작전위치파악", "onClick" + mediaPlayer.getCurrentPosition());
                if(MyAdapter.selectedMP3_dataPath==null) {
                    Toast.makeText(getContext(), "재생할 곡을 먼저 선택해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        mediaPlayer.seekTo(position);
                    }
                    if (mediaPlayer.getCurrentPosition() == 0) {
                        try {
                            mediaPlayer.setDataSource(MyAdapter.selectedMP3_dataPath);
                            Log.d("재생시작", "onClick" + MyAdapter.selectedMP3_dataPath);
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mediaPlayer.start();
                        Log.d("재생play", "onClick" + mediaPlayer.getCurrentPosition());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mediaPlayer.isPlaying()) {
                                    btnSetting(true, false, true);
                                } else {
                                    btnSetting(true, true, false);
                                }
                            }
                        });

                        Thread thread = new Thread(new Runnable() {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

                            @Override
                            public void run() {
                                while (mediaPlayer.isPlaying()) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                            tvProgress.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                                        }
                                    });
                                    SystemClock.sleep(200);
                                }//end of while

                            }
                        });

                        thread.start();
                    }
                }

                break;
            case R.id.btnStop:


                btnSetting(true, true, false);

                Log.d("재생stop1", "onClick"+mediaPlayer.getCurrentPosition());
                mediaPlayer.stop();
//                mediaPlayer.stop();
                mediaPlayer.reset();
                Log.d("재생stop2", "onClick"+mediaPlayer.getCurrentPosition());
                seekBar.setProgress(0);
                tvProgress.setText("00:00");




                break;
        }
    }

    private void btnSetting(boolean b, boolean b1, boolean b2) {
        btnPause.setEnabled(b);
        btnPlay.setEnabled(b1);
        btnStop.setEnabled(b2);


    }



    /******************************
    나
     */
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnPause:
//
//                try {
//                    mediaPlayer.pause();
//                    mediaPlayer.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mediaPlayer.seekTo(0);
//                btnPause.setEnabled(false);
//                btnPlay.setEnabled(true);
//                btnStop.setEnabled(true);
//                Log.d("재생pause", "onClick");
//
//                break;
//            case R.id.btnPlay:
//
//                try {
//                    mediaPlayer.setDataSource(MyAdapter.selectedMP3_dataPath);
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//
//
//                    Thread thread = new Thread() {
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//                        @Override
//                        public void run() {
//                            if(mediaPlayer==null) {
//                                return;
//                            }
//
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    seekBar.setMax(mediaPlayer.getDuration());
////                                    tvProgress.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
//                                }
//                            });
//
//                            while (mediaPlayer.isPlaying()) {
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
//                                        tvProgress.setText("진행시간 :"+simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
//                                    }
//                                });
//                                SystemClock.sleep(200);
//                            }
//                        }
//                    };
//                    Log.d("재생5", "onClick");
//                    thread.start();
//                    Log.d("재생6", "onClick");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case R.id.btnStop:
//                Log.d("재생stop", "onClick");
//                mediaPlayer.stop();
//                mediaPlayer.reset();
//                seekBar.setProgress(0);
//                break;
//        }
//    }



}








