package com.cafe.camera.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * 图片操作工具类
 */
public final class BitmapTools {
	
	/**
	 * 图片反转
	 * @param b
	 * @param rotateDegree
	 * @return
	 */
	public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree){
		Matrix matrix = new Matrix();
		matrix.postRotate((float)rotateDegree);
		Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
		return rotaBitmap;
	}
	
	/**
	 * data to bitmap
	 * @param data
	 * @return
	 */
	public static Bitmap dataToBitmap(byte[] data){
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}
	/** 
	 *  处理图片  
	 * @param bm 所要转换的bitmap 
	 * @param newWidth新的宽 
	 * @param newHeight新的高   
	 * @return 指定宽高的bitmap 
	 */ 
	 public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){   
	    // 获得图片的宽高   
	    int width = bm.getWidth();   
	    int height = bm.getHeight();   
	    // 计算缩放比例   
	    float scaleWidth = ((float) newWidth) / width;   
	    float scaleHeight = ((float) newHeight) / height;   
	    // 取得想要缩放的matrix参数   
	    Matrix matrix = new Matrix();   
	    matrix.postScale(scaleWidth, scaleHeight);   
	    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);   
	    return newbm;   
	}  
}