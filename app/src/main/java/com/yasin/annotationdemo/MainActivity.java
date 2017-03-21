package com.yasin.annotationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yasin.annotationdemo.annotation.AnnoUtils;
import com.yasin.annotationdemo.annotation.OnClick;
import com.yasin.annotationdemo.annotation.ViewInject;

public class MainActivity extends Activity {
    @ViewInject(R.id.id_tv_hello)
    private TextView mTextView;
    @ViewInject(R.id.id_tv_hello2)
    private TextView mTextView2;
    @ViewInject(R.id.id_tv_hello3)
    private TextView mTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开始给view注入
        AnnoUtils.inject(this);
        mTextView.setText("hello yasin!");
        mTextView2.setText("hello yasin2!");
        mTextView3.setText("hello yasin3!");
    }
    @OnClick({R.id.id_tv_hello,R.id.id_tv_hello2,R.id.id_tv_hello3})
    public void onClick(View view){
        TextView tv= (TextView) view;
        Toast.makeText(this,tv.getText().toString(),Toast.LENGTH_SHORT).show();
    }
}
