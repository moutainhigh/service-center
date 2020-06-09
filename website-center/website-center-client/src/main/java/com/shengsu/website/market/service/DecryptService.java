package com.shengsu.website.market.service;

import com.shengsu.website.market.entity.Decrypt;
import com.shengsu.website.market.entity.WeChatDecrypt;

public interface DecryptService {
    String decrypt(Decrypt decrypt);
    String getPhoneNumber(WeChatDecrypt weChatDecrypt);
}
