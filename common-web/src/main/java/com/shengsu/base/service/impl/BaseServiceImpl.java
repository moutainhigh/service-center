package com.shengsu.base.service.impl;

import com.shengsu.base.mapper.BaseMapper;
import com.shengsu.base.service.BaseService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class BaseServiceImpl<M extends Serializable, PK extends Serializable> implements
        BaseService<M, PK>
{
    /**
     * @return baseMapper
     */
    public abstract BaseMapper<M, PK> getBaseMapper();

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int save(M model)
    {
        return this.getBaseMapper().save(model);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int update(M model)
    {
        return this.getBaseMapper().update(model);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int delete(PK id)
    {
        return this.getBaseMapper().delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int batchDelete(List<PK> list)
    {
        return this.getBaseMapper().batchDelete(list);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int softDelete(PK id)
    {
        return this.getBaseMapper().softDelete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int batchSoftDelete(List<PK> list)
    {
        return this.getBaseMapper().batchSoftDelete(list);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public M get(PK id)
    {
        return this.getBaseMapper().get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public M getOne(M model)
    {
        return this.getBaseMapper().getOne(model);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<M> getMany(List<PK> list)
    {
        return this.getBaseMapper().getMany(list);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public int countAll(M model)
    {
        return this.getBaseMapper().countAll(model);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<M> listAll()
    {
        return this.getBaseMapper().listAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<M> listByPage(M model)
    {
        return this.getBaseMapper().listByPage(model);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<M> listByModel(M model)
    {
        return this.getBaseMapper().listByModel(model);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public int getItemIndex(M model)
    {
        return getBaseMapper().getItemIndex(model);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int batchSave(List<M> list)
    {
        return this.getBaseMapper().batchSave(list);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int batchUpdate(List<M> list)
    {
        return this.getBaseMapper().batchUpdate(list);
    }
}
