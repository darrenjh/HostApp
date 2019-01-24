package com.yang.hostapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.didi.virtualapk.PluginManager;
import com.yang.hostapp.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int PERMISSION_REQUEST_CODE_STORAGE = 20171222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setListener(mOnClickListener);
        if(hasPermission()){
            loadPlugin(this);
        }else{
            requestPermission();
        }
        ARouter.getInstance().inject(this);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_STORAGE);
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PERMISSION_REQUEST_CODE_STORAGE == requestCode) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                this.loadPlugin(this);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_start_plug1:
                    final String pkg = "com.yang.plug1";
                    if (PluginManager.getInstance(MainActivity.this).getLoadedPlugin(pkg) == null) {
                        Toast.makeText(MainActivity.this, "没有安装插件app", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClassName(MainActivity.this, "com.yang.plug1.Plugin1Activity");
                    startActivity(intent);
                    break;
                case R.id.btn_start_router:
                    ARouter.getInstance().build("/test/1").navigation();
                    break;
                default:
                    break;
            }
        }
    };

    private void loadPlugin(final Context base) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(MainActivity.this, "sdcard was NOT MOUNTED!", Toast.LENGTH_SHORT).show();
        }
        String apkName="TestPlug1.apk";
        PluginManager pluginManager = PluginManager.getInstance(base);
        File apk = new File(Environment.getExternalStorageDirectory(), apkName);
        if (apk.exists()) {
            try {
                pluginManager.loadPlugin(apk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                File file = new File(base.getFilesDir(), apkName);
                java.io.InputStream inputStream = base.getAssets().open(apkName, 2);
                java.io.FileOutputStream outputStream = new java.io.FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;

                while ((len = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.close();
                inputStream.close();
                pluginManager.loadPlugin(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
