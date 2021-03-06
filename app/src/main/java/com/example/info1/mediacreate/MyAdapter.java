package com.example.info1.mediacreate;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MediaViewHoler> {

    int layout;
    static ArrayList<MyData> list;
    static String selectedMP3_artist;
    static String selectedMP3_title;
    static String selectedMP3_albumImage;
    static String selectedMP3_dataPath;
    int flag=0;

    public MyAdapter(int layout, ArrayList<MyData> list) {
        this.layout = layout;
        this.list = list;
    }



    /*아이템뷰 뷰로 만들기
        리사이클러뷰에 표시되는 각 아이템을 하나의 뷰로 만들기 위해서 inflate시켜준다.
         */
    @NonNull
    @Override
    public MediaViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        MediaViewHoler mediaViewHoler=new MediaViewHoler(view);
        return mediaViewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull final MediaViewHoler mediaViewHoler, final int position) {
        mediaViewHoler.tvSinger.setText(list.get(position).getArtist());
        mediaViewHoler.tvTitle.setText(list.get(position).getTitle());
//        mediaViewHoler.tvTitle.setText(list.get(position).getTitle());

        /*
        분으로 변환 하면 꺼짐... ㅠㅠ
         */
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");
//        String time=simpleDateFormat.format(list.get(position).getDuration());
//        mediaViewHoler.tvDuration.setText(time);

//        android.text.format.DateFormat df=new android.text.format.DateFormat(list.get(position).getDuration()));
//        String time= String.valueOf(df.format("mm:ss", new java.util.Date()));
//        mediaViewHoler.tvDuration.setText(time);

        int a = Integer.parseInt(list.get(position).getDuration())/60000;
        String c,d;
        if(a<10){
            c = 0+String.valueOf(a);
        }else{
            c = String.valueOf(a);
        }
        int b = (Integer.parseInt(list.get(position).getDuration()) - (60000*a))/1000;
        if(b<10){
            d = 0+String.valueOf(b);
        }else{
            d = String.valueOf(b);
        }
        mediaViewHoler.tvDuration.setText(c+":"+d);

        /*mp3파일에서 앨범아트 가져오기
        Uri artworkUri=Uri.parse("content://media/external/audio/albumart");는 고정된 주소이다.

         */
        final Uri artworkUri=Uri.parse("content://media/external/audio/albumart");
        final Uri albumArtUri=ContentUris.withAppendedId(artworkUri, Integer.parseInt(list.get(position).getAlbumId()));
        Picasso.with(mediaViewHoler.itemView.getContext()).load(albumArtUri).error(R.drawable.ic_launcher_background).into(mediaViewHoler.imgAlbum);

        mediaViewHoler.itemView.setTag(position);
        Log.d("어댑터3-1", "onBindViewHolder");

        /*리사이클러뷰에 표시되는 아이템뷰 클릭 시 이벤트 발생부분
          해당 곡의 제목과 가수를 보여준다.
         */
        mediaViewHoler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedMP3_artist=list.get(position).getArtist();
                selectedMP3_title=list.get(position).getTitle();
                selectedMP3_albumImage=list.get(position).getAlbumArt();
                selectedMP3_dataPath=list.get(position).getDirectory();

                FragHomeActivity.tvSinger_play.setText(selectedMP3_artist);

                FragHomeActivity.tvTitle_play.setText(selectedMP3_title);

//                notifyItemChanged(position);

                final Uri albumArtUri=ContentUris.withAppendedId(artworkUri, Integer.parseInt(list.get(position).getAlbumId()));
                Picasso.with(mediaViewHoler.itemView.getContext()).load(albumArtUri).error(R.drawable.ic_launcher_background).into(FragHomeActivity.imgAlbum_play);

            }
        });


       mediaViewHoler.btnLike.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(flag==0) {
                   Log.d("어댑터3-1", "btnLike"+flag);
                   mediaViewHoler.btnLike.setBackgroundResource(R.mipmap.likeheart);
                   flag=1;
                   Log.d("어댑터3-2", "btnLike"+flag);
               }
               else if (flag==1) {
                   mediaViewHoler.btnLike.setBackgroundResource(R.mipmap.likeheartincircular);
                   flag=0;
               }
           }
       });


    }

    @Override
    public int getItemCount() {
        return (list!=null)? list.size() : 0;
    }

    public static class MediaViewHoler extends RecyclerView.ViewHolder {

        TextView tvSinger;
        TextView tvTitle;
        TextView tvDuration;
        ImageView imgAlbum;
        ImageButton btnLike;

        public MediaViewHoler(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvSinger=itemView.findViewById(R.id.tvSinger);
            tvDuration=itemView.findViewById(R.id.tvDuration);
            imgAlbum=itemView.findViewById(R.id.imgAlbum);
            btnLike=itemView.findViewById(R.id.btnLike);

//            final Uri artworkUri=Uri.parse("content://media/external/audio/albumart");
//            Uri albumArtUri=ContentUris.withAppendedId(artworkUri, FragHomeActivity.id);
//            Picasso.with(itemView.getContext()).load(albumArtUri).error(R.drawable.ic_launcher_background).into(id);


//            FragHomeActivity.recyclerView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos=getAdapterPosition();
//                    if(pos!=RecyclerView.NO_POSITION) {
//                        Toast.makeText(context, "노래 클릭함", Toast.LENGTH_SHORT).show();
//                        Log.d("어댑터10", "onBindViewHolder");
//                    }
//                }
//            });



        }





    }
}
