package bwie.com.pptwo.bean;

/**
 * Author: 杨高峰(windy)
 * Date: 2019/5/16 18:30
 * Description:
 */
public class Result<T> {

    private String message = "请求失败！";
    private String status = "-1";
    private T result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
