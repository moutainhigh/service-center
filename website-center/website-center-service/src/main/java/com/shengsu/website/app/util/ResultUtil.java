package com.shengsu.website.app.util;

import com.shengsu.constant.CommonConst;
import com.shengsu.result.ResultBean;
import com.shengsu.website.app.constant.ResultCode;

import java.util.HashMap;

/**
 * 结果工具
 * @author zxh
 *
 */
public class ResultUtil {
    public static <T> ResultBean formResult(boolean success, ResultCode resultCode) {
        return new ResultBean(success, resultCode.getCode(), resultCode.getResultMessage());
    }

	public static <T> ResultBean<T> formResult(boolean success, ResultCode resultCode, T body){
		return new ResultBean(success, resultCode.getCode(), resultCode.getResultMessage(), body);
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
}
