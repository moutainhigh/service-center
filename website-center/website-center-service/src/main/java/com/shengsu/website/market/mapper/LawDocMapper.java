package com.shengsu.website.market.mapper;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.website.market.entity.LawDoc;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LawDocMapper extends BaseMapper<LawDoc, String> {
    LawDoc selectByFullName(String docName);

    LawDoc selectByDocId(String docId);

    Integer countLawDocByPage(LawDoc lawDoc);

    List<LawDoc> selectLawDocByPage(LawDoc lawDoc);

    LawDoc selectPreviousLawDoc(LawDoc lawDoc);

    LawDoc selectNextLawDoc(LawDoc lawDoc);

    int updateDownloads(String docId);

    int existCheckExceptSelf(LawDoc lawDoc);

    List<LawDoc> listLawDoc(LawDoc lawDoc);
}
