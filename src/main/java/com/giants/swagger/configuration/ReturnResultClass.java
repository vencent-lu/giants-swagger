package com.giants.swagger.configuration;

import java.util.List;

/**
 * ReturnResultClass TODO
 * date time: 2021/5/29 11:02
 * Copyright 2021 github.com/vencent-lu/giants-swagger Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
public class ReturnResultClass {

    private Class<?> type;
    private String dataProperty;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getDataProperty() {
        return dataProperty;
    }

    public void setDataProperty(String dataProperty) {
        this.dataProperty = dataProperty;
    }

}
