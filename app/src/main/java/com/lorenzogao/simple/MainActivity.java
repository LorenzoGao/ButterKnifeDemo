package com.lorenzogao.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.butterknife.ButterKnife;
import com.butterknife.Unbinder;
import com.butterknife.annotations.BindView;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv2)
    TextView tv3;





    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder=  ButterKnife.bind(this);



        tv1.setText("butterKnifeDemo1");
//        tv2.setText("butterKnifeDemo2");

    }




    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();

    }
}
