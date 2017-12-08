package com.whieenz.filedownload.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by heziwen on 2017/12/8.
 * 作用：
 */

public class FileUtil1 {
    public File ImgCachePath;
    public File ImgSavePath;
    public File ImgSharePath;
    public File ApkSavePath;
    public File LogSavePath;
    public File ImgCapTempPath;
    public File ImgCapCutPath;
    public File ImgCacheDefaultPath;

    public static String APP_DATA_ROOT_PATH;
    public static String IMG_SAVE_PATH;
    public static String IMG_SHARE_PATH;
    public static String APK_INSTALL_PATH;
    public static String APK_LOG_PATH;
    public static String IMG_SAVE_PATH_CAP_TEMP;
    public static String IMG_SAVE_PATH_CAP_CUT;
    public static String IMG_CACHE_XUTILS_SDCARD_PATH;
    public static String IMG_CACHE_XUTILS_DEFAULT_PATH;
    public static String FINAL_IMAGE_PATH;
    public static String FINAL_TEMP_PATH;
    public static String SDPATH;

    public File XLPath;
    public Context mContext;
    private static FileUtil1 mInstance;

    public FileUtil1(Context context) {
        mContext = context;
    }

    /**
     * 创建文件工具类示例
     *
     * @param context
     *            上下文
     * @return
     */
    public static synchronized FileUtil1 createInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FileUtil1(context);
            mInstance.initPath();
        }
        return mInstance;
    }

    /**
     * 获取文件工具类实例
     *
     * @return
     */
    public static synchronized FileUtil1 getInstance() {
        if (mInstance == null)
            throw new IllegalStateException("FileUtil must be create by call createInstance(Context context)");
        return mInstance;
    }

    /**
     * 初始化本地缓存路径
     */
    public void initPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            SDPATH = Environment.getExternalStorageDirectory() + "/";
            IMG_SAVE_PATH = SDPATH + "**/images/save/";
            IMG_SHARE_PATH = SDPATH + "**/images/share/";
            APK_INSTALL_PATH = SDPATH + "**/app/";
            APK_LOG_PATH = SDPATH + "**/log/";
            IMG_SAVE_PATH_CAP_TEMP = SDPATH + "**/images/save/capture/**_temp/";
            IMG_SAVE_PATH_CAP_CUT = SDPATH + "**/images/save/capture/**_cut/";
            IMG_CACHE_XUTILS_SDCARD_PATH = SDPATH + "**/images/cache/";// 用于保存图片缓存吧
            IMG_CACHE_XUTILS_DEFAULT_PATH = SDPATH + "Android/data/" + mContext.getPackageName() + "/cache/imgCache/";

            APP_DATA_ROOT_PATH = getAppPath() + "**/";
            FINAL_IMAGE_PATH = APP_DATA_ROOT_PATH + "images/";
            FINAL_TEMP_PATH = APP_DATA_ROOT_PATH + "temp/";

            XLPath= new File(APP_DATA_ROOT_PATH);
//            if (!"".exists()) {
//                XLPath.mkdirs();
//            }
            XLPath = new File(FINAL_IMAGE_PATH);
            if (!XLPath.exists()) {
                XLPath.mkdirs();
            }

            XLPath = new File(FINAL_TEMP_PATH);
            if (!XLPath.exists()) {
                XLPath.mkdirs();
            }

            // 拍照图片保存地址
            ImgCapTempPath = new File(IMG_SAVE_PATH_CAP_TEMP);
            if (!ImgCapTempPath.exists()) {
                ImgCapTempPath.mkdirs();
            }
            // 裁剪后图片保存地址
            ImgCapCutPath = new File(IMG_SAVE_PATH_CAP_CUT);
            if (!ImgCapCutPath.exists()) {
                ImgCapCutPath.mkdirs();
            }
            // 图片保存、缓存地址
            ImgSavePath = new File(IMG_SAVE_PATH);
            if (!ImgSavePath.exists()) {
                ImgSavePath.mkdirs();
            }
            // 分享图片的临时保存路径
            ImgSharePath = new File(IMG_SHARE_PATH);
            if (!ImgSharePath.exists()) {
                ImgSharePath.mkdirs();
            }
            // 检测更新时保存路径
            ApkSavePath = new File(APK_INSTALL_PATH);
            if (!ApkSavePath.exists()) {
                ApkSavePath.mkdirs();
            }
            // 异常保存路径
            LogSavePath = new File(APK_LOG_PATH);
            if (!LogSavePath.exists()) {
                LogSavePath.mkdirs();
            }
            ImgCachePath = new File(IMG_CACHE_XUTILS_SDCARD_PATH);
            if (!ImgCachePath.exists()) {
                ImgCachePath.mkdirs();
            }
            ImgCacheDefaultPath = new File(IMG_CACHE_XUTILS_DEFAULT_PATH);
            if (!ImgCacheDefaultPath.exists()) {
                ImgCacheDefaultPath.mkdirs();
            }

        }

    }

    private String getAppPath() {
        Log.i(TAG, "MyApplication-getAppPath():" + mContext.getFilesDir().getParent());
        return mContext.getFilesDir().getParent() + "/";
    }

    /**
     * [将文件保存到SDcard方法]<BR>
     * [功能详细描述]
     *
     * @param fileName
     * @throws IOException
     */
    public boolean saveFile2SDCard(String fileName, byte[] dataBytes) throws IOException {
        boolean flag = false;
        FileOutputStream fs = null;
        try {
            if (!TextUtils.isEmpty(fileName)) {
                File file = newFileWithPath(fileName.toString());
                if (file.exists()) {
                    file.delete();
                    Log.w(TAG, "httpFrame  threadName:" + Thread.currentThread().getName() + " 文件已存在 则先删除: "
                            + fileName.toString());
                }
                fs = new FileOutputStream(file);
                fs.write(dataBytes, 0, dataBytes.length);
                fs.flush();
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fs != null)
                fs.close();
        }

        return flag;
    }

    /**
     * 创建一个文件，如果其所在目录不存在时，他的目录也会被跟着创建
     *
     * @author songdiyuan
     * @date 2015-8-24
     * @return
     */
    public File newFileWithPath(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        int index = filePath.lastIndexOf(File.separator);

        String path = "";
        if (index != -1) {
            path = filePath.substring(0, index);
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path.toString());
                // 如果文件夹不存在
                if (!file.exists() && !file.isDirectory()) {
                    boolean flag = file.mkdirs();
                    if (flag) {
                        Log.i(TAG, "httpFrame  threadName:" + Thread.currentThread().getName() + " 创建文件夹成功："
                                + file.getPath());
                    } else {
                        Log.e(TAG, "httpFrame  threadName:" + Thread.currentThread().getName() + " 创建文件夹失败："
                                + file.getPath());
                    }
                }
            }
        }
        return new File(filePath);
    }

    /**
     * 判断文件是否存在
     *
     * @param strPath
     * @return
     */
    public boolean isExists(String strPath) {
        if (strPath == null) {
            return false;
        }

        final File strFile = new File(strPath);

        if (strFile.exists()) {
            return true;
        }
        return false;

    }
}
