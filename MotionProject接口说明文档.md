

# Motion后端接口详情

[toc]

## 1. 用户接口

*共有2个接口*

###  1.1 登陆

`/userRecord/login	[POST]`

**请求格式:**  header应为Content-Type: application/json

**接口说明:** 登录系统

**参数说明:**

| 参数名称 | 参数类型 | 必要参数 | 参数说明 |
| -------- | -------- | -------- | -------- |
| userName | String   | 是       | 用户名   |
| passWord | String   | 是       | 密码     |

**请求示例:**

```json
{
    "userName":"ugk",
    "passWord":"123"
}
```

**返回说明:**

| 字段名    | 字段类型  | 是否一定存在 | 字段说明                       |
| --------- | --------- | ------------ | ------------------------------ |
| status    | String    | 是           | 成功：OK，失败：ERROR          |
| message   | String    | 是           | 提示信息                       |
| data      | JSON/null | 否           | 成功才有                       |
| +id       | int       | 是           | 用户id（user表id关联device表） |
| +userName | String    | 是           | 用户名                         |

**返回示例:**

```json
{
    "status": "OK",
    "message": "登陆成功",
    "data": {
        "id": 40,
        "userName": "hhh"
    }
}
```

### 1.2 注册

` /userRecord/register	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 注册系统用户

**请求说明:**

| 参数说明 | 参数类型 | 必要参数 | 参数说明 |
| -------- | -------- | -------- | -------- |
| userName | String   | 是       | 用户名   |
| passWord | String   | 是       | 密码     |

**请求示例:**

```json
{
    "userName":"hhh",
    "passWord":"123"
}
```

**返回说明:**

| 字段名    | 字段类型  | 是否一定存在 | 字段说明                                    |
| --------- | --------- | ------------ | ------------------------------------------- |
| status    | String    | 是           | 成功：OK，失败：ERROR                       |
| message   | String    | 是           | 提示信息                                    |
| data      | JSON/null | 否           | 成功才有                                    |
| +id       | int       | 是           | 用户在表中的id（关联device.create_user_id） |
| +userName | String    | 是           | 用户名                                      |

**返回示例:**

```json
{
    "status": "OK",
    "message": "注册成功",
    "data": {
        "id": 52,
        "userName": "hhhsshh"
    }
}
```

## 2. 设备管理接口

*共有4个接口*

### 2.1 新增设备

`/deviceRecord/addDevice	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 新增设备

**请求说明：**

| 参数名称     | 类型   | 必要参数 | 说明                       |
| ------------ | ------ | -------- | -------------------------- |
| name         | String | 是       | 设备名称（同用户唯一）     |
| createUserId | String | 是       | 创建设备的用户id（）       |
| scribe       | String | 否       | 设备描述信息（非必填参数） |
| mqttSub      | String | 是       | mqtt订阅端参数（全库唯一） |
| mqttPub      | String | 是       | mqtt发布端参数（全库唯一） |

**请求示例：**

```json
{
    "name":"ceshi",
    "createUserId":45,
    "mqttPub":"ceshiPub1",
    "mqttSub":"ceshiSub1"
}
```

**返回说明：**

| 字段名        | 字段类型  | 是否一定存在 | 字段说明                                   |
| ------------- | --------- | ------------ | ------------------------------------------ |
| status        | String    | 是           | 成功：OK，失败：ERROR                      |
| message       | String    | 是           | 提示信息                                   |
| data          | JSON/null | 否           | 成功才有                                   |
| +id           | int       | 是           | 设备在表中的的id（关联sentence.device_id） |
| +name         | String    | 是           | 设备名                                     |
| +createUserId | int       | 是           | 添加设备的用户id                           |
| +mqttSub      | String    | 是           | mqtt订阅端参数                             |
| +mqttPub      | String    | 是           | mqtt发布端参数                             |

**返回示例：**

```json
{
    "status": "OK",
    "message": "设备添加成功",
    "data": {
        "id": 26,
        "name": "ceshi",
        "createUserId": 45,
        "mqttSub": "ceshiSub1",
        "mqttPub": "ceshiPub1"
    }
}
```

### 2.2 删除设备

