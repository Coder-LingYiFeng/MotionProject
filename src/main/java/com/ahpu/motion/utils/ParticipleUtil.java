package com.ahpu.motion.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParticipleUtil {
    public String getParticipleRes(String sentence) {
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

        return sb.substring(0, sb.length() - 1);

    }


}
