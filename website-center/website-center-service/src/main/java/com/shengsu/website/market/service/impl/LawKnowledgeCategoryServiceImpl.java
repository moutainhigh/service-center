package com.shengsu.website.market.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.impl.BaseServiceImpl;
import com.shengsu.result.ResultBean;
import com.shengsu.result.ResultUtil;
import com.shengsu.website.app.constant.ResultCode;
import com.shengsu.website.market.mapper.LawKnowledgeCategoryMapper;
import com.shengsu.website.market.entity.LawKnowledgeCategory;
import com.shengsu.website.market.po.LawKnowledgeCategoryListPo;
import com.shengsu.website.market.service.LawKnowledgeCategoryService;
import com.shengsu.website.market.util.LawKnowledgeCategoryUtils;
import com.shengsu.website.market.vo.ListCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.shengsu.website.constant.BdappConst.CATEGORY_ROOT_ID;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-04-10 16:04
 **/
@Service("lawKnowledgeCategoryService")
public class LawKnowledgeCategoryServiceImpl extends BaseServiceImpl<LawKnowledgeCategory, String> implements LawKnowledgeCategoryService {
    @Autowired
    private LawKnowledgeCategoryMapper lawKnowledgeCategoryMapper;
    @Override
    public BaseMapper<LawKnowledgeCategory, String> getBaseMapper() {
        return lawKnowledgeCategoryMapper;
    }

    @Override
    public ResultBean listCategory(ListCategoryVo listCategoryVo) {
        //查询到所有的菜单
        List<LawKnowledgeCategory> lawKnowledgeCategories = lawKnowledgeCategoryMapper.listCategory(listCategoryVo.getSystemTag());
        // 构造返回值
        List<LawKnowledgeCategoryListPo>lawKnowledgeCategoryListPos = LawKnowledgeCategoryUtils.toLawKnowledgeCategoryListPos(lawKnowledgeCategories);
        //根节点
        List<LawKnowledgeCategoryListPo> rootMenu = new ArrayList<>();
        //设置根节点
        for (LawKnowledgeCategoryListPo nav : lawKnowledgeCategoryListPos) {
            if (CATEGORY_ROOT_ID.equals(nav.getParentId())) {
                rootMenu.add(nav);
            }
        }
        //为根菜单设置子菜单，getClild递归调用
        for (LawKnowledgeCategoryListPo nav : rootMenu) {
            //获取根节点下的所有子节点 使用getChild方法
            List<LawKnowledgeCategoryListPo> childList = getChild(nav.getCategoryId(), lawKnowledgeCategoryListPos);
            //给根节点设置子节点
            nav.setChildren(childList);
        }
        return ResultUtil.formResult(true, ResultCode.SUCCESS, rootMenu);
    }

    @Override
    public String getNameByCategoryId(String categoryId) {
        return lawKnowledgeCategoryMapper.getNameByCategoryId(categoryId);
    }

    @Override
    public List<LawKnowledgeCategory> getManyByThirdCategoryIds(List<String> thirdCategoryIds) {
        return lawKnowledgeCategoryMapper.getManyByThirdCategoryIds(thirdCategoryIds);
    }

    @Override
    public LawKnowledgeCategory getByCategoryId(String categoryId) {
        return lawKnowledgeCategoryMapper.getByCategoryId(categoryId);
    }

    //递归获取子节点
    private List<LawKnowledgeCategoryListPo> getChild(String categoryId,List<LawKnowledgeCategoryListPo> lawKnowledgeCategoryListPos) {
        //子菜单
        List<LawKnowledgeCategoryListPo> childList = new ArrayList<>();
        for (LawKnowledgeCategoryListPo nav : lawKnowledgeCategoryListPos) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (categoryId.equals(nav.getParentId())) {
                childList.add(nav);
            }
        }
        //递归设置子节点
        for (LawKnowledgeCategoryListPo nav : childList) {
            nav.setChildren(getChild(nav.getCategoryId(), lawKnowledgeCategoryListPos));
        }
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<LawKnowledgeCategoryListPo>();
        }
        return childList;
    }
}
