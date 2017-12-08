package com.whieenz.filedownload;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.whieenz.filedownload.util.FileUtil;
import com.whieenz.filedownload.util.PackageUtil;
import com.whieenz.filedownload.util.ZipUtil;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void begin(View view) {
        //运行时权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},1);
        }
        String zipUrl = "http://10.122.2.89:9080/grm/gris/contentmanage.ecmDownLoadServlet?fileName=YGSOFT_ZNCCV1&fileType=ZIP&userCode=5003&orgCode=1101&resId=ef617af51fd513a26938ce9fd519f697";
        String url = "http://10.122.2.89:9080/grm/gris/contentmanage.ecmDownLoadServlet?fileName=zncc&fileType=RAR&userCode=5003&orgCode=1101&resId=ef617af51fd513a26938ce9fd519f697";
        OkGo.get(zipUrl)//
                .tag(this)//
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // file 即为文件数据，文件保存在指定目录
                        Log.i("", file.toString());
                        String filePath = FileUtil.getSavePath("com.whieenz.file/apk");
                        ZipUtil.upZipFileWithReName(file, filePath, "ygsoft_zncc.apk");
//                        try {
//                            //ZipUtils.upZipFile(file,filePath);
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        String apkPath = FileUtil.getSavePath("com.whieenz.file/apk/ygsoft_zncc.apk");
                        File apk = new File(apkPath);
                        PackageUtil.installApk(MainActivity.this, apk);
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                        Log.i("", String.valueOf(currentSize));
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("", String.valueOf(""));

                    }
                });
    }
}
