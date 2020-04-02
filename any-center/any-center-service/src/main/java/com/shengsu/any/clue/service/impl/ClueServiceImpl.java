package com.shengsu.any.clue.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.any.account.service.AccountRecordService;
import com.shengsu.any.account.service.AccountServcie;
import com.shengsu.any.account.util.AccountRecordUtils;
import com.shengsu.any.account.vo.BalanceChangeVo;
import com.shengsu.any.app.constant.ResultCode;
import com.shengsu.any.app.util.RedisUtil;
import com.shengsu.any.app.util.ResultUtil;
import com.shengsu.any.clue.entity.Clue;
import com.shengsu.any.clue.entity.CluePersonal;
import com.shengsu.any.clue.entity.Pns;
import com.shengsu.any.clue.mapper.ClueMapper;
import com.shengsu.any.clue.po.ClueLibPo;
import com.shengsu.any.clue.po.ClueListPo;
import com.shengsu.any.clue.po.MyCluePo;
import com.shengsu.any.clue.service.CluePersonalService;
import com.shengsu.any.clue.service.ClueService;
import com.shengsu.any.clue.service.PnsService;
import com.shengsu.any.clue.util.*;
import com.shengsu.any.clue.vo.*;
import com.shengsu.any.message.entity.Message;
import com.shengsu.any.message.service.MessageService;
import com.shengsu.any.message.util.MessageUtils;
import com.shengsu.any.system.util.SystemDictUtils;
import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.service.AuthorizedService;
import com.shengsu.any.user.service.UserService;
import com.shengsu.any.wechat.entity.TempMessageData;
import com.shengsu.any.wechat.entity.TempMessageParamData;
import com.shengsu.any.wechat.service.TemplateMessageService;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.helper.constant.MQProducerEnum;
import com.shengsu.helper.constant.SmsSignEnum;
import com.shengsu.helper.constant.SmsTemplateEnum;
import com.shengsu.helper.entity.AxbBindRequest;
import com.shengsu.helper.entity.AxbUnBindRequest;
import com.shengsu.helper.entity.BindResponse;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.helper.service.*;
import com.shengsu.result.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.shengsu.any.app.constant.BizConst.*;
import static com.shengsu.any.message.constant.MessageConst.MESSAGE_CONTENT_CLUE_BUY_SUCCESS;
import static com.shengsu.any.wechat.constant.TemplateMessageConst.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-01-08 10:48
 **/