`/deviceRecord/deleteDevice	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 删除设备

**请求说明：**

| 参数名称     | 类型   | 必要参数 | 说明             |
| ------------ | ------ | -------- | ---------------- |
| name         | String | 是       | 设备名称         |
| createUserId | String | 是       | 创建设备的用户id |

**请求示例：**

```json
{
    "name":"八号",
    "createUserId":45
}
```

**返回说明：**

| 字段名        | 字段类型  | 是否一定存在 | 字段说明                                   |
| ------------- | --------- | ------------ | ------------------------------------------ |
| status        | String    | 是           | 成功：OK，失败：ERROR                      |
| message       | String    | 是           | 提示信息                                   |
| data          | JSON/null | 否           | 成功才有                                   |
| +id           | int       | 是           | 设备在表中的的id（关联sentence.device_id） |
| +name         | String    | 是           | 设备名                                     |
| +createUserId | int       | 是           | 添加设备的用户id                           |
| +createDate   | String    | 是           | 添加设备的时间                             |
| +mqttSub      | String    | 是           | mqtt订阅端参数                             |
| +mqttPub      | String    | 是           | mqtt发布端参数                             |

**返回示例：**

```json
{
    "status": "OK",
    "message": "设备移除成功",
    "data": {
        "id": 26,
        "name": "ceshi",
        "createUserId": 45,
        "createDate": "2021-11-13 01:09:48",
        "mqttSub": "ceshiSub1",
        "mqttPub": "ceshiPub1"
    }
}
```

### 2.3 查询设备信息

`/deviceRecord/getAllDevice	[GET]`

**请求格式:** 无

**接口说明:** 查询设备信息

**请求说明：**

| 参数名称     | 类型   | 必要参数 | 说明             |
| ------------ | ------ | -------- | ---------------- |
| createUserId | String | 是       | 拥有设备的用户id |

**请求示例：**

`/deviceRecord/getAllDevice?createUserId=45`

**返回说明：**

| 字段名        | 字段类型  | 是否一定存在 | 字段说明                                   |
| ------------- | --------- | ------------ | ------------------------------------------ |
| status        | String    | 是           | 成功：OK，失败：ERROR                      |
| message       | String    | 是           | 提示信息                                   |
| data          | JSON/null | 否           | 成功才有                                   |
| +id           | int       | 是           | 设备在表中的的id（关联sentence.device_id） |
| +name         | String    | 是           | 设备名                                     |
| +createUserId | int       | 是           | 添加设备的用户id                           |
| +createDate   | String    | 是           | 添加设备的时间                             |
| +mqttSub      | String    | 是           | mqtt订阅端参数                             |
| +mqttPub      | String    | 是           | mqtt发布端参数                             |

**返回示例：**

```json
{
    "status": "OK",
    "message": "5个设备信息获取成功",
    "data": [
        {
            "id": 15,
            "name": "13号",
            "createUserId": 45,
            "createDate": "2021-11-12 21:54:17",
            "mqttSub": "sub15",
            "mqttPub": "pub15"
        },
        {
            "id": 16,
            "name": "14号",
            "createUserId": 45,
            "createDate": "2021-11-11 19:41:46",
            "mqttSub": "sub45",
            "mqttPub": "pub45"
        }
    ]
}
```

### 2.4 更新设备信息

` /deviceRecord/updateDevice	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 更新设备信息

**请求说明：**

| 参数名称     | 类型   | 必要参数 | 说明                   |
| ------------ | ------ | -------- | ---------------------- |
| id           | int    | 是       | 设备的id               |
| createUserId | String | 是       | 更新的创建设备的用户id |
| name         | String | 是       | 更新的设备名           |
| scribe       | String | 是       | 更新的设备描述         |
| mqttSub      | String | 是       | 更新的mqttSub参数      |
| mqttPub      | String | 是       | 更新的mqttPub参数      |

**请求示例：**

```json
{
    "id":9,
    "name":"9号更新",
    "createUserId":48,
    "scribe":"测试mapper",
    "mqttSub":"spring",
    "mqttPub":"spring"
}
```

**返回说明：**

| 字段名        | 字段类型  | 是否一定存在 | 字段说明                                   |
| ------------- | --------- | ------------ | ------------------------------------------ |
| status        | String    | 是           | 成功：OK，失败：ERROR                      |
| message       | String    | 是           | 提示信息                                   |
| data          | JSON/null | 否           | 成功才有                                   |
| +id           | int       | 是           | 设备在表中的的id（关联sentence.device_id） |
| +name         | String    | 是           | 设备名                                     |
| +createUserId | int       | 是           | 添加设备的用户id                           |
| +mqttSub      | String    | 是           | mqtt订阅端参数                             |
| +mqttPub      | String    | 是           | mqtt发布端参数                             |

返回示例：

```json
{
    "status": "OK",
    "message": "设备信息更新成功",
    "data": {
        "id": 9,
        "name": "9号更新",
        "createUserId": 48,
        "scribe": "测试mapper",
        "mqttSub": "spring",
        "mqttPub": "spring"
    }
}
```

## 3. 数据API

*共有6个接口*

### 3.1 获取时间区间内词云数据

