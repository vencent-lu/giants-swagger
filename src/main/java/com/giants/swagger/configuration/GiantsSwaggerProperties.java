package com.giants.swagger.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GiantsSwaggerProperties TODO
 * date time: 2021/5/29 10:36
 * Copyright 2021 www.meikuangrm.com Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@ConfigurationProperties(prefix = "giants.swagger")
public class GiantsSwaggerProperties {

    private List<Class> ignoreParameterTypes;

    private List<String> ignoreModelPropertyNames;

    private ReturnResultClass returnResultClass;

    public List<Class> getIgnoreParameterTypes() {
        return ignoreParameterTypes;
    }

    public void setIgnoreParameterTypes(List<Class> ignoreParameterTypes) {
        this.ignoreParameterTypes = ignoreParameterTypes;
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
