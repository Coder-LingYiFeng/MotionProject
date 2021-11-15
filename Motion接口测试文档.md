# Motion接口测试文档

**测试工具：POSTMAN**

[toc]

## 1. 用户接口

### 1.1 登陆

`/userRecord/login	[POST]`

**1. 测试用例**

**测试内容：** *用户名不存在的情况*

```json
请求：
{
"userName":"hhdddh",
"passWord":"123"
}

结果：
{
"status": "ERROR",
"message": "用户名不存在"
}
```

**2. 测试用例**

**测试内容：** *用户名存在但是密码错误*

```json
请求：
{
"userName":"hhh",
"passWord":"1234"
}

结果：
{
"status": "ERROR",
"message": "密码错误"
}
```

**3. 测试用例**

**测试内容：** *传入空的用户名或密码或不传用户名或密码参数*

```json
请求：
{
"userName":"",
"passWord":"123"
}

{
"passWord":"123"
}

{
"userName":"hhh"
}

{
"userName":"hhh",
"passWord":""
}

结果：
{
"status": "ERROR",
"message": "用户名或密码为空"
}
```

**4. 测试用例**

**测试内容：** *用户名密码均正确*

```json
请求：
{
"userName":"hhh",
"passWord":"123"
}

结果：
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

**1. 测试用例**

**测试内容：** *注册相同用户名*

```json
请求：
{
"userName":"hhh",
"passWord":"123"
}

结果：
{
"status": "ERROR",
"message": "用户名已存在"
}
```

**2. 测试用例**

**测试内容：**  *传入空的用户名或密码或不传用户名或密码参数*

```json
请求：
{
"passWord":"123"
}

{
"userName":"",
"passWord":"123"
}

{
"userName":"hhhgg"
}

{
"userName":"hhhgg",
"passWord":""
}

结果：
{
"status": "ERROR",
"message": "用户名或密码为空"
}
```

**3. 测试用例**

**测试内容：** *使用未注册的用户名*

```json
请求：
{
"userName":"hhhgg",
"passWord":"123"
}

结果：
{
"status": "OK",
"message": "注册成功",
"data": {
"id": 53,
"userName": "hhhgg"
}
}
```

## 2. 设备管理接口

### 2.1 新增设备

`/deviceRecord/addDevice	[POST]`

**1. 测试用例**

**测试内容：** *使用相同设备名*

```json
请求：
{
"name":"ceshi",
"createUserId":45,
"mqttPub":"ceshiPub1",
"mqttSub":"ceshiSub1"
}

结果：
{
"status": "ERROR",
"message": "设备名已存在"
}
```

**2. 测试用例**

**测试内容：** *使用已存在的mqttPub*

```json
请求：
{
"name":"ceshi1",
"createUserId":45,
"mqttPub":"ceshiPub1",
"mqttSub":"ceshiSub1"
}

结果：
{
"status": "ERROR",
"message": "mqttPub已存在"
}
```

**3. 请求用例**

**测试内容：** *使用已存在的mqttSub*

```json
请求：
{
"name":"ceshi1",
"createUserId":45,
"mqttPub":"ceshiPub2",
"mqttSub":"ceshiSub1"
}

结果：
{
"status": "ERROR",
"message": "mqttSub已存在"
}
```

**4. 测试用例**

**测试内容：** *设备名为空或不传设备名参数*

```json
请求：
{
"name":"",
"createUserId":45,
"mqttPub":"ceshiPub2",
"mqttSub":"ceshiSub1"
}

{
"createUserId":45,
"mqttPub":"ceshiPub2",
"mqttSub":"ceshiSub1"
}

结果：
{
"status": "ERROR",
"message": "设备名为空"
}
```

**5. 测试用例**

**测试内容：** *不传创建设备的createUserId或传入不存在的user.id*

```json
请求：
{
"name":"ceshi1",
"mqttPub":"ceshiPub",
"mqttSub":"ceshiSub1"
}

{
"name":"ceshi1",
"createUserId":100000,
"mqttPub":"ceshiPub",
"mqttSub":"ceshiSub1"
}

结果：
{
"status": "ERROR",
"message": "设备管理者不存在"
}
```

**6. 测试用例**

**测试内容：** *传入空的mqttPub或不传mqttPub参数*

```json
请求：
{
"name":"ceshi1",
"createUserId":45,
"mqttSub":"ceshiSub1"
}

{
"name":"ceshi1",
"createUserId":45,
"mqttPub":"",
"mqttSub":"ceshiSub1"
}

