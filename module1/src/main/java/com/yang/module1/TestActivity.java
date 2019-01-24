package com.yang.module1;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yang.module1.databinding.ActivityTestBinding;

@Route(path="/test/1")
public class TestActivity extends AppCompatActivity {
    ActivityTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_test);
        ARouter.getInstance().inject(this);
    }
}
