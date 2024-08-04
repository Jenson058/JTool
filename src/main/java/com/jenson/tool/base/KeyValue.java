package com.jenson.tool.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KeyValue<K,V> {

    private K key;
    private V value;

}