结果：
{
"status": "ERROR",
"message": "设备mqttPub为空"
}
```

**7. 测试用例**

**测试内容：** *传入空的mqttSub或不传mqttPub参数*

```JSON
请求：
{
"name":"ceshi1",
"createUserId":45,
"mqttPub":"ceshiPub1"
}

{
"name":"ceshi1",
"createUserId":45,
"mqttPub":"ceshiPub1",
"mqttSub":""
}

结果：
{
"status": "ERROR",
"message": "设备mqttSub为空"
}
```

**8. 测试用例 **

**测试内容：** *存在的user.id，同一用户下未出现的设备名，不存在的mqttPub，mqttSub值*

```json
请求：
{
"name":"ceshi2",
"createUserId":45,
"mqttPub":"ceshiPub2",
"mqttSub":"ceshiSub2"
}

结果：
{
"status": "OK",
"message": "设备添加成功",
"data": {
"id": 30,
"name": "ceshi2",
"createUserId": 45,
"mqttSub": "ceshiSub2",
"mqttPub": "ceshiPub2"
}
}
```

### 2.2 删除设备

`/deviceRecord/deleteDevice	[POST]`

**1. 测试用例**

**测试内容：** *该用户下不存在的设备名*

```json
请求：
{
"name":"ceshi888",
"createUserId":45
}

{
"name":"ceshi",
"createUserId":1000
}

结果：
{
"status": "ERROR",
"message": "设备不存在"
}
```

**2. 测试用例**

**测试内容：** *传入空的设备名或不传设备名参数*

```json
请求：
{
"createUserId":1000
}

{
"name":"",
"createUserId":1000
}

结果：
{
"status": "ERROR",
"message": "设备名为空"
}
```

**3. 请求用例**

**测试内容：** *不传createUserId参数*

```json
请求：
{
"name":"ceshi"
}

结果：
{
"status": "ERROR",
"message": "设备ID为空"
}
```

**4. 请求用例**

**测试内容：** *存在的用户及存在的用户下的设备名*

```json
请求：
{
"name":"ceshi2",
"createUserId":45
}

结果：
{
"status": "OK",
"message": "设备移除成功",
"data": {
"id": 30,
"name": "ceshi2",
"createUserId": 45,
"createDate": "2021-11-13 13:21:46",
"mqttSub": "ceshiSub2",
"mqttPub": "ceshiPub2"
}
}
```

### 2.3 查询设备信息

`/deviceRecord/getAllDevice	[GET]`

**1. 请求示例**

**测试内容：** *传入不存在的参数值*

```json
请求：
key: createUserId
value: 1000

结果：
{
"status": "ERROR",
"message": "用户不存在"
}
```

**2. 请求示例**

**测试内容：** *不传参数值*

```json
请求：
key:
value:

结果：
{
"status": "ERROR",
"message": "createUserId参数未传"
}
```

## 3. 数据API

### 3.1 获取词云数据

`/dataAPI/getWordCloudByTimeSection	[POST]`

**1. 测试用例**

**测试内容：** *传入不存在的设备名*

```json
请求：
{
"createUserId":29,
"name":"三号",
"startTime":"2021-11-09 14:11:00",
"endTime":"2021-11-09 14:11:59"
}


结果：
{
"message": "无此设备",
"status": "ERROR"
}
```

**2. 请求示例**

**测试内容：** *时间间隔过小或间隔为负*

```json
请求：
{
"createUserId":25,
"name":"三号",
"startTime":"2021-11-09 14:11:50",
"endTime":"2021-11-09 14:11:51"
}

{
"createUserId":25,
"name":"三号",
"startTime":"2021-11-09 14:11:50",
"endTime":"2021-11-09 12:11:51"
}

结果：
{
"message": "无数据，请调大时间差,并检查是否存在该设备相关语句信息",
"status": "ERROR"
}
```

**3. 测试用例**

**测试内容：** *不传createUserId参数*

```json
请求：
{
"name":"三号",
"startTime":"2021-11-09 14:11:00",
"endTime":"2021-11-09 14:11:59"
}

结果：
{
"message": "createUserId为空",
"status": "ERROR"
}
```

**4. 测试用例**

**测试内容：** *传入空的设备名或不传设备名参数*

```json
请求：
{
"createUserId":25,
"name":"",
"startTime":"2021-11-09 14:11:00",
"endTime":"2021-11-09 14:11:59"
}

{
"createUserId":25,
"startTime":"2021-11-09 14:11:00",
"endTime":"2021-11-09 14:11:59"
}

结果：
{
"message": "设备名为空",
"status": "ERROR"
}
```

**5. 测试用例**

**测试内容：** *传入空的开始时间或不传开始时间参数*

```json
测试：
{
"createUserId":25,
"name":"三号",
"endTime":"2021-11-09 14:11:59"
}

