package com.shengsu.website.decrypt.service;

import com.shengsu.website.decrypt.entity.Decrypt;
import com.shengsu.website.decrypt.entity.WeChatDecrypt;

public interface DecryptService {
    String decrypt(Decrypt decrypt);
    String getPhoneNumber(WeChatDecrypt weChatDecrypt);
}
