package com.shengsu.website.market.service;

import com.shengsu.website.market.entity.Decrypt;
import com.shengsu.website.market.entity.WeChatDecrypt;
import com.shengsu.website.market.vo.AlipayDecryptVo;

public interface DecryptService {
    String decrypt(Decrypt decrypt);
    String getPhoneNumber(WeChatDecrypt weChatDecrypt);
    String aliDecrypt(AlipayDecryptVo alipayDecryptVo);
}
