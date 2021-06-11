package com.giants.swagger.springfox.plugin;

import com.fasterxml.classmate.TypeResolver;
import com.giants.swagger.configuration.GiantsSwaggerProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationModelsProviderPlugin;
import springfox.documentation.spi.service.contexts.RequestMappingContext;

import javax.annotation.Resource;

/**
 * CustomModelsProvider TODO
 * date time: 2021/5/29 8:41
 * Copyright 2021 github.com/vencent-lu/giants-swagger Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GiantsCustomModelsProvider implements OperationModelsProviderPlugin {

    @Resource
    private TypeResolver typeResolver;
    @Resource
    private GiantsSwaggerProperties giantsSwaggerProperties;

    private boolean supports = true;

    @Override
    public void apply(RequestMappingContext context) {
        if (this.giantsSwaggerProperties.getReturnResultClass() != null) {
            context.operationModelsBuilder().addReturn(typeResolver.resolve(this.giantsSwaggerProperties.getReturnResultClass().getType()));
        }
        this.supports = false;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return this.supports;
    }
}
