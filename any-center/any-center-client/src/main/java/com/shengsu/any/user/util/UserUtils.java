package com.shengsu.any.user.util;

import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.po.UserPo;
import com.shengsu.any.user.vo.UserBandVo;
import com.shengsu.any.user.vo.UserEditVo;
import com.shengsu.helper.entity.SystemDict;
import com.shengsu.user.entity.AliasUser;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

public class UserUtils {
    public static void setFields(List<User> users, Map<String, SystemDict> systemDicts){
        for(User user : users){
            String field = "";
            if (StringUtils.isNotBlank(user.getField())){
                List<String> fieldItems = Arrays.asList(StringUtils.split(user.getField(), ","));
                for (String item : fieldItems){
                    SystemDict dict = systemDicts.get(item);
                    if (null!=dict) field = field + " "+ dict.getDisplayName();
                }
            }
            user.setField(field.trim());
        }
    }
    public static UserPo toUserPo(User user){
            UserPo userPo = new UserPo();
            userPo.setRealName(user.getRealName());
            userPo.setGender(user.getGender());
            userPo.setTel(user.getTel());
            userPo.setIdCard(user.getIdCard());
            userPo.setLawFirm(user.getLawFirm());
            userPo.setLawyerLicenseNo(user.getLawyerLicenseNo());
            userPo.setField(user.getField());
        return userPo;
    }
    public static User toUser(String tel){
        if (StringUtils.isNotBlank(tel)) {
            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setUserName(tel);
            user.setTel(tel);
            return user;
        }
        return null;
    }

    public static UserDetailsPo toUserDetailsPo(User user) {
        if (user != null) {
            UserDetailsPo detailsPo = new UserDetailsPo();
            detailsPo.setUserId(user.getUserId());
            detailsPo.setUserName(user.getUserName());
            detailsPo.setRealName(user.getRealName());
            detailsPo.setGender(user.getGender());
            detailsPo.setTel(user.getTel());
            detailsPo.setIdCard(user.getIdCard());
            detailsPo.setProvinceCode(user.getProvinceCode());
            detailsPo.setCityCode(user.getCityCode());
            detailsPo.setDistrictCode(user.getDistrictCode());
            detailsPo.setLawFirm(user.getLawFirm());
            detailsPo.setLawyerLicenseNo(user.getLawyerLicenseNo());
            detailsPo.setField(user.getField());
            detailsPo.setAuthState(StringUtils.isBlank(user.getAuthState())?"0":user.getAuthState());
            detailsPo.setCreateTime(user.getCreateTime());
            detailsPo.setIdCardFrontOssResourceId(user.getIdCardFrontOssResourceId());
            detailsPo.setIdCardBackOssResourceId(user.getIdCardBackOssResourceId());
            detailsPo.setLicenseOssResourceId(user.getLicenseOssResourceId());
            detailsPo.setOpenid(user.getWechatOpenid());
            return detailsPo;
        }
        return null;
    }


    public static List<UserDetailsPo> toUserDetailsPos(List<User> users) {
        if (users != null) {
            List<UserDetailsPo> userDetailsPos = new ArrayList<>();
            for (User user :
                    users) {
                userDetailsPos.add(toUserDetailsPo(user));
            }
            return userDetailsPos;
        }
        return null;
    }

    public static Map<String, User> toUserMap(List<User> users) {
        if (users != null) {
            Map<String, User> userMap = new HashMap<>();
            for (User user : users) {
                userMap.put(user.getUserId(), user);
            }
            return userMap;
        }
        return null;
    }
    public static User toUser(UserBandVo userBandVo){
        if (userBandVo !=null) {
            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setWechatUnionid(userBandVo.getUnionid());
            user.setWechatOpenid(userBandVo.getOpenid());
            user.setTel(userBandVo.getTel());
            user.setRealName(userBandVo.getNickname());
            user.setGender(userBandVo.getSex());
            user.setUserName(userBandVo.getTel());
            return user;
        }
        return null;
    }
    public static User toUser(UserEditVo userEditVo){
        if (userEditVo != null) {
            User user = new User();
            user.setUserId(userEditVo.getUserId());
            user.setRealName(userEditVo.getRealName());
            user.setGender(userEditVo.getGender());
            user.setIconOssResourceId(userEditVo.getIconOssResourceId());
            user.setIdCard(userEditVo.getIdCard());
            user.setLawFirm(userEditVo.getLawFirm());
            user.setLawyerLicenseNo(userEditVo.getLawyerLicenseNo());
            user.setIdCardFrontOssResourceId(userEditVo.getIdCardFrontOssResourceId());
            user.setIdCardBackOssResourceId(userEditVo.getIdCardBackOssResourceId());
            user.setLicenseOssResourceId(userEditVo.getLicenseOssResourceId());
            user.setField(userEditVo.getField());
            user.setProvinceCode(userEditVo.getProvinceCode());
            user.setCityCode(userEditVo.getCityCode());
            user.setDistrictCode(userEditVo.getDistrictCode());
            return user;
        }
        return null;
    }


    public static Map<String,AliasUser> toAliasUserMap(List<AliasUser> aliasUsers) {
        if (aliasUsers != null) {
            Map<String, AliasUser> aliasUserMap = new HashMap<>();
            for (AliasUser aliasUser : aliasUsers) {
                aliasUserMap.put(aliasUser.getUserId(), aliasUser);
            }
            return aliasUserMap;
        }
        return null;
    }
}
