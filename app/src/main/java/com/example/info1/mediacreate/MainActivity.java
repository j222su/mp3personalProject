package com.example.info1.mediacreate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEdit, btnHome, btnLikeList;
    boolean first = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHome=findViewById(R.id.btnHome);
        btnEdit=findViewById(R.id.btnEdit);
        btnLikeList=findViewById(R.id.btnLikeList);

        btnHome.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnLikeList.setOnClickListener(this);

        btnHome.callOnClick();


    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        Fragment fragmentActivity=null;
        switch (v.getId()) {
            case R.id.btnHome :
                if(first){
                    FragHomeActivity.btnStop.callOnClick();
                }
                first = true;
                fragmentActivity=new FragHomeActivity();
                ft.replace(R.id.frameLayout, fragmentActivity);
                ft.commit();
                break;
            case R.id.btnEdit :
                if(first){
                    FragHomeActivity.btnStop.callOnClick();
                }
                first = true;
                fragmentActivity=new FragEditActivity();
                ft.replace(R.id.frameLayout, fragmentActivity);
                ft.commit();
                break;
            case R.id.btnLikeList :
                if(first){
                    FragHomeActivity.btnStop.callOnClick();
                }
                first = true;
                fragmentActivity=new FragLikeListActivity();
                ft.replace(R.id.frameLayout, fragmentActivity);
                ft.commit();
                break;
            default :
        }
//        ft.replace(R.id.frameLayout, fragmentActivity);
//        ft.commit();
    }
}
