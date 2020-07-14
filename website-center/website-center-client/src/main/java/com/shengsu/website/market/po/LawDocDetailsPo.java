package com.shengsu.website.market.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-13 15:37
 **/
@Data
public class LawDocDetailsPo implements Serializable {
    private LawDocQueryPo lawDoc;

    private LawDocPreviousPo previousLawDocPo;

    private LawDocPreviousPo nextLawDocPo;
}
