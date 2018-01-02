package com.xiaxiao.xiaoandroid.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: BitmapUtil
 * @Description: Bitmap工具类
 * @author <xingyong>xingyong@cy2009.com
 * @date 2012-5-24 下午15:14:02
 */
public class BitmapUtil {
	/** 用来标识请求照相功能的activity */
	public static final int CAMERA_WITH_DATA = 3023;
	/** 用来标识请求gallery的activity */
	public static final int PHOTO_PICKED_WITH_DATA = 3021;
	/** 头像存放目录 */
	public static final String HEAD_IMAGE_PATH = Environment
			.getExternalStorageDirectory().getPath() + "/gameplatform/head/";
	
	/**
	 * 将Bitmap转化为字节数组
	 * @param bitmap  源图片
	 * @return byte[] 字节数组
	 */
	public static byte[] bitampToByteArray(Bitmap bitmap) {
		byte[] array = null;
		try {
			if (null != bitmap) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
				array = os.toByteArray();
				os.close();
			}
		} catch (IOException e) {
			return array;
		}
		return array;
	}
	
	/**
	 * 拍照功能
	 * 
	 * @param myActivity
	 */
	public static void doTakePhoto(Activity myActivity){
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File file = getTempFileFromCamera();
			intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
				myActivity.startActivityForResult(intent,
					CAMERA_WITH_DATA);
			}else{
				Toast.makeText(myActivity,"not_have_sdcard", Toast.LENGTH_LONG).show();
			}
		} catch (ActivityNotFoundException e) {
			Toast.makeText(myActivity, "no find", Toast.LENGTH_LONG).show();
		}
	}

	public static File getTempFileFromCamera(){
		File distFile = new File(HEAD_IMAGE_PATH + "temp.jpg");
		if (!distFile.getParentFile().exists())
			distFile.getParentFile().mkdirs();
		return distFile;
	}

	/**
	 * 请求相册程序
	 *
	 * @param myActivity
	 */
	public static void doPickPhotoFromGallery(Activity myActivity) {
		try {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			myActivity.startActivityForResult(intent,
					PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(myActivity, "no find", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 裁剪照片
	 *
	 * @param photo
	 * @param myActivity
	 *//*
	public static void doCropPhoto(Bitmap photo, Activity myActivity) {

		// 将选择的图片等比例压缩后缓存到存储卡根目录，并返回图片文件
		File f = CropUtil.makeTempFile(photo, "temp.jpg");
		// 调用CropImage类对图片进行剪切
		Intent intent = new Intent(myActivity, CropImage.class);
		Bundle extras = new Bundle();
		extras.putString("circleCrop", "true");
		extras.putInt("aspectX", 200);
		extras.putInt("aspectY", 200);
		intent.setDataAndType(Uri.fromFile(f), "image/jpeg");
		intent.putExtras(extras);
		myActivity.startActivityForResult(intent, C.PICKED_WITH_DATA);
	}*/

	/**
	 * 将字节数组转化为Bitmap
	 * @param array 源字节数组
	 * @return Bitmap 图片
	 */
	public static Bitmap byteArrayToBitmap(byte[] array) {
		if (null == array) {
			return null;
		}
		return BitmapFactory.decodeByteArray(array, 0, array.length);
	}

	/**
	 * 获取圆角bitmap,RCB means Rounded Corner Bitmap
	 * @param bitmap
	 * @param roundPX
	 * @return
	 */
	public static Bitmap getRCB(Bitmap bitmap, float roundPX) {
		if (bitmap == null || roundPX <= 0) {
			return bitmap;
		}
		Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(dstbmp);
		Paint paint = new Paint();
		RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		Path mPath = new Path();
		float[] mCorner = new float[] { roundPX, roundPX, roundPX, roundPX,roundPX, roundPX, roundPX, roundPX};
		mPath.addRoundRect(rectF, mCorner, Path.Direction.CW);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawPath(mPath, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return dstbmp;
	}


	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 将指定图片存储到指定路径中
	 *
	 * @param savePath
	 *            待存储路径
	 * @param bit
	 *            源图数据
	 * @param quality
	 *            质量
	 */
	public static void savePic(String savePath, Bitmap bit, int quality) {
		if (savePath == null)
			return;
		File file1 = new File(savePath);

		if (!file1.getParentFile().exists())
			file1.getParentFile().mkdirs();

		FileOutputStream bitmapWriter = null;
		try {
			bitmapWriter = new FileOutputStream(file1);
			if (bit.compress(Bitmap.CompressFormat.JPEG, quality, bitmapWriter)) {
				bitmapWriter.flush();
			}
		} catch (Exception e) {
		} catch (OutOfMemoryError error) {
		} finally {
			try {
				if (bitmapWriter != null) {
					bitmapWriter.close();
					bitmapWriter = null;
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 根据指定的宽和高进行缩放
	 *
	 * @param bitmap
	 *            原图
	 * @param w
	 *            待缩放的宽度
	 * @param h
	 *            待缩放的高度
	 * @return 返回缩放后的图像或是原图
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, float w, float h) {
		if (bitmap == null)
			return null;
		Bitmap output = null;
		Matrix matrix = null;
		try {
			// 图像宽和高,不符合条件时返回原图
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			if (width <= 0 || height <= 0)
				return bitmap;

			float scaleWidth = w / width;
			float scaleHeight = h / height;
			matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			output = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
					true);
		} catch (Exception e) {
			output = bitmap;
		} catch (OutOfMemoryError errory) {
			output = bitmap;
		} finally {
			matrix = null;
		}
		return output;
	}

	/**
	 * 根据指定的宽和高进行缩放
	 *
	 * @param bitmap
	 *            原图
	 * @param w
	 *            待缩放的宽度
	 * @param h
	 *            待缩放的高度
	 * @return 返回缩放后的图像或是原图
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
		return resizeBitmap(bitmap, (float) w, (float) h);
	}

	/**
	 * 图片加边框
	 *
	 * @param bmpOriginal
	 *            原图
	 * @param borderWidth
	 *            边框尺寸
	 * @param borderColor
	 *            边框颜色
	 * @return 返回修改后的图片
	 */
	public static Bitmap toBorder(Bitmap bmpOriginal, int borderWidth,
								  int borderColor) {
		if (bmpOriginal == null)
			return null;
		if (borderWidth < 0)
			return bmpOriginal;
		Bitmap ouput = null;
		try {
			ouput = Bitmap.createBitmap(bmpOriginal.getWidth(),
					bmpOriginal.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(ouput);
			// 画边框
			Rect rec = canvas.getClipBounds();
			rec.bottom--;
			rec.right--;
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			// 设置边框颜色
			paint.setColor(borderColor);
			paint.setStyle(Paint.Style.STROKE);
			// 设置边框宽度
			paint.setStrokeWidth(borderWidth);
			canvas.drawBitmap(bmpOriginal, 0, 0, paint);
			canvas.drawRect(rec, paint);
		} catch (Exception e) {
			ouput = bmpOriginal;
		} catch (OutOfMemoryError e) {
			ouput = bmpOriginal;
		}

		return ouput;
	}
	public static Bitmap getThumbnail(File file, Activity act) throws FileNotFoundException, IOException {
//		int degree = ImageUtil.readPicDegree(file.getAbsolutePath());
		InputStream input = new FileInputStream(file);
		int size = getXY(act)[0] > getXY(act)[1] ? getXY(act)[1] : getXY(act)[0];
		BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
		onlyBoundsOptions.inJustDecodeBounds = true;
		onlyBoundsOptions.inDither = true;// optional
		onlyBoundsOptions.inPreferredConfig = Config.ARGB_8888;// optional
		BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
		input.close();
		if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
			return null;

		int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

		double ratio = (originalSize > size) ? (originalSize / size) : 1.0;

		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
		bitmapOptions.inDither = true;// optional
		bitmapOptions.inPreferredConfig = Config.ARGB_4444;// optional
		input = new FileInputStream(file);
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
		input.close();
		/*if ( degree != 0 )
		{
			bitmap = ImageUtil.rotateBitmap(degree, bitmap);
		}*/
		return bitmap;
	}

	public static Bitmap getThumbnail(Uri uri, Activity act) throws FileNotFoundException, IOException {
		InputStream input = act.getContentResolver().openInputStream(uri);
		/*int size = getXY(act)[0] > getXY(act)[1] ? getXY(act)[1] : getXY(act)[0];
		BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
		onlyBoundsOptions.inJustDecodeBounds = true;
		onlyBoundsOptions.inDither = true;// optional
		onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
		input.close();
		if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
			return null;

		int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

		double ratio = (originalSize > size) ? (originalSize / size) : 1.0;

		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
		bitmapOptions.inDither = true;// optional
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		input = act.getContentResolver().openInputStream(uri);
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);*/
		Bitmap bitmap = BitmapFactory.decodeStream(input);
		input.close();
		return bitmap;
	}
	
	private static int getPowerOfTwoForSampleRatio(double ratio) {
		int k = Integer.highestOneBit((int) Math.floor(ratio));
		if (k == 0)
			return 1;
		else
			return k;
	}

	/**
	 * 获取屏幕宽和高
	 */
	private static int[] pxy = { 0, 0 };

	public static int[] getXY(Activity act) {
		if ((pxy[0] == 0) && (pxy[1] == 0)) {
			DisplayMetrics dm = new DisplayMetrics();
			act.getWindowManager().getDefaultDisplay().getMetrics(dm);
			pxy[0] = dm.widthPixels;
			pxy[1] = dm.heightPixels;
		}
		return pxy;
	}



}