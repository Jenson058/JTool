package com.jenson.ajie.util.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class MapUtil<K, V> {
    private Map<K, V> map;

    public MapUtil() {
        this.map = new HashMap<>();
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public Map<K, V> get() {
        return map;
    }

    public V get(K key) {
        return map.get(key);
    }

    public String splicing() {
        StringBuilder sb = new StringBuilder();
        for (K key : map.keySet()) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(key.toString()).append("=").append(map.get(key).toString());
        }
        return sb.toString();
    }


}
