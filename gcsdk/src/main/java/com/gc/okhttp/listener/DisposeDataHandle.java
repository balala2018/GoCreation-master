package com.gc.okhttp.listener;

/**
 * Created by 大白 on 2018/4/26.
 */

public class DisposeDataHandle {
    public DisposeDataListener mListener = null;
    public Class<?> mClass = null;
    public String mSource = null;
    public DisposeDataHandle(DisposeDataListener listener){
        this.mListener=listener;
    }
    public  DisposeDataHandle(DisposeDataListener listener,Class<?> clazz)
    {
        this.mListener= listener;
        this.mClass= clazz;
    }
    public DisposeDataHandle(DisposeDataListener listener, String source)
    {
        this.mListener = listener;
        this.mSource = source;
    }
}
