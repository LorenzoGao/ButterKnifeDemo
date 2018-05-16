package com.lorenzogao.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.butterknife.annotations.BindView;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.tv1)
    TextView tv3;
    @BindView(R.id.tv2)
    TextView tv4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  LoginActivity_ViewBinding loginActivity_viewBinding=new LoginActivity_ViewBinding(this);

         //textView1.setText("textView1");

    }
}
