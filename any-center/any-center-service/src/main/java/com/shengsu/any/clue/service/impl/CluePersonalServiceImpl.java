package com.shengsu.any.clue.service.impl;

import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.mapper.CluePersonalMapper;
import com.shengsu.any.clue.po.CluePersonalPo;
import com.shengsu.any.clue.service.CluePersonalService;
import com.shengsu.any.clue.service.ClueService;
import com.shengsu.any.clue.util.CluePersonalUtils;
import com.shengsu.any.clue.util.ClueUtils;
import com.shengsu.any.clue.vo.CluePersonalVo;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.service.UserService;
import com.shengsu.any.user.util.UserUtils;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.helper.service.SystemDictService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.shengsu.any.app.constant.BizConst.DICT_CODE_FIELD;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:48
 **/
@Service("cluePersonalService")
public class CluePersonalServiceImpl extends BaseServiceImpl<CluePersonal, String> implements CluePersonalService {
    @Autowired
    private UserService userService;
    @Autowired
    private CluePersonalMapper cluePersonalMapper;
    @Autowired
    private ClueService clueService;
    @Autowired
    private SystemDictService systemDictService;
    @Override
    public BaseMapper<CluePersonal, String> getBaseMapper() {
        return cluePersonalMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(String clueId,String userId) {
        CluePersonal cluePersonal = new CluePersonal();
        cluePersonal.setClueId(clueId);
        cluePersonal.setUserId(userId);
        save(cluePersonal);
    }

    /**
     * @return java.util.List<java.lang.String>
     * @Author Bell
     * @Description 查询线索ID
     * @Date 2020/2/15
     * @Param [userId]
     **/
    @Override
    public List<CluePersonal> listByUserId(String userId) {
        List<CluePersonal> cluePersonals = cluePersonalMapper.listByUserId(userId);
        return cluePersonals;
    }

    @Override
    public ResultBean listSoldRecord(CluePersonalVo cluePersonalVo){
        CluePersonal cluePersonal = CluePersonalUtils.toCluePersonal(cluePersonalVo);
        List<CluePersonal> cluePersonals = cluePersonalMapper.listByCreateTime(cluePersonal);
        if(cluePersonals == null || cluePersonals.size() == 0){
            return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
        }
        List<String> clueIds = cluePersonals.stream().map(CluePersonal::getClueId).collect(Collectors.toList());
        cluePersonalVo.setClueIds(clueIds);

        List<Clue> clues = clueService.getClues(cluePersonalVo);
        if(clues==null || clues.size()==0){
            return ResultUtil.formResult(true,ResultCode.SUCCESS,null);
        }
        Map<String, SystemDict> systemDictMap = systemDictService.mapByDictCode("clue_type");
        ClueUtils.setClueTypes(clues,systemDictMap);
        Map<String,Clue> clueMap= ClueUtils.toClueMap(clues);
        List<String> userIds = cluePersonals.stream().map(CluePersonal::getUserId).collect(Collectors.toList());
        List<User> users = userService.getMany(userIds);
        if(users==null || users.size()==0){
            return ResultUtil.formResult(true,ResultCode.SUCCESS,null);
        }
        Map<String, SystemDict> systemDicts = systemDictService.mapByDictCode(DICT_CODE_FIELD);
        UserUtils.setFields(users,systemDicts);
        Map<String,User>  userMap=UserUtils.toUserMap(users);
        List<CluePersonalPo> cluePersonalPos = CluePersonalUtils.toCluePersonalPos(cluePersonals,userMap,clueMap);
        if(cluePersonalPos ==null || cluePersonalPos.size() == 0){
            return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, cluePersonalPos);
    }
}
