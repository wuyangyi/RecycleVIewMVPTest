package com.zz.recycleviewmvptest.bean;

import com.zz.recycleviewmvptest.widget.Utils;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * 对象P转为String形式存入数据库
 * @param <P>
 */
public class BaseConvert<P> implements PropertyConverter<P, String> {
    @Override
    public P convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        return Utils.base64Str2Object(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(P entityProperty) {
        if (entityProperty == null) {
            return null;
        }
        return Utils.object2Base64Str(entityProperty);
    }

}
