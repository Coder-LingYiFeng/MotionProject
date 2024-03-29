package com.detection.motion.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 统计词频工具类
 */
@Component
public class StatisticalUtil {
    public Map<String, Integer> statisticalWords(String sentence) {
        //空格切分
        String[] words = sentence.split(" ");
        // 统计词频率
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                map.replace(word, map.get(word) + 1);
            }
        }
        return map;
    }
}
