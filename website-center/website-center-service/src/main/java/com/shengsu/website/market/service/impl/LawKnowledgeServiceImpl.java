package com.shengsu.website.market.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.constant.CommonConst;
import com.shengsu.helper.constant.MQEnum;
import com.shengsu.helper.service.MQProducerService;
import com.shengsu.helper.service.OssService;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.user.entity.User;
import com.shengsu.user.service.UserService;
import com.shengsu.user.util.UserUtils;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.market.entity.LawKnowledge;
import com.shengsu.website.market.entity.LawKnowledgeCategory;
import com.shengsu.website.market.mapper.LawKnowledgeMapper;
import com.shengsu.website.market.po.*;
import com.shengsu.website.market.service.LawKnowledgeCategoryService;
import com.shengsu.website.market.service.LawKnowledgeService;
import com.shengsu.website.market.util.KeyWordUtils;
import com.shengsu.website.market.util.LawKnowledgeCategoryUtils;
import com.shengsu.website.market.util.LawKnowledgeUtils;
import com.shengsu.website.market.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.shengsu.website.app.constant.BizConst.*;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-10 18:03
 **/
@Service("lawKnowledgeService")
public class LawKnowledgeServiceImpl extends BaseServiceImpl<LawKnowledge, String> implements LawKnowledgeService {
    @Value("${lawknowledge.picture-range}")
    private int pictureRange;
    @Autowired
    private MQProducerService mqProducerService;
    @Autowired
    private LawKnowledgeCategoryService lawKnowledgeCategoryService;
    @Autowired
    private OssService ossService;
    @Autowired
    private LawKnowledgeMapper lawKnowledgeMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public BaseMapper<LawKnowledge, String> getBaseMapper() {
        return lawKnowledgeMapper;
    }

    /**
    * @Description: boss运营后台创建法律文库
    * @Param: * @Param lawKnowledgeCreateVo: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean create(LawKnowledgeCreateVo lawKnowledgeCreateVo) {
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByTitle(lawKnowledgeCreateVo.getTitle());
        if (lawKnowledge != null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_DATA_REPEAT, null);
        }

        lawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledgeCreateVo);
        String knowledgeId = UUID.randomUUID().toString();
        lawKnowledge.setKnowledgeId(knowledgeId);
        lawKnowledgeMapper.save(lawKnowledge);
        // 发送ES mq消息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("knowledgeId",knowledgeId);
        jsonObject.put("operateType",OPERATE_TYPE_CREATE);
        mqProducerService.send(MQEnum.ELASTICSEARCH_LAWKNOWLEDGE, JSON.toJSONString(jsonObject));
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }
    /**
     * @Description: boss运营后台删除法律文库
     * @Param: * @Param lawKnowledgeCreateVo:
     * @Return: * @return: com.shengsu.result.ResultBean
     * @date:
     */
    @Override
    public ResultBean remove(LawKnowledgeDeleteVo lawKnowledgeDeleteVo) {
        String knowledgeId = lawKnowledgeDeleteVo.getKnowledgeId();
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByKnowledgeId(knowledgeId);
        if (lawKnowledge == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_ID_ERROR, null);
        }

