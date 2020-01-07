package com.shengsu.any.app.util;

import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.constant.CommonConst;
import com.shengsu.result.ResultBean;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 结果工具
 * @author zxh
 *
 */
public class ResultUtil {
    public static <T> ResultBean formResult(boolean success, ResultCode resultCode, T data) {
        return new ResultBean(success, resultCode.getCode(), resultCode.getResultMessage(), data);
    }
    public static <T> ResultBean formResult(boolean success, ResultCode resultCode) {
        return new ResultBean(success, resultCode.getCode(), resultCode.getResultMessage());
    }
    /**
     * 返回分页结果
     * @return
     */
    public static <T> ResultBean formPageResult(boolean success, ResultCode resultCode, T data,int totalCount){
        HashMap<String, Object> map = new HashMap<>();
        map.put(CommonConst.ROOT, data);
        map.put(CommonConst.TOTAL_COUNT, totalCount);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }

    /**
     * 返回root结果
     * @param success
     * @param resultCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultBean
    formRootResult(boolean success, ResultCode resultCode, T data){
        HashMap<String, Object> map = new HashMap<>();
        map.put(CommonConst.ROOT, data);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }
    /**
     * 转换空值返回结构
     * @return
     */
    public static ResultBean formNullResult(){
        HashMap<String, Object> map = new HashMap<>();
        map.put(CommonConst.ROOT, new ArrayList<>());
        map.put(CommonConst.TOTAL_COUNT, 0);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }
}
