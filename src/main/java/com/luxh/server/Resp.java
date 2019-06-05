package com.luxh.server;

/**
 * @Description 响应
 * @Author luxiaohua
 * @Date 2019/6/5
 */
public class Resp {

    private int code;

    private String message;

    private Object data;


    public static Resp ok(Object data) {
        Resp resp = new Resp();
        resp.setCode(0);
        resp.setMessage("success");
        resp.setData(data);
        return resp;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
