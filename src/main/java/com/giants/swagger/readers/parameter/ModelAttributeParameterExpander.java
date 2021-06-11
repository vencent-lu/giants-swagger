package com.giants.swagger.readers.parameter;

import com.giants.swagger.configuration.GiantsSwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.common.Compatibility;
import springfox.documentation.schema.property.bean.AccessorsProvider;
import springfox.documentation.schema.property.field.FieldProvider;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spring.web.readers.parameter.ExpansionContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ModelAttributeParameterExpander TODO
 * date time: 2021/6/11 14:52
 * Copyright 2021 github.com/vencent-lu/giants-swagger Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
public class ModelAttributeParameterExpander extends springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander {

    @Resource
    private GiantsSwaggerProperties giantsSwaggerProperties;

    @Autowired
    public ModelAttributeParameterExpander(
            FieldProvider fields,
            AccessorsProvider accessors,
            EnumTypeDeterminer enumTypeDeterminer) {
        super(fields, accessors, enumTypeDeterminer);
    }

    @Override
    public List<Compatibility<springfox.documentation.service.Parameter, RequestParameter>> expand(ExpansionContext context) {
        if (this.giantsSwaggerProperties.getIgnoreRequestParameterTypes().contains(context.getParamType().getErasedType())) {
            return new ArrayList<>();
        }
        return super.expand(context);
    }
}
