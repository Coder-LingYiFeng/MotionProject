package com.detection.motion.utils;

import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PahoUtil {
    @Value("${mqtt.broker}")
    String broker;
    String clientId = "SpringBoot_Motion";
    MemoryPersistence persistence = new MemoryPersistence();
    MqttClient client;
    MqttConnectOptions connOpts = new MqttConnectOptions();



    public void sendMessage(String pubTopic, JSONObject sentenceInfo) {
        if (client == null) {
            try {
                client = new MqttClient(broker, clientId, persistence);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(client);
        int qos = 0;
        try {
            // MQTT 连接选项

            connOpts.setUserName("SpringBoot_Motion");
            connOpts.setPassword("SpringBoot_Motion_password".toCharArray());
            // 保留会话
            connOpts.setCleanSession(true);

            // 建立连接
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);

            System.out.println("Connected");
            System.out.println("Publishing message: " + sentenceInfo);

            // 消息发布所需参数
            MqttMessage message = new MqttMessage(sentenceInfo.toString().getBytes());
            message.setQos(qos);
            client.publish(pubTopic, message);
            System.out.println("Message published");

            client.disconnect();
            System.out.println("Disconnected");
//            client.close();
//            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}


