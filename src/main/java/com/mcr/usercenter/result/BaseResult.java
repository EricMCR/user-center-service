package com.mcr.usercenter.result;

import lombok.Data;

import java.util.Date;

@Data
public class BaseResult {

    /**
     * 请求状态码
     */
    private int status;

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回详情
     */
    private String desc;

    /**
     * 服务时间
     */
    private Date serviceTime;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 默认请求成功
     */
    public BaseResult() {
        this.setStatus(200);
        this.setSuccess(true);
        this.setMsg("请求成功");
        this.setServiceTime(getCurrentTime());
    }

    /**
     * 传入数据表示请求成功
     */
    public BaseResult(Object data) {
        this.setStatus(200);
        this.setSuccess(true);
        this.setMsg("请求成功");
        this.setData(data);
        this.setServiceTime(getCurrentTime());
    }

    /**
     * 请求失败
     */
    public BaseResult(int errorCode, String desc){
        this.setStatus(errorCode);
        this.setSuccess(false);
        this.setMsg("请求失败");
        this.setDesc(desc);
        this.setServiceTime(getCurrentTime());
    }

    /**
     * 获取当前时间
     * @return 当前时间 Date对象
     */
    private static Date getCurrentTime() {
        return new Date(System.currentTimeMillis());
    }

    public static BaseResult getSuccessResult() {
        return new BaseResult();
    }

    public static BaseResult getSuccessResult(Object data) {
        return new BaseResult(data);
    }

    public static BaseResult getFailedResult() {
        return new BaseResult(500, "");
    }

    public static BaseResult getFailedResult(int errorCode, String desc) {
        return new BaseResult(errorCode, desc);
    }
}
