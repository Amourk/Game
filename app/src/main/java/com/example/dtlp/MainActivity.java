package com.example.dtlp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtlp.Date.DateOperation;
import com.example.dtlp.Date.User;
import com.example.dtlp.Enter.ForgotPassword;
import com.example.dtlp.Enter.Registered;
import com.example.dtlp.user_main.MainActivity_2;
import com.example.dtlp.view.CircleImageView;
import com.google.gson.Gson;

import java.io.IOException;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends FragmentActivity {

    private CircleImageView qq;
    private TextView registered,forgotpassword;
    private EditText account,password;
    private Button login;

    private String APPKEY ="1c3678b10a353";
    private String APPSECRETE="63787b577405c5d08b466e609661a843";
    private String KEY = "31704cfaa4b17e8f90c7dac59dbec4dd";


    public static MainActivity instance = null;

    public static String  PASSWORD;
    public boolean FLAG = false;
    DateOperation dateOperation = new DateOperation();

    OkHttpClient okHttpClient = new OkHttpClient();
    private String url1 = "http://192.168.0.116:8080/TotemDown/LoginServe?username=linyuanbin&password=123456";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        instance = this;


        Bmob.initialize(this,KEY);
        //1.初始化sdk
        SMSSDK.initSDK(this,APPKEY,APPSECRETE);
        //2.到清单文件中配置信息 （添加网络相关权限以及一个activity信息）

        initView();
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                password.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public void doClick(View view)
    {
        switch (view.getId())
        {
            case R.id.registered:
                Intent intent = new Intent(MainActivity.this,Registered.class);
                startActivity(intent);
                break;
            case R.id.Login:
                if (password.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "请输入密码！！", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    login();
                }
                break;
            case R.id.ForgotPassword:
                Intent intent1 = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(intent1);
                break;
//            case R.id.registered:
////                registered();
//                RegisterPage registerPage=new RegisterPage();
//                //注册回调事件
//                registerPage.setRegisterCallback(new EventHandler(){
//                    @Override
//                    //事件完成后
//                    public void afterEvent(int event, int result, Object data) {
//                        //判断结果是否已经完成
//                        if(result==SMSSDK.RESULT_COMPLETE){//解析完成
//                            //获取数据data
//                            HashMap<String,Object> maps= (HashMap<String, Object>) data;//数据强转
//                            //国家
//                            String country= (String) maps.get("country");
//                            //手机号码
//                            String phone= (String) maps.get("phone");
//                            submitUserInfo(country,phone);
//                        }
//                        Intent intent = new Intent(MainActivity.this,Registered.class);
//                        startActivity(intent);
//                    }
//                });
//                //显示注册界用下载的inde.xml文档中的show()方法
//                registerPage.show(MainActivity.this);
        }
    }
//    新建提交方法 提交用户信息到服务器在监听中返回结果
//    public void submitUserInfo(String country,String phone){
//        Random r=new Random();//获得一个随机数
//        String uid=Math.abs(r.nextInt())+"";
//        String nickName="MyApp";
//        SMSSDK.submitUserInfo(uid,nickName,null,country,phone);
//    }
    private void initView(){
        qq = (CircleImageView) findViewById(R.id.iv_bottom);
        registered = (TextView) findViewById(R.id.registered);
        forgotpassword = (TextView) findViewById(R.id.ForgotPassword);
        account = (EditText) findViewById(R.id.Account);
        password = (EditText) findViewById(R.id.Password_);
        login = (Button) findViewById(R.id.Login);
    }
    private void login()
    {
//        String Account = account.getText().toString();
//        BmobQuery<user> query = new BmobQuery<>();
//        query.addWhereEqualTo("number",Account);
//        query.findObjects(new FindListener<user>() {
//            @Override
//            public void done(List<user> list, BmobException e)
//            {
//                if (e==null)
//                {
//                    for (user user : list)
//                    {
//                        PASSWORD = user.getPassword();
//                        if (password.getText().toString().equals(PASSWORD))
//                        {
//                            Toast.makeText(MainActivity.this, "登录成功！！", Toast.LENGTH_SHORT).show();
//                            Intent intent1 = new Intent(MainActivity.this,MainActivity_2.class);
//                            startActivity(intent1);
//                            MainActivity.this.finish();
//                        }
//                        else
//                        {
//                            Toast.makeText(MainActivity.this, "密码输入错误！！", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "密码或者账号不对哦！！", Toast.LENGTH_SHORT).show();
//                }
//                if (list.size() == 0)
//                {
//                    Toast.makeText(MainActivity.this, "密码或者账号不对哦！！", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        String aco = account.getText().toString();
        String pass = password.getText().toString();
        String post ="{\"state\":\"login\",\"UserName\":\""+aco+"\",\"UserPassword\":\""+pass+"\"}";
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(url1)
                .post(requestBody1)
                .build();
        CallHttp(request2);
        Log.i("info", "post = " + post);

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
                        Gson gson = new Gson();
                        User user = gson.fromJson(res, User.class);
//                        user.getState();
                        if ( user.getState().equals("true"))
                        {
//                            Log.i("info", "res  = " + res.toString());
                            Toast.makeText(MainActivity.this, "登录成功！！", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(MainActivity.this, MainActivity_2.class);
                            startActivity(intent1);
                            MainActivity.this.finish();
                        }//得到的res为用户ID  保存到本地
                        else
                            Toast.makeText(MainActivity.this, "登录失败了哦！！！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