        lawKnowledgeMapper.delete(knowledgeId);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }
    /**
     * @Description: boss运营后台编辑法律文库
     * @Param: * @Param lawKnowledgeCreateVo:
     * @Return: * @return: com.shengsu.result.ResultBean
     * @date:
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultBean edit(LawKnowledgeUpdateVo lawKnowledgeUpdateVo) {
        String knowledgeId = lawKnowledgeUpdateVo.getKnowledgeId();
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByKnowledgeId(knowledgeId);
        if (lawKnowledge == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_ID_ERROR, null);
        }
        lawKnowledge = lawKnowledgeMapper.selectByTitle(lawKnowledgeUpdateVo.getTitle());
        if (lawKnowledge != null && !lawKnowledge.getKnowledgeId().equals(knowledgeId)) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_DATA_REPEAT, null);
        }
        lawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledgeUpdateVo);
        lawKnowledgeMapper.update(lawKnowledge);
        // 发送ES mq消息(es中保存一份)
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("knowledgeId",knowledgeId);
        jsonObject.put("title",lawKnowledge.getTitle());
        jsonObject.put("content",lawKnowledge.getContent());
        jsonObject.put("operateType",OPERATE_TYPE_UPDATE);
        mqProducerService.send(MQEnum.ELASTICSEARCH_LAWKNOWLEDGE, JSON.toJSONString(jsonObject));
        return ResultUtil.formResult(true, ResultCode.SUCCESS, null);
    }

    @Override
    public ResultBean query(LawKnowledgeQueryVo lawKnowledgeQueryVo) {
        String knowledgeId = lawKnowledgeQueryVo.getKnowledgeId();
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByKnowledgeId(knowledgeId);
        if (lawKnowledge == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_ID_ERROR, null);
        }

        List<String> nodeIds = LawKnowledgeUtils.toNodeIds(lawKnowledge);
        List<LawKnowledgeCategory> lawKnowledgeCategories = lawKnowledgeCategoryService.getMany(nodeIds);
        Map<String, LawKnowledgeCategory> nodeMap = LawKnowledgeUtils.toNodeMap(lawKnowledgeCategories);
        LawKnowledgeQueryPo lawKnowledgeQueryPo = LawKnowledgeUtils.toLawKnowledgeQueryPo(lawKnowledge, nodeMap);


        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawKnowledgeQueryPo);
    }

    @Override
    public ResultBean<LawKnowledgeQueryPo> queryWithKeyWord(LawKnowledgeQueryVo lawKnowledgeQueryVo) {
        LawKnowledgeQueryPo lawKnowledgeQueryPo = (LawKnowledgeQueryPo) query(lawKnowledgeQueryVo).getBody();
        if (lawKnowledgeQueryPo == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_ID_ERROR, null);
        }
        KeyWordUtils.addKeyWord(lawKnowledgeQueryPo, lawKnowledgeQueryVo.getCity(), lawKnowledgeQueryPo.getSource());
        return ResultUtil.formResult(true,ResultCode.SUCCESS,lawKnowledgeQueryPo);
    }
    /**
    * @Description: boss运营后后台分页查询法律知识文库
    * @Param: * @Param lawKnowledgeListByPageVo:
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date:
    */
    @Override
    public ResultBean listKnowledgeByPage(LawKnowledgeListByPageVo lawKnowledgeListByPageVo) {
        LawKnowledge lawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledgeListByPageVo);
        Integer count = lawKnowledgeMapper.countAll(lawKnowledge);
        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.listByPage(lawKnowledge);
            List<String> creators = LawKnowledgeUtils.toListCreator(lawKnowledges);
            List<User> users = userService.getMany(creators);
            Map<String, User> map = UserUtils.toUserMap(users);
            for (LawKnowledge knowledge : lawKnowledges) {
                User user = map.get(knowledge.getCreator());
                if (user != null) {
                    String creator = user.getRealName();
                    knowledge.setCreator(creator);
                }
            }

            List<String> nodeIds = new ArrayList<>();
            for (LawKnowledge knowledge : lawKnowledges) {
                String firstCategoryId = knowledge.getFirstCategoryId();
                String secondCategoryId = knowledge.getSecondCategoryId();
                String thirdCategoryId = knowledge.getThirdCategoryId();
                Collections.addAll(nodeIds, firstCategoryId, secondCategoryId, thirdCategoryId);
            }
            List<LawKnowledgeCategory> lawKnowledgeCategories = lawKnowledgeCategoryService.getMany(nodeIds);
            Map<String, LawKnowledgeCategory> nodeMap = LawKnowledgeUtils.toNodeMap(lawKnowledgeCategories);

            List<LawKnowledgePagePo> lawKnowledgePagePos = LawKnowledgeUtils.toLawKnowledgePagePos(lawKnowledges, nodeMap);
            resultMap.put(CommonConst.ROOT, lawKnowledgePagePos);
            resultMap.put(CommonConst.TOTAL_COUNT, count);
        }

        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }
    /**
    * @Description: 客户端分页展示法律知识文库
    * @Param: * @Param lawKnowledgeListPageVo: 
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date: 
    */
    @Override
    public ResultBean listPage(LawKnowledgeListPageVo lawKnowledgeListPageVo) {
        String firstCategoryId = lawKnowledgeListPageVo.getFirstCategoryId();
        String secondCategoryId = lawKnowledgeListPageVo.getSecondCategoryId();
        String thirdCategoryId = lawKnowledgeListPageVo.getThirdCategoryId();
        LawKnowledge lawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledgeListPageVo);
        Map<String, Object> resultMap = new HashMap<>();
        // 三级类目不为空
        if (StringUtils.isNotBlank(thirdCategoryId)) {
            // 包含三级数据不为空,三级数据为空,一级分类和二级分类为空,二级分类不为空
            Integer count = lawKnowledgeMapper.countThirdNotNull(lawKnowledge);
            if (count > 0) {
                List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.listPageThirdNotNull(lawKnowledge);
                if (null == lawKnowledges || lawKnowledges.size() == 0) {
                    return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
                }
                List<LawKnowledgeListPagePo> lawKnowledgeListPagePos = LawKnowledgeUtils.toLawKnowledgeListPagePos(lawKnowledges);
                resultMap.put(CommonConst.ROOT, lawKnowledgeListPagePos);
                resultMap.put(CommonConst.TOTAL_COUNT, count);
            }
        }
        // 三级类目数据为空,二级类目不为空
        if (StringUtils.isBlank(thirdCategoryId) && StringUtils.isNotBlank(secondCategoryId)) {
            // 包含一级分类和二级分类为空和二级分类不为空
            Integer count = lawKnowledgeMapper.countSecondNotNull(lawKnowledge);
            if (count > 0) {
                List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.listPageSecondNotNull(lawKnowledge);
                if (null == lawKnowledges || lawKnowledges.size() == 0) {
                    return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
                }
                List<LawKnowledgeListPagePo> lawKnowledgeListPagePos = LawKnowledgeUtils.toLawKnowledgeListPagePos(lawKnowledges);
                resultMap.put(CommonConst.ROOT, lawKnowledgeListPagePos);
                resultMap.put(CommonConst.TOTAL_COUNT, count);
            }
        }
        // 二级分类为空,一级分类不为空
        if (StringUtils.isBlank(secondCategoryId)) {
            // 包含 一级分类所有的
            Integer count = lawKnowledgeMapper.countFirstNotNull(lawKnowledge);
            if (count > 0) {
                List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.listPageFirstNotNull(lawKnowledge);
                if (null == lawKnowledges || lawKnowledges.size() == 0) {
                    return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
                }
                List<LawKnowledgeListPagePo> lawKnowledgeListPagePos = LawKnowledgeUtils.toLawKnowledgeListPagePos(lawKnowledges);
                resultMap.put(CommonConst.ROOT, lawKnowledgeListPagePos);
                resultMap.put(CommonConst.TOTAL_COUNT, count);
            }
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }
    /**
    * @Description: 全文搜索分页查询
    * @Param: * @Param lawKnowledgeListPageVo:
    * @Return: * @return: com.shengsu.result.ResultBean
    * @date:
    */
    // 增加全文搜索分页查询
    @Override
    public ResultBean fullTextSearchListPage(FullTextSearchListPageVo fullTextSearchListPageVo) {
        LawKnowledge lawKnowledge = LawKnowledgeUtils.toLawKnowledge(fullTextSearchListPageVo);
        Integer count = lawKnowledgeMapper.countFullTextSearch(lawKnowledge);
        Map<String, Object> resultMap = new HashMap<>();
        if (count > 0) {
            List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.fullTextSearchListPage(lawKnowledge);
            if (null == lawKnowledges || lawKnowledges.size() == 0) {
                return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
            }
            List<LawKnowledgeListPagePo> lawKnowledgeListPagePos = LawKnowledgeUtils.toLawKnowledgeListPagePos(lawKnowledges);
            resultMap.put(CommonConst.ROOT, lawKnowledgeListPagePos);
            resultMap.put(CommonConst.TOTAL_COUNT, count);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, resultMap);
    }

    @Override
    public int updatePv(String knowledgeId) {
        return lawKnowledgeMapper.updatePv(knowledgeId);
    }

    @Override
    public ResultBean getDetails(LawKnowledgeDetailsVo lawKnowledgeDetailsVo) {
        String knowledgeId = lawKnowledgeDetailsVo.getKnowledgeId();
        LawKnowledge lawKnowledge = lawKnowledgeMapper.selectByKnowledgeId(knowledgeId);
        if (lawKnowledge == null) {
            return ResultUtil.formResult(false, ResultCode.LAW_KNOWLEDGE_ID_ERROR, null);
        }
        List<String> nodeIds = LawKnowledgeUtils.toNodeIds(lawKnowledge);
        List<LawKnowledgeCategory> lawKnowledgeCategories = lawKnowledgeCategoryService.getMany(nodeIds);
        Map<String, LawKnowledgeCategory> nodeMap = LawKnowledgeUtils.toNodeMap(lawKnowledgeCategories);

        //获取当前
        LawKnowledgeCurrentPo lawKnowledgeCurrentPo = LawKnowledgeUtils.toLawKnowledgeCurrentPo(lawKnowledge, nodeMap);
        lawKnowledgeCurrentPo.setPictureOssUrl(getNoRepeatRandom(1).get(0).toString());
        LawKnowledgeDetailsPo lawKnowledgeDetailsPo = new LawKnowledgeDetailsPo();
        lawKnowledgeDetailsPo.setLawKnowledgeCurrentPo(lawKnowledgeCurrentPo);

        //获取上一篇
        LawKnowledge paramLawKnowledge = LawKnowledgeUtils.toLawKnowledge(lawKnowledge);
        // 先判断此分类属于哪级分类  前一篇的话就是找时间最挨近的最小的,后一篇就找时间最挨近的最大的
        //当前分类属于第三级分类
        LawKnowledge previousLawKnowledge = null;
        if (StringUtils.isNotBlank(lawKnowledge.getThirdCategoryId())) {
            // 先获取当前分类下的上一篇,如果为空,获取二级分类下的知识文库,如果还未空 获取一级分类下的知识文库
            previousLawKnowledge = lawKnowledgeMapper.selectThirdPreviousLawKnowledge(paramLawKnowledge);
            if (null == previousLawKnowledge) {
                // 三级分类下为空,获取二级分类下的文章
                previousLawKnowledge = lawKnowledgeMapper.selectSecondPreviousLawKnowledge(paramLawKnowledge);
                if (null == previousLawKnowledge) {
                    // 二级分类为空,获取一级分类下的文章
                    previousLawKnowledge = lawKnowledgeMapper.selectFirstPreviousLawKnowledge(paramLawKnowledge);
                }
            }
        }
        // 当前分类属于二级分类
        if (StringUtils.isBlank(lawKnowledge.getThirdCategoryId()) && StringUtils.isNotBlank(lawKnowledge.getSecondCategoryId())) {
            // 先获取当前分类下的上一篇,如果未空 获取一级分类下的知识文库
            previousLawKnowledge = lawKnowledgeMapper.selectSecondPreviousLawKnowledge(paramLawKnowledge);
//            if (null == previousLawKnowledge) {
//                // 二级分类为空,获取一级分类下的文章
//                previousLawKnowledge = lawKnowledgeMapper.selectFirstPreviousLawKnowledge(paramLawKnowledge);
//            }

        }
        // 当前分类属于一级分类
        if (StringUtils.isBlank(lawKnowledge.getThirdCategoryId()) && StringUtils.isBlank(lawKnowledge.getSecondCategoryId()) && StringUtils.isNotBlank(lawKnowledge.getFirstCategoryId())) {
            previousLawKnowledge = lawKnowledgeMapper.selectFirstPreviousLawKnowledge(paramLawKnowledge);
        }
        LawKnowledgePreviousPo lawKnowledgePreviousPo = LawKnowledgeUtils.toLawKnowledgePreviousPo(previousLawKnowledge);
        lawKnowledgeDetailsPo.setLawKnowledgePreviousPo(lawKnowledgePreviousPo);
        // 获取下一篇
        //当前分类属于第三级分类
        LawKnowledge nextLawKnowledge = null;
        if (StringUtils.isNotBlank(lawKnowledge.getThirdCategoryId())) {
            // 先获取当前分类下的上一篇,如果为空,获取二级分类下的知识文库,如果还未空 获取一级分类下的知识文库
            nextLawKnowledge = lawKnowledgeMapper.selectThirdNextLawKnowledge(paramLawKnowledge);
            if (null == nextLawKnowledge) {
                // 三级分类下为空,获取二级分类下的文章
                nextLawKnowledge = lawKnowledgeMapper.selectSecondNextLawKnowledge(paramLawKnowledge);
                if (null == nextLawKnowledge) {
                    // 二级分类为空,获取一级分类下的文章
                    nextLawKnowledge = lawKnowledgeMapper.selectFirstNextLawKnowledge(paramLawKnowledge);
                }
            }
        }
        // 当前分类属于二级分类
        if (StringUtils.isBlank(lawKnowledge.getThirdCategoryId()) && StringUtils.isNotBlank(lawKnowledge.getSecondCategoryId())) {
            // 先获取当前分类下的上一篇,如果未空 获取一级分类下的知识文库
            nextLawKnowledge = lawKnowledgeMapper.selectSecondNextLawKnowledge(paramLawKnowledge);
//            if (null == nextLawKnowledge) {
//                // 二级分类为空,获取一级分类下的文章
//                nextLawKnowledge = lawKnowledgeMapper.selectFirstNextLawKnowledge(paramLawKnowledge);
//            }

        }
        // 当前分类属于一级分类
        if (StringUtils.isBlank(lawKnowledge.getThirdCategoryId()) && StringUtils.isBlank(lawKnowledge.getSecondCategoryId()) && StringUtils.isNotBlank(lawKnowledge.getFirstCategoryId())) {
            nextLawKnowledge = lawKnowledgeMapper.selectFirstNextLawKnowledge(paramLawKnowledge);
        }
        LawKnowledgeNextPo lawKnowledgeNextPo = LawKnowledgeUtils.toLawKnowledgeNextPo(nextLawKnowledge);
        lawKnowledgeDetailsPo.setLawKnowledgeNextPo(lawKnowledgeNextPo);
        KeyWordUtils.addKeyWord(lawKnowledgeDetailsPo, lawKnowledgeDetailsVo.getCity(), lawKnowledgeDetailsVo.getSource());

        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawKnowledgeDetailsPo);
    }

    @Override
    public ResultBean getLatestThree() {
        List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.getLatestThree();
        List<LawKnowledgeCategory> lawKnowledgeCategories = new ArrayList<>();
        for (LawKnowledge lawKnowledge : lawKnowledges) {
            if (StringUtils.isNotBlank(lawKnowledge.getThirdCategoryId())) {
                // 三级分类不为空,获取知识库类目
                LawKnowledgeCategory lawKnowledgeCategory = lawKnowledgeCategoryService.getByCategoryId(lawKnowledge.getThirdCategoryId());
                lawKnowledgeCategories.add(lawKnowledgeCategory);
            } else if (StringUtils.isBlank(lawKnowledge.getThirdCategoryId()) && StringUtils.isNotBlank(lawKnowledge.getSecondCategoryId())) {
                // // 三级分类为空,二级分类不为空 获取知识库类目
                LawKnowledgeCategory lawKnowledgeCategory = lawKnowledgeCategoryService.getByCategoryId(lawKnowledge.getSecondCategoryId());
                lawKnowledgeCategories.add(lawKnowledgeCategory);
            } else if (StringUtils.isBlank(lawKnowledge.getThirdCategoryId()) && StringUtils.isBlank(lawKnowledge.getSecondCategoryId()) && StringUtils.isNotBlank(lawKnowledge.getFirstCategoryId())) {
                // // 三级分类为空,二级分类为空,一级分类不为空 获取知识库类目
                LawKnowledgeCategory lawKnowledgeCategory = lawKnowledgeCategoryService.getByCategoryId(lawKnowledge.getFirstCategoryId());
                lawKnowledgeCategories.add(lawKnowledgeCategory);
            }
        }
        Map<String, LawKnowledgeCategory> lawKnowledgeCategoryMap = LawKnowledgeCategoryUtils.toLawKnowledgeCategoryMap(lawKnowledgeCategories);
        List<Integer> itemList = getNoRepeatRandom(lawKnowledges.size());
        List<LawKnowledgeSimplePo> lawKnowledgeSimplePos = LawKnowledgeUtils.toLawKnowledgeSimplePos(lawKnowledges, lawKnowledgeCategoryMap, itemList);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawKnowledgeSimplePos);
    }

    /**
     * @Description: 获取指定范围内的随机不重复数
     * @Param: * @Param size:
     * @Return: * @return: java.util.HashSet<java.lang.Integer>
     * @date:
     */
    private List<Integer> getNoRepeatRandom(int size) {
        HashSet<Integer> hashSet = new HashSet<>();
        Integer i = 0;
        for (int j = 1; j != 0; j++) {
            i = (int) (Math.random() * pictureRange) + 1;
            hashSet.add(i);
            if (size == hashSet.size()) {
                break;
            }
        }
        return new ArrayList<>(hashSet);
    }

    @Override
    public ResultBean getRandomTitles() {
        List<LawKnowledge> lawKnowledges = lawKnowledgeMapper.getAllTitle();
        Collections.shuffle(lawKnowledges);
        List<LawKnowledge> result = new ArrayList<>();
        for (int i = 0; i < lawKnowledges.size(); i++) {
            if (i >= LAW_HEADLINES_RANDOM_COUNT)
                break;
            result.add(lawKnowledges.get(i));
        }
        List<LawKnowledgePo> lawKnowledgePos = LawKnowledgeUtils.toLawknowledgePO(result);
        return ResultUtil.formResult(true, ResultCode.SUCCESS, lawKnowledgePos);
    }
    /**
     * es 搜索引擎
     * 根据文档内容字段分页查询
     */
    @Override
    public ResultBean esContentFieldListByPage(EsListByPageVo esListByPageVo){
        // 获取参数
        Integer page = esListByPageVo.getPage();
        Integer pageSize = esListByPageVo.getPageSize();
        String content = esListByPageVo.getContent();
        // 开始分页组装
        Pageable pageable = PageRequest.of(page,pageSize);
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("content", content))
                .withPageable(pageable).build();
        AggregatedPage<LawKnowledge> pageLawKnowledge = elasticsearchTemplate.queryForPage(query, LawKnowledge.class);
        List<LawKnowledge> lawKnowledges = pageLawKnowledge.getContent();
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, lawKnowledges, (int) pageLawKnowledge.getTotalElements());
    }

    /**
     * 多字段匹配
     * 多字段中完全匹配
     */
    public ResultBean esManyFieldsListByPage(LawKnowledgeListByPageVo lawKnowledgeListByPageVo) {
        // 获取参数
        Integer page = lawKnowledgeListByPageVo.getPage();
        Integer pageSize = lawKnowledgeListByPageVo.getPageSize();
        // 开始分页组装
        Pageable pageable = PageRequest.of(page, pageSize);
        //查询结果进行排序
        SortBuilder sortBuilder = new FieldSortBuilder("date_time")
                .order(SortOrder.DESC);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(lawKnowledgeListByPageVo.getTitle())) {
            MatchQueryBuilder matchQueryBuilder = matchQuery("title", lawKnowledgeListByPageVo.getTitle());
            boolQueryBuilder.must(matchQueryBuilder);
        }
        if (StringUtils.isNotBlank(lawKnowledgeListByPageVo.getCreator())) {
            TermQueryBuilder termQueryBuilder = termQuery("creator", lawKnowledgeListByPageVo.getCreator());
            boolQueryBuilder.must(termQueryBuilder);
        }
        if (StringUtils.isNotBlank(lawKnowledgeListByPageVo.getFirstCategoryId())) {
            TermQueryBuilder termQueryBuilder = termQuery("first_category_id", lawKnowledgeListByPageVo.getFirstCategoryId());
            boolQueryBuilder.must(termQueryBuilder);
        }
        if (StringUtils.isNotBlank(lawKnowledgeListByPageVo.getSecondCategoryId())) {
            TermQueryBuilder termQueryBuilder = termQuery("second_category_id", lawKnowledgeListByPageVo.getSecondCategoryId());
            boolQueryBuilder.must(termQueryBuilder);
        }
        if (StringUtils.isNotBlank(lawKnowledgeListByPageVo.getThirdCategoryId())) {
            TermQueryBuilder termQueryBuilder = termQuery("third_category_id", lawKnowledgeListByPageVo.getThirdCategoryId());
            boolQueryBuilder.must(termQueryBuilder);
        }
        if (StringUtils.isNotBlank(lawKnowledgeListByPageVo.getCreateStartTime()) && StringUtils.isNotBlank(lawKnowledgeListByPageVo.getCreateEndTime())) {
            RangeQueryBuilder rangeQueryBuilder = rangeQuery("create_time").from(lawKnowledgeListByPageVo.getCreateStartTime()).to(lawKnowledgeListByPageVo.getCreateEndTime());
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        SearchQuery query = queryBuilder
                .withQuery(boolQueryBuilder)
                .withSort(sortBuilder)
                .withPageable(pageable).build();
        AggregatedPage<LawKnowledge> pageLawKnowledge = elasticsearchTemplate.queryForPage(query, LawKnowledge.class);
        List<LawKnowledge> lawKnowledges = pageLawKnowledge.getContent();
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, lawKnowledges, (int) pageLawKnowledge.getTotalElements());
    }
    /**
     * 根据文档内容字段分页查询并排序
     */
    public ResultBean esContentFieldListByPageSort(EsListByPageVo esListByPageVo){
        // 获取参数
        Integer page = esListByPageVo.getPage();
        Integer pageSize = esListByPageVo.getPageSize();
        String content = esListByPageVo.getContent();
        // 开始分页组装
        Pageable pageable = PageRequest.of(page,pageSize);
        //查询结果进行排序
        SortBuilder sortBuilder = new FieldSortBuilder("dateTime")
                .order(SortOrder.DESC);
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("content", content))
                .withSort(sortBuilder)
                .withPageable(pageable).build();
        AggregatedPage<LawKnowledge> pageLawKnowledge = elasticsearchTemplate.queryForPage(query,LawKnowledge.class);
        List<LawKnowledge> lawKnowledges = pageLawKnowledge.getContent();
        return ResultUtil.formPageResult(true, ResultCode.SUCCESS, lawKnowledges,(int)pageLawKnowledge.getTotalElements());
    }
}
