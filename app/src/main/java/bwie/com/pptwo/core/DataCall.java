package bwie.com.pptwo.core;


import bwie.com.pptwo.bean.Result;

/**
 * Author: 杨高峰(windy)
 * Date: 2019/5/10 14:26
 * Description:
 */
public interface DataCall<T> {

    void success(T result);

    void fail(Result result);
}
