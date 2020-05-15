package com.shengsu.helper.service;

import com.shengsu.helper.entity.SecurityToken;

public interface StsService {
    SecurityToken getToken();
    SecurityToken getToken(String fileDir);
}
