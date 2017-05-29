package com.example.dtlp.start;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.user_main.MainActivity_2;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class loginActivity extends Activity implements AnimationListener {

    private ImageView _ivWelcome;
    private SharedPreferences sp;
    private String isEnter = "";
    OkHttpClient okHttpClient = new OkHttpClient();

    public static String SDPATH = "";//本地根目录
//    public static SQLiteDatabase db ;

        @Override
protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_the_animation);
        _ivWelcome = (ImageView)findViewById(R.id.start_picture);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_);
        animation.setAnimationListener(this);
        _ivWelcome.setAnimation(animation);
            //创建APP的一个文件夹 用来保存用户的本地基本信息
            SDPATH = Environment.getExternalStorageDirectory() + "/";
            File file = new File(SDPATH +"dtlp");
            file.mkdir();
            System.out.println("创建了文件夹");


//            Request.Builder builder = new Request.Builder();
//            Request request = builder
//                    .get()
//                    .url(MainActivity.URL)
//                    .build();
//            CallHttp(request);
//
//            db = openOrCreateDatabase("User.db",MODE_PRIVATE,null);
//            db.execSQL("create table if not exists User (_id integer primary key autoincrement," +
//                    "UserId text ,UserName text,UserEmail text,UserSex text,UserBirthday text" +
//                    ",UserMajor text,isEnter text,UserPassword text )");
    }

        @Override
public void onAnimationEnd(Animation animation) {
            sp = getSharedPreferences("login", Context.MODE_PRIVATE);
            isEnter = sp.getString("isEnter","");
            if (isEnter.equals("true"))
            {
                startActivity(new Intent(this,MainActivity_2.class));
                finish();
            }
            else
            {
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }
    }

        @Override
public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

@Override
public void onAnimationStart(Animation animation) {
// TODO Auto-generated method stub

  }
    public void CallHttp(Request request)

    {
        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
                Log.i("info", " e  = "  + e .toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("info", " GET请求成功！！！");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Conten.setText(res);
                    }
                });
            }
        });
    }
}
