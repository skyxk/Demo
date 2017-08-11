package com.clt.demo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类
 * Created by  on 2017/5/18.
 */

public class StringUtil {

    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bytes = Base64.decode(string, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static String bitmapToString(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }





    public static Date stringToDate(String str){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(str);
//            System.out.println(date.toLocaleString().split(" ")[0]);//切割掉不要的时分秒数据
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //字符节转换字符串
    public static byte[] stringToByte(String str){

        byte[] date = null;
        try {
            date=str.getBytes();
//            System.out.println(date.toLocaleString().split(" ")[0]);//切割掉不要的时分秒数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    //字符节转换字符串
    public static String  byteToString(byte[] date){

        String str = null;
        try {

           str =  new String(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


}
