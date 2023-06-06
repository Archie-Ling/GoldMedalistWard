package org.example.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //fieldName不是字段名(gmt_create),而是类的属性名(gmtCreate)
        this.setFieldValByName("createdTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.strictInsertFill(metaObject,"isDeleted",Integer.class,0);
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
