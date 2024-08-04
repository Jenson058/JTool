package com.jenson.tool.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ListUtil {

    public static <O> List<O> obj2List(O o){
        List<O> oList = new ArrayList<>();
        oList.add(o);
        return oList;
    }

    public static Boolean isEmpty(List<?> list){
        return !(Objects.nonNull(list) && list.size() > 0);
    }

    public static Boolean exist(List<?> list,Object obj){
        if(isEmpty(list) || Objects.isNull(obj)){
            return  false;
        }
        for (Object o : list){
            if (Objects.equals(o,obj))
                return true;
        }
        return false;
    }

    public static <O> List<O> multilevelNesting(List<List<O>> objList){
        return objList.stream()
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
