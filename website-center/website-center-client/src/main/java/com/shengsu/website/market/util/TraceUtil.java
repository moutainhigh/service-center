package com.shengsu.website.market.util;

import com.shengsu.website.home.entity.LawcaseConsult;
import com.shengsu.website.market.entity.*;
import com.shengsu.website.market.vo.AlipayDecryptVo;

import java.util.UUID;

/**
 * @program: yuanshou-website-platform
 * @author: Bell
 * @create: 2020-04-08 18:25
 **/
public class TraceUtil {
    public static Decrypt toDecrypt(Trace trace){
        Decrypt decrypt = new Decrypt();
        decrypt.setText(trace.getText());
        decrypt.setSessionKey(trace.getSessionKey());
        return decrypt;
    }
    public static LawcaseConsult toLawcaseConsult(Trace trace , Contact contact){
        LawcaseConsult lawcaseConsult = new LawcaseConsult();
        lawcaseConsult.setConsultId(UUID.randomUUID().toString());
        lawcaseConsult.setContact(contact.getMobile());
        lawcaseConsult.setOrigin(trace.getOrigin());
        lawcaseConsult.setSource(trace.getSource());
        return lawcaseConsult;
    }
    public static LawcaseConsult toLawcaseConsult(WeChatDecrypt weChatDecrypt , String contact){
        LawcaseConsult lawcaseConsult = new LawcaseConsult();
        lawcaseConsult.setConsultId(UUID.randomUUID().toString());
        lawcaseConsult.setContact(contact);
        lawcaseConsult.setOrigin(weChatDecrypt.getOrigin());
        lawcaseConsult.setSource(weChatDecrypt.getSource());
        return lawcaseConsult;
    }
    public static LawcaseConsult toLawcaseConsult(String contact){
        LawcaseConsult lawcaseConsult = new LawcaseConsult();
        lawcaseConsult.setContact(contact);
        return lawcaseConsult;
    }
    public static LawcaseConsult toLawcaseConsult(AlipayDecryptVo alipayDecryptVo,AlipayDecrypt alipayDecrypt){
        LawcaseConsult lawcaseConsult = new LawcaseConsult();
        lawcaseConsult.setConsultId(UUID.randomUUID().toString());
        lawcaseConsult.setContact(alipayDecrypt.getMobile());
        lawcaseConsult.setOrigin(alipayDecryptVo.getOrigin());
        lawcaseConsult.setSource(alipayDecryptVo.getSource());
        return lawcaseConsult;
    }
}
