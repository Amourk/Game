package com.example.dtlp.user_main;


import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtlp.Date.UploadUtil;
import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.entity.ItemBean;
import com.example.dtlp.side_pull_box.About;
import com.example.dtlp.side_pull_box.Personal_information;
import com.example.dtlp.start.loginActivity;
import com.example.dtlp.tap_fragment.fragment_classify;
import com.example.dtlp.tap_fragment.fragment_history;
import com.example.dtlp.tap_fragment.fragment_push;
import com.example.dtlp.utils.ItemDataUtils;
import com.example.dtlp.view.CircleImageView;
import com.example.dtlp.widget.DragLayout;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity_2 extends BaseActivity implements  View.OnClickListener {
    private DragLayout dl;
    private ListView lv;
    private ImageView ivIcon, ivBottom;
    private QuickAdapter<ItemBean> quickAdapter;
    private  ImageButton imag_search;
    private Button exit;
    public static ImageView imageView;
    //毛钰铭
    //fragment tap123

    private LinearLayout Layout_history,Layout_push,Layout_classify;
    private Fragment fragment_push,fragment_history,fragment_classify;
    private ImageButton imageButton_push,imageButton_history,imageButton_classify;
    private TextView textView_history,textView_push,textView_classify,name;






    //fragment tap123

    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //头像1
    private CircleImageView headImage1;
    //头像2
    private ImageView headImage2;
    //调用照相机返回图片临时文件
    private File tempFile;
    // 1: qq, 2: weixin
    private int type;

    StringBuilder str;

    public static boolean isEnter = false;

    private static boolean isExit = false;

    private String UserID = "Sun May 21 18:13:33 CST 2017mzZxs";
    File filename = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt"); // 要读取以上路径的input。txt文件


    OkHttpClient okHttpClient = new OkHttpClient();

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        setStatusBar();
        initDragLayout();
        initView();



        exit = (Button) findViewById(R.id.exit);
        name = (TextView) findViewById(R.id.name);

        //从本地用户文件中将数据读取出来
        String userdata = "";//读出来的Json数据
        String UserName = "";
//        File filename = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt"); // 要读取以上路径的input。txt文件
        Log.i("userdata", "userdata: = " + filename);
        try {
            FileReader read = new FileReader(filename);
            StringBuffer sb = new StringBuffer();
            char ch[] = new char[1024];
            int d = read.read(ch);
            while(d!=-1){
                String str = new String(ch,0,d);
                sb.append(str);
                d = read.read(ch);
            }
            userdata= sb.toString();
            System.out.print(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("userdata", "userdata: = " + userdata);
        //解析得到的Json数据 得到用户名字
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(userdata);
             UserName=jsonObject.getJSONObject("userData").getString("UserName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        name.setText(UserName); //进入MainActivity_2 将以用户名字赋予name的控件

        createCameraTempFile(savedInstanceState);


        //进入MainActivity_2 将isEnter赋值为true 判断是否进入使之退出后直接进去MainActivity_2 不必再次登录
        //在保存本地的用户信息添加isEnter的值
//        try {
//            JSONObject  jsonObject1 = new JSONObject(userdata);
//            JSONObject user = jsonObject1.getJSONObject("userData");
//            user.put("isEnter",true);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        SharedPreferences msharedPreferences = getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = msharedPreferences.edit();
        editor.putString("isEnter","true");
        editor.commit();


        initViewf();
        initevent();
        select(0);
        search();


        SharedPreferences sharedPreferences=getSharedPreferences("Head_portrait", Context.MODE_PRIVATE);
        //第一步:取出字符串形式的Bitmap
        String imageString=sharedPreferences.getString("image", "");
        //第二步:利用Base64将字符串转换为ByteArrayInputStream
        byte[] byteArray=Base64.decode(imageString, Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
        //第三步:利用ByteArrayInputStream生成Bitmap
        Bitmap bitmap=BitmapFactory.decodeStream(byteArrayInputStream);
        ivBottom.setImageBitmap(bitmap);
        ivIcon.setImageBitmap(bitmap);

        if (MainActivity.instance!=null){

            MainActivity.instance.finish();
        }
    }


    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            //界面打开的时候
            @Override
            public void onOpen() {
            }
            //界面关闭的时候
            @Override
            public void onClose() {
            }

            //界面滑动的时候
            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(ivIcon, 1 - percent);
            }
        });
    }



    public void initView() {

        imageView = (ImageView) findViewById(R.id.id_photo);

        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        ivBottom = (ImageView) findViewById(R.id.iv_bottom);

        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(quickAdapter=new QuickAdapter<ItemBean>(this,R.layout.item_left_layout, ItemDataUtils.getItemBeans()) {
            @Override
            protected void convert(BaseAdapterHelper helper, ItemBean item) {
                helper.setImageResource(R.id.item_img,item.getImg())
                        .setText(R.id.item_tv,item.getTitle());
            }
        });
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
//                Toast.makeText(MainActivity_2.this,"Click Item "+position, Toast.LENGTH_SHORT).show();
                if (position == 0)
                {
                    Intent intent1 = new Intent(MainActivity_2.this,Personal_information.class);
                    startActivityForResult(intent1,00);//00
                }
                if (position == 3)
                {
                    Intent intent1 = new Intent(MainActivity_2.this,About.class);
                    startActivityForResult(intent1,00);//00
                }

                if (position==4)
                {
                    Toast.makeText(MainActivity_2.this,"当前已是最新版本，无需更新！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
            }
        });
        ivBottom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                uploadHeadImage();
            }
        });
    }
    public   void search(){
        imag_search=(ImageButton)findViewById(R.id.img_button_aearch);
        imag_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity_2.this,main_search.class);
                startActivity(intent);
            }
        });
    }

    public void doClick(View view)
    {
        switch (view.getId())
        {
            case R.id.exit:

                SharedPreferences sp = getSharedPreferences("login",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                Intent intent1 = new Intent(MainActivity_2.this,MainActivity.class);
                startActivity(intent1);
                break;

        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCarema();
            } else {
                // Permission Denied
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            } else {
                // Permission Denied
            }
        }
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.qqLayout:
//                type = 1;
//                uploadHeadImage();
//                break;
//            case R.id.weixinLayout:
//                type = 2;
//                uploadHeadImage();
//                break;
//        }
//    }


    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main_1, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(MainActivity_2.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(MainActivity_2.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCarema();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(MainActivity_2.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(MainActivity_2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统图库
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCarema() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    /**
     * 创建调用系统照相机待存储的临时文件
     *
     * @param savedInstanceState
     */
    private void createCameraTempFile(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"),
                    System.currentTimeMillis() + ".jpg");
        }
    }

    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);//图片的路径

                    String url = "http://192.168.0.18:8080/TotemDown/UploadPicture?username=linyuanbin&password=13944677348";
                    new MyAsyncTask().execute(cropImagePath,url);

                    Log.i("file", "cropImagePath = : " + cropImagePath);

                        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    if (type == 1) {
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        bitMap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
                        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
                        byte[] byteArray=byteArrayOutputStream.toByteArray();
                        String imageString=new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
                        //第三步:将String保持至SharedPreferences

                        //头像上传到服务器
//                        String post ="{\"state\":\"uploadHeaPortr\",\"UserID\":\""+MainActivity.UserID+"\",\"imagefile\":\""+str1.toString()+"\"}";
//                        RequestBody requestBody1 = RequestBody
//                                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//                        Request.Builder builder3 = new Request.Builder();
//                        Request request2 = builder3
//                                .url(MainActivity.URL)
//                                .post(requestBody1)
//                                .build();
//                        CallHttp(request2);
//                        Log.i("info", "post = " + post);


                        SharedPreferences sharedPreferences=getSharedPreferences("Head_portrait", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("image", imageString);
                        editor.commit();


                        ivBottom.setImageBitmap(bitMap);
                        ivIcon.setImageBitmap(bitMap);
                    } else {
                        headImage2.setImageBitmap(bitMap);
                    }
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......

                }
                break;
        }
    }
    //服务器返回数据的处理
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

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Gson gson = new Gson();
//                        User user = gson.fromJson(res, User.class);
////                        user.getState();
//                        if ( user.getState().equals("true"))
//                        {
////                            Log.i("info", "res  = " + res.toString());
//                            Toast.makeText(MainActivity_2.this, "登录成功！！", Toast.LENGTH_SHORT).show();
//                            Intent intent1 = new Intent(MainActivity_2.this, MainActivity_2.class);
//                            startActivity(intent1);
//                            MainActivity_2.this.finish();
//                        }//得到的res为用户ID  保存到本地
//                        else
//                            Toast.makeText(MainActivity_2.this, "登录失败了哦！！！", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }





    /**
     * 打开截图界面
     *
     * @param uri
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }


    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
//            Message m = new Message();
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
//            m.what = 1;
            mHandler.sendEmptyMessageDelayed(0, 2000);
//            handle.sendMessage(m);

        } else {
            isEnter = true;
            Log.i("info", "isEnter5 =  " + MainActivity_2.isEnter);
            finish();
            System.exit(0);
            isEnter = true;
            Log.i("info", "isEnter6 =  " + MainActivity_2.isEnter);
        }
    }



    //毛钰铭
    //tap fragment
    private  void initViewf(){
        //申明控件
        Layout_push=(LinearLayout)findViewById(R.id.layout_tap01);
        Layout_history=(LinearLayout)findViewById(R.id.layout_tap02);
        Layout_classify=(LinearLayout)findViewById(R.id.layout_tap03);

        imageButton_push=(ImageButton)findViewById(R.id.img_button_push);
        imageButton_history=(ImageButton)findViewById(R.id.img_button_history);
        imageButton_classify=(ImageButton)findViewById(R.id.img_button_classify);
        textView_push=(TextView)findViewById(R.id.text_fragment_push);
        textView_history=(TextView)findViewById(R.id.text_fragment_history);
        textView_classify=(TextView)findViewById(R.id.text_fragment_classify);


    }

    private  void initevent(){

        Layout_push.setOnClickListener(this);
        Layout_history.setOnClickListener(this);
        Layout_classify.setOnClickListener(this);

        }
    public void restimg(){
        imageButton_push.setImageResource(R.drawable.push7);
        imageButton_history.setImageResource(R.drawable.history6);
        imageButton_classify.setImageResource(R.drawable.discover10);
        textView_push.setTextColor(Color.rgb(112,128,144));
        textView_history.setTextColor(Color.rgb(112,128,144));
        textView_classify.setTextColor(Color.rgb(112,128,144));

    }
    //点击事件
    @Override
    public void onClick(View view){
        restimg();
        switch (view.getId()){
            case R.id.layout_tap01:
                select(0);
                break;
            case R.id.layout_tap02:
                select(1);
                break;
            case R.id.layout_tap03:
                select(2);
                break;

        }

    }

    //
    public void select(int i){
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        hideFragment(ft);
        switch (i){
            case 0:
                if(fragment_push==null){
                    fragment_push=new fragment_push();
                    ft.add(R.id.fragment_boom,fragment_push);//fragment_boom在main3.xml

                }
                else {
                    ft.show(fragment_push);
                }
                imageButton_push.setImageResource(R.drawable.push6);

                textView_push.setTextColor(Color.rgb(30,144,255));
                break;
            case 1:
                if(fragment_history==null){
                    fragment_history=new fragment_history();
                    ft.add(R.id.fragment_boom,fragment_history);

                }
                else{
                    ft.show(fragment_history);
                    Log.i("imageUrl", "OOOOOOOOOOOOOOOOOO ");
                }
                imageButton_history.setImageResource(R.drawable.history10);
                textView_history.setTextColor(Color.rgb(30,144,255));
                break;
            case 2:
                if(fragment_classify==null){
                    fragment_classify=new fragment_classify();
                    ft.add(R.id.fragment_boom,fragment_classify);
                }
                else{
                    ft.show(fragment_classify);
                }
                imageButton_classify.setImageResource(R.drawable.discover11);
                textView_classify.setTextColor(Color.rgb(30,144,255));
                break;
        }
        ft.commit();
    }

    private void hideFragment(android.app.FragmentTransaction transaction){//件不需要显示的Fragment隐藏

//        if(mFragment_push!=null){
//            transaction.hide(mFragment_push);//隐藏
//        }

        if (fragment_push!=null){
            transaction.hide(fragment_push);
        }
        if (fragment_history!=null){
            transaction.hide(fragment_history);
        }
        if (fragment_classify!=null){
            transaction.hide(fragment_classify);
        }

    }//hide
    //tap fragment


    @Override
    protected void onRestart() {



        //修改用户姓名之后MainActivity_2重新返回 便修改名字
        String userdata = "";
        String UserName = "";
//        File filename = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt"); // 要读取以上路径的input。txt文件
        Log.i("userdata", "userdata: = " + filename);
        try {
            FileReader read = new FileReader(filename);
            StringBuffer sb = new StringBuffer();
            char ch[] = new char[1024];
            int d = read.read(ch);
            while(d!=-1){
                String str = new String(ch,0,d);
                sb.append(str);
                d = read.read(ch);
            }
            userdata= sb.toString();
            System.out.print(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("userdata", "userdata: = " + userdata);
        //解析得到的Json数据 得到用户名字
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(userdata);
            UserName=jsonObject.getJSONObject("userData").getString("UserName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        name.setText(UserName);


//        SharedPreferences sp = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
//        name.setText(sp.getString("nickname",""));

        super.onRestart();
    }
}
class MyAsyncTask extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... strings) {

        try {
            File file=new File(strings[0]);
            Log.i("file", "file=  " + file.getName());
            return UploadUtil.uploadFile(file,strings[1]);
        }catch (Exception ex){

            ex.printStackTrace();
        }

        return "failed";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
    //    @Override
//    protected void onPostExecute(String s) {
//
//        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
//    }
}
