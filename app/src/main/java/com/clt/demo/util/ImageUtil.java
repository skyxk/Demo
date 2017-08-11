package com.clt.demo.util;

/**
 * Created by Administrator on 2017/8/4.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 图片工具类
 * @author 水寒
 * 欢迎访问水寒的个人博客：http://www.sunhome.org.cn
 *
 */
public class ImageUtil {


    /**
     * 自适应图片的ImageView
     * @param context
     * @param image
     */
    public static void setImageViewMathParent(Activity context,
                                              ImageView image, Bitmap bitmap) {
        //获得屏幕密度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        //获得屏幕宽度和图片宽度的比例
        int a = displayMetrics.widthPixels;
        int b = bitmap.getWidth();
        float scalew = (float)displayMetrics.widthPixels / (float) bitmap.getWidth();
//        float scalew =(float)1/2;
        //获得ImageView的参数类
        ViewGroup.LayoutParams vgl=image.getLayoutParams();
        //设置ImageView的宽度为屏幕的宽度
        vgl.width=displayMetrics.widthPixels/2;
        //设置ImageView的高度
//        vgl.height=(int) (bitmap.getHeight()*scalew);
        vgl.height=displayMetrics.heightPixels/4;
        //设置图片充满ImageView控件
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        //等比例缩放
        image.setAdjustViewBounds(true);
        image.setLayoutParams(vgl);
        image.setImageBitmap(bitmap);

        if (bitmap != null && bitmap.isRecycled()) {
            bitmap.recycle();
        }

    }


    /**
     * 设置水印图片在左上角
//     * @param Context
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskLeftTop(
            Context context, Bitmap src, Bitmap watermark,
            int paddingLeft, int paddingTop) {
        return createWaterMaskBitmap(src, watermark,
                dp2px(context, paddingLeft), dp2px(context, paddingTop));
    }

    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark,
                                                int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newb);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();
        return newb;
    }

    /**
     * 设置水印图片在右下角
//     * @param Context
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskRightBottom(
            Context context, Bitmap src, Bitmap watermark,
            int paddingRight, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark,
                src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
                src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到右上角
//     * @param Context
     * @param src
     * @param watermark
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap createWaterMaskRightTop(
            Context context, Bitmap src, Bitmap watermark,
            int paddingRight, int paddingTop) {
        return createWaterMaskBitmap( src, watermark,
                src.getWidth() - watermark.getWidth() - dp2px(context, paddingRight),
                dp2px(context, paddingTop));
    }

    /**
     * 设置水印图片到左下角
//     * @param Context
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskLeftBottom(
            Context context, Bitmap src, Bitmap watermark,
            int paddingLeft, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark, dp2px(context, paddingLeft),
                src.getHeight() - watermark.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 设置水印图片到中间
//     * @param Context
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createWaterMaskCenter(Bitmap src, Bitmap watermark) {
        return createWaterMaskBitmap(src, watermark,
                (src.getWidth() - watermark.getWidth()) / 2,
                (src.getHeight() - watermark.getHeight()) / 2);
    }

    /**
     * 给图片添加文字到左上角
     * @param context
     * @param bitmap
     * @param text
     * @return
     */
    public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text,
                                           int size, int color, int paddingLeft, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                dp2px(context, paddingLeft),
                dp2px(context, paddingTop) + bounds.height());
    }

    /**
     * 绘制文字到右下角
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
//     * @param paddingLeft
//     * @param paddingTop
     * @return
     */
    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text,
                                               int size, int color, int paddingRight, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 绘制文字到右上方
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingRight
     * @param paddingTop
     * @return
     */
    public static Bitmap drawTextToRightTop(Context context, Bitmap bitmap, String text,
                                            int size, int color, int paddingRight, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                dp2px(context, paddingTop) + bounds.height());
    }

    /**
     * 绘制文字到左下方
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String text,
                                              int size, int color, int paddingLeft, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                dp2px(context, paddingLeft),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * 绘制文字到中间
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @return
     */
    public static Bitmap drawTextToCenter(Context context, Bitmap bitmap, String text,
                                          int size, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                (bitmap.getWidth() - bounds.width()) / 2,
                (bitmap.getHeight() + bounds.height()) / 2);
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    /**
     * 缩放图片
     * @param src
     * @param w
     * @param h
     * @return
     */
    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w == 0 || h == 0 || src == null) {
            return src;
        } else {
            // 记录src的宽高
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            float scaleWidth = (float) (w / width);
            float scaleHeight = (float) (h / height);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        }
    }

    /**
     * dip转pix
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 对图片中的 黑色或白色进行透明化处理
     * @param bitmap 名
     * @param nubber
     * @param type B:黑色  W:白色
     * @return 结果图字节数据组
     */
    public static Bitmap transferAlpha(Bitmap bitmap, int nubber, String type) {
        Bitmap mBack = null;
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            // 保存所有的像素的数组，图片宽×高
            int[] pixels = new int[width * height];

            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            int number = nubber * 255 / 100;
            for (int i = 0; i < pixels.length; i++) {

                if(pixels[i]==-1){
                    pixels[i] = (number << 24) | (pixels[i] & 0x00FFFFFF);
                }
            }
            mBack = Bitmap.createBitmap(pixels, width,height, Bitmap.Config.ARGB_8888);

            return mBack;


            //返回处理后图像的bitmap
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mBack;

    }


    /**
     * 检查颜色是否为 白色 或者 黑色阈值范围
     * @param rgb 颜色值
     * @param color 0:白色 1:黑色
     * @return 检查结果
     */
    private static boolean checkColor(int rgb,int offset,int color){
        int R = (rgb & 0xff0000) >> 16;
        int G = (rgb & 0xff00) >> 8;
        int B = (rgb & 0xff);

        if(color == 0){
            return ((255 - R) <= offset) && ((255 - G) <= offset) && ((255 - B) <= offset);
        }else{
            return ((R <= offset) && (G <= offset) && (B <= offset));
        }
    }


    /**
     * 判断图片的长度是否超过188
     */
    public static boolean hAndWIsLe188(Bitmap bitmap){

        try {
            if(bitmap.getHeight() <= 188 && bitmap.getWidth() <= 188){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //水印图片
//        Bitmap waterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.weixin);

//        Bitmap watermarkBitmap = ImageUtil.createWaterMaskCenter(sourBitmap, waterBitmap);
//        watermarkBitmap = ImageUtil.createWaterMaskLeftBottom(this, watermarkBitmap, waterBitmap, 0, 0);
//        watermarkBitmap = ImageUtil.createWaterMaskRightBottom(this, watermarkBitmap, waterBitmap, 0, 0);
//        watermarkBitmap = ImageUtil.createWaterMaskLeftTop(this, watermarkBitmap, waterBitmap, 0, 0);
//        watermarkBitmap = ImageUtil.createWaterMaskRightTop(this, watermarkBitmap, waterBitmap, 0, 0);

//        Bitmap textBitmap = ImageUtil.drawTextToLeftTop(this, sourBitmap, "左上角", 16, Color.BLUE, 0, 16);
//        textBitmap = ImageUtil.drawTextToLeftTop(this, textBitmap, "右下角", 16, Color.RED, 0, 32);
//        textBitmap = ImageUtil.drawTextToRightTop(this, textBitmap, "右上角", 16, Color.YELLOW, 0, 0);
//        textBitmap = ImageUtil.drawTextToLeftBottom(this, textBitmap, "左下角", 16, Color.BLACK, 0, 0);
//        textBitmap = ImageUtil.drawTextToCenter(this, textBitmap, "中间", 16, Color.RED);

//        mWartermarkImage.setImageBitmap(textBitmap);
}