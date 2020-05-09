package com.shengsu.website.decrypt.util;

import com.shengsu.website.decrypt.entity.Contact;
import com.shengsu.website.decrypt.entity.Decrypt;
import com.shengsu.website.decrypt.entity.Trace;
import com.shengsu.website.home.entity.LawcaseConsult;

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
}
