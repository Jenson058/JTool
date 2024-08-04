package com.jenson.tool.base;

import org.springframework.data.domain.Page;

import java.util.List;

public interface SuperService<V, B, SearchBo> {

    Page<V> pageAll(SearchBo searchBo);

    List<V> listAll(SearchBo searchBo);

    V get(Integer id);

    V get(SearchBo searchBo);

    V edit(B b);

    List<V> edit(List<B> bList);

    V del(Integer id);

    V del(SearchBo searchBo);

    List<V> del(List<B> bList);

}
