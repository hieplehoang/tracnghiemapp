package com.nhuocquy.tracnghiemapp.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NhuocQuy on 12/9/2015.
 */
public class MyVar {
    private static Map<String, Object> map = new HashMap<>();
    public static void setAttribute(String key, Object value){
        map.put(key, value);
    }
    public static Object getAttribute(String key){
        return map.get(key);
    }
}
