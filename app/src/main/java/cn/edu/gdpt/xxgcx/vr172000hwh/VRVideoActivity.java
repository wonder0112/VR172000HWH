package cn.edu.gdpt.xxgcx.vr172000hwh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.vr.sdk.widgets.video.VrVideoView;

public class VRVideoActivity extends AppCompatActivity {
    //一.1 变量声明
    private VrVideoView mVrMainVideo;
    private SeekBar mSbMainProgress;
    private TextView mTvMainProgress;

    // 一、界面设计和初始化；二、全景视频播放；三、点击实现状态切换；四、显示进度信息，通过进度条控制视频
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrvideo);
        initView();//一.3 调用初始化函数
    }
    //一.2 初始化函数
    private void initView() {
        mVrMainVideo = (VrVideoView) findViewById(R.id.vr_main_video);
        mSbMainProgress = (SeekBar) findViewById(R.id.sb_main_progress);
        mTvMainProgress = (TextView) findViewById(R.id.tv_main_progress);
    }
}
