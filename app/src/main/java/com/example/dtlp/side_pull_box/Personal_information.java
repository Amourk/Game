package com.example.dtlp.side_pull_box;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtlp.R;
import com.example.dtlp.side_pull_box.view1.SelectAddressDialog;
import com.example.dtlp.side_pull_box.view1.myinterface.SelectAddressInterface;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 阳瑞 on 2017/3/29.
 */
public class Personal_information extends Activity implements SelectAddressInterface{

    private LinearLayout nickname,sex,birthday,major,where,email,number;
    private TextView sex_,nickname_,birthday_,major_,where_,email_,number_,name;
    private EditText personal_information_name_text;

    public static final String REGEX_EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    private Pattern pattern = Pattern.compile(REGEX_EMAIL);
    private Matcher matcher;

    private SelectAddressDialog dialog;

    private String url1 = "http://192.168.0.101:8080/TotemDown/LoginServe?username=linyuanbin&password=123456";

    OkHttpClient okHttpClient = new OkHttpClient();

    String bir = "";
    String whe ="";
    String se = "";
    String nick = "";
    String maj = "";
    String ema = "";
    boolean isCheckOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_pull_box_personal_information);
        initview();
        display();
    }

    private void display() {
        SharedPreferences sp = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
        nickname_.setText(sp.getString("nickname",""));
        SharedPreferences sp1 = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
        major_.setText(sp1.getString("major",""));
        SharedPreferences sp2 = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
        email_.setText(sp2.getString("email",""));
        SharedPreferences sp3 = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
        birthday_.setText(sp3.getString("birthday",""));
        SharedPreferences sp4 = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
        sex_.setText(sp4.getString("sex",""));
        SharedPreferences sp5 = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
        where_.setText(sp5.getString("where",""));

    }
    private void  TP()
    {
//        String post ="{\"UserBirthday\":\""+bir+"\"}";
//        RequestBody requestBody1 = RequestBody
//                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//        Request.Builder builder1 = new Request.Builder();
//        Request request1 = builder1
//                .url(url1+"postString")
//                .post(requestBody1)
//                .build();
//        CallHttp(request1);



//        String post1 ="{\"where\":\""+whe+"\"}";
        String post1 ="{\"state\":\"update\",\"UserID\":\"Thu Apr 27 18:42:02 CST 20170uv4k\",\"UserNickName\":\""+nick+"\",\"UserSex\":\""+se + "\",\"UserBirthday\":\""+bir + "\"" +
                ",\"UserMajor\":\""+maj+"\",\"UserEmail\":\""+ema+"\"}";
        RequestBody requestBody2 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post1);
        Request.Builder builder2 = new Request.Builder();
        Request request2 = builder2
                .url(url1)
                .post(requestBody2)
                .build();
        CallHttp(request2);

//        String post2 ="{\"UserSex\":\""+se+"\"}";
//        RequestBody requestBody3 = RequestBody
//                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post2);
//        Request.Builder builder3 = new Request.Builder();
//        Request request3 = builder3
//                .url(url1+"postString")
//                .post(requestBody3)
//                .build();
//        CallHttp(request3);

//        String post3 ="{\"UserNickName\":\""+nick+"\"}";
//        RequestBody requestBody4 = RequestBody
//                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post3);
//        Request.Builder builder4 = new Request.Builder();
//        Request request4 = builder4
//                .url(url1+"postString")
//                .post(requestBody4)
//                .build();
//        CallHttp(request4);


//        String post4 ="{\"UserMajor\":\""+maj+"\"}";
//        RequestBody requestBody5 = RequestBody
//                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post4);
//        Request.Builder builder5 = new Request.Builder();
//        Request request5 = builder5
//                .url(url1+"postString")
//                .post(requestBody5)
//                .build();
//        CallHttp(request5);


