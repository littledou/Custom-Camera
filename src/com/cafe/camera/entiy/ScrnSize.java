package com.cafe.camera.entiy;

/**
 * 屏幕尺寸
 * @author cafe_wang
 */
public class ScrnSize {
	public int ScrnW_px,ScrnH_px;//获得的单位是px，px即pixels，是绝对像素，有多少个像素点就是多少个像素点。
	//dip即device independent pixel，设备独立像素，无像素无关。
	public float dpi;
	public int ScrnW_dip,ScrnH_dip;
	public ScrnSize(int ScrnW,int ScrnH,float dpi){
		this.dpi = dpi;
		this.ScrnW_px = ScrnW;
		this.ScrnH_px = ScrnH;
		ScrnW_dip = (int) (ScrnW_px/dpi+0.5f);
		ScrnH_dip = (int) (ScrnH_px/dpi+0.5f);
	}
	@Override
	public String toString() {
		return "ScrnSize [ScrnW_px=" + ScrnW_px + ", ScrnH_px=" + ScrnH_px
				+ ", dpi=" + dpi + ", ScrnW_dip=" + ScrnW_dip + ", ScrnH_dip="
				+ ScrnH_dip + "]";
	}
	
}
