package com.gc.okhttp.exception;

/**
 * Created by 大白 on 2018/4/28.
 * @function 自定义异常类
 */

public class OkHttpException extends Exception {
    private  static  final  long serialVersionUID = 1L;
    /**
     * the Server  return code
     */
    private  int ecode;
    /**
     * the server return error message
     */
    private Object emsg;

    public OkHttpException(int ecode, Object emsg){
        this.ecode=ecode;
        this.emsg=emsg;
    }
    public int getEcode() {
        return ecode;
    }
    public Object getEmsg(){
        return emsg;
    }
}