{
"createUserId":25,
"name":"三号",
"startTime":"",
"endTime":"2021-11-09 14:11:59"
}

结果：
{
"message": "查询开始时间为空",
"status": "ERROR"
}
```

**6. 测试用例**

**测试内容：** *传入空的结束时间或不传结束时间*

```json
测试：
{
"createUserId":25,
"name":"三号",
"startTime":"2021-11-09 14:11:00"
}

{
"createUserId":25,
"name":"三号",
"startTime":"2021-11-09 14:11:00",
"endTime":""
}

结果：
{
"message": "查询结束时间为空",
"status": "ERROR"
}
```

**7. 测试用例**

**测试内容：** *均传入正确且存在的参数*

```json
请求：
{
"createUserId":47,
"name":"五号设备",
"startTime":"2021-11-11 19:27:48",
"endTime":"2021-11-11 20:11:16"
}

结果：
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

### 3.2 获取设备语句数据

`/dataAPI/getAllSentenceMessageByDeviceId	[POST]`

**1. 测试用例**

**测试内容：** *传入该用户下不存在的设备名*

```json
请求：
{
"name":"五号设备",
"createUserId":48
}

结果：
{
"status": "ERROR",
"message": "设备不存在"
}
```

**2. 测试用例**

**测试内容：** *传入空的设备名或不传入设备名参数*

```json
请求：
{
"name":"",
"createUserId":48
}

{
"createUserId":48
}

结果：
{
"status": "ERROR",
"message": "设备名为空"
}
```

**3. 测试用例**

**测试内容：** *不传入createUserId参数*

```json
请求：
{
"name":"五号设备"
}

结果：
{
"status": "ERROR",
"message": "参数createUserID为空"
}
```

**4. 测试用例**

**测试内容：** *存在的用户及该用户拥有的设备名*

```json
请求：
{
"name":"五号设备",
"createUserId":47
}

结果：
{
"status": "OK",
"message": "数据查询成功",
"data": [
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
}
]
}
```

### 3.3 获取24小时内语句信息

`/dataAPI/getSentenceByDeviceIdOnOneday	[POST]`

**1. 测试用例**

**测试内容：** *该用户下未拥有的设备名*

```json
请求：
{
"name":"五号设备",
"createUserId":49
}

结果：
{
"status": "ERROR",
"message": "设备不存在"
}
```

**2. 测试用例**

**测试内容：** *传入空的设备名或不传入设备名参数*

```json
请求：
{
"name":"",
"createUserId":49
}

{
"createUserId":49
}

结果：
{
"status": "ERROR",
"message": "设备名为空"
}
```

**3. 测试用例**

**测试内容：** *不传入createUserId参数*

```json
请求：
{
"name":"五号设备"
}

结果：
{
"status": "ERROR",
"message": "参数createUserId为空"
}
```

**4. 测试用例**

**测试内容：** *存在的用户及该用户拥有的设备名*

```json
请求：
{
"name":"五号设备",
"createUserId":47
}

结果：
{
"status": "OK",
"message": "最新24小时内的单位时间信息量",
"data": {
"AllSentenceOneDay": [
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
},
{
"id": 81,
"deviceId": 8,
"sentence": "测试mqtt的通信行为test1111",
"time": "2021-11-13 02:20:23",
"sentiment": 2,
"confidence": 0.581671,
"positiveProb": 0.811752,
"negativeProb": 0.188248
},
{
"id": 82,
"deviceId": 8,
"sentence": "测试mqtt的通信行为test1111",
"time": "2021-11-13 02:32:20",
"sentiment": 2,
"confidence": 0.581671,
"positiveProb": 0.811752,
"negativeProb": 0.188248
},
{
"id": 83,
"deviceId": 8,
"sentence": "测试mqtt的通信行为test1111",
"time": "2021-11-13 02:49:10",
"sentiment": 2,
"confidence": 0.581671,
"positiveProb": 0.811752,
"negativeProb": 0.188248
}
],
"echatsData": [
{
"2021-11-12 13:55:47": 0
},
{
"2021-11-12 14:55:47": 6
},
{
"2021-11-12 15:55:47": 0
},
{
"2021-11-12 16:55:47": 0
},
{
"2021-11-12 17:55:47": 0
},
{
"2021-11-12 18:55:47": 4
},
{
"2021-11-12 19:55:47": 0
},
{
"2021-11-12 20:55:47": 0
},
{
"2021-11-12 21:55:47": 0
},
{
"2021-11-12 22:55:47": 2
},
{
"2021-11-12 23:55:47": 0
},
{
"2021-11-13 00:55:47": 0
},
{
"2021-11-13 01:55:47": 3
},
{
"2021-11-13 02:55:47": 0
},
{
"2021-11-13 03:55:47": 0
},
{
"2021-11-13 04:55:47": 0
},
{
"2021-11-13 05:55:47": 0
},
{
"2021-11-13 06:55:47": 0
},
{
"2021-11-13 07:55:47": 0
},
{
"2021-11-13 08:55:47": 0
},
{
"2021-11-13 09:55:47": 0
},
{
"2021-11-13 10:55:47": 0
},
{
"2021-11-13 11:55:47": 0
},
{
"2021-11-13 12:55:47": 0
}
]
}
}
```