`/dataAPI/getWordCloudByTimeSection	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 返回指定时间段内设备所有语句的词频数据，用于词云图

**参数说明:**

| 参数名称     | 参数类型 | 必要参数 | 参数说明         |
| ------------ | -------- | -------- | ---------------- |
| createUserId | int      | 是       | 创建设备用户的id |
| name         | String   | 是       | 设备名           |
| startTime    | String   | 是       | 指定的开始时间   |
| endTime      | String   | 是       | 指定的结束时间   |

**请求示例：**

```json
{
    "createUserId":25,
    "name":"三号",
    "startTime":"2021-11-09 14:11:00",
    "endTime":"2021-11-09 14:11:59"
}
```

**返回说明：**

| 字段名      | 字段类型  | 是否一定存在 | 字段说明                                     |
| ----------- | --------- | ------------ | -------------------------------------------- |
| status      | String    | 是           | 成功：OK，失败：ERROR                        |
| message     | String    | 是           | 提示信息                                     |
| data        | JSON/null | 否           | 成功才有                                     |
| +name       | String    | 是           | 词语                                         |
| +value      | String    | 是           | 词频                                         |
| sentence    | JSON      | 是           | 所有语句信息                                 |
| +id         | int       | 是           | sentence表自增id                             |
| +deviceId   | int       | 是           | 该语句的所属的设备id                         |
| +sentence   | String    | 是           | 语句信息                                     |
| +time       | String    | 是           | 语句存库的时间                               |
| +sentiment  | int       | 是           | 表示情感极性分类结果, 0:负向，1:中性，2:正向 |
| +confidence | double    | 是           | 表示分类的置信度                             |

**返回示例：**

```json
{
    "sentence": [
        {
            "id": 38,
            "deviceId": 8,
            "sentence": "测试mqtt的通信行为test1111",
            "time": "2021-11-11 20:11:16",
            "sentiment": 2,
            "confidence": 0.581671,
            "positiveProb": 0.811752,
            "negativeProb": 0.188248
        }
    ],
    "data": [
        {
            "name": "通信",
            "value": "1"
        },
        {
            "name": "test1111",
            "value": "1"
        },
        {
            "name": "测试",
            "value": "1"
        },
        {
            "name": "mqtt",
            "value": "1"
        },
        {
            "name": "行为",
            "value": "1"
        }
    ],
    "message": "数据获取成功",
    "status": "OK"
}
```

###  3.2 获取设备语句所有语句数据

`/dataAPI/getAllSentenceMessageByDeviceId	[POST]`

**请求格式:**  header应为Content-Type: application/json

**接口说明:** 返回指定设备所有语句信息

**参数说明:**

| 参数名称     | 参数类型 | 必要参数 | 参数说明         |
| ------------ | -------- | -------- | ---------------- |
| createUserId | int      | 是       | 创建设备用户的id |
| name         | String   | 是       | 设备名           |

**请求示例：**

```json
{
    "name":"五号设备",
    "createUserId":47
}
```

**返回说明：**

| 字段名        | 字段类型  | 是否一定存在 | 字段说明                                     |
| ------------- | --------- | ------------ | -------------------------------------------- |
| status        | String    | 是           | 成功：OK，失败：ERROR                        |
| message       | String    | 是           | 提示信息                                     |
| data          | JSON/null | 否           | 成功才有                                     |
| +id           | int       | 是           | 无作用                                       |
| +deviceId     | int       | 是           | 句子所属设备的id                             |
| +sentence     | String    | 是           | 语句本体                                     |
| +time         | String    | 是           | 写库时间                                     |
| +sentiment    | int       | 是           | 表示情感极性分类结果, 0:负向，1:中性，2:正向 |
| +confidence   | double    | 是           | 表示分类的置信度                             |
| +positiveProb | double    | 是           | 表示属于积极类别的概率                       |
| +negativeProb | double    | 是           | 表示属于消极类别的概率                       |

**返回示例：**

```json
{
    "status": "OK",
    "message": "数据查询成功",
    "data": [
        {
            "id": 51,
            "deviceId": 8,
            "sentence": "pub通过数据库获得ceshi客户端已关闭bug",
            "time": "2021-11-11 21:20:38",
            "sentiment": 0,
            "confidence": 0.986234,
            "positiveProb": 0.00619487,
            "negativeProb": 0.993805
        },
        {
            "id": 52,
            "deviceId": 8,
            "sentence": "pub通过数据库获得ceshi客户端已关闭bug",
            "time": "2021-11-11 21:20:41",
            "sentiment": 0,
            "confidence": 0.986234,
            "positiveProb": 0.00619488,
            "negativeProb": 0.993805
        }
    ]
}

```

### 3.3 获取24小时内语句信息

`/dataAPI/getSentenceByDeviceIdOnOneday	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 返回24小时内所有语句信息及单位时间语句数量

**参数说明:**

| 参数名称     | 参数类型 | 必要参数 | 参数说明         |
| ------------ | -------- | -------- | ---------------- |
| createUserId | int      | 是       | 创建设备用户的id |
| name         | String   | 是       | 设备名           |

**请求示例：**

```json
{
    "name":"五号设备",
    "createUserId":47
}
```

**返回说明：**

| 字段名             | 字段类型  | 是否一定存在 | 字段说明                                                |
| ------------------ | --------- | ------------ | ------------------------------------------------------- |
| status             | String    | 是           | 成功：OK，失败：ERROR                                   |
| message            | String    | 是           | 提示信息                                                |
| data               | JSON/null | 否           | 成功才有                                                |
| +AllSentenceOneDay | JSON      | 是           | 成功才有                                                |
| ++id               | int       | 是           | 无作用                                                  |
| ++deviceId         | int       | 是           | 句子所属设备的id                                        |
| ++sentence         | String    | 是           | 语句本体                                                |
| ++time             | String    | 是           | 写库时间                                                |
| ++sentiment        | int       | 是           | 表示情感极性分类结果, 0:负向，1:中性，2:正向            |
| ++confidence       | double    | 是           | 表示分类的置信度                                        |
| ++positiveProb     | double    | 是           | 表示属于积极类别的概率                                  |
| ++negativeProb     | double    | 是           | 表示属于消极类别的概率                                  |
| +echatsData        | JSON      | 是           | 单位时间语句数量用于echarts（不同图标数据需要二次处理） |

**返回示例：**

