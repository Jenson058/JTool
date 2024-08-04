package com.jenson.tool.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jenson.tool.util.ListUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;

import java.util.List;

public abstract class SuperServiceImpl<M extends BaseMapper<Entity>,Entity,Vo,SearchBo extends SuperSearchBo> extends ServiceImpl<M,Entity> {
    public void supplement(Vo vo){
        supplement(ListUtil.obj2List(vo));
    }
    public abstract void supplement(List<Vo> voList);
    public void supplement(Page<Vo> voPage){
        supplement(voPage.getContent());
    }
    public abstract BooleanExpression getWhere(SearchBo searchBo);
}
