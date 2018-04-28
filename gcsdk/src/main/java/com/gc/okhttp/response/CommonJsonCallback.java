package com.gc.okhttp.response;
import android.os.Handler;
import android.os.Looper;

import com.gc.okhttp.exception.OkHttpException;
import com.gc.okhttp.listener.DisposeDataHandle;
import com.gc.okhttp.listener.DisposeDataListener;
import com.gc.util.ResponseEntityToModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 大白 on 2018/4/27.
 * 1.执行我们的回调函数，2.异常处理；3.转发消息到我们的UI线程；4.将JSON转化为对应的实体对象
 */

public class CommonJsonCallback implements Callback {
    /**
     * the logic layer exception, may alter in different app
     */
    //与服务器返回的字段的一个对应关系
    protected final String RESULT_CODE = "ecode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";
    protected final String COOKIE_STORE = "Set-Cookie"; // decide the server it

    /**
     * 自定义异常类型
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int JSON_ERROR = -2; // the JSON relative error
    protected final int OTHER_ERROR = -3; // the unknow error

    /**
     * 将其它线程的数据转发到UI线程
     */
    private Handler mDeliveryHandler;//进行消息的转发
    private DisposeDataListener mListener;
    private Class<?> mClass;

    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

            //请求失败处理
            @Override
            public void onFailure(final Call call, final IOException ioexception) {
                /**
                 * 此时还在非UI线程，因此要转发
                 */
                mDeliveryHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onFailure(new OkHttpException(NETWORK_ERROR, ioexception));
                    }
                });
            }
            //真正的响应处理函数
            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String result = response.body().string();
                mDeliveryHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handleResponse(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
            /**
             * 处理服务器返回的响应数据
             */
            private void handleResponse(Object responseObj) throws JSONException {
                //为了保证代码的健壮性
                if (responseObj == null && responseObj.toString().trim().equals("")) {
                    mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
                    return;
                }

                try {
                    /**
                     * 协议确定后看这里如何修改
                     */
                    JSONObject result = new JSONObject(responseObj.toString());
                    //开始尝试解析json
                    if (result.has(RESULT_CODE)) {
                        //从JASON对象中取出我们的响应码，若为0，则是正确的响应。
                        if (result.getInt(RESULT_CODE) == RESULT_CODE_VALUE) {
                            //mClass为空的话不需要解析，直接返回数据到应用层
                            if (mClass == null) {
                                mListener.onSuccess(responseObj);
                            } else {
                                //即，需要我们将jason对象转化为实体对象
                                Object obj = ResponseEntityToModule.parseJsonObjectToModule(result, mClass);
                                //表明正确的转为了实体对象
                                if (obj != null) {
                                    mListener.onSuccess(obj);
                                } else {
                                    //返回的不是合法的json
                                    mListener.onFailure(new OkHttpException(JSON_ERROR,
                                            EMPTY_MSG));
                                }
                            }
                        } else {
                            //将服务器返回给我们的一场回调到应用层去处理
                            mListener.onFailure(new OkHttpException(OTHER_ERROR, result.get
                                    (RESULT_CODE)));
                        }
                    }
                } catch (Exception e) {
                    //请求失败
                    mListener.onFailure(new OkHttpException(OTHER_ERROR, e.getMessage()));
                    e.printStackTrace();
                }
            }
        }