@Slf4j
@Service("clueService")
public class ClueServiceImpl extends BaseServiceImpl<Clue, String> implements ClueService{
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
    @Autowired
    private TemplateMessageService templateMessageService;
    @Value("${pns.expireTimeSecond}")
    private Integer expireTimeSecond;
    @Value("${pns.areaCodes}")
    private String areaCodes;
    private static Random random = new Random();
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private MQProducerService mqProducerService;

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
        clue.setClueCode(ClueUtil.getStringRandom(16));
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
        int totalCount = clueMapper.countAll(clueListByPageVo);
        List<Clue> clues = clueMapper.listByPage(clueListByPageVo);
        if (clues.isEmpty()) {
            return ResultUtil.formResult(true, ResultCode.SUCCESS, clues);
        }
        List<String> clueTypes = ClueUtils.toClueType(clues);
        List<String> clueIds = ClueUtils.toClueId(clues);
        List<SystemDict> systemDicts = systemDictService.getManyByDisplayValue("clue_type", clueTypes);
        SystemDictUtils.toSystemDicts(systemDicts, clues);
        List<Pns> pns = pnsService.getMany(clueIds);
        List<ClueLibPo> clueLibPos = ClueUtils.toClue(clues, pns,expireTimeSecond);
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, clueLibPos, totalCount);
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
        if (CLUE_STATE_PEND.equals(clueState) || CLUE_STATE_OFFSHELF.equals(clueState)) {
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
        if (CLUE_STATE_ONSHELF.equals(clueState)) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_STATE_ONSHELF);
        }
        if (CLUE_STATE_SOLD.equals(clueState)) {
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
        if (CLUE_STATE_OFFSHELF.equals(clueState)) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_STATE_OFFSHELF);
        }
        if (CLUE_STATE_SOLD.equals(clueState) || CLUE_STATE_PEND.equals(clueState)) {
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
        if (CLUE_STATE_SOLD.equals(clueState) || CLUE_STATE_ONSHELF.equals(clueState)) {
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
        // 加锁
        long time = System.currentTimeMillis() + 1000*1;  //超时时间：1秒
        boolean isLock = redisUtil.lock(clueId, String.valueOf(time));
        if(!isLock){
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_CLUE_IS_LOCKED);
        }

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
            userId = authorizedService.getUserByToken(token).getUserId();
        }
        User lawyer = userService.get(userId);
        // 获取该用户的账户余额
        BigDecimal balance = accountServcie.getAccountBalanceByUserId(userId);
        if (balance == null) balance = new BigDecimal(0);
        if (balance.compareTo(clue.getCluePrice()) < 0) {
            return ResultUtil.formResult(true, ResultCode.EXCEPTION_ACCOUNT_INSUFFICIENT_BALANCE);
        }
        //绑定隐私号码
        List<String> codes = Arrays.asList(areaCodes.split(","));
        //打乱顺序
        Collections.shuffle(codes);
        AxbBindRequest axbBindRequest=null;
        BindResponse bindResponse=null;
        // 多次尝试直至成功
        for (String areaCode:codes) {
            axbBindRequest = AxbBindRequestUtils.toAxbBindRequest(clue, lawyer, areaCode, expireTimeSecond);
            log.info("pns请求参数：" + JSON.toJSONString(axbBindRequest));
            bindResponse = pnsClientService.sendAxbBindRequest(axbBindRequest);
            log.info("pns响应参数：" + JSON.toJSONString(bindResponse));
            if (PNS_CODE_SUCCESS.equals(bindResponse.getCode())) {
                break;
            }
        }
        if (!PNS_CODE_SUCCESS.equals(bindResponse.getCode())) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_PNS, bindResponse.getMsg());
        }
        //存储虚拟号码到线索表
        clue.setTelX(bindResponse.getData().getTelX());
        clueMapper.updateClueTelX(clue);
        //存储隐私号码相关信息
        Pns pns = PnsUtils.toPns(bindResponse, axbBindRequest, clueId);
        pnsService.save(pns);
        // 修改线索状态-已购买
        clueMapper.updateClueSold(clueId);
        // 创建我的线索
        cluePersonalService.create(clueId, userId);
        // 修改账户余额
        accountServcie.updateBalanceByUserId(userId, balance.subtract(clue.getCluePrice()));
        // 增加账户记录
        BalanceChangeVo balanceChangeVo = AccountRecordUtils.toBalanceChangeVo(clue, clueId, userId, ACCOUNT_ACTION_TYPE_BUY_CLUE);
        accountRecordService.create(balance, balance.subtract(clue.getCluePrice()), ACCOUNT_BALANCE_SOURCE_CASH_OUT, balanceChangeVo);
        // 发送消息
        Message message = MessageUtils.toMessage(userId);
        message.setMessageContent(MESSAGE_CONTENT_CLUE_BUY_SUCCESS);
        messageService.save(message);
        // 发送短信给客户和律师
        SystemDict systemDict = systemDictService.getOneByDisplayValue(DICT_CODE_CLUE_TYPE, clue.getClueType());
        // 发短信给律师
        JSONObject param = new JSONObject();
        param.put("lawyer",lawyer.getRealName());
        param.put("clueType",systemDict.getDisplayName());
        smsService.sendSms(lawyer.getTel(), SmsTemplateEnum.SMS_186613636, JSON.toJSONString(param),SmsSignEnum.SMS_SIGN_CODE_SSKJ);
        // 发短信给客户
        param = new JSONObject();
        param.put("client",clue.getAppellation());
        param.put("lawyer",lawyer.getRealName());
        param.put("tel",lawyer.getTel());
        smsService.sendSms(clue.getTel(), SmsTemplateEnum.SMS_186951639, JSON.toJSONString(param),SmsSignEnum.SMS_SIGN_CODE_SSKJ);

        //解锁
        redisUtil.unlock(clueId,String.valueOf(time));
        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

    /**
     * @return com.shengsu.result.ResultBean
     * @Author Bell
     * @Description 解绑隐私号码
     * @Date 2020/2/26
     * @Param [bindId]
     **/
    @Override
    public ResultBean sendAxbUnBindRequest(AxbUnBindRequestVo axbUnBindRequestVo) {
        AxbUnBindRequest axbUnBindRequest = AxbUnBindRequestUtils.toAxbUnBindRequest(axbUnBindRequestVo);
        BindResponse bindResponse = pnsClientService.sendAxbUnBindRequest(axbUnBindRequest);
        if (!PNS_CODE_SUCCESS.equals(bindResponse.getCode())) {
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_PNS, bindResponse.getMsg());
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, bindResponse.getMsg());
    }

    /**
     * @return com.shengsu.result.ResultBean
     * @Author Bell
     * @Description 查询线索
     * @Date 2020/2/15
     * @Param [userId]
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
            return ResultUtil.formResult(true, ResultCode.SUCCESS, list);
        }

        List<Clue> clues = clueMapper.getMany(list);
        List<String> clueTypes = ClueUtils.toClueType(clues);

        List<SystemDict> systemDicts = systemDictService.getManyByDisplayValue("clue_type", clueTypes);
        SystemDictUtils.toSystemDicts(systemDicts, clues);
        List<Pns> pns = pnsService.getMany(list);
        List<MyCluePo> myCluePos = ClueUtils.toClueWebPagePo(clues, cluePersonals, pns, expireTimeSecond);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, myCluePos);
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
        int totalCount = clueMapper.countAll(clueListByPageVo);
        List<Clue> clues = clueMapper.clueClientListByPage(clueListByPageVo);
        if (clues.isEmpty()) {
            return ResultUtil.formResult(true, ResultCode.SUCCESS, clues);
        }
        List<String> clueTypes = ClueUtils.toClueType(clues);

        List<SystemDict> systemDicts = systemDictService.getManyByDisplayValue("clue_type", clueTypes);
        SystemDictUtils.toSystemDicts(systemDicts, clues);
        List<ClueListPo> clueListPos = ClueUtils.toClueClientPo(clues);
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, clueListPos, totalCount);
    }
    @Override
    public ResultBean pushAllTemplateMsg() {
        // 获取所有律师用户的openId
        List<User> users = userService.getAllPushUsers();

        for (User user : users){
            // 设置模板
            StringBuilder stringBuilder = new StringBuilder(user.getRealName());
            if(!user.getAuthState().equals(0)){
                stringBuilder.append("律师");
            }
            TempMessageParamData data = templateMessageService.assembleTemplateDate(TEMPLATE_MESSAGE_CLUE_UPDATE_FIRST_VALUE,stringBuilder.toString(),TEMPLATE_MESSAGE_ACLUE_UPDATE_REMARK_VALUE);
            TempMessageData tempMessageData = new TempMessageData();
            tempMessageData.setOpenId(user.getWechatOpenid());
            tempMessageData.setData(data);
            // 发送消息
            mqProducerService.send(MQProducerEnum.ANY_WECHAT, JSON.toJSONString(tempMessageData));}

        return ResultUtil.formResult(true, ResultCode.SUCCESS);
    }

}
