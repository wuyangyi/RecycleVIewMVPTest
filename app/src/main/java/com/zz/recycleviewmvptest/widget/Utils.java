package com.zz.recycleviewmvptest.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    //系统相册目录
    public static String galleryPath= Environment.getExternalStorageDirectory()
            + File.separator + Environment.DIRECTORY_DCIM
            +File.separator+"Camera"+File.separator;


    public static String converTimeFormat(String time) {
        String parts[] = time.split("-");
        if (parts.length == 3) {
            return parts[0] + "." + parts[1];
        }
        return time;
    }

    public static float dpToPixel(Context context, float dp) {
        return dp * (getDisplayMetrics(context).densityDpi / 160f);
    }

    /**
     * 获得屏幕密度
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftKeyboard(Context context, View view) {
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(
                    view.getWindowToken(), 0);
        }
    }

    public static <T> T base64Str2Object(String productBase64) {
        T device = null;
        if (productBase64 == null) {
            return null;
        }
        // 读取字节
        byte[] base64 = Base64.decode(productBase64.getBytes(), Base64.DEFAULT);

        // 封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            // 再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            // 读取对象
            device = (T) bis.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return device;
    }

    public static <T> String object2Base64Str(T object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {   //Device为自定义类
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(object);
            // 将字节流编码成base64的字符串
            return new String(Base64.encode(baos
                    .toByteArray(), Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存图片
     * @param bmp
     * @param picName
     * @param mContext
     */
    public static void saveBmp2Gallery(Bitmap bmp, String picName, Context mContext) {

        String fileName = null;

        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName+ ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.PNG, 90, outStream);
            }
            Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.getStackTrace();
            Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
        }finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //通知相册更新
        try {
            MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                    bmp, fileName, null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            mContext.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取当前系统时间
     * @return
     */
    public static String getNowSystemTime() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    /**
     * 获得时间差
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getDateByString(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "";
        try {
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            long times = endDate.getTime() - startDate.getTime();
            int day = (int) (times / (1000 * 60 * 60 * 12));
            times = times - day * (1000 * 60 * 60 * 12);
            int h = (int) (times / (1000 * 60 * 60));
            times = times - (h * 1000 * 60 * 60);
            int m = (int) (times / (1000 * 60));
            times = times - (h * 1000 * 60);
            int s = (int) (times / (1000));
            time = time + (day > 0 ? day + "日" : "");
            time = time + (day > 0 || h > 0 ? h + "时" : "");
            time = time + (day > 0 || h > 0 || m > 0 ? m + "分" : "");
            time = time + s + "秒";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("时间差", time);
        return time;
    }

    public static int sp2px(Context context, float sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }
}