```json
{
    "status": "OK",
    "message": "最新24小时内的单位时间信息量",
    "data": {
        "AllSentenceOneDay": [
            {
                "id": 64,
                "deviceId": 8,
                "sentence": "测试mqtt的通信行为test11",
                "time": "2021-11-12 11:00:59",
                "sentiment": 2,
                "confidence": 0.394824,
                "positiveProb": 0.727671,
                "negativeProb": 0.272329
            },
            {
                "id": 65,
                "deviceId": 8,
                "sentence": "测试mqtt的通信行为test11",
                "time": "2021-11-12 15:02:24",
                "sentiment": 2,
                "confidence": 0.394824,
                "positiveProb": 0.727671,
                "negativeProb": 0.272329
            },
            {
                "id": 66,
                "deviceId": 8,
                "sentence": "测试mqtt的通信行为test11",
                "time": "2021-11-12 15:03:29",
                "sentiment": 2,
                "confidence": 0.394824,
                "positiveProb": 0.727671,
                "negativeProb": 0.272329
            },
            {
                "id": 67,
                "deviceId": 8,
                "sentence": "好开心",
                "time": "2021-11-12 15:04:04",
                "sentiment": 2,
                "confidence": 0.999896,
                "positiveProb": 0.999953,
                "negativeProb": 4.68791E-5
            },
            {
                "id": 68,
                "deviceId": 8,
                "sentence": "好难受",
                "time": "2021-11-12 15:04:22",
                "sentiment": 0,
                "confidence": 0.959881,
                "positiveProb": 0.0180537,
                "negativeProb": 0.981946
            },
            {
                "id": 69,
                "deviceId": 8,
                "sentence": "好难受",
                "time": "2021-11-12 15:04:55",
                "sentiment": 0,
                "confidence": 0.959881,
                "positiveProb": 0.0180537,
                "negativeProb": 0.981946
            },
            {
                "id": 70,
                "deviceId": 8,
                "sentence": "好难受",
                "time": "2021-11-12 15:05:08",
                "sentiment": 0,
                "confidence": 0.959881,
                "positiveProb": 0.0180537,
                "negativeProb": 0.981946
            },
            {
                "id": 75,
                "deviceId": 8,
                "sentence": "测试mqtt的通信行为test11",
                "time": "2021-11-12 19:48:16",
                "sentiment": 2,
                "confidence": 0.394824,
                "positiveProb": 0.727671,
                "negativeProb": 0.272329
            },
            {
                "id": 76,
                "deviceId": 8,
                "sentence": "测试mqtt的通信行为test11",
                "time": "2021-11-12 19:48:57",
                "sentiment": 2,
                "confidence": 0.394824,
                "positiveProb": 0.727671,
                "negativeProb": 0.272329
            },
            {
                "id": 77,
                "deviceId": 8,
                "sentence": "测试mqtt的通信行为test1111",
                "time": "2021-11-12 19:49:07",
                "sentiment": 2,
                "confidence": 0.581671,
                "positiveProb": 0.811752,
                "negativeProb": 0.188248
            },
            {
                "id": 78,
                "deviceId": 8,
                "sentence": "测试mqtt的通信行为test1111",
                "time": "2021-11-12 19:50:00",
                "sentiment": 2,
                "confidence": 0.581671,
                "positiveProb": 0.811752,
                "negativeProb": 0.188248
            },
            {
                "id": 79,
                "deviceId": 8,
                "sentence": "测试mqtt的通信行为test1111",
                "time": "2021-11-12 23:27:02",
                "sentiment": 2,
                "confidence": 0.581671,
                "positiveProb": 0.811752,
                "negativeProb": 0.188248
            },
            {
                "id": 80,
                "deviceId": 8,
                "sentence": "测试mqtt的通信行为test1111",
                "time": "2021-11-12 23:39:34",
                "sentiment": 2,
                "confidence": 0.581671,
                "positiveProb": 0.811752,
                "negativeProb": 0.188248
            }
        ],
        "echatsData": [
            {
                "2021-11-12 02:02:39": 0
            },
            {
                "2021-11-12 03:02:39": 0
            },
            {
                "2021-11-12 04:02:39": 0
            },
            {
                "2021-11-12 05:02:39": 0
            },
            {
                "2021-11-12 06:02:39": 0
            },
            {
                "2021-11-12 07:02:39": 0
            },
            {
                "2021-11-12 08:02:39": 0
            },
            {
                "2021-11-12 09:02:39": 0
            },
            {
                "2021-11-12 10:02:39": 1
            },
            {
                "2021-11-12 11:02:39": 0
            },
            {
                "2021-11-12 12:02:39": 0
            },
            {
                "2021-11-12 13:02:39": 0
            },
            {
                "2021-11-12 14:02:39": 1
            },
            {
                "2021-11-12 15:02:39": 5
            },
            {
                "2021-11-12 16:02:39": 0
            },
            {
                "2021-11-12 17:02:39": 0
            },
            {
                "2021-11-12 18:02:39": 0
            },
            {
                "2021-11-12 19:02:39": 4
            },
            {
                "2021-11-12 20:02:39": 0
            },
            {
                "2021-11-12 21:02:39": 0
            },
            {
                "2021-11-12 22:02:39": 0
            },
            {
                "2021-11-12 23:02:39": 2
            },
            {
                "2021-11-13 00:02:39": 0
            },
            {
                "2021-11-13 01:02:39": 0
            }
        ]
    }
}

```

### 3.4 获取情感饼图数据

