package com.example.dtlp.tap_fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.dtlp.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 阳瑞 on 2017/5/11.
 */
public class Image extends Activity {
    public static String Label;
     String ID;
    private String imageurl;
    PictureTagLayout pictureTagLayout;
    private TextView textView;

    Handler handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            Bitmap bitmap = (Bitmap) msg.obj;
            Drawable drawable =new BitmapDrawable(bitmap);
//            Drawable drawable =new BitmapDrawable();
            pictureTagLayout.setBackground(drawable);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main_image1);

        pictureTagLayout = (PictureTagLayout) findViewById(R.id.tupian);
        textView = (TextView) findViewById(R.id.tvPictureTagLabel);


        Label = "";
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        final String image=bundle.getString("image");
         ID=bundle.getString("ID");
        imageurl = image;
        Log.i("BBBBaaa", " ima = " + image);
        Log.i("BBBBaaa", " ID = " + ID);


        Thread mThread = new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                Bitmap bmp = getURLimage(image);
                msg.obj = bmp;
                System.out.println("000");
//                        }
                handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
            }
        };
        mThread.start();
        Log.i("DDDDDDD", "textImage = " + Label);

//        Intent mIntent = new Intent();
//        mIntent.putExtra("label",Label);
//        mIntent.putExtra("infol","info");
////        mIntent.putExtra("change02", "2000");
//        // 设置结果，并进行传送
//        this.setResult(0, mIntent);

    }

    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent();
        mIntent.putExtra("a",Label);
        mIntent.putExtra("b",ID);
        mIntent.putExtra("c",imageurl);
//        mIntent.putExtra("change02", "2000");
        // 设置结果，并进行传送
        this.setResult(0, mIntent);
        super.onBackPressed();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            moveTaskToBack(true);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
