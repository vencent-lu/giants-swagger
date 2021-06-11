package com.giants.swagger.factory.support;

import com.giants.swagger.configuration.GiantsSwaggerProperties;
import com.giants.swagger.mappers.ServiceModelToSwagger2MapperImpl;
import com.giants.swagger.readers.parameter.ModelAttributeParameterExpander;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * SwaggerBeanDefinitionRegistryPostProcessor TODO
 * date time: 2021/5/28 14:44
 * Copyright 2021 github.com/vencent-lu/giants-swagger Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Component
public class GiantsSwaggerBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        registry.removeBeanDefinition("serviceModelToSwagger2MapperImpl");
        BeanDefinitionBuilder serviceModelToSwagger2MapperImplBuilder =
                BeanDefinitionBuilder.rootBeanDefinition(ServiceModelToSwagger2MapperImpl.class);
        registry.registerBeanDefinition("serviceModelToSwagger2MapperImpl",
                serviceModelToSwagger2MapperImplBuilder.getBeanDefinition());

        registry.removeBeanDefinition("modelAttributeParameterExpander");
        BeanDefinitionBuilder modelAttributeParameterExpanderBuilder =
                BeanDefinitionBuilder.rootBeanDefinition(ModelAttributeParameterExpander.class);
        registry.registerBeanDefinition("modelAttributeParameterExpander",
                modelAttributeParameterExpanderBuilder.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
