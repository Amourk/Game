package com.example.dtlp.Enter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dtlp.R;
import com.example.dtlp.User.user;


/**
 * Created by 阳瑞 on 2017/3/23.
 */
public class ForgotPassword extends Activity {
    private EditText number,change_password,sure_change_password;
    private Button sure_modify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        initview();
    }

    public void doClick(View view)
    {
        switch (view.getId())
        {
            case R.id.Sure_Modify:

                break;
        }
    }

    private void initview() {
        number = (EditText) findViewById(R.id.Number);
        change_password = (EditText) findViewById(R.id.Chang_Password);
        sure_change_password = (EditText) findViewById(R.id.Sure_Chang_Password);
        sure_modify = (Button) findViewById(R.id.Sure_Modify);
    }
    public void mofify()
    {
        String new_password = change_password.getText().toString();
        user user =new user();
        user.setValue("number",new_password);
//        user.update()
    }
}
