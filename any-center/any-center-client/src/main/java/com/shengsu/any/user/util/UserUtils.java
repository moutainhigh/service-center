package com.shengsu.any.user.util;

import com.shengsu.any.user.entity.User;
import com.shengsu.any.user.po.UserDetailsPo;
import com.shengsu.any.user.vo.UserBandVo;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class UserUtils {
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
}
