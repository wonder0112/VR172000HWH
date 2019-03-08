package cn.edu.gdpt.xxgcx.vr172000hwh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.InputStream;

public class VRPicActivity extends AppCompatActivity {
//1、初始化；2全景图片的加载和赋值给GoogleVR控件；3、完善
    private VrPanoramaView mVrMainPic;
    private  AsyncTask<Void,Void,Bitmap> task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrpic);
        initView();
        task =new LoadPicAsyncTask();
        task.execute();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mVrMainPic.resumeRendering();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mVrMainPic.pauseRendering();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVrMainPic.shutdown();
        if(task!=null){
            task.cancel(true);
            task=null;
        }
    }
    private void initView() {
        mVrMainPic = (VrPanoramaView) findViewById(R.id.vr_main_pic);
    }
    //定义异步任务内部函数，用于加载图片，赋值给GoogleVR控件
    private  class LoadPicAsyncTask extends AsyncTask<Void,Void,Bitmap>{
        //先执行预处理
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //doInBackground必须重写的方法，主要用于执行子线程（后台）的主要任务
        @Override
        protected Bitmap doInBackground(Void... voids) {
            try{
                InputStream is=getAssets().open("andes.jpg");
                Bitmap bitmap=BitmapFactory.decodeStream(is);
                is.close();
                return bitmap;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        //用于反馈任务执行的进度
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        //最后执行，一般用于跟UI打交道
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            VrPanoramaView.Options options=new VrPanoramaView.Options();//定义参数变量
            options.inputType=VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
            mVrMainPic.loadImageFromBitmap(bitmap,options);//将图片加载给UI控件
        }


    }

}
