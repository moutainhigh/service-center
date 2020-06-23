package com.shengsu.trade.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.helper.service.CodeGeneratorService;
import com.shengsu.helper.service.RedisService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.trade.app.constant.ResultCode;
import com.shengsu.trade.pay.entity.BdBizInfo;
import com.shengsu.trade.pay.entity.BdOrderInfo;
import com.shengsu.trade.pay.entity.PayOrder;
import com.shengsu.trade.pay.nuomi.common.NuomiApiException;
import com.shengsu.trade.pay.nuomi.util.NuomiSignature;
import com.shengsu.trade.pay.service.BdpayService;
import com.shengsu.trade.pay.service.PayOrderService;
import com.shengsu.trade.pay.util.PayOrderUtils;
import com.shengsu.trade.pay.vo.BaiduOrderVo;
import com.shengsu.trade.pay.vo.TelConsultVo;
import com.shengsu.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import static com.shengsu.trade.app.constant.BizConst.*;
/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-09 11:46
 **/
@Slf4j
@Service("bdpayService")
public class BdpayServiceImpl implements BdpayService{
    // 胜诉
    @Value("${bdpay.shengsu.appKey}")
    private String ssAppKey;
    @Value("${bdpay.shengsu.appId}")
    private String ssAppId;
    @Value("${bdpay.shengsu.dealId}")
    private String ssDealId;
    @Value("${bdpay.shengsu.rsaPrivateKey}")
    private String rsaPrivateKey;
    @Value("${bdpay.shengsu.rsaPublicKey}")
    private String ssRsaPublicKey;
    // 援手
    @Value("${bdpay.yuanshou.appKey}")
    private String ysAppKey;
    @Value("${bdpay.yuanshou.appId}")
    private String ysAppId;
    @Value("${bdpay.yuanshou.dealId}")
    private String ysDealId;
    @Value("${bdpay.yuanshou.rsaPublicKey}")
    private String ysRsaPublicKey;
    @Autowired
    CodeGeneratorService codeGeneratorService;
    @Autowired
    private PayOrderService payOrderService;
    @Resource
    private RedisService redisService;

