package com.example.dtlp.Enter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dtlp.Date.User;
import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.user_main.MainActivity_2;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//.User.user;

/**
 * Created by 阳瑞 on 2017/3/19.
 */
public class Registered extends Activity {


    private EditText name,password,job,conpassword,number;
    private RadioButton man,woman;
    private Button registered_;
    private ImageView registered_picture;

    private String KEY = "31704cfaa4b17e8f90c7dac59dbec4dd";

    private static final String MOBILE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    private static final String PASSWORD_PATTERN = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

    OkHttpClient okHttpClient = new OkHttpClient();

    private Pattern pattern = Pattern.compile(MOBILE_PATTERN);
    private Matcher matcher;

    private Pattern pattern1 = Pattern.compile(PASSWORD_PATTERN);
    private Matcher matcher1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered);
        Bmob.initialize(this,KEY);

//        loginActivity.db = openOrCreateDatabase("User.db",MODE_PRIVATE,null);

        initView();

    }
    public void doClick(View view)
    {
        switch (view.getId())
        {
            case R.id.registered_:
                if (name.getText().toString().equals(""))
                {
                    Toast.makeText(Registered.this, "亲 昵称没填哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (password.getText().toString().equals(""))
                {
                    Toast.makeText(Registered.this, "亲 密码没填哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (conpassword.getText().toString().equals(""))
                {
                    Toast.makeText(Registered.this, "亲 确认密码没填哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!password.getText().toString().equals(conpassword.getText().toString()))
                {
                    Toast.makeText(Registered.this, "亲 两次密码不对哦", Toast.LENGTH_SHORT).show();
                }
                else if (number.getText().toString().equals(""))
                {
                    Toast.makeText(Registered.this, "亲 电话号码没填哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (job.getText().toString().equals(""))
                {
                    Toast.makeText(Registered.this, "亲 职业没填哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!(man.isChecked()||woman.isChecked()))
                {
                    Toast.makeText(Registered.this, "亲 性别没选哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    String num = number.getText().toString();
                    String pass = password.getText().toString();
                    if (!validatenumber(num))
                    {
                        Toast.makeText(Registered.this, "亲 电话号码输入不对哦！！", Toast.LENGTH_SHORT).show();

                    }else  if (!validatepassword(pass))
                    {
                        Toast.makeText(Registered.this, "亲 请输入由英文和数字组成的密码哦！！", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        registered();
                    }
                }
                break;
            case R.id.registered_picture:
//                type = 1;
//                uploadHeadImage();
                break;
        }

    }
    private void initView(){
        name = (EditText) findViewById(R.id.Name);
        password = (EditText) findViewById(R.id.Password);
        conpassword = (EditText) findViewById(R.id.ConPassword);
        job = (EditText) findViewById(R.id.Job);
        number = (EditText) findViewById(R.id.Number);
        man = (RadioButton) findViewById(R.id.man);
        woman = (RadioButton) findViewById(R.id.woman);
        registered_ = (Button) findViewById(R.id.registered_);
        registered_picture = (ImageView) findViewById(R.id.registered_picture);

    }
    public void registered()
    {
//        user u = new user();
//        String sex = "";
//        if (man.isChecked())
//        {
//            sex = man.getText().toString();
//        }else if (woman.isChecked())
//        {
//            sex = woman.getText().toString();
//        }
//
//        u.setName(name.getText().toString());
//        u.setPassword(password.getText().toString());
//        u.setNumber(number.getText().toString());
//        u.setJob(job.getText().toString());
//        u.setSex(sex);
//        u.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    Toast.makeText(Registered.this, "注册成功！ 欢迎使用！！", Toast.LENGTH_SHORT).show();
//                    Intent intent1 = new Intent(Registered.this,MainActivity_2.class);
//                    startActivity(intent1);
//                    Registered.this.finish();
//                    Log.i("info", "添加数据成功，返回objectId为："+s);
//                }else{
//                    Log.i("info", "创建数据失败：" + e.getMessage());
//                }
//            }
//        });



        String na = name.getText().toString();
        String pa = password.getText().toString();
        String num = number.getText().toString();
        String jo = job.getText().toString();
        String  se ="";
        if (man.isChecked())
        {
            se = man.getText().toString();
        }else if (woman.isChecked())
        {
            se = woman.getText().toString();
        }
        String post ="{\"state\":\"register\",\"UserName\":\""+na+"\",\"UserPassword\":\""+pa + "\",\"UserTel\":\""+num + "\"" +
                ",\"UserMajor\":\""+jo+"\",\"UserSex\":\""+se+"\"}";
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.URL)
                .post(requestBody1)
                .build();
        CallHttp(request2);
    }

    public void CallHttp(Request request)

    {
        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("info", " GET请求成功！！！");

                final Gson gson = new Gson();
                final User user = gson.fromJson(res, User.class);
                 MainActivity.UserID = user.getUserID();
//                String UserName = user.getUserName();
//                String UserPassword = user.getUserPassword();
//                String UserSex = user.getUserSex();
//                String UserMajor=user.getUserMajor();
//                loginActivity.db.execSQL("insert into User (UserID,UserName,UserPassword,UserSex,UserMajor) values " +
//                        "('"+UserID+"','"+UserName+"','"+UserPassword+"','"+UserSex+"','"+UserMajor+"',)");//添加UserID到数据库中


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("info", "res  = " + res.toString());
//                        Gson gson = new Gson();
//                        User user = gson.fromJson(res, User.class);//解析服务器端传过来的值UserID和state
                        if (user.getState().equals("true"))
                        {
//                            Log.i("info", "res  = " + res.toString());
                            Toast.makeText(Registered.this, "注册成功！ 欢迎使用！！", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(Registered.this,MainActivity_2.class);
                            startActivity(intent1);
                            Registered.this.finish();
                        }//得到的res为用户ID  保存到本地
                        else
                            Toast.makeText(Registered.this, "注册失败了哦！！！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public boolean validatenumber(String number)
    {
        matcher = pattern.matcher(number);
        return matcher.matches();
    }
    public boolean validatepassword(String pass)
    {
        matcher1 = pattern1.matcher(pass);
        return matcher1.matches();
    }



//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission Granted
//                gotoCarema();
//            } else {
//                // Permission Denied
//            }
//        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission Granted
//                gotoPhoto();
//            } else {
//                // Permission Denied
//            }
//        }
//    }
//
//
////    @Override
////    public void onClick(View v) {
////        switch (v.getId()) {
////            case R.id.qqLayout:
////                type = 1;
////                uploadHeadImage();
////                break;
////            case R.id.weixinLayout:
////                type = 2;
////                uploadHeadImage();
////                break;
////        }
////    }
//
//
//    /**
//     * 上传头像
//     */
//    private void uploadHeadImage() {
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
//        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
//        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
//        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
//        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(true);
//        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main_1, null);
//        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        //popupWindow在弹窗的时候背景半透明
//        final WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                params.alpha = 1.0f;
//                getWindow().setAttributes(params);
//            }
//        });
//
//        btnCarema.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //权限判断
//                if (ContextCompat.checkSelfPermission(Registered.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    //申请WRITE_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(Registered.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//                } else {
//                    //跳转到调用系统相机
//                    gotoCarema();
//                }
//                popupWindow.dismiss();
//            }
//        });
//        btnPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //权限判断
//                if (ContextCompat.checkSelfPermission(Registered.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    //申请READ_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(Registered.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
//                } else {
//                    //跳转到调用系统图库
//                    gotoPhoto();
//                }
//                popupWindow.dismiss();
//            }
//        });
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//    }
//
//    /**
//     * 跳转到相册
//     */
//    private void gotoPhoto() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
//    }
//
//
//    /**
//     * 跳转到照相机
//     */
//    private void gotoCarema() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
//        startActivityForResult(intent, REQUEST_CAPTURE);
//    }
//
//    /**
//     * 创建调用系统照相机待存储的临时文件
//     *
//     * @param savedInstanceState
//     */
//    private void createCameraTempFile(Bundle savedInstanceState) {
//        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
//            tempFile = (File) savedInstanceState.getSerializable("tempFile");
//        } else {
//            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"),
//                    System.currentTimeMillis() + ".jpg");
//        }
//    }
//
//    /**
//     * 检查文件是否存在
//     */
//    private static String checkDirPath(String dirPath) {
//        if (TextUtils.isEmpty(dirPath)) {
//            return "";
//        }
//        File dir = new File(dirPath);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        return dirPath;
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("tempFile", tempFile);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        switch (requestCode) {
//            case REQUEST_CAPTURE: //调用系统相机返回
//                if (resultCode == RESULT_OK) {
//                    gotoClipActivity(Uri.fromFile(tempFile));
//                }
//                break;
//            case REQUEST_PICK:  //调用系统相册返回
//                if (resultCode == RESULT_OK) {
//                    Uri uri = intent.getData();
//                    gotoClipActivity(uri);
//                }
//                break;
//            case REQUEST_CROP_PHOTO:  //剪切图片返回
//                if (resultCode == RESULT_OK) {
//                    final Uri uri = intent.getData();
//                    if (uri == null) {
//                        return;
//                    }
//                    String cropImagePath= getRealFilePathFromUri(getApplicationContext(), uri);
//                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
//                    if (type == 1) {
//                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//                        bitMap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
//                        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
//                        byte[] byteArray=byteArrayOutputStream.toByteArray();
//                        String imageString=new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
//                        //第三步:将String保持至SharedPreferences
//                        SharedPreferences sharedPreferences=getSharedPreferences("Head_portrait", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("image", imageString);
//                        editor.commit();
////                        headImage1.setImageBitmap(bitMap);
//                        String post = "{\"image\":\""+imageString+"\"}";
//                        RequestBody requestBody1 = RequestBody
//                                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//                        Request.Builder builder3 = new Request.Builder();
//                        Request request2 = builder3
//                                .url(url1+"postString")
//                                .post(requestBody1)
//                                .build();
//                        CallHttp(request2);
//                        registered_picture.setImageBitmap(bitMap);
//                    } else {
//                        headImage2.setImageBitmap(bitMap);
//                    }
//                    //此处后面可以将bitMap转为二进制上传后台网络
//                    //......
//
//                }
//                break;
//        }
//    }
//
//
//
//
//
//    /**
//     * 打开截图界面
//     *
//     * @param uri
//     */
//    public void gotoClipActivity(Uri uri) {
//        if (uri == null) {
//            return;
//        }
//        Intent intent = new Intent();
//        intent.setClass(this, ClipImageActivity.class);
//        intent.putExtra("type", type);
//        intent.setData(uri);
//        startActivityForResult(intent, REQUEST_CROP_PHOTO);
//    }
//
//
//    /**
//     * 根据Uri返回文件绝对路径
//     * 兼容了file:///开头的 和 content://开头的情况
//     *
//     * @param context
//     * @param uri
//     * @return the file path or null
//     */
//    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
//        if (null == uri) return null;
//        final String scheme = uri.getScheme();
//        String data = null;
//        if (scheme == null)
//            data = uri.getPath();
//        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
//            data = uri.getPath();
//        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
//            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
//            if (null != cursor) {
//                if (cursor.moveToFirst()) {
//                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                    if (index > -1) {
//                        data = cursor.getString(index);
//                    }
//                }
//                cursor.close();
//            }
//        }
//        return data;
    }