//        String post5 ="{\"UserEmail\":\""+ema+"\"}";
//        RequestBody requestBody6 = RequestBody
//                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post5);
//        Request.Builder builder6 = new Request.Builder();
//        Request request6 = builder6
//                .url(url1+"postString")
//                .post(requestBody6)
//                .build();
//        CallHttp(request6);


    }

    private void initview() {
        nickname = (LinearLayout) findViewById(R.id.nickName);
        sex = (LinearLayout) findViewById(R.id.sex);
        birthday = (LinearLayout) findViewById(R.id.birthday);
        major = (LinearLayout) findViewById(R.id.major);
        where = (LinearLayout) findViewById(R.id.where);
        email = (LinearLayout) findViewById(R.id.email);
        number = (LinearLayout) findViewById(R.id.number);

        where_ = (TextView) findViewById(R.id.where_);
        sex_ = (TextView) findViewById(R.id.sex_);
        nickname_ = (TextView) findViewById(R.id.nickName_);
        birthday_ = (TextView) findViewById(R.id.birthday_);
        major_ = (TextView) findViewById(R.id.major_);
        email_ = (TextView) findViewById(R.id.email_);
        number_ = (TextView) findViewById(R.id.email_);
        name = (TextView) findViewById(R.id.name);
    }

    public void doClick(View view) {

        switch (view.getId())
        {
            case R.id.nickName:

                personal_information_name_Dialog();

                break;
            case R.id.sex:
                personal_information_sex_Dialog();
                break;
            case R.id.birthday:
                new DatePickerDialog(Personal_information.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        isCheckOn = true;
                         bir = String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth);
//                        String post ="{\"birthday\":\""+bir+"\"}";
//                        RequestBody requestBody1 = RequestBody
//                                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//                        Request.Builder builder3 = new Request.Builder();
//                        Request request2 = builder3
//                                .url(url1+"postString")
//                                .post(requestBody1)
//                                .build();
//                        CallHttp(request2);


                        SharedPreferences msharedPreferences = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor  = msharedPreferences.edit();
                        editor.putString("birthday",String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth));
                        editor.commit();
                        birthday_.setText(String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth));
                    }
                },2000,1,2).show();
                break;
            case R.id.major:
                personal_information_major_Dialog();
                break;
            case R.id.where:
                if (dialog == null) {
                    dialog = new SelectAddressDialog(Personal_information.this,
                            Personal_information.this,SelectAddressDialog.STYLE_TWO,null);
                }
                dialog.showDialog();
                break;
            case R.id.email:
                    personal_information_email_Dialog();
                break;
        }
    }


    @Override
    public void setAreaString(String area) {
        isCheckOn = true;
        where_.setText(area);
        whe = area;
//        String post ="{\"where\":\""+area+"\"}";
//        RequestBody requestBody1 = RequestBody
//                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//        Request.Builder builder3 = new Request.Builder();
//        Request request2 = builder3
//                .url(url1+"postString")
//                .post(requestBody1)
//                .build();
//        CallHttp(request2);

        SharedPreferences msharedPreferences = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = msharedPreferences.edit();
        editor.putString("where",area);
        editor.commit();
    }
    /**
     * 显示单选对话框
     *
     * @param
     */
    public void personal_information_sex_Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] items = new String[]{"男", "女",};
         final String[] sex1 = new String[1];
        builder.setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {/*设置单选条件的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    sex1[0] = items[which];
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (!sex1[0].toString().equals("")) {

                    se = sex1[0];
//                    String post ="{\"state\":\"update\",\"UserID\":\"Thu Apr 27 18:42:02 CST 20170uv4k\",\"sex\":\""+sex1[0]+"\"}";
//                    RequestBody requestBody1 = RequestBody
//                            .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//                    Request.Builder builder3 = new Request.Builder();
//                    Request request2 = builder3
//                            .url(url1)
//                            .post(requestBody1)
//                            .build();
//                    CallHttp(request2);

                    SharedPreferences msharedPreferences = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor  = msharedPreferences.edit();
                    editor.putString("sex",sex1[0]);
                    editor.commit();
                    sex_.setText(sex1[0]);
                    isCheckOn = true;
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void personal_information_name_Dialog()
    {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.side_pull_box_personal_information_name,
                (ViewGroup) findViewById(R.id.personal_information_name));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText personal_information_name_text = (EditText) layout.findViewById(R.id.personal_information_name_text);
                if (!personal_information_name_text.getText().toString().equals(""))
                {
                    String name = personal_information_name_text.getText().toString();
                    if (!validateName(name))
                    {
                        Toast.makeText(Personal_information.this, "亲 名字太长了哦！！！", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        nick = personal_information_name_text.getText().toString();
//                        String post ="{\"nickname\":\""+nickname+"\"}";
//                        RequestBody requestBody1 = RequestBody
//                                     .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//                        Request.Builder builder3 = new Request.Builder();
//                        Request request2 = builder3
//                              .url(url1+"postString")
//                              .post(requestBody1)
//                              .build();
//                        CallHttp(request2);

                        SharedPreferences msharedPreferences = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = msharedPreferences.edit();
                        editor.putString("nickname", personal_information_name_text.getText().toString());
                        editor.commit();
                        nickname_.setText(personal_information_name_text.getText().toString());
                        isCheckOn = true;
                    }
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void personal_information_major_Dialog()
    {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.side_pull_box_personal_information_major,
                (ViewGroup) findViewById(R.id.personal_information_major));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText personal_information_major_text = (EditText) layout.findViewById(R.id.personal_information_major_text);
                if (!personal_information_major_text.getText().toString().equals("")) {

                    maj = personal_information_major_text.getText().toString();
//                    String post ="{\"major\":\""+maj+"\"}";
//                    RequestBody requestBody1 = RequestBody
//                            .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//                    Request.Builder builder3 = new Request.Builder();
//                    Request request2 = builder3
//                            .url(url1+"postString")
//                            .post(requestBody1)
//                            .build();
//                    CallHttp(request2);

                    SharedPreferences msharedPreferences = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor  = msharedPreferences.edit();
                    editor.putString("major",personal_information_major_text.getText().toString());
                    editor.commit();
                    major_.setText(personal_information_major_text.getText().toString());
                    isCheckOn = true;
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    public void personal_information_email_Dialog()
    {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.side_pull_box_personal_information_email,
                (ViewGroup) findViewById(R.id.personal_information_email));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText personal_information_email_text = (EditText) layout.findViewById(R.id.personal_information_email_text);
                if (!personal_information_email_text.getText().toString().equals("")) {
                    String email =personal_information_email_text.getText().toString();
                    if (!validateEmail(email))
                    {
                        Toast.makeText(Personal_information.this, "亲 请输入正确的邮箱格式哦！！！", Toast.LENGTH_SHORT).show();
                    }else {

                         ema = personal_information_email_text.getText().toString();
//                        String post ="{\"email\":\""+ema+"\"}";
//                        RequestBody requestBody1 = RequestBody
//                                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//                        Request.Builder builder3 = new Request.Builder();
//                        Request request2 = builder3
//                                .url(url1+"postString")
//                                .post(requestBody1)
//                                .build();
//                        CallHttp(request2);

                        SharedPreferences msharedPreferences = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor  = msharedPreferences.edit();
                        editor.putString("email",personal_information_email_text.getText().toString());
                        editor.commit();
                        email_.setText(personal_information_email_text.getText().toString());
                        isCheckOn = true;
                    }
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
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
    public boolean validateEmail(String email)
    {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean validateName(String name)
    {
        return name.length()<7;
    }


    @Override
    protected void onDestroy() {
        if (isCheckOn)
        {
//            TP();
            Log.i("info", "post = ");
        }
        super.onDestroy();
    }
}
