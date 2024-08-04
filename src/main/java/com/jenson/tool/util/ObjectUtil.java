package com.jenson.tool.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class ObjectUtil {

    public static <T> T copyNotNullProperties(Object source, T target, String... ignoreProperties) {
        CopyBeanUtil.copyNotNullProperties(source, target, null, ignoreProperties);
        return target;
    }

    public static <T> T copyNotNullProperties(Object source, T target, Class<?> editable) {
        CopyBeanUtil.copyNotNullProperties(source, target, editable, null);
        return target;
    }

    public static <T> T copyNotNullProperties(Object source, Class<T> editable) {
        try {
            T target = (T) editable.newInstance();
            CopyBeanUtil.copyNotNullProperties(source, target, editable, null);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T copyNotNullProperties(Object source, T target) {
        CopyBeanUtil.copyNotNullProperties(source, target, null, null);
        return target;
    }

    public static <T> List<T> copyNotNullPropertiesList(List<?> list, Class<T> editable) {
        List<T> tList = new ArrayList<>();
        list.forEach(source -> {
            try {
                T target = (T) editable.newInstance();
                CopyBeanUtil.copyNotNullProperties(source, target, editable, null);
                tList.add(target);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return tList;
    }

    public static Map<String, Object> obj2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    public static List<Map<String, Object>> obj2Map(List<?> objList) {
        return objList.stream().map(ObjectUtil::obj2Map).collect(Collectors.toList());
    }


    /**
     * 这个类用于调用拷贝方法
     */
    private static class CopyBeanUtil {

        private static void copyNotNullProperties(Object source, Object target, Class<?> editable, String[] ignoreProperties) {

            Assert.notNull(source, "Source must not be null");
            Assert.notNull(target, "Target must not be null");

            Class<?> actualEditable = target.getClass();
            if (Objects.nonNull(editable)) {
                if (!editable.isInstance(target)) {
                    throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
                }
                actualEditable = editable;
            }

            PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
            List<String> ignoreList = Objects.nonNull(ignoreProperties) ? Arrays.asList(ignoreProperties) : null;

            for (PropertyDescriptor targetPd : targetPds) {
                if (Objects.nonNull(targetPd.getWriteMethod()) && (Objects.isNull(ignoreProperties) || (!ignoreList.contains(targetPd.getName())))) {
                    PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                    if (Objects.nonNull(sourcePd) && Objects.nonNull(sourcePd.getReadMethod()))
                        try {
                            Method readMethod = sourcePd.getReadMethod();
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (Objects.nonNull(value)) {
                                boolean isEmpty = false;
                                if (value instanceof Set) {
                                    Set<?> s = (Set<?>) value;
                                    if (s.isEmpty())
                                        isEmpty = true;
                                } else if (value instanceof Map) {
                                    Map<?, ?> m = (Map<?, ?>) value;
                                    if (m.isEmpty())
                                        isEmpty = true;
                                } else if (value instanceof List) {
                                    List<?> l = (List<?>) value;
                                    if (l.size() < 1)
                                        isEmpty = true;
                                } else if (value instanceof Collection) {
                                    Collection<?> c = (Collection<?>) value;
                                    if (c.size() < 1) {
                                        isEmpty = true;
                                    }
                                }
                                if (!isEmpty) {
                                    Method writeMethod = targetPd.getWriteMethod();
                                    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                        writeMethod.setAccessible(true);
                                    }
                                    writeMethod.invoke(target, value);
                                }
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException("Could not copy properties from source to target", ex);
                        }
                }
            }
        }
    }
}
