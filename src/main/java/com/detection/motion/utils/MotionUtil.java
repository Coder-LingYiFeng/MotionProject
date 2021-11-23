package com.detection.motion.utils;


import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class MotionUtil {
    @Value("${motion.api.app-id}")
    public String APP_ID;
    @Value("${motion.api.api-key}")
    public String API_KEY;
    @Value("${motion.api.secret-key}")
    public String SECRET_KEY;

    public JSONObject getResult(String text) {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        return client.sentimentClassify(text, null);
    }
}