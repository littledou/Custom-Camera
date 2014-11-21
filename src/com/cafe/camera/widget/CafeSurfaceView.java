package com.cafe.camera.widget;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cafe.camera.R;
import com.cafe.camera.entiy.ScrnSize;
import com.cafe.camera.util.CamParaUtil;
import com.cafe.camera.util.DLogUtil;
import com.cafe.camera.util.DisplayUtil;

public class CafeSurfaceView extends RelativeLayout implements SurfaceHolder.Callback,Camera.PictureCallback{

	private final static String TAG = "CafeSurfaceView";
	private SurfaceHolder surfaceHolder;
	private SurfaceView surfaceView;
	private Camera camera;
	private List<String> camera_colorfilter;//拍照自带滤镜效果
	private ScrnSize screInfo;
	float previewRate = -1f;
	

	private Context context;
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	public CafeSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		screInfo = DisplayUtil.getScreenWidth(context);
		View mView = LayoutInflater.from(context).inflate(R.layout.cafe_photo_view, null) ;
		android.view.ViewGroup.LayoutParams lp = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		this.addView(mView, lp);
		surfaceView = (SurfaceView) mView.findViewById(R.id.camera_surface);
		surfaceView.setFocusable(true);//可自动聚焦的
		surfaceView.setFocusableInTouchMode(true);
		surfaceView.setClickable(true);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceHolder.addCallback(this);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
		camera_colorfilter = initCameraColor();
		//预览横屏解决
		try {
			Camera.Parameters parameters = camera.getParameters();
			parameters.setPictureFormat(PixelFormat.JPEG);//设置拍照后存储的图片格式
			//设置PreviewSize和PictureSize
			float h = screInfo.ScrnH_px;
			float w = screInfo.ScrnW_px;
			previewRate = h/w;
			//照片尺寸预览尺寸适应当前屏幕显示尺寸
			Size pictureSize = CamParaUtil.getInstance().getPropPictureSize(parameters.getSupportedPictureSizes(),previewRate, screInfo.ScrnW_px);
			parameters.setPictureSize(pictureSize.width, pictureSize.height);
			Size previewSize = CamParaUtil.getInstance().getPropPreviewSize(parameters.getSupportedPreviewSizes(), previewRate, screInfo.ScrnW_px);
			parameters.setPreviewSize(previewSize.width, previewSize.height);
			List<String> focusModes = parameters.getSupportedFocusModes();
			if(focusModes.contains("continuous-video")){
				parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			}
			if(surfaceView.getResources().getConfiguration().orientation!=
					Configuration.ORIENTATION_LANDSCAPE){
				parameters.set("orientation", "portrait");
				//对于android2。2及以上版本
				camera.setDisplayOrientation(90);
				//对于android2.2及以上取消注释
				parameters.setRotation(90);
			}else {
				parameters.set("orientation", "landscape");
				camera.setDisplayOrientation(0);
				parameters.setRotation(0);
			}
			camera.setParameters(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			camera.setPreviewDisplay(holder);
			//设置预览大小
			camera.startPreview();
		} catch (Exception e) {
			camera.release();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera.release();
	}

	/**
	 * camera自带的图片拍摄效果
	 * @return 
	 */
	private List<String> initCameraColor(){
		Camera.Parameters parameters = camera.getParameters();
		return parameters.getSupportedColorEffects();
	}

	public void onPictureTaken(){
		camera.autoFocus(mAutoFocusCallback);//自动对焦
	}
	Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			if(success){
				DLogUtil.syso("focu success ");
			}
		}
	};
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		//拍照回调
		interface1.take(data);
	}
	private TakenpicInterface interface1;
	public void setInterface(TakenpicInterface interface1){
		this.interface1 = interface1;
	}
	public interface TakenpicInterface{
		public void take(byte[] data);
	}
	public void takPic(){
		camera.takePicture(mShutterCallback, null, CafeSurfaceView.this);
	}
	/*为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量*/
	ShutterCallback mShutterCallback = new ShutterCallback() {
		//快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
		public void onShutter() {
		}
	};
	
	
}
