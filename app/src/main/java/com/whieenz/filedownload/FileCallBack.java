package com.whieenz.filedownload;

import com.lzy.okgo.callback.FileCallback;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by heziwen on 2017/12/8.
 * 作用：
 */

public abstract class FileCallBack extends FileCallback {
    abstract void onError();
    abstract void onSuccess();
    abstract void onProgress();
    public FileCallBack(String destFileDir, String destFileName) {
        super(destFileDir, destFileName);
    }

    @Override
    public void onSuccess(File file, Call call, Response response) {

    }

//    abstract


    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
    }

    @Override
    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
        super.upProgress(currentSize, totalSize, progress, networkSpeed);
    }

    @Override
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
        super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
    }

}
