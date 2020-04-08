package com.shengsu.website.home.po;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zyc on 2019/9/17.
 */
@Data
public class SuccessCaseDetailsPo implements Serializable {

    private SuccessCaseQueryPo successCase;

    private SuccessCasePreviousPo previousSuccessCase;

    private SuccessCasePreviousPo nextSuccessCase;
}
