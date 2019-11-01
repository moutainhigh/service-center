package com.shengsu.util;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleResult;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author:
 * @Description: 极光推送工具类
 * @Date: 2019/4/2
 */
@Component
public class JiPushUtil {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(JiPushUtil.class);
    @Value("${jiguang.apnsProduction}")
    private boolean apnsProduction;//上线之后要改为true
    @Value("${jiguang.appKey}")
    private String appKey;
    @Value("${jiguang.masterSecret}")
    private String masterSecret;
    private JPushClient jPushClient;


    public JiPushUtil(String masterSecret, String appKey, boolean apnsProduction){
    	jPushClient = new JPushClient(masterSecret,appKey);
    	this.apnsProduction = apnsProduction;
    }

    /**
     * 推送给设备标识参数的用户
     * @param aliasList 别名或别名组
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAliasList(Collection<String> aliasList, String notification_title, String msg_title, String msg_content, Object extrasparam) {
        int result = 0;
        try {
            String extrasparamStr = objectToJson(extrasparam);
            PushPayload pushPayload= buildPushObject_all_aliasList_alertWithTitle(aliasList,notification_title,msg_title,msg_content,extrasparamStr);
            LOGGER.info("推送给设备标识参数的用户"+pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            LOGGER.info("推送结果"+pushResult);
            System.out.println(result);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (APIConnectionException e) {
            e.printStackTrace();

        } catch (APIRequestException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 推送给Tag参数的用户
     * @param tagsList Tag或Tag组
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToTagList(List<String> tagsList, String notification_title, String msg_title, String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload= buildPushObject_all_tagList_alertWithTitle(tagsList,notification_title,msg_title,msg_content,extrasparam);
            LOGGER.info(""+pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            LOGGER.info(""+pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (APIConnectionException e) {
            e.printStackTrace();

        } catch (APIRequestException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 发送给所有安卓用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAllAndroid( String notification_title, String msg_title, String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload= buildPushObject_android_all_alertWithTitle(notification_title,msg_title,msg_content,extrasparam);
            LOGGER.info(""+pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            LOGGER.info(""+pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * 发送给所有IOS用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAllIos(String notification_title, String msg_title, String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload= buildPushObject_ios_all_alertWithTitle(notification_title,msg_title,msg_content,extrasparam);
            LOGGER.info(""+pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            LOGGER.info(""+pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * 发送给所有用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public int sendToAll( String notification_title, String msg_title, String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload= buildPushObject_android_and_ios(notification_title,msg_title,msg_content,extrasparam);
            LOGGER.info(""+pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            LOGGER.info(""+pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }



    /**
     * 向所有平台所有用户推送消息
     * @param notification_title
     * @param msg_title
     * @param msg_content
     * @param extrasparam
     * @return
     */
    private PushPayload buildPushObject_android_and_ios(String notification_title, String msg_title, String msg_content, String extrasparam) {
        LOGGER.info("----------向所有平台所有用户推送消息中......");
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(notification_title)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",extrasparam)
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("default")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extrasparam",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)

                                .build()
                        )
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("extrasparam",extrasparam)
                        .build())

                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(apnsProduction)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build()
                )
                .build();
    }


    /**
     * 向所有平台单个或多个指定别名用户推送消息
     * @param aliasList
     * @param notification_title
     * @param msg_title
     * @param msg_content
     * @param extrasparam
     * @return
     */
    private PushPayload buildPushObject_all_aliasList_alertWithTitle(Collection<String> aliasList,String notification_title, String msg_title, String msg_content, Object extrasparam) {
        String extrasparamStr = objectToJson(extrasparam);
        LOGGER.info("----------向所有平台单个或多个指定别名用户推送消息中......");
        //创建一个IosAlert对象，可指定APNs的alert、title等字段
        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();

        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.alias(aliasList))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()

                                .setAlert(msg_content)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extrasparam",extrasparamStr)

                                .build())
                        //指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(msg_content)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("default")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extrasparam",extrasparamStr)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                //取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)

                                .build())
                        .build())
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()

                        .setMsgContent(msg_content)

                        .setTitle(msg_title)

                        .addExtra("extrasparam",extrasparamStr)

                        .build())

                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(apnsProduction)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)

                        .build())

                .build();

    }

    /**
     *向所有平台单个或多个指定Tag用户推送消息
     * @param tagsList
     * @param notification_title
     * @param msg_title
     * @param msg_content
     * @param extrasparam
     * @return
     */
    private PushPayload buildPushObject_all_tagList_alertWithTitle(List<String> tagsList,String notification_title, String msg_title, String msg_content, String extrasparam) {

        LOGGER.info("----------向所有平台单个或多个指定Tag用户推送消息中.......");
        //创建一个IosAlert对象，可指定APNs的alert、title等字段
        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();

        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.tag(tagsList))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()

                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extrasparam",extrasparam)

                                .build())
                        //指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("default")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extrasparam",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                //取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)

                                .build())


                        .build())
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()

                        .setMsgContent(msg_content)

                        .setTitle(msg_title)

                        .addExtra("extrasparam",extrasparam)

                        .build())

                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(apnsProduction)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)

                        .build())

                .build();

    }


    /**
     * 向android平台所有用户推送消息
     * @param notification_title
     * @param msg_title
     * @param msg_content
     * @param extrasparam
     * @return
     */
    private PushPayload buildPushObject_android_all_alertWithTitle(String notification_title, String msg_title, String msg_content, String extrasparam) {
        LOGGER.info("----------向android平台所有用户推送消息中......");
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.android())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extrasparam",extrasparam)
                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("extrasparam",extrasparam)
                        .build())

                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(apnsProduction)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }


    /**
     * 向ios平台所有用户推送消息
     * @param notification_title
     * @param msg_title
     * @param msg_content
     * @param extrasparam
     * @return
     */
    private PushPayload buildPushObject_ios_all_alertWithTitle( String notification_title, String msg_title, String msg_content, String extrasparam) {
        LOGGER.info("----------向ios平台所有用户推送消息中.......");
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.ios())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("default")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("extrasparam",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)

                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("extrasparam",extrasparam)
                        .build())

                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(apnsProduction)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }

    /**
     *
     * @auth
     * @date 2018年5月2日
     * @decripe 定时推送,利用DeviceSN做别名,点对点发送,同时记录返回的msg_id
     * @param
     *
     */
    public ScheduleResult sendSchedulePush(Object obj, String deviceSN, Date date, String MsgType, String name) {
    	String objStr = objectToJson(obj);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        ScheduleResult result = null;
        PushPayload push = PushPayload.newBuilder().setPlatform(Platform.all())
                .setMessage(Message.newBuilder().setMsgContent(objStr)
                        .addExtras(Collections.singletonMap("MsgType", MsgType)).build())
                .setAudience(Audience.alias(deviceSN)).build();
        try {
            result = jPushClient.createSingleSchedule(name, time, push);
            LOGGER.info("Got result - " + result);
            LOGGER.info("send objStr - " + objStr);
            System.out.println(result);
            System.out.println(objStr);
        } catch (APIConnectionException e) {
            LOGGER.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOGGER.error("Error response from JPush server. Should review and fix it. ", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     *
     * @auth
     * @date 2018年5月2日
     * @decripe 定时推送,推送到所有设备,同时记录返回的msg_id
     */
    public ScheduleResult sendSchedulePushAll(Object obj, Date date, String MsgType, String name) {
        String objStr = objectToJson(obj);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        ScheduleResult result = null;
        PushPayload push = PushPayload.newBuilder().setPlatform(Platform.all())
                .setMessage(Message.newBuilder().setMsgContent(objStr)
                        .addExtras(Collections.singletonMap("MsgType", MsgType)).build())
                .setAudience(Audience.all()).build();
        try {
            result = jPushClient.createSingleSchedule(name, time, push);
            LOGGER.info("Got result - " + result);
            LOGGER.info("send objStr - " + objStr);
            System.out.println(result);
            System.out.println(objStr);
        } catch (APIConnectionException e) {
            LOGGER.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOGGER.error("Error response from JPush server. Should review and fix it. ", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());
        }
        return result;
    }

    /**
     *
     * @auth
     * @date 2018年5月2日
     * @decripe 删除定时任务
     * @param
     */
    public void deleteSchedule(String scheduleId) {
        try {
            jPushClient.deleteSchedule(scheduleId);
        } catch (APIConnectionException e) {
            LOGGER.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOGGER.error("Error response from JPush server. Should review and fix it. ", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());
        }
    }

    /**
     *
     * @auth
     * @date 2018年5月2日
     * @decripe:把obj对象的json串推送到别名为DeviceSN的设备上,同时记录返回的msg_id
     * @throws BizException
     */
    public PushResult SendPush(Object obj, String DeviceSN, String MsgType) throws BizException {
        String objStr = objectToJson(obj);
        PushPayload push = PushPayload.newBuilder().setPlatform(Platform.all())
                .setMessage(Message.newBuilder().setMsgContent(objStr)
                        .addExtras(Collections.singletonMap("MsgType", MsgType)).build())
                .setAudience(Audience.alias(DeviceSN)).build();
        PushResult result = null;
        try {
            result = jPushClient.sendPush(push);
            LOGGER.info("Got result - " + result);
            LOGGER.info("send objStr - " + objStr);
            System.out.println(result);
            System.out.println(objStr);
        } catch (APIConnectionException e) {
            LOGGER.error("Connection error. Should retry later. ", e);
            LOGGER.error("Sendno: " + push.getSendno());

        } catch (APIRequestException e) {
            LOGGER.error("Error response from JPush server. Should review and fix it. ", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());
            LOGGER.info("Msg ID: " + e.getMsgId());
            LOGGER.error("Sendno: " + push.getSendno());
        }
        if (result == null) {
            throw new BizException("与设备通话失败，请联系管理员处理！");
        }
        return result;
    }

    /**
     *
     * @auth
     * @date 2018年5月2日
     * @decripe 把obj对象的json串推送到所有设备上
     * @throws BizException
     */
    public PushResult sendPushAll(Object obj, String MsgType) throws BizException {
        String objStr = objectToJson(obj);
        PushPayload push = PushPayload.newBuilder().setPlatform(Platform.all())
                .setMessage(Message.newBuilder().setMsgContent(objStr)
                        .addExtras(Collections.singletonMap("MsgType", MsgType)).build())
                .setAudience(Audience.all()).build();
        PushResult result = null;
        try {
            result = jPushClient.sendPush(push);
            LOGGER.info("Got result - " + result);
            LOGGER.info("send objStr - " + objStr);
            System.out.println(result);
            System.out.println(objStr);
        } catch (APIConnectionException e) {
            LOGGER.error("Connection error. Should retry later. ", e);
            LOGGER.error("Sendno: " + push.getSendno());

        } catch (APIRequestException e) {
            LOGGER.error("Error response from JPush server. Should review and fix it. ", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());
            LOGGER.info("Msg ID: " + e.getMsgId());
            LOGGER.error("Sendno: " + push.getSendno());
        }
        if (result == null) {
            throw new BizException("推送失败,请联系管理员处理！");
        }
        return result;
    }
    /**
     * 增加定时推送 alert功能(同时发送多个)
     * @auth
     * @date 2019年4月17日
     *
     */
    public ScheduleResult sendSchedulePushList(Object obj, Collection<String> aliasList, Date date, String MsgType, String notification_title, Object extrasparam, String content, String name) {
        String objStr = objectToJson(obj);
        String extrasparamStr = objectToJson(extrasparam);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        ScheduleResult result = null;
        PushPayload push = PushPayload.newBuilder().setPlatform(Platform.all())
        		.setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(content)
                                .setTitle(notification_title)
                                .addExtra("extrasparam",extrasparamStr)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(content)
                                .incrBadge(1)
                                .setSound("default")
                                .addExtra("extrasparam",extrasparamStr)
                                .build())
                        .build())
                .setMessage(Message.newBuilder().setMsgContent(objStr)
                        .addExtras(Collections.singletonMap("MsgType", MsgType)).build())
                .setOptions(Options.newBuilder()
                		 //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                		 .setApnsProduction(apnsProduction)
                		 //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                		 .setSendno(1)
                		 //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
                        .build())
                .setAudience(Audience.alias(aliasList)).build();
        try {
            result = jPushClient.createSingleSchedule(name, time, push);
            LOGGER.info("Got result - " + result);
            LOGGER.info("send objStr - " + objStr);
            System.out.println(result);
            System.out.println(objStr);
        } catch (APIConnectionException e) {
            LOGGER.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOGGER.error("Error response from JPush server. Should review and fix it. ", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());
        }
        return result;
    }
    public static String objectToJson(Object o) {
        //String json = JsonUtil.getJsonString4JavaPOJO(o, "yyyy-MM-dd HH:mm:ss");
    	String json = JSON.toJSONString(o);
        return json;
    }
}
