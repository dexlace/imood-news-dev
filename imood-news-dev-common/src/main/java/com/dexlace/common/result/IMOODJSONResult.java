package com.dexlace.common.result;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/28
 */


/**
 自定义响应数据结构
 * 				本类可提供给 H5/ios/安卓/公众号/小程序 使用
 * 				前端接受此类数据（json object)后，可自行根据业务去实现相关功能
 *
 * 				200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 * 				556: 用户qq校验异常
 * 			    557: 校验用户是否在CAS登录，用户门票的校验
 */
public class IMOODJSONResult {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据  Object类型可以适配所有数据类型
    private Object data;

    private String ok;	// 不使用

    /**
     * 几个静态方法，对应几个构造函数，这里是为了避免去new 对象
     */
    public static IMOODJSONResult build(Integer status, String msg, Object data) {
        return new IMOODJSONResult(status, msg, data);
    }

    public static IMOODJSONResult build(Integer status, String msg, Object data, String ok) {
        return new IMOODJSONResult(status, msg, data, ok);
    }

    public static IMOODJSONResult ok(Object data) {
        return new IMOODJSONResult(data);
    }

    public static IMOODJSONResult ok() {
        return new IMOODJSONResult(null);
    }

    public static IMOODJSONResult errorMsg(String msg) {
        return new IMOODJSONResult(500, msg, null);
    }

    public static IMOODJSONResult errorUserTicket(String msg) {
        return new IMOODJSONResult(557, msg, null);
    }

    public static IMOODJSONResult errorMap(Object data) {
        return new IMOODJSONResult(501, "error", data);
    }

    public static IMOODJSONResult errorTokenMsg(String msg) {
        return new IMOODJSONResult(502, msg, null);
    }

    public static IMOODJSONResult errorException(String msg) {
        return new IMOODJSONResult(555, msg, null);
    }

    public static IMOODJSONResult errorUserQQ(String msg) {
        return new IMOODJSONResult(556, msg, null);
    }

    public IMOODJSONResult() {

    }


    /**
     * 一系列的构造函数
     * @param status
     * @param msg
     * @param data
     */
    public IMOODJSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }


    /**
     *
     * @param status 状态码
     * @param msg 响应消息
     * @param data 额外的数据内容
     * @param ok
     */
    public IMOODJSONResult(Integer status, String msg, Object data, String ok) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }


    /**
     * 传一个参数的构造函数默认返回结果为200，表示成功
     * @param data 返回的内容  data用Object类型表示可以适配任何类型
     */
    public IMOODJSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }


    /**
     * 以下是get和set方法
     *
     */

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

}

