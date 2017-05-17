package com.example.dtlp.tap_fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.dtlp.Date.User;
import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.user_main.user_main_image.PhotoWallAdapter;
import com.example.dtlp.user_main.user_main_image.Picture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by cool on 2017-03-28.
 */
public class fragment_push extends Fragment {
    private View view;


    //用于展示照片墙的GridView
    private GridView mPhotoWall = null;

    private MaterialRefreshLayout materialRefreshLayout;

    //GridView的适配器
    private PhotoWallAdapter mAdapter = null;

    private Context mContext = null;

    private int mImageThumbSize;
    private int mImageThumbSpacing;

    private String ima[] = new String[10];
    private String ima1[] = new String[10];
    private String ID[] = new String[10];
    private String UserID = "Thu Apr 27 20:28:09 CST 201731ZDD";


    OkHttpClient okHttpClient = new OkHttpClient();



    Handler handle = new Handler() {

        public void handleMessage(Message msg) {
            System.out.println("111");
//                    tup = (Bitmap[]) msg.obj;
//                    tupp = (ArrayList<Bitmap>) msg.obj;

            String data = (String) msg.obj;
            Type listType = new TypeToken<ArrayList<Picture>>() {
            }.getType();
            ArrayList<Picture> foos = new Gson().fromJson(data, listType);
            for (int i = 0; i < foos.size(); i++) {
                ima[i] = foos.get(i).getPAddress();
                ID[i] = foos.get(i).getPID();
                System.out.println("name [" + i + "] = " + foos.get(i).getPAddress());
            }
            for (int k = 0; k < ima.length; k++) {
                Log.i("ccccc", "tupp =  " + ima[k]);
            }

            mAdapter = new PhotoWallAdapter(view.getContext(), 0, ima, mPhotoWall);
            mPhotoWall.setAdapter(mAdapter);
            mPhotoWall.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    final int numColumns = (int) Math.floor(mPhotoWall.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                    if (numColumns > 0) {
                        int columnWidth = (mPhotoWall.getWidth() / numColumns) - mImageThumbSpacing;
                        mAdapter.setItemHeight(columnWidth);
                        mPhotoWall.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });


            mPhotoWall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(view.getContext(), com.example.dtlp.tap_fragment.Image.class);
                    intent.putExtra("image",ima[position]);
                    intent.putExtra("ID",ID[position]);
                    Log.i("BBBBBBB", " ima = " + ima[position]);
                    Log.i("BBBBBBB", " ID = " + ID[position]);
                    startActivityForResult(intent,0);
//                            startActivity(intent);
                    Toast.makeText(mContext , position + "" , Toast.LENGTH_SHORT).show();
                }
            });

        }
    };


    @Override
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup group, Bundle bundle){
        view =layoutInflater.inflate(R.layout.fragment_push,group,false);

        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        try {
            flash2();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int j = 0; j < ima1.length; j++) {
            Log.i("info", "ima =  " + ima1[j]);
        }
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            flash2();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(MainActivity.this, "111111", Toast.LENGTH_SHORT).show();
                        materialRefreshLayout.finishRefresh();
                    }
                }, 3000);
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(MainActivity.this, "222222222", Toast.LENGTH_SHORT).show();
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }, 3000);
            }
        });


        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
        mPhotoWall = (GridView) view.findViewById(R.id.id_photo_wall);
        mContext = view.getContext();
//        mPhotoWall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Toast.makeText(mContext , position + "" , Toast.LENGTH_SHORT).show();
//            }
//        });



        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String MarkName = data.getStringExtra("a");
        String PID = data.getStringExtra("b");
//        String TabID =UserID+PID;
        switch (requestCode) {
            case 0:
                Log.i("DDDDDDD", "info = " + PID);
                Log.i("DDDDDDD", "textMain = " + MarkName);

                String name1 = "mark";

                String key = "{\"state\":\"" +name1+ "\"," +
                        "\"UserID\":\""+UserID+"\",\"PID\":\""+PID+"\",\"MarkName\":\""+MarkName+"\"}";
                try {
                    URLDecoder.decode(key, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                RequestBody requestBody1 = RequestBody
                        .create(MediaType.parse("text/plain; charset=utf-8"), key);
                Request.Builder builder3 = new Request.Builder();
                Request request2 = builder3
                        .url(MainActivity.URL)
                        .post(requestBody1)
                        .build();

                okhttp3.Call call1 = okHttpClient.newCall(request2);
                call1.enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        Log.i("info", " GET请求失败！！！");
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {

                        final String res = response.body().string();
                        Log.i("info", " labelres"+res);
                        Gson gson = new Gson();
                        User user = gson.fromJson(res, User.class);
//                        user.getState();
                        if ( !user.getState().equals("true"))
                        {
                            Log.i("info", " 标签添加失败了哦！！！");
                        }
                    }
                });
                break;

            default:
                break;
        }
    }

    public void flash2() throws UnsupportedEncodingException {
        String name1 = "request";

        String key = "{\"state\":\"" + name1 + "\"}";
        URLDecoder.decode(key, "utf-8");
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/plain; charset=utf-8"), key);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.URL)
                .post(requestBody1)
                .build();

        okhttp3.Call call1 = okHttpClient.newCall(request2);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("infoo", " GET请求成功！！！");

                Log.i("infoo", "res = " + res);

                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.obj = res;
                        handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
                    }
                };
                mThread.start();
            }
        });
    }
}
