package com.shengsu.website.market.service;

import com.shengsu.base.service.BaseService;
import com.shengsu.result.ResultBean;
import com.shengsu.website.market.entity.LawDoc;
import com.shengsu.website.market.vo.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-13 17:02
 **/
public interface LawDocService extends BaseService<LawDoc,String> {
    ResultBean create(LawDocCreateVo lawDocCreateVo);
    ResultBean remove(LawDocDeleteVo lawDocDeleteVo);
    ResultBean edit(LawDocUpdateVo lawDocUpdateVo);
    ResultBean query(LawDocQueryVo lawDocQueryVo);
    ResultBean ListLawDocByPage(ListLawDocByPageVo listLawDocByPageVo);
    ResultBean ListLawDoc(ListLawDocVo listLawDocVo);
    ResultBean getDetails(LawDocDetailsVo lawDocDetailsVo);
    ResultBean updateDownloads(String docId);
}
