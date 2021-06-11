package com.giants.swagger.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GiantsSwaggerProperties TODO
 * date time: 2021/6/11 14:40
 * Copyright 2021 github.com/vencent-lu/giants-swagger Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@ConfigurationProperties(prefix = "giants.swagger")
public class GiantsSwaggerProperties {

    /**
     * 忽略参数类型，入参 和 返回结果对象都会忽略
     */
    private List<Class> ignoreParameterTypes;

    /**
     * 忽略请求参数类型，入参对象都会忽略
     */
    private List<Class> ignoreRequestParameterTypes;

    /**
     * 忽略model中的属性名称列表
     */
    private List<String> ignoreModelPropertyNames;

    /**
     * 返回结果封装对象
     */
    @NestedConfigurationProperty
    private ReturnResultClass returnResultClass;

    public List<Class> getIgnoreParameterTypes() {
        return ignoreParameterTypes;
    }

    public void setIgnoreParameterTypes(List<Class> ignoreParameterTypes) {
        this.ignoreParameterTypes = ignoreParameterTypes;
    }

    public List<Class> getIgnoreRequestParameterTypes() {
        return ignoreRequestParameterTypes;
    }

    public void setIgnoreRequestParameterTypes(List<Class> ignoreRequestParameterTypes) {
        this.ignoreRequestParameterTypes = ignoreRequestParameterTypes;
    }

    public List<String> getIgnoreModelPropertyNames() {
        return ignoreModelPropertyNames;
    }

    public void setIgnoreModelPropertyNames(List<String> ignoreModelPropertyNames) {
        this.ignoreModelPropertyNames = ignoreModelPropertyNames;
    }

    public ReturnResultClass getReturnResultClass() {
        return returnResultClass;
    }

    public void setReturnResultClass(ReturnResultClass returnResultClass) {
        this.returnResultClass = returnResultClass;
    }
}