`/dataAPI/getMotionPieByTimeSection	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 返回指定时间段内设备所有语句的情感极性结果，用于饼图

**参数说明:**

| 参数名称     | 参数类型 | 必要参数 | 参数说明         |
| ------------ | -------- | -------- | ---------------- |
| createUserId | int      | 是       | 创建设备用户的id |
| name         | String   | 是       | 设备名           |
| startTime    | String   | 是       | 指定的开始时间   |
| endTime      | String   | 是       | 指定的结束时间   |

**请求示例：**

```json
{
    "createUserId":47,
    "name":"五号设备",
    "startTime":"2021-11-10 19:12:49",
    "endTime":"2021-11-11 10:56:19"
}
```

**返回说明：**

| 字段名  | 字段类型  | 是否一定存在 | 字段说明              |
| ------- | --------- | ------------ | --------------------- |
| status  | String    | 是           | 成功：OK，失败：ERROR |
| message | String    | 是           | 提示信息              |
| data    | JSON/null | 否           | 成功才有              |
| +name   | String    | 是           | 情感极性结果          |
| +value  | String    | 是           | 情感极性结果总数      |

**返回示例：**

```json
{
    "data": [
        {
            "name": "负向",
            "value": "4"
        },
        {
            "name": "正向",
            "value": "17"
        }
    ],
    "message": "数据获取成功",
    "status": "OK"
}

```

### 3.5 获取时间区间内设备的GPS信息

`/dataAPI/getDeviceGpsByTimeSection	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 返回指定时间段内设备的GPS信息

**参数说明:**

| 参数名称     | 参数类型 | 必要参数 | 参数说明         |
| ------------ | -------- | -------- | ---------------- |
| createUserId | int      | 是       | 创建设备用户的id |
| name         | String   | 是       | 设备名           |
| startTime    | String   | 是       | 指定的开始时间   |
| endTime      | String   | 是       | 指定的结束时间   |

**请求示例：**

```json
{
    "createUserId":47,
    "name":"五号设备",
    "startTime":"2021-11-23 19:50:41",
    "endTime":"2021-11-23 21:23:42"
}
```

**返回说明：**

| 字段名     | 字段类型  | 是否一定存在 | 字段说明              |
| ---------- | --------- | ------------ | --------------------- |
| status     | String    | 是           | 成功：OK，失败：ERROR |
| message    | String    | 是           | 提示信息              |
| data       | JSON/null | 否           | 成功才有              |
| +id        | int       | 是           | gps信息表的自增id     |
| +deviceId  | int       | 是           | 关联的设备表id        |
| +longitude | String    | 是           | 经度                  |
| +latitude  | String    | 是           | 纬度                  |
| +dateTime  | String    | 是           | 插入信息的时间        |

**返回示例：**

```json
{
    "status": "OK",
    "message": "数据获取成功",
    "data": [
        {
            "id": 1413,
            "deviceId": 8,
            "longitude": "118.409",
            "latitude": "31.54",
            "dateTime": "2021-11-23 19:50:41"
        },
        {
            "id": 1414,
            "deviceId": 8,
            "longitude": "118.409",
            "latitude": "31.74",
            "dateTime": "2021-11-23 21:23:42"
        }
    ]
}
```

### 3.6 获取设备的最后一次GPS信息

`/dataAPI/getLastInfo	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 返回设备的最后一次GPS信息

**参数说明:**

| 参数名称     | 参数类型 | 必要参数 | 参数说明         |
| ------------ | -------- | -------- | ---------------- |
| createUserId | int      | 是       | 创建设备用户的id |
| name         | String   | 是       | 设备名           |
| startTime    | String   | 是       | 指定的开始时间   |
| endTime      | String   | 是       | 指定的结束时间   |

**请求示例：**

```json
{
    "createUserId":47,
    "name":"五号设备",
    "startTime":"2021-11-23 19:50:41",
    "endTime":"2021-11-23 21:23:42"
}
```

**返回说明：**

| 字段名     | 字段类型  | 是否一定存在 | 字段说明              |
| ---------- | --------- | ------------ | --------------------- |
| status     | String    | 是           | 成功：OK，失败：ERROR |
| message    | String    | 是           | 提示信息              |
| data       | JSON/null | 否           | 成功才有              |
| +id        | int       | 是           | gps信息表的自增id     |
| +deviceId  | int       | 是           | 关联的设备表id        |
| +longitude | String    | 是           | 经度                  |
| +latitude  | String    | 是           | 纬度                  |
| +dateTime  | String    | 是           | 插入信息的时间        |

**返回示例：**

```json
{
    "status": "OK",
    "message": "数据获取成功",
    "data": [
        {
            "id": 1413,
            "deviceId": 8,
            "longitude": "118.409",
            "latitude": "31.54",
            "dateTime": "2021-11-23 19:50:41"
        },
        {
            "id": 1414,
            "deviceId": 8,
            "longitude": "118.409",
            "latitude": "31.74",
            "dateTime": "2021-11-23 21:23:42"
        }
    ]
}
```



## 4. 提供给树莓派的接口

*共2个接口*

### 4.1 语句分析及存库

`/raspberryPiAPI/insertSentence	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 将树莓派传入的数据进行情感分析后进行持久化并通过mqtt通知前端服务

**参数说明:**

