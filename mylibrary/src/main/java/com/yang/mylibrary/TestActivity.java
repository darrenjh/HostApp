package com.yang.mylibrary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yang.mylibrary.databinding.ActivityTestBinding;

/**
 * Created by yang on 2019/01/23.
 */
@Route(path = "/test/1")
public class TestActivity extends AppCompatActivity {
    ActivityTestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_test);
    }
}
