package com.jenson.tool.base;

import java.util.List;
import java.util.Optional;

public interface SuperRepository<Entity extends SuperEntity> {

    Optional<Entity> findById(Integer id);

    List<Entity> findByDeleted(Boolean deleted);

    Optional<Entity> findByIdAndDeleted(Integer id, Boolean deleted);

    List<Entity> findByIdIn(List<Integer> idList);
    List<Entity> findByIdInAndDeleted(List<Integer> idList, Boolean deleted);

}