| 参数名称 | 参数类型 | 必要参数 | 参数说明   |
| -------- | -------- | -------- | ---------- |
| deviceId | int      | 是       | 设备id     |
| sentence | String   | 是       | 收录的文本 |

**请求示例：**

```json
{
    "deviceId":8,
    "sentence":"测试mqtt的通信行为test1111"
}
```

**返回说明：**

| 字段名        | 字段类型  | 是否一定存在 | 字段说明                                     |
| ------------- | --------- | ------------ | -------------------------------------------- |
| status        | String    | 是           | 成功：OK，失败：ERROR                        |
| message       | String    | 是           | 提示信息                                     |
| data          | JSON/null | 否           | 成功才有                                     |
| +deviceId     | int       | 是           | 设备的deviceId（关联user.id）                |
| +sentence     | String    | 是           | 语句本体                                     |
| +sentiment    | int       | 是           | 表示情感极性分类结果, 0:负向，1:中性，2:正向 |
| +confidence   | double    | 是           | 表示分类的置信度                             |
| +positiveProb | double    | 是           | 表示属于积极类别的概率                       |
| +negativeProb | double    | 是           | 表示属于消极类别的概率                       |

**返回示例：**

```json
{
    "status": "OK",
    "message": "插入数据库成功",
    "data": {
        "deviceId": 8,
        "sentence": "测试mqtt的通信行为test1111",
        "sentiment": 2,
        "confidence": 0.581671,
        "positiveProb": 0.811752,
        "negativeProb": 0.188248
    }
}

```

### 4.2 设备GPS信息存库

