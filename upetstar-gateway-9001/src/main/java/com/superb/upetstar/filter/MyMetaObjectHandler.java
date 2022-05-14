package com.superb.upetstar.filter;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author hym
 */
@Component // 交由spring管理，可以实现自动注入
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 当我们这么做的时候，再用MP做添加，俩时间中就会有当前时间，不用再去手动set进去
     * 使用MP实现添加操作，该方法就会执行
     *
     * @param metaObject 源数据（表名，字段名 —— 数据的数据）
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 设置 属性值 通过 名称
        // 属性名 属性值 元数据：metaObject 表名称、字段
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    /**
     * 使用MP实现修改操作，这个方法就会执行
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

}