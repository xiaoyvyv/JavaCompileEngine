package com.xiaoyv.javaengine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.tools.r8.D8;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ThreadUtils;

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
//        Intent intent = new Intent(this, SingleSampleActivity.class);
//        startActivity(intent);
    }

    public void openGithub(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("https://github.com/xiaoyvyv/JavaCompileEngine"));
        startActivity(intent);
    }

    public void testR8(View view) {
        ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Boolean>() {
            @Override
            public Boolean doInBackground() throws Throwable {
                String classFile = PathUtils.getExternalAppFilesPath() + "/Main.class";
                LogUtils.e("start", classFile);


                String[] command = new String[]{classFile, "--lib", "JavaEngineSetting.getRtPath()",
                        "--output", PathUtils.getExternalAppFilesPath()};
                D8.main(command);
                return null;
            }

            @Override
            public void onSuccess(Boolean result) {
                LogUtils.e("success");
            }
        });
    }
}
