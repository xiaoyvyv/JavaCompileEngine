package com.xiaoyv.javaengine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xiaoyv.javaengine.compile.listener.CompilerListener;
import com.xiaoyv.javaengine.compile.listener.ExecuteListener;
import com.xiaoyv.javaengine.console.JavaConsole;
import com.xiaoyv.javaengine.executor.DexExecutor;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        setSupportActionBar(toolbar);

        initEvent();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
    }

    private void initEvent() {

    }


    public void openSingleActivity(View view) {
        Intent intent = new Intent(this, SingleSampleActivity.class);
        startActivity(intent);
    }

    public void openGithub(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("https://github.com/xiaoyvyv/JavaCompileEngine"));
        startActivity(intent);
    }
}