`/raspberryPiAPI/insertGps	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 将树莓派传入的数据进行情感分析后进行持久化并通过mqtt通知前端服务

**参数说明:**

| 参数名称  | 参数类型 | 必要参数 | 参数说明 |
| --------- | -------- | -------- | -------- |
| deviceId  | int      | 是       | 设备id   |
| latitude  | String   | 是       | 经度     |
| longitude | String   | 是       | 纬度     |

**请求示例：**

```json
{
    "deviceId":8,
    "latitude":3.5678888888999,
    "longitude":6.5678999999999
}
```

**返回说明：**

| 字段名     | 字段类型  | 是否一定存在 | 字段说明                      |
| ---------- | --------- | ------------ | ----------------------------- |
| status     | String    | 是           | 成功：OK，失败：ERROR         |
| message    | String    | 是           | 提示信息                      |
| data       | JSON/null | 否           | 成功才有                      |
| +deviceId  | int       | 是           | 设备的deviceId（关联user.id） |
| +longitude | String    | 是           | 经度信息                      |
| +latitude  | String    | 是           | 纬度信息                      |

**返回示例：**

```json
{
    "status": "OK",
    "message": "插入成功",
    "data": {
        "deviceId": 8,
        "longitude": "6.5678999999999",
        "latitude": "3.5678888888999"
    }
}
```

## 5. 任务调度接口

*共有6个接口*

### 5.1 添加负向条数超过阈值发送邮件的任务

/job/addNegativeNumJob	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 开启负向条数超过阈值发送邮件的任务

**参数说明:**

| 参数名称       | 参数类型 | 必要参数 | 参数说明     |
| -------------- | -------- | -------- | ------------ |
| deviceId       | int      | 是       | 设备id       |
| jobGroup       | String   | 是       | 任务组名     |
| negativeMaxNum | int      | 是       | 负向条数阈值 |
| cron           | String   | 是       | cron表达式   |
| startTime      | String   | 是       | 开始时间     |
| endTime        | String   | 是       | 结束时间     |
| toMail         | String   | 是       | 邮箱账号     |

**请求示例：**

```json
{
    "deviceId":8,
    "jobGroup":"negativeNum",
    "negativeMaxNum":20,
    "cron":"0/59 * * * * ?",
    "startTime":"2021-11-10 19:12:49",
    "endTime":"2021-11-18 20:19:52",
    "toMail":"1918404715@qq.com"
}
```

**返回说明：**

| 字段名          | 字段类型  | 是否一定存在 | 字段说明              |
| --------------- | --------- | ------------ | --------------------- |
| status          | String    | 是           | 成功：OK，失败：ERROR |
| message         | String    | 是           | 提示信息              |
| jobData         | JSON/null | 是           | 任务信息              |
| +jobName        | String    | 是           | 任务名称              |
| +corn           | String    | 是           | cron表达式            |
| +toMail         | String    | 是           | 邮箱账号              |
| +triggrtName    | String    | 是           | 触发器名称            |
| +startTime      | String    | 是           | 开始时间              |
| +jobGroup       | String    | 是           | 任务组名              |
| +endTime        | String    | 是           | 结束时间              |
| +negativeMaxNum | int       | 是           | 负面阈值              |
| +deviceId       | int       | 是           | 关联设备的id          |

**返回示例：**

```json
{
    "jobData": {
        "jobName": "8:negativeNum",
        "cron": "0/59 * * * * ?",
        "toMail": "1918404715@qq.com",
        "triggrtName": "Triggrt:8:negativeNum",
        "startTime": "2021-11-10 19:12:49",
        "jobGroup": "negativeNum",
        "endTime": "2021-11-18 20:19:52",
        "negativeMaxNum": 20,
        "deviceId": 8
    },
    "message": "任务开启成功！！！",
    "status": "OK"
}
```

### 5.2 添加负向条数占比超过阈值发送邮件的任务

/job/addNegativeProJob	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 开启负向条数占比超过阈值发送邮件的任务

**参数说明:**

| 参数名称       | 参数类型 | 必要参数 | 参数说明         |
| -------------- | -------- | -------- | ---------------- |
| deviceId       | int      | 是       | 设备id           |
| jobGroup       | String   | 是       | 任务组名         |
| negativeMaxPro | double   | 是       | 负向条数占比阈值 |
| cron           | String   | 是       | cron表达式       |
| startTime      | String   | 是       | 开始时间         |
| endTime        | String   | 是       | 结束时间         |
| toMail         | String   | 是       | 邮箱账号         |

**请求示例：**

```json
{
    "deviceId":8,
    "jobGroup":"negativePro",
    "negativeMaxPro":0.3,
    "cron":"0/5 * * * * ?",
    "startTime":"2021-11-10 19:12:49",
    "endTime":"2021-11-18 20:19:52",
    "toMail":"1918404715@qq.com"
}
```

**返回说明：**

| 字段名          | 字段类型  | 是否一定存在 | 字段说明              |
| --------------- | --------- | ------------ | --------------------- |
| status          | String    | 是           | 成功：OK，失败：ERROR |
| message         | String    | 是           | 提示信息              |
| jobData         | JSON/null | 是           | 任务信息              |
| +jobName        | String    | 是           | 任务名称              |
| +corn           | String    | 是           | cron表达式            |
| +toMail         | String    | 是           | 邮箱账号              |
| +triggrtName    | String    | 是           | 触发器名称            |
| +startTime      | String    | 是           | 开始时间              |
| +jobGroup       | String    | 是           | 任务组名              |
| +endTime        | String    | 是           | 结束时间              |
| +negativeMaxPro | double    | 是           | 负面阈值条数占比      |
| +deviceId       | int       | 是           | 关联设备的id          |

**返回示例：**

```json
{
    "jobData": {
        "jobName": "8:negativePro",
        "cron": "0/5 * * * * ?",
        "toMail": "1918404715@qq.com",
        "negativeMaxPro": 0.3,
        "triggrtName": "Triggrt:8:negativePro",
        "startTime": "2021-11-10 19:12:49",
        "jobGroup": "negativePro",
        "endTime": "2021-11-18 20:19:52",
        "deviceId": 8
    },
    "message": "任务开启成功！！！",
    "status": "OK"
}
```

### 5.3 添加定时发送邮件的任务

/job/addTimingWarningJob	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 开启定时发送邮件的任务

**参数说明:**

| 参数名称  | 参数类型 | 必要参数 | 参数说明   |
| --------- | -------- | -------- | ---------- |
| deviceId  | int      | 是       | 设备id     |
| jobGroup  | String   | 是       | 任务组名   |
| cron      | String   | 是       | cron表达式 |
| startTime | String   | 是       | 开始时间   |
| endTime   | String   | 是       | 结束时间   |
| toMail    | String   | 是       | 邮箱账号   |

**请求示例：**

```json
{
    "deviceId":8,
    "jobGroup":"timing",
    "cron":"0/59 * * * * ?",
    "startTime":"2021-11-10 19:12:49",
    "endTime":"2021-11-18 20:19:52",
    "toMail":"1918404715@qq.com"
}
```

**返回说明：**

| 字段名       | 字段类型  | 是否一定存在 | 字段说明              |
| ------------ | --------- | ------------ | --------------------- |
| status       | String    | 是           | 成功：OK，失败：ERROR |
| message      | String    | 是           | 提示信息              |
| jobData      | JSON/null | 是           | 任务信息              |
| +jobName     | String    | 是           | 任务名称              |
| +corn        | String    | 是           | cron表达式            |
| +toMail      | String    | 是           | 邮箱账号              |
| +triggrtName | String    | 是           | 触发器名称            |
| +startTime   | String    | 是           | 开始时间              |
| +jobGroup    | String    | 是           | 任务组名              |
| +endTime     | String    | 是           | 结束时间              |
| +deviceId    | int       | 是           | 关联设备的id          |

**返回示例：**

```json
{
    "jobData": {
        "jobName": "8:timing",
        "cron": "0/59 * * * * ?",
        "toMail": "1918404715@qq.com",
        "triggrtName": "Triggrt:8:timing",
        "startTime": "2021-11-10 19:12:49",
        "jobGroup": "timing",
        "endTime": "2021-11-18 20:19:52",
        "deviceId": 8
    },
    "message": "任务开启成功！！！",
    "status": "OK"
}
```

### 5.4 获取指定任务的信息

/job/getJobInfo	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 获取所有任务信息

**参数说明:**

| 参数名称 | 参数类型 | 必要参数 | 参数说明 |
| -------- | -------- | -------- | -------- |
| deviceId | int      | 是       | 设备id   |
| jobGroup | String   | 是       | 任务组名 |

**请求示例：**

```json
{
    "deviceId":8,
    "jobGroup":"negativeNum"
}
```

**返回说明：**

| 字段名       | 字段类型  | 是否一定存在 | 字段说明        |
| ------------ | --------- | ------------ | --------------- |
| jobExists    | boolean   | 是           | job信息是否存在 |
| jobData      | JSON/null | 是           | 任务信息        |
| +jobName     | String    | 是           | 任务名称        |
| +corn        | String    | 是           | cron表达式      |
| +toMail      | String    | 是           | 邮箱账号        |
| +triggrtName | String    | 是           | 触发器名称      |
| +startTime   | String    | 是           | 开始时间        |
| +jobGroup    | String    | 是           | 任务组名        |
| +endTime     | String    | 是           | 结束时间        |
| +deviceId    | int       | 是           | 关联设备的id    |
| +negativeNum | String    | 否           | 条数阈值job才有 |
| +negativePro | String    | 否           | 占比阈值job才有 |

**返回示例：**

```json
{
    "jobData": {
        "jobName": "8:negativeNum",
        "toMail": "1918404715@qq.com",
        "jobGoup": "negativeNum",
        "corn": "0/59 * * * * ?",
        "triggrtName": "Triggrt:8:negativeNum",
        "startTime": "2021-11-10 19:12:49",
        "endTime": "2021-11-18 20:19:52",
        "negativeMaxNum": 20,
        "deviceId": 8
    },
    "jobExists": true
}
```

### 5.5 获取所有任务的信息

/job/getJobs	[GET]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 获取所有任务信息

**参数说明:** 无

**请求示例：**

`127.0.0.1:8000/job/getJobs`

**返回说明：**

| 字段名               | 字段类型  | 是否一定存在 | 字段说明                    |
| -------------------- | --------- | ------------ | --------------------------- |
| [deviceId]:[jobName] | JSON/null | 是           | 任务名由设备id和jobName拼接 |
| +jobName             | String    | 是           | 任务名称                    |
| +corn                | String    | 是           | cron表达式                  |
| +toMail              | String    | 是           | 邮箱账号                    |
| +triggrtName         | String    | 是           | 触发器名称                  |
| +startTime           | String    | 是           | 开始时间                    |
| +jobGroup            | String    | 是           | 任务组名                    |
| +endTime             | String    | 是           | 结束时间                    |
| +deviceId            | int       | 是           | 关联设备的id                |
| +negativeNum         | String    | 否           | 条数阈值job才有             |
| +negativePro         | String    | 否           | 占比阈值job才有             |

**返回示例：**

```json
{
  "8:negativeNum": {
    "jobName": "8:negativeNum",
    "toMail": "1918404715@qq.com",
    "jobGoup": "negativeNum",
    "corn": "0/59 * * * * ?",
    "triggrtName": "Triggrt:8:negativeNum",
    "startTime": "2021-11-10 19:12:49",
    "endTime": "2021-11-18 20:19:52",
    "negativeMaxNum": 20,
    "deviceId": 8
  },
  "8:negativePro": {
    "jobName": "8:negativePro",
    "toMail": "1918404715@qq.com",
    "negativeMaxPro": 0.3,
    "jobGoup": "negativePro",
    "corn": "0/59 * * * * ?",
    "triggrtName": "Triggrt:8:negativePro",
    "startTime": "2021-11-10 19:12:49",
    "endTime": "2021-11-18 20:19:52",
    "deviceId": 8
  }
}
```

### 5.6 停止指定任务

/job/deleteJob	[POST]`

