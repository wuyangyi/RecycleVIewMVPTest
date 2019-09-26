package com.zz.recycleviewmvptest.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.zz.recycleviewmvptest.BuildConfig;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;

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

import static com.zz.recycleviewmvptest.base.BaseFragment.REQUEST_STORAGE_CAMERA_TAKE_PHOTO;

public class Utils {
    public static String APP_SYSTEM_PATH = Environment.getExternalStorageDirectory() + "/小熊爱睡觉/";//app文件目录
    public static String galleryPath= APP_SYSTEM_PATH + "Camera/"; //图片保存目录
    public static final int REQUEST_SELECT_PICTURE = 0x01; //相册选择图片
    public static final int REQUEST_TAKE_PICTURE = 0x02; //相机拍照


    public static String converTimeFormat(String time) {
        String parts[] = time.split("-");
        if (parts.length == 3) {
            return parts[0] + "." + parts[1];
        }
        return time;
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

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
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
    //String转对象
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

    //对象转String
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
            ToastUtils.showToast("保存成功");
        } catch (Exception e) {
            e.getStackTrace();
            ToastUtils.showToast("保存失败");
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

    public static int px2sp(Context context, float pxVal) {
        return (int) (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxVal / scale);
    }

    public static float dpToPixel(Context context, float dp) {
        return dp * (getDisplayMetrics(context).densityDpi / 160f);
    }

    /**
     * 选择图片
     */
    public static void pickFromGallery(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
    }

    //拍照
    public static String takePicture(Activity activity) {
        String state = Environment.getExternalStorageState();
        String picFileFullName="";
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = new File(galleryPath);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            picFileFullName = outFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(activity.getApplicationContext(), BuildConfig.APPLICATION_ID+".provider", outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            activity.startActivityForResult(intent, REQUEST_STORAGE_CAMERA_TAKE_PHOTO);
        } else {
            /*Log.e(TAG, "请确认已经插入SD卡");*/
        }
        return picFileFullName;
    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) { // api >= 19
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * 根据本地图片地址获取Bitmap
     * @param path
     * @return
     */
    public static Bitmap getBitmapForPath(String path) {
        Bitmap bmpDefaultPic;
        bmpDefaultPic = BitmapFactory.decodeFile(path, null);
        return bmpDefaultPic;
    }

    /**
     * Textview或者button设置Drawable
     */
    public static Drawable getCompoundDrawables(Context context, int imgRsID) {
        if (imgRsID == 0) {
            return null;
        }
        Drawable drawable = ContextCompat.getDrawable(context, imgRsID);
        if (drawable == null) {
            return null;
        }
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    /**
     * 根据图片名称获取bitmap
     * @param name
     * @return
     */
    public static Bitmap getBitmapByName(Context context, String name) {
        int resID = context.getResources().getIdentifier(name, "mipmap", context.getPackageName());
        return BitmapFactory.decodeResource(context.getResources(), resID);
    }

    public static void Copy(Context context, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", content);
        clipboardManager.setPrimaryClip(clipData);
        ToastUtils.showToast("复制成功");

    }
}
