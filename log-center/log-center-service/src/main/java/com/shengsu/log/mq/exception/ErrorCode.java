package com.shengsu.log.mq.exception;

import java.io.Serializable;

/**
 * Created by zyc on 2019/9/27.
 */
public interface ErrorCode extends Serializable {

    /**
     * 错误码
     *
     * @return
     */
    String getCode();

    /**
     * 错误信息
     *
     * @return
     */
    String getMsg();
}
