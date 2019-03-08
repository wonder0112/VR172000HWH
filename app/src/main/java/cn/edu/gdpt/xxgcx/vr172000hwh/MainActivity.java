package cn.edu.gdpt.xxgcx.vr172000hwh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //任务：一、初始化；二、Activity跳转
    //1、变量声明
    private ImageView mIvMainPic;
    private ImageView mIvMainVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//3、调用函数
        mIvMainPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),VRPicActivity.class);
                startActivity(intent);
            }
        });
        mIvMainVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),VRVideoActivity.class);
                startActivity(intent);
            }
        });

    }
    //初始化函数的定义
    private void initView() {
        mIvMainPic = (ImageView) findViewById(R.id.iv_main_pic);
        mIvMainVideo = (ImageView) findViewById(R.id.iv_main_video);
    }
}
