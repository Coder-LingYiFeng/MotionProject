package com.detection.motion.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分词工具类
 */
@Component
public class ParticipleUtil {
    public String getParticipleRes(String sentence) {
        JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
        //jieba分词
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
            //空格隔断便于后面统计词频
            sb.append(string).append(" ");
        }

        return sb.substring(0, sb.length() - 1);

    }


}
