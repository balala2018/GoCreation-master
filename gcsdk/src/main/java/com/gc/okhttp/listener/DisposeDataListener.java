package com.gc.okhttp.listener;

/**
 * Created by 大白 on 2018/4/26.
 * 自定义事件监听
 */

public interface DisposeDataListener {
/**
 * 请求成功回调事件处理
 */
public void onSuccess(Object responseObj);
/**
 * 请求失败回调事件处理
 */
public void onFailure(Object reasonObj);

    void onProgress(int obj);
}
