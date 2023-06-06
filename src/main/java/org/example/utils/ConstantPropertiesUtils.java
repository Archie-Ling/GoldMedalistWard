package org.example.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

//当项目启动，spring接口，spring加载之后，执行接口一个方法
@Component
public class ConstantPropertiesUtils implements InitializingBean {


    //定义公开静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = "oss-cn-chengdu.aliyuncs.com";
        ACCESS_KEY_ID = "LTAI5tS6amFwgFinXXU9KMst";
        ACCESS_KEY_SECRET = "9lJ2OsiEvYBeIXLQErVMNQMhDQQKgA";
        BUCKET_NAME = "lkw-edu";
    }
}