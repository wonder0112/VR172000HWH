package cn.edu.gdpt.xxgcx.vr172000hwh;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class VRVideoActivity extends AppCompatActivity {
    //一.1 变量声明
    private VrVideoView mVrMainVideo;
    private SeekBar mSbMainProgress;
    private TextView mTvMainProgress;
    private AsyncTask<Void,Void,Void> task;

    // 一、界面设计和初始化；二、全景视频播放（视频congo.mp4->mVrMainVideo）；三、点击实现状态切换；四、显示进度信息，通过进度条控制视频
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrvideo);
        initView();//一.3 调用初始化函数
        task=new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                String fileName="congo.mp4";
                VrVideoView.Options options=new VrVideoView.Options();
                options.inputFormat=VrVideoView.Options.FORMAT_DEFAULT;
                options.inputType=VrVideoView.Options.TYPE_STEREO_OVER_UNDER;
                try {
                    mVrMainVideo.loadVideoFromAsset(fileName,options);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();
        mVrMainVideo.setTag(true);
        mVrMainVideo.setEventListener(new VrVideoEventListener(){
            @Override
            public void onClick() {
                super.onClick();
                boolean isPlay=(boolean)mVrMainVideo.getTag();
                if (isPlay) {
                    mVrMainVideo.pauseVideo();
                    isPlay=false;
                }else {
                    mVrMainVideo.playVideo();
                    isPlay=true;
                }
                mVrMainVideo.setTag(isPlay);
            }
            @Override
            public void onNewFrame() {
                super.onNewFrame();
                long duration=mVrMainVideo.getDuration();//视频长度，单位毫秒
                long currentPosition=mVrMainVideo.getCurrentPosition();//视频当前位置
                int percent=(int)(currentPosition*100f/duration+0.5f);//0-100
                mSbMainProgress.setMax(100);
                mSbMainProgress.setProgress(percent);
                mTvMainProgress.setText(percent+"%");
            }

            @Override
            public void onCompletion() {
                super.onCompletion();
                mSbMainProgress.setMax(100);
                mSbMainProgress.setProgress(100);
                mTvMainProgress.setText(100+"%");
                mVrMainVideo.seekTo(0);//实训循环播放
            }
            @Override
            public void onLoadSuccess() {
                super.onLoadSuccess();
                mSbMainProgress.setMax(100);
                mSbMainProgress.setProgress(0);
                mTvMainProgress.setText(0+"%");
            }
        });

        mSbMainProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    long duration=mVrMainVideo.getDuration();//视频长度  15000ms
                    //progress:0-100   20->0.2  3000ms
                    long newPosition=(long)(progress*0.01*duration);
                    mVrMainVideo.seekTo(newPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
    //一.2 初始化函数
    private void initView() {
        mVrMainVideo = (VrVideoView) findViewById(R.id.vr_main_video);
        mSbMainProgress = (SeekBar) findViewById(R.id.sb_main_progress);
        mTvMainProgress = (TextView) findViewById(R.id.tv_main_progress);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mVrMainVideo.resumeRendering();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mVrMainVideo.pauseRendering();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVrMainVideo.shutdown();
        if(task!=null){
            task.cancel(true);
            task=null;
        }
    }
}
