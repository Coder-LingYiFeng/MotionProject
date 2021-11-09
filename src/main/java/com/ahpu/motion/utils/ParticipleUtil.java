package com.ahpu.motion.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParticipleUtil {
    public Map<String, Integer> getParticipleRes(String sentence) {
        JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
        List<String> strings = jiebaSegmenter.sentenceProcess(sentence);
        StringBuilder sb = new StringBuilder();
        // 简单处理
        for (String string : strings) {
            if (string.equals("，") ||
                    string.equals("。") ||
                    string.equals("、") ||
                    string.equals("：") ||
                    string.equals("？") ||
                    string.length() == 1) {
                continue;
            }
            sb.append(string).append(" ");
        }
        String sentenceResult = sb.substring(0, sb.length() - 1);

        return statisticalWords(sentenceResult);

    }

    private Map<String, Integer> statisticalWords(String sentence) {
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
