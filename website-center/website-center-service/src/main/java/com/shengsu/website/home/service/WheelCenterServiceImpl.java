package com.shengsu.website.home.service;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.home.entity.WheelCenter;
import com.shengsu.website.home.mapper.WheelCenterMapper;
import com.shengsu.website.home.po.WheelCenterListPo;
import com.shengsu.website.home.po.WheelCenterQueryPo;
import com.shengsu.website.home.util.WheelCenterUtils;
import com.shengsu.website.home.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyc on 2019/9/17.
 */
@Service(value = "wheelCenterService")
public class WheelCenterServiceImpl extends BaseServiceImpl implements WheelCenterService {

    @Autowired
    private WheelCenterMapper wheelCenterMapper;


    @Override
    public BaseMapper getBaseMapper() {
        return wheelCenterMapper;
    }

    @Override
    public ResultBean create(WheelCenterCreateVo wheelCenterCreateVo) {
        WheelCenter wheelCenter = WheelCenterUtils.toWheelCenter(wheelCenterCreateVo);
        wheelCenterMapper.save(wheelCenter);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean remove(WheelCenterDeleteVo wheelCenterDeleteVo) {

        Long id = wheelCenterDeleteVo.getId();
        WheelCenter wheelCenter = wheelCenterMapper.selectById(id);
        if (wheelCenter == null) {
            return ResultUtil.formResult(false, ResultCode.WHEEL_ID_ERROR, null);
        }

        wheelCenterMapper.softDelete(id);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean edit(WheelCenterUpdateVo wheelCenterUpdateVo) {

        Long id = wheelCenterUpdateVo.getId();
        WheelCenter wheelCenter = wheelCenterMapper.selectById(id);
        if (wheelCenter == null) {
            return ResultUtil.formResult(false, ResultCode.WHEEL_ID_ERROR, null);
        }

        wheelCenter = WheelCenterUtils.toWheelCenter(wheelCenterUpdateVo);
        wheelCenterMapper.update(wheelCenter);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean query(WheelCenterQueryVo wheelCenterQueryVo) {
        Long id = wheelCenterQueryVo.getId();
        WheelCenter wheelCenter = wheelCenterMapper.selectById(id);
        WheelCenterQueryPo wheelCenterQueryPo = WheelCenterUtils.toWheelCenterQueryPo(wheelCenter);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, wheelCenterQueryPo);
    }

    @Override
    public ResultBean list(WheelCenterListVo wheelCenterListVo) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer type = wheelCenterListVo.getType();
        List<WheelCenter> wheelCenters = wheelCenterMapper.selectByType(type);
        if (wheelCenters != null && !wheelCenters.isEmpty()) {
            List<WheelCenterListPo> wheelCenterListPos = WheelCenterUtils.toWheelCenterListPo(wheelCenters);
            resultMap.put(CommonConst.ROOT, wheelCenterListPos);
            resultMap.put(CommonConst.TOTAL_COUNT, wheelCenterListPos.size());
            return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
        }

        return ResultUtil.formResult(false, ResultCode.SUCCESS, null);
    }
}
