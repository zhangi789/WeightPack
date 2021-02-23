package com.dnxc.cn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zhy.widget.CircleIndicatorView;

public class MainActivity extends AppCompatActivity {

    CircleIndicatorView mCircleIndicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleIndicatorView=findViewById(R.id.mm);
        mCircleIndicatorView.setTotalSize(4);
        mCircleIndicatorView.setCurrentPostion(1);
    }
}