package com.shengsu.website.app.constant;


/**
 * @author zxh
 * @ClassName: BizConst
 * @Description: 业务常量
 */
public interface BizConst {
    //法律头条-随机数
    Integer LAW_HEADLINES_RANDOM_COUNT = 10;
    // 系统标记-胜诉
    String SYSTEM_TAG_SHENGSU = "shengsu";
    //系统标记-律师傅
    String SYSTEM_TAG_LVSHIFU = "lvshifu";
    String SYSTEM_TAG_YUANSHOU = "yuanshou";
    // 操作类型-创建
    String OPERATE_TYPE_CREATE = "create";
    // 操作类型-修改
    String OPERATE_TYPE_UPDATE = "update";
    // 操作类型-批量创建
    String OPERATE_TYPE_BATCHCREATE = "batchCreate";
    // 知识文库索引名称
    String LAWKNOWLEDGE_INDEX_NAME = "website-center";
    // 知识文库索引类型
    String LAWKNOWLEDGE_INDEX_TYPE = "law_knowledge";
    // 新闻中心索引名称
    String NEWSCENTERE_INDEX_NAME = "website-center";
    // 新闻中心索引类型
    String NEWSCENTER_INDEX_TYPE = "news_center";
    //文档类型-人力资源库
    String DICT_CODE_DOC_TYPE_HUMAN = "human_doc_type";
    //文档子类型-合同库
    String DICT_CODE_DOC_TYPE_CONTRACT = "contract_doc_type";
}