### 3.4 获取情感饼图数据

`/dataAPI/getMotionPieByTimeSection	[POST]`

**1. 测试用例**

**测试内容：** *传入该用户不存在的设备名*

```json
请求：
{
"createUserId":40,
"name":"五号设备",
"startTime":"2021-11-10 19:12:49",
"endTime":"2021-11-11 10:56:19"
}


结果：
{
"message": "无此设备",
"status": "ERROR"
}
```

**2. 测试用例**

**测试内容：** *不传createUserId参数*

```json
请求：
{
"name":"五号设备",
"startTime":"2021-11-10 19:12:49",
"endTime":"2021-11-11 10:56:19"
}

结果：
{
"message": "createUserId为空",
"status": "ERROR"
}
```

**3. 测试用例**

**测试内容：** *传入空的设备名或不传入设备名参数*

```json
请求：
{
"createUserId":40,
"startTime":"2021-11-10 19:12:49",
"endTime":"2021-11-11 10:56:19"
}

{
"createUserId":40,
"name":"",
"startTime":"2021-11-10 19:12:49",
"endTime":"2021-11-11 10:56:19"
}

结果：
{
"message": "设备名为空",
"status": "ERROR"
}
```

**4. 测试用例**

**测试内容：** *传入空的开始时间或不传开始时间参数*

```json
请求：
{
"createUserId":40,
"name":"五号设备",
"endTime":"2021-11-11 10:56:19"
}

{
"createUserId":40,
"name":"五号设备",
"startTime":"",
"endTime":"2021-11-11 10:56:19"
}

结果：
{
"message": "查询开始时间为空",
"status": "ERROR"
}
```

**5. 测试用例**

**测试内容：** *传入空的结束时间或不传入结束时间参数*

```json
请求：
{
"createUserId":40,
"name":"五号设备",
"startTime":"2021-11-10 19:12:49"
}

{
"createUserId":40,
"name":"五号设备",
"startTime":"2021-11-10 19:12:49",
"endTime":""
}

结果：
{
"message": "查询结束时间为空",
"status": "ERROR"
}
```

**6. 测试用例**

**测试内容：** *传入时间差过小或时间差为负数*

```json
请求：
{
"createUserId":47,
"name":"五号设备",
"startTime":"2021-11-11 19:12:49",
"endTime":"2021-11-11 19:12:59"
}

{
"createUserId":47,
"name":"五号设备",
"startTime":"2021-11-11 19:12:49",
"endTime":"2021-11-11 10:56:19"
}

结果：
{
"message": "无数据，请调大时间差",
"status": "ERROR"
}
```

**7. 测试用例**

**测试内容：** *传入存在的用户id及该用户拥有的设备名和较大的时间差*

```json
请求：
{
"createUserId":47,
"name":"五号设备",
"startTime":"2021-11-10 19:12:49",
"endTime":"2021-11-11 10:56:19"
}

结果：
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

## 4. 提供给树莓派的接口

### 4.1 语句分析及存库

`/raspberryPiAPI/insertSentence	[POST]`

**1. 测试用例**

**测试内容：** *传入不存在的设备id*

```json
请求：
{
"deviceId":1000,
"sentence":"测试mqtt的通信行为test1111"

}

结果：
{
"status": "ERROR",
"message": "插入数据库失败，无此设备"
}
```

**2. 测试用例**

**测试内容：** *不传入deviceId参数*

```json
请求：
{
"sentence":"测试mqtt的通信行为test1111"
}

结果：
{
"status": "ERROR",
"message": "设备id为空"
}
```

**3. 测试用例**

**测试内容：** *传入空的语句或不传语句参数*

```json
请求：
{
"deviceId":8
}

{
"deviceId":8,
"sentence":""
}

结果：
{
"status": "ERROR",
"message": "语句内容为空"
}
```

**4. 测试用例**

**测试内容：** *传入存在的设备id及非空语句*

```json
请求：
{
"deviceId":8,
"sentence":"测试mqtt的通信行为test1111"
}

结果：
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

