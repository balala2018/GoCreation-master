package com.gc.okhttp.listener;

/**
 * Created by 大白 on 2018/4/28.
 *  @function 监听下载进度
 */

public interface DisposeDownloadListener extends  DisposeDataListener {
    public void onProgress(int progrss);
}
