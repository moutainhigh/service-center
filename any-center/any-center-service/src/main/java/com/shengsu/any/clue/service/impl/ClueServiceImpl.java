package com.shengsu.any.clue.service.impl;

import com.alibaba.fastjson.JSON;
import com.shengsu.any.account.service.AccountRecordService;
import com.shengsu.any.account.service.AccountServcie;
import com.shengsu.any.account.vo.BalanceChangeVo;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.entity.Pns;
import com.shengsu.any.clue.mapper.ClueMapper;
import com.shengsu.any.clue.po.ClueClientPo;
import com.shengsu.any.clue.po.CluePo;
import com.shengsu.any.clue.po.ClueWebPagePo;
import com.shengsu.any.clue.service.CluePersonalService;
import com.shengsu.any.clue.service.ClueService;
import com.shengsu.any.clue.service.PnsService;
import com.shengsu.any.clue.util.ClueUtils;
import com.shengsu.any.clue.util.PnsUtils;
import com.shengsu.any.clue.vo.*;
import com.shengsu.any.message.constant.MessageConst;
import com.shengsu.any.message.entity.Message;
import com.shengsu.any.message.service.MessageService;
import com.shengsu.any.message.util.MessageUtils;
import com.shengsu.any.system.entity.SystemDict;
import com.shengsu.any.system.service.SystemDictService;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.service.AuthorizedService;
import com.shengsu.any.user.service.UserService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.SmsTemplateEnum;
import com.shengsu.helper.entity.AxbBindRequest;
import com.shengsu.helper.entity.BindResponse;
import com.shengsu.helper.entity.SmsParam184105294;
import com.shengsu.helper.entity.SmsParam184115275;
import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.helper.service.PnsClientService;
import com.shengsu.helper.service.SmsService;
import com.shengsu.result.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
public class ClueServiceImpl extends BaseServiceImpl<Clue, String> implements ClueService,MessageConst {
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private CodeGeneratorService codeGeneratorService;
    @Autowired
    private CluePersonalService cluePersonalService;
    @Autowired
    private AuthorizedService authorizedService;
    @Autowired
    private AccountServcie accountServcie;
    @Autowired
    private AccountRecordService accountRecordService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SystemDictService systemDictService;
    @Autowired
    private PnsClientService pnsClientService;
    @Autowired
    private PnsService pnsService;
    @Value("${pns.expiration}")
    private Integer expiration;
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
        List<String> clueIds = ClueUtils.toClueId(clues);
        Map<String, Object> disPlayName = new HashMap<>();
        disPlayName.put("dictCode", "clue_type");
        disPlayName.put("displayValue", clueTypes);
        List<SystemDict> systemDicts = systemDictService.getManyByDisplayValue(disPlayName);
        for (SystemDict systemDict : systemDicts) {
            for (Clue clue : clues) {
                if (systemDict.getDisplayValue().equals(clue.getClueType())) {
                    clue.setClueType(systemDict.getDisplayName());
                }
            }
        }
        List<Pns> pns = pnsService.getMany(clueIds);
        List<CluePo> cluePos = ClueUtils.toClue(clues,pns);
        if (totalCount > 0) {
            map.put("root", cluePos);
        }
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, map,totalCount);
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

    /**
    * @Description: 线索购买
    * @Param: * @Param clueBuyVo: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean buy(ClueBuyVo clueBuyVo) {
        String clueId = clueBuyVo.getClueId();
        Clue clue = get(clueId);
        if (CLUE_STATE_SOLD.equals(clue.getClueState())) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_NOT_RESALE);
        }
        if (CLUE_STATE_OFFSHELF.equals(clue.getClueState())) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_FFSHELF_NOT_RESALE);
        }
        String token = clueBuyVo.getToken();
        String userId="";
        if (StringUtils.isNoneBlank(token)) {
            userId =  authorizedService.getUserByToken(token).getUserId();
        }
        // 获取该用户的账户余额
        BigDecimal balance = accountServcie.getAccountBalanceByUserId(userId);
        if (balance==null) balance = new BigDecimal(0);
        if (balance.compareTo(clue.getCluePrice())<0){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_ACCOUNT_INSUFFICIENT_BALANCE);
        }
        // 修改线索状态-已购买
        clueMapper.updateClueSold(clueId);
        // 创建我的线索
        cluePersonalService.create(clueId,userId);
        // 修改账户余额
        accountServcie.updateAccountBalance(userId,balance.subtract(clue.getCluePrice()));
        // 增加账户记录
        BalanceChangeVo balanceChangeVo = new BalanceChangeVo();
        balanceChangeVo.setClueId(clueId);
        balanceChangeVo.setUserId(userId);
        balanceChangeVo.setAmount(clue.getCluePrice());
        balanceChangeVo.setActionType(ACCOUNT_ACTION_TYPE_BUY_CLUE);
        accountRecordService.create(balance,balance.subtract(clue.getCluePrice()),ACCOUNT_BALANCE_SOURCE_CASH_OUT,balanceChangeVo);
        // 发送消息
        Message message = MessageUtils.toMessage(userId);
        message.setMessageContent(MESSAGE_CONTENT_CLUE_BUY_SUCCESS);
        messageService.save(message);
        // 发送短信给客户和律师
        User lawyer =userService.get(userId);
        SystemDict systemDict = systemDictService.getOneByDisplayValue(DICT_CODE_CLUE_TYPE,clue.getClueType());
        // 发短信给律师
        SmsParam184105294 smsParam184105294 =  new SmsParam184105294(lawyer.getRealName(),systemDict.getDisplayName());
        smsService.sendSms(lawyer.getTel(), SmsTemplateEnum.SMS_184105294, JSON.toJSONString(smsParam184105294));
        // 发短信给客户
        SmsParam184115275 smsParam184115275 =  new SmsParam184115275(clue.getAppellation(),lawyer.getRealName());
        smsService.sendSms(clue.getTel(), SmsTemplateEnum.SMS_184115275, JSON.toJSONString(smsParam184115275));
        //绑定隐私号码
        AxbBindRequest axbBindRequest = new AxbBindRequest();
        axbBindRequest.setTelA(clue.getTel());
        axbBindRequest.setTelB(lawyer.getTel());
        axbBindRequest.setAreaCode("10");
        axbBindRequest.setExpiration(expiration);
        axbBindRequest.setRecord(1);
        BindResponse bindResponse = pnsClientService.sendAxbBindRequest(axbBindRequest);
        //存储虚拟号码到线索表
        clue.setTelX(bindResponse.getData().getTelX());
        clueMapper.updateClueTelX(clue);
        //存储隐私号码相关信息
        Pns pns = PnsUtils.toPns(bindResponse,axbBindRequest,clueId);
        pnsService.save(pns);
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
        List<String> clueTypes = ClueUtils.toClueType(clues);

        Map<String, Object> disPlayName = new HashMap<>();
        disPlayName.put("dictCode", "clue_type");
        disPlayName.put("displayValue", clueTypes);
        List<SystemDict> systemDicts = systemDictService.getManyByDisplayValue(disPlayName);
        for (SystemDict systemDict : systemDicts) {
            for (Clue clue : clues) {
                if (systemDict.getDisplayValue().equals(clue.getClueType())) {
                    clue.setClueType(systemDict.getDisplayName());
                }
            }
        }
        List<Pns> pns = pnsService.getMany(list);
        List<ClueWebPagePo> clueWebPagePos = ClueUtils.toClueWebPagePo(clues, cluePersonals, pns);
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
        List<SystemDict> systemDicts = systemDictService.getManyByDisplayValue(disPlayName);
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
        }
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, map,totalCount);
    }
}