    @Override
    public ResultBean order(BaiduOrderVo baiduOrderVo) throws NuomiApiException {
        String amount = baiduOrderVo.getAmount();
        int totalAmount =  new BigDecimal(amount).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
        String outTradeNo = "";
        ResultBean<BdOrderInfo> orderInfoResult = null;
        if (SYSTEM_TAG_YUANSHOU.equals(baiduOrderVo.getSystemTag())){
            outTradeNo = codeGeneratorService.generateCode("YBTN");
            //插入6位随机数
            outTradeNo=new StringBuilder(outTradeNo).insert(4,PayOrderUtils.randnum(6)).toString();
            orderInfoResult =  getOrderInfo(outTradeNo,amount,totalAmount,ysAppKey,ysDealId,rsaPrivateKey);
        }else if(SYSTEM_TAG_SHENGSU.equals(baiduOrderVo.getSystemTag())){
            outTradeNo = codeGeneratorService.generateCode("SBTN");
            //插入6位随机数
            outTradeNo=new StringBuilder(outTradeNo).insert(4,PayOrderUtils.randnum(6)).toString();
            orderInfoResult =  getOrderInfo(outTradeNo,amount,totalAmount,ssAppKey,ssDealId,rsaPrivateKey);
        }
        BdOrderInfo orderInfo = null;
        if (orderInfoResult.isSuccess()) {
            orderInfo = orderInfoResult.getBody();
        }
        // 在线咨询将参数保存在缓存中
        if (CONSULT_TAG_TEL.equals(baiduOrderVo.getConsultTag())){
            // 获取电话咨询参数
            TelConsultVo telConsultOrderVo =baiduOrderVo.getTelConsultVo();
            // 将客户电话等数据存储到redis,时效是1小时
            setConsultDataToRedis(outTradeNo,telConsultOrderVo.getTel(),telConsultOrderVo.getLawField());
        }
        //返回orderInfo
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("orderInfo",orderInfo);
        log.info("下单参数：",JSON.toJSONString(orderInfo));

        return ResultUtil.formResult(true, ResultCode.SUCCESS,resultMap);
    }
    /**
     * @Description: 保存咨询数据到redis
     * @Param: * @Param tel:
     * @Param lawField:
     * @Return: * @return: void
     * @date:
     */
    private void setConsultDataToRedis(String outTradeNo,String tel,String lawField){
        JSONObject param = new JSONObject();
        param.put("tel",tel);
        param.put("lawField",lawField);
        redisService.set(outTradeNo, JSON.toJSONString(param),86400L);
    }
    /**
    * @Description: 获取订单信息
    * @Return: * @return: com.shengsu.result.ResultBean<com.shengsu.trade.pay.entity.BdOrderInfo>
    * @date: 
    */
    private ResultBean<BdOrderInfo> getOrderInfo(String outTradeNo,String amount,int totalAmount, String appKey, String dealId, String rsaPrivateKey)throws NuomiApiException {
        //百度收银台创建
        BdOrderInfo orderInfo = new BdOrderInfo();
        orderInfo.setAppKey(appKey);
        orderInfo.setDealId(dealId);
        orderInfo.setDealTitle("百度小程序支付");
        orderInfo.setTotalAmount(String.valueOf(totalAmount));
        orderInfo.setTpOrderId(outTradeNo);
        orderInfo.setSignFieldsRange("1");

        //百度支付签名
        Map<String, String> data = new HashMap<>();
        data.put("appKey", orderInfo.getAppKey());
        data.put("dealId", dealId);
        data.put("totalAmount", orderInfo.getTotalAmount());
        data.put("tpOrderId", orderInfo.getTpOrderId());
        String sign = NuomiSignature.genSignWithRsa(data, rsaPrivateKey);
        if (StringUtils.isBlank(sign)) {
            log.error("签名错误");
            return ResultUtil.formResult(false, ResultCode.EXCEPTION_BAIDU_SIGN_FAIL);
        }
        orderInfo.setRsaSign(sign);
        BdBizInfo bizInfo = new BdBizInfo();
        bizInfo.setAppKey(appKey);
        bizInfo.setDealld(dealId);
        bizInfo.setTotalAmount(String.valueOf(totalAmount));
        bizInfo.setTpOrderId(outTradeNo);
        orderInfo.setBdBizInfo(bizInfo);

        // order表生成订单数据
        PayOrder payOrder = PayOrderUtils.toPayOrder("",outTradeNo,"",new BigDecimal(amount),PAY_TYPE_BDPAY,ORDER_STATUS_UNPAID);
        payOrderService.create(payOrder);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, orderInfo);
    }

    @Override
    public ResultBean checkSignWithRsa(Map<String, String> param, String rsaSign) {
        String outTradeNo =param.get("tpOrderId");
        String publicKey= outTradeNo.contains("YBTN")?ysRsaPublicKey:ssRsaPublicKey;
        try {
            boolean checkSign = NuomiSignature.checkSignWithRsa(param,publicKey,rsaSign);
            return  ResultUtil.formResult(true, ResultCode.SUCCESS,checkSign);
        } catch (NuomiApiException e) {
            log.info("验签异常：",e);
            return  ResultUtil.formResult(false, ResultCode.EXCEPTION_BAIDU_CHECK_SIGN_FAIL,false);
        }
    }

    @Override
    public String formatPaySubtype(String paySubtype) {
        // 支付渠道转化
        switch(paySubtype)
        {
            case "1117":
                paySubtype=PAY_TYPE_WECHAT;
                break;
            case "1087":
                paySubtype=PAY_TYPE_ALIPAY;
                break;
            case "1108":
                paySubtype=PAY_SUBTYPE_DUXIAOMAN;
                break;
            case "10004":
                paySubtype=PAY_SUBTYPE_BAIDU_FLASH_PAYMENT;
                break;
            case "1124":
                paySubtype=PAY_SUBTYPE_HUABEI;
                break;
            default:
                paySubtype="";
                break;
        }
        return paySubtype;
    }

    @Override
    public ResultBean orderQuery(String orderNo) throws NuomiApiException {
        String url = "https://dianshang.baidu.com/platform/entity/openapi/queryorderdetail";
        // 获取orderId
        PayOrder payOrder = payOrderService.getByOrderNo(orderNo);
        String orderId = "";
        String siteId = "";
        String appId = orderNo.contains("YBTN")?ysAppId:ssAppId;
        String appKey = orderNo.contains("YBTN")?ysAppKey:ssAppKey;
        if (null !=payOrder){
            orderId=payOrder.getTransactionId();
            siteId =payOrder.getSiteId();
        }
        Map<String, String> data = new HashMap<>();
        data.put("appId", appId);
        data.put("appKey", appKey);
        data.put("orderId", orderId);
        data.put("siteId", siteId);
        data.put("sign", NuomiSignature.genSignWithRsa(data, rsaPrivateKey));
        String result = HttpClientUtil.doGet(url, data);
        return ResultUtil.formResult(true, ResultCode.SUCCESS,result);
    }
}
