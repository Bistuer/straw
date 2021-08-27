package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.vo.R;

import java.io.Serializable;


/**
 * @author fanzhen
 * @Date 2021/06/06
 */
public class ServiceException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 5636751085451957328L;

    private int code = R.INTERNAL_SERVER_ERROR;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message); 
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause,
                            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause,
                            int code) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause,
                            boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作。
     */
    public static ServiceException invalidRequest(String message) {
        return new ServiceException(message, R.INVALID_REQUEST);
    }

    /**
     * 404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作。
     */
    public static ServiceException notFound(String message) {
        return new ServiceException(message, R.NOT_FOUND);
    }

    /**
     * 410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
     * <p>
     * public static final int GONE = 410;
     */
    public static ServiceException gone(String message) {
        return new ServiceException(message, R.GONE);
    }

    /**
         * 422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
     */
    public static ServiceException unprocesabelEntity(String message) {
        return new ServiceException(message, R.UNPROCESABLE_ENTITY);
    }

}