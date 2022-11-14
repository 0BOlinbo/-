package com.example.zuoye;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class second extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这个是获取布局文件的，这里是你下一个页面的布局文件//注意这个是跳转界面的不能设置错，应该是第一个
        setContentView(R.layout.activity_second);
    }
}
