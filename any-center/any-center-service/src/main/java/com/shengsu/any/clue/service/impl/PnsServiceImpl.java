package com.shengsu.any.clue.service.impl;

import com.alibaba.fastjson.JSON;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.clue.entity.Pns;
import com.shengsu.any.clue.mapper.PnsMapper;
import com.shengsu.any.clue.service.PnsService;
import com.shengsu.any.clue.util.AxbBindRequestUtils;
import com.shengsu.any.clue.vo.ClueBuyVo;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.AxbBindRequest;
import com.shengsu.helper.entity.BindResponse;
import com.shengsu.helper.service.PnsClientService;
import com.shengsu.result.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.shengsu.any.app.constant.BizConst.PNS_CODE_SUCCESS;

/**
 * @program: service-center
 * @author: Bell
 * @create: 2020-02-24 20:00
 **/
@Slf4j
@Service("pnsService")
public class PnsServiceImpl extends BaseServiceImpl<Pns, String> implements PnsService {
    @Autowired
    private PnsMapper pnsMapper;
    @Autowired
    private PnsClientService pnsClientService;
    @Override
    public BaseMapper<Pns, String> getBaseMapper() {
        return pnsMapper;
    }
    @Override
    public ResultBean rebind(ClueBuyVo clueBuyVo){
        Pns pns = pnsMapper.query(clueBuyVo.getClueId());
        AxbBindRequest axbBindRequest = AxbBindRequestUtils.toAxbBindRequest(pns);
        log.info("pns请求参数："+ JSON.toJSONString(axbBindRequest));
        BindResponse bindResponse = pnsClientService.sendAxbBindRequest(axbBindRequest);
        log.info("pns响应参数："+JSON.toJSONString(bindResponse));
        if (!PNS_CODE_SUCCESS.equals(bindResponse.getCode())) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_PNS, bindResponse.getMsg());
        }
        pnsMapper.updateBindTime(clueBuyVo.getClueId());
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
}
