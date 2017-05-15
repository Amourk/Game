package com.example.dtlp.user_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtlp.R;

public class main_search extends Activity {
private EditText editText_main_search;
    private Button back;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        back=(Button) findViewById(R.id.main_search_back);
        editText_main_search=(EditText)findViewById(R.id.main_search_text);
        editText_main_search.setFocusable(true);
        editText_main_search.setFocusableInTouchMode(true);
        editText_main_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // do something
                    Toast.makeText(main_search.this,"搜索成功",Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(main_search.this,MainActivity_2.class);
                setResult(00,intent);
                finish();

            }
        });
    }
}
