package com.cafe.camera.activity;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

import com.cafe.camera.R;
import com.cafe.camera.entiy.ScrnSize;
import com.cafe.camera.util.BitmapTools;
import com.cafe.camera.util.DLogUtil;
import com.cafe.camera.util.DisplayUtil;
import com.cafe.camera.widget.CafeSurfaceView;
import com.cafe.camera.widget.CafeSurfaceView.TakenpicInterface;

public class MainActivity extends Activity implements TakenpicInterface,OnClickListener{

	/**
	 * 手机信息，包括宽高
	 */
	//预览界面
	private CafeSurfaceView camera_surface;
	private List<String> camera_colorfilter;
	private ImageView take_pic;
	private RelativeLayout mView;
	private ScrnSize screInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		screInfo = DisplayUtil.getScreenWidth(this);
		mView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_main, null);
		setContentView(mView);
		camera_surface = (CafeSurfaceView) findViewById(R.id.cafeSurfaceView);
		take_pic = (ImageView) findViewById(R.id.take_pic);
		take_pic.setOnClickListener(this);
		camera_surface.setInterface(this);
	}

	@Override
	public void take(byte[] data) {//监听返回图片
		DLogUtil.syso("activity take");
		mView.removeAllViews();
		ImageView imageView = new ImageView(this);
		imageView.setImageBitmap(BitmapTools.dataToBitmap(data));
		imageView.setScaleType(ScaleType.FIT_XY);
		setContentView(imageView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.take_pic:
			camera_surface.takPic();
			break;
		default:
			break;
		}
	}
}
