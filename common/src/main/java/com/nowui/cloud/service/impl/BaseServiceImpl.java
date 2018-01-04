package com.nowui.cloud.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.nowui.cloud.util.Util;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.nowui.cloud.entity.BaseEntity;
import com.nowui.cloud.mapper.BaseMapper;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ZhongYongQiang
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> {

    @Autowired
    protected M mapper;

    @Autowired
    private T entity;

    @Autowired
    protected RedisTemplate<String, Object> redis;

    protected String getItemCacheName(String id) {
        return entity.getTableName() + "_item_" + id;
    }

    public Integer count(@Param("ew") Wrapper<T> var1) {
        Integer count = mapper.selectCount(var1);
        return count;
    }

    public List<T> list(@Param("ew") Wrapper<T> var1, Integer m, Integer n) {
        List<T> list = mapper.selectPage(new Page<T>(m, n), var1.setSqlSelect(entity.getTableId()));

        for (T baseEntity : list) {
            baseEntity.putAll(find(baseEntity.getString(entity.getTableId())));
        }

        return list;
    }

    public List<T> list(@Param("ew") Wrapper<T> var1) {
        List<T> list = mapper.selectList(var1.setSqlSelect(entity.getTableId()));

        for (T baseEntity : list) {
            baseEntity.putAll(find(baseEntity.getString(entity.getTableId())));
        }

        return list;
    }

    public T find(String id) {
        T baseEntity = (T) redis.opsForValue().get(getItemCacheName(id));

        if (baseEntity == null) {
            baseEntity = mapper.selectById(id);

            if (baseEntity != null) {
                redis.opsForValue().set(entity.getTableName(), baseEntity);
            }
        }

        return baseEntity;
    }

    public T find(String id, Boolean systemStatus) {
        T baseEntity = (T) redis.opsForValue().get(getItemCacheName(id));

        if (baseEntity == null) {
            List<T> list = mapper.selectList(
                    new EntityWrapper<T>()
                            .eq(entity.getTableId(), id)
                            .eq(BaseEntity.SYSTEM_STATUS, systemStatus)
            );
            if (list.size() == 0) {
                return null;
            } else {
                baseEntity = list.get(0);

                redis.opsForValue().set(entity.getTableName(), baseEntity);

                return baseEntity;
            }
        } else {
            if (baseEntity.getSystemStatus().equals(systemStatus)) {
                return baseEntity;
            } else {
                return null;
            }
        }
    }

    public Boolean save(T baseEntity, String systemCreateUserId) {
        String id = Util.getRandomUUID();

        baseEntity.put(entity.getTableId(), id);
        baseEntity.setSystemCreateUserId(systemCreateUserId);
        baseEntity.setSystemCreateTime(new Date());
        baseEntity.setSystemUpdateUserId(systemCreateUserId);
        baseEntity.setSystemUpdateTime(new Date());
        baseEntity.setSystemVersion(0);
        baseEntity.setSystemStatus(true);

        Boolean success = mapper.insert(baseEntity) != 0;

        if (success) {
            baseEntity.keepTableFieldValue();

            redis.opsForValue().set(getItemCacheName(id), baseEntity);
        }

        return success;
    }

    public Boolean update(T baseEntity, String id, String systemUpdateUserId, Integer systemVersion) {
        baseEntity.setSystemUpdateUserId(systemUpdateUserId);
        baseEntity.setSystemUpdateTime(new Date());
        baseEntity.setSystemVersion(systemVersion + 1);

        System.out.println(systemVersion);

        Boolean success = mapper.update(
                baseEntity,
                new EntityWrapper<T>()
                        .eq(entity.getTableId(), id)
                        .eq(BaseEntity.SYSTEM_VERSION, systemVersion)
                        .eq(BaseEntity.SYSTEM_STATUS, true)
        ) != 0;

        if (success) {
            baseEntity.keepTableFieldValue();

            redis.opsForValue().set(getItemCacheName(id), baseEntity);
        }

        return success;
    }

    public Boolean delete(String id, String systemUpdateUserId, Integer systemVersion) {
        entity.setSystemUpdateUserId(systemUpdateUserId);
        entity.setSystemUpdateTime(new Date());
        entity.setSystemVersion(systemVersion + 1);
        entity.setSystemStatus(false);

        Boolean success = mapper.update(
                entity,
                new EntityWrapper<T>()
                        .eq(entity.getTableId(), id)
                        .eq(BaseEntity.SYSTEM_VERSION, systemVersion)
                        .eq(BaseEntity.SYSTEM_STATUS, true)
        ) != 0;

        if (success) {
            redis.delete(getItemCacheName(id));
        }

        return success;
    }

}
