package com.phoenix.phoenixbase.test;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.phoenix.phoenixbase.R;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initFun();
    }

    private void init() {
        mImageView = (ImageView) findViewById(R.id.t_imageview);
    }

    private void initFun() {
        ImageHelper.getInstance().get("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png", mImageView);
    }
}
