package com.shengsu.any.clue.service.impl;

import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.clue.Po.ClueClientPo;
import com.shengsu.any.clue.Po.CluePo;
import com.shengsu.any.clue.Po.ClueWebPagePo;
import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.mapper.ClueMapper;
import com.shengsu.any.clue.service.CluePersonalService;
import com.shengsu.any.clue.service.ClueService;
import com.shengsu.any.clue.util.ClueUtils;
import com.shengsu.any.clue.vo.*;
import com.shengsu.any.system.entity.SystemDict;
import com.shengsu.any.system.mapper.SystemDictMapper;
import com.shengsu.any.user.service.AuthorizedService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.result.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shengsu.any.app.constant.BizConst.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:48
 **/
@Service("clueService")
public class ClueServiceImpl extends BaseServiceImpl<Clue, String> implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private SystemDictMapper systemDictMapper;
    @Autowired
    private CodeGeneratorService codeGeneratorService;
    @Autowired
    private CluePersonalService cluePersonalService;
    @Autowired
    private AuthorizedService authorizedService;
    @Override
    public BaseMapper<Clue, String> getBaseMapper() {
        return clueMapper;
    }

    /**
     * @return com.shengsu.result.ResultBean
     * @Author Bell
     * @Description 新增线索
     * @Date 2020/2/11
     * @Param [clueVo]
     **/
    @Override
    public ResultBean create(ClueVo clueVo) {
        Clue clue = ClueUtils.toClue(clueVo);
        clue.setClueCode(codeGeneratorService.generateCode(PREFIX_CLUE_CODE));
        clue.setClueState(CLUE_STATE_PEND);
        clueMapper.save(clue);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    /**
     * @return com.shengsu.result.ResultBean
     * @Author Bell
     * @Description 分页条件查询
     * @Date 2020/2/11
     * @Param [clueListByPageVo]
     **/
    @Override
    public ResultBean clueListByPage(ClueListByPageVo clueListByPageVo) {
        Map<String, Object> map = new HashMap();
        int totalCount = clueMapper.countAll(clueListByPageVo);
        List<Clue> clues = clueMapper.listByPage(clueListByPageVo);
        if (clues.isEmpty()) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_NOT_FOUND);
        }
        List<String> clueTypes = ClueUtils.toClueType(clues);

        Map<String, Object> disPlayName = new HashMap<>();
        disPlayName.put("dictCode", "clue_type");
        disPlayName.put("displayValue", clueTypes);
        List<SystemDict> systemDicts = systemDictMapper.getManyByDisplayValue(disPlayName);
        for (SystemDict systemDict : systemDicts) {
            for (Clue clue : clues) {
                if (systemDict.getDisplayValue().equals(clue.getClueType())) {
                    clue.setClueType(systemDict.getDisplayName());
                }
            }
        }
        List<CluePo> cluePos = ClueUtils.toClue(clues);
        if (totalCount > 0) {
            map.put("root", cluePos);
            map.put("totalCount", totalCount);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }

    /**
     * @return com.shengsu.result.ResultBean
     * @Author Bell
     * @Description 编辑线索
     * @Date 2020/2/11
     * @Param [clueVo]
     **/
    @Override
    public ResultBean edit(ClueEditVo clueVo) {
        String clueState = clueMapper.getClueState(clueVo.getClueId());
        if (clueState.equals(CLUE_STATE_PEND) || clueState.equals(CLUE_STATE_OFFSHELF)) {
            Clue clue = ClueUtils.toClue(clueVo);
            clueMapper.update(clue);
            return ResultUtil.formResult(true, ResultCode.SUCCESS);
        } else {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_STATE);
        }
    }

    /**
     * @return com.shengsu.result.ResultBean
     * @Author Bell
     * @Description 上架线索
     * @Date 2020/2/11
     * @Param [clueShelfVo]
     **/
    @Override
    public ResultBean onShelf(ClueShelfVo clueShelfVo) {
        String clueState = clueMapper.getClueState(clueShelfVo.getClueId());
        if (clueState.equals(CLUE_STATE_ONSHELF)) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_STATE_ONSHELF);
        }
        if (clueState.equals(CLUE_STATE_SOLD)) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_STATE_SOLD);
        }
        clueShelfVo.setClueState(CLUE_STATE_ONSHELF);
        clueMapper.onShelf(clueShelfVo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    /**
     * @return com.shengsu.result.ResultBean
     * @Author Bell
     * @Description 下架线索
     * @Date 2020/2/11
     * @Param [clueShelfVo]
     **/
    @Override
    public ResultBean offShelf(ClueShelfVo clueShelfVo) {
        String clueState = clueMapper.getClueState(clueShelfVo.getClueId());
        if (clueState.equals(CLUE_STATE_OFFSHELF)) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_STATE_OFFSHELF);
        }
        if (clueState.equals(CLUE_STATE_SOLD) || clueState.equals(CLUE_STATE_PEND)) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_STATE_FORBID);
        }
        clueShelfVo.setClueState(CLUE_STATE_OFFSHELF);
        clueMapper.offShelf(clueShelfVo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    /**
     * @return com.shengsu.result.ResultBean
     * @Author Bell
     * @Description 删除线索
     * @Date 2020/2/11
     * @Param [clueShelfVo]
     **/
    @Override
    public ResultBean clueDelete(ClueShelfVo clueShelfVo) {
        String clueState = clueMapper.getClueState(clueShelfVo.getClueId());
        if (clueState.equals(CLUE_STATE_SOLD) || clueState.equals(CLUE_STATE_ONSHELF)) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_STATE_FORBID_DELETE);
        }
        clueMapper.softDelete(clueShelfVo);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    @Override
    public ResultBean buy(ClueBuyVo clueBuyVo) {
        String clueId = clueBuyVo.getClueId();
        Clue clue = get(clueId);
        if (CLUE_STATE_SOLD.equals(clue.getClueState())) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_NOT_RESALE);
        }
        String token = clueBuyVo.getToken();
        String userId="";
        if (StringUtils.isNoneBlank(token)) {
            userId =  authorizedService.getUserByToken(token).getUserId();
        }
        // TODO 后期校验账户余额
        cluePersonalService.create(clueId,userId);
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }
    /**
     * @Author Bell
     * @Description 查询线索
     * @Date  2020/2/15
     * @Param [userId]
     * @return com.shengsu.result.ResultBean
     **/
    @Override
    public ResultBean listMyClue(String userId) {
        List<CluePersonal> cluePersonals = cluePersonalService.listByUserId(userId);
        List<String> list = new ArrayList<>();
        for (CluePersonal cluePersonal : cluePersonals) {
            String clueId = cluePersonal.getClueId();
            list.add(clueId);
        }
        if (list.isEmpty()) {
            return ResultUtil.formResult(true, ResultCode.SUCCESS);
        }
        List<Clue> clues = clueMapper.getMany(list);
        for (Clue clue : clues) {
            for (CluePersonal cluePersonal : cluePersonals) {
                if (cluePersonal.getClueId().equals(clue.getClueId())) {
                    clue.setCreateTime(cluePersonal.getCreateTime());
                }
            }
        }
        List<String> clueTypes = ClueUtils.toClueType(clues);

        Map<String, Object> disPlayName = new HashMap<>();
        disPlayName.put("dictCode", "clue_type");
        disPlayName.put("displayValue", clueTypes);
        List<SystemDict> systemDicts = systemDictMapper.getManyByDisplayValue(disPlayName);
        for (SystemDict systemDict : systemDicts) {
            for (Clue clue : clues) {
                if (systemDict.getDisplayValue().equals(clue.getClueType())) {
                    clue.setClueType(systemDict.getDisplayName());
                }
            }
        }
        List<ClueWebPagePo> clueWebPagePos = ClueUtils.toClueWebPagePo(clues);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, clueWebPagePos);
    }
    /**
     * @Author Bell
     * @Description 客户端查询线索库
     * @Date  2020/2/17
     * @Param [clueListByPageVo]
     * @return com.shengsu.result.ResultBean
     **/
    @Override
    public ResultBean clueClientListByPage(ClueListByPageVo clueListByPageVo) {
        Map<String, Object> map = new HashMap();
        int totalCount = clueMapper.countAll(clueListByPageVo);
        List<Clue> clues = clueMapper.clueClientListByPage(clueListByPageVo);
        if (clues.isEmpty()) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_NOT_FOUND);
        }
        List<String> clueTypes = ClueUtils.toClueType(clues);

        Map<String, Object> disPlayName = new HashMap<>();
        disPlayName.put("dictCode", "clue_type");
        disPlayName.put("displayValue", clueTypes);
        List<SystemDict> systemDicts = systemDictMapper.getManyByDisplayValue(disPlayName);
        for (SystemDict systemDict : systemDicts) {
            for (Clue clue : clues) {
                if (systemDict.getDisplayValue().equals(clue.getClueType())) {
                    clue.setClueType(systemDict.getDisplayName());
                }
            }
        }
        List<ClueClientPo> clueClientPos = ClueUtils.toClueClientPo(clues);
        if (totalCount > 0) {
            map.put("root", clueClientPos);
            map.put("totalCount", totalCount);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, map);
    }
}
