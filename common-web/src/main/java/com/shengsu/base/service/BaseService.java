package com.shengsu.base.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<M extends Serializable, PK extends Serializable>
{
    /**
     * 
     * @Title: save
     * @Description: 保存模型对象
     * @param @param entity
     * @return int
     * @throws
     */
    public int save(M model);

    /**
     * 
     * @Title: batchSave
     * @Description: 批量保存模型对象
     * @param @param list
     * @param @return
     * @return int
     * @throws
     */
    public int batchSave(List<M> list);    

    /**
     * 
     * @Title: update
     * @Description: 更新模型对象
     * @param @param entity
     * @return int
     * @throws
     */
    public int update(M model);
    
    /**
     * 
     * @Title: batchUpdate
     * @Description: 批量更新模型对象
     * @param @param list
     * @param @return
     * @return int
     * @throws
     */
    public int batchUpdate(List<M> list);

    /**
     * 
     * @Title: delete
     * @Description: 根据主键删除模型对象
     * @param @param id
     * @return int
     * @throws
     */
    public int delete(PK id);

    /**
     * 
     * @Title: batchDelete
     * @Description: 根据主键列表批量删除模型对象
     * @param @param list
     * @return int
     * @throws
     */
    public int batchDelete(List<PK> list);

    /**
     * 
     * @Title: delete
     * @Description: 根据主键软删除模型对象
     * @param @param id
     * @return int
     * @throws
     */
    public int softDelete(PK id);

    /**
     * 
     * @Title: batchDelete
     * @Description: 根据主键列表批量软删除模型对象
     * @param @param list
     * @return int
     * @throws
     */
    public int batchSoftDelete(List<PK> list);

    /**
     * 
     * @Title: get
     * @Description: 根据主键获取模型对象
     * @param @param id
     * @param @return
     * @return M
     * @throws
     */
    public M get(PK id);

    /**
     * 
     * @Title: getOne
     * @Description: 根据模型对象中的条件获取模型对象
     * @param @param entity
     * @param @return
     * @return M
     * @throws
     */
    public M getOne(M model);
    
    /**
     * 
     * @Title: getMany
     * @Description: 根据主键获取多个模型对象
     * @param @param List<PK>
     * @param @return
     * @return List<M>
     * @throws
     */
    public List<M> getMany(List<PK> list);

    /**
     * 
     * @Title: countAll
     * @Description: 统计模型对象在数据库表中的记录数
     * @param @return
     * @return int
     * @throws
     */
    public int countAll(M model);

    /**
     * 
     * @Title: listAll
     * @Description: 查询所有模型对象
     * @param @return
     * @return List<M>
     * @throws
     */
    public List<M> listAll();

    /**
     * 
     * @Title: listByPage
     * @Description: 分页查询所有模型对象
     * @param @param entity
     * @param @return
     * @return List<M>
     * @throws
     */
    public List<M> listByPage(M model);

    /**
     * 
     * @Title: listByModel
     * @Description: 根据模型条件查询所有
     * @param @param entity
     * @param @return
     * @return List<M>
     * @throws
     */
    public List<M> listByModel(M model);
    
    /**
     * 
     * @Title: getItemIndex
     * @Description: 获取指定数据在第几行
     * @param @param entity
     * @param @return
     * @return int
     * @throws
     */
    public int getItemIndex(M model);
}