**请求格式:** header应为Content-Type: application/json

**接口说明:** 停止指定任务

**参数说明:**

| 参数名称 | 参数类型 | 必要参数 | 参数说明 |
| -------- | -------- | -------- | -------- |
| deviceId | int      | 是       | 设备id   |
| jobGroup | String   | 是       | 任务组名 |

**请求示例：**

```json
{
  "deviceId":8,
  "jobGroup":"negativeNum"
}
```

**返回说明：**

| 字段名        | 字段类型  | 是否一定存在 | 字段说明        |
| ------------- | --------- | ------------ | --------------- |
| status        | String    | 是           | 是否成功        |
| message       | String    | 是           | 提示信息        |
| Data          | JSON/null | 是           | job信息         |
| jobExists     | boolean   | 是           | job信息是否存在 |
| +jobData      | JSON/null | 是           | 任务信息        |
| ++jobName     | String    | 是           | 任务名称        |
| ++corn        | String    | 是           | cron表达式      |
| ++toMail      | String    | 是           | 邮箱账号        |
| ++triggrtName | String    | 是           | 触发器名称      |
| ++startTime   | String    | 是           | 开始时间        |
| ++jobGroup    | String    | 是           | 任务组名        |
| ++endTime     | String    | 是           | 结束时间        |
| ++deviceId    | int       | 是           | 关联设备的id    |
| ++negativeNum | String    | 否           | 条数阈值job才有 |
| ++negativePro | String    | 否           | 占比阈值job才有 |

**返回示例：**

```json
{
  "Data": {
    "jobData": {
      "jobName": "8:negativeNum",
      "toMail": "1918404715@qq.com",
      "jobGoup": "negativeNum",
      "corn": "0/59 * * * * ?",
      "triggrtName": "Triggrt:8:negativeNum",
      "startTime": "2021-11-10 19:12:49",
      "endTime": "2021-11-18 20:19:52",
      "negativeMaxNum": 20,
      "deviceId": 8
    },
    "jobExists": true
  },
  "message": "8:negativeNum:任务关闭成功！",
  "status": "OK"
}
```