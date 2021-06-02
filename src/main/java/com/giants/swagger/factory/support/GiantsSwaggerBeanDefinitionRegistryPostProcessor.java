package com.giants.swagger.factory.support;

import com.giants.common.collections.CollectionUtils;
import com.giants.swagger.configuration.GiantsSwaggerProperties;
import com.giants.swagger.mappers.ServiceModelToSwagger2MapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.service.contexts.Defaults;

import javax.annotation.Resource;

/**
 * SwaggerBeanDefinitionRegistryPostProcessor TODO
 * date time: 2021/5/28 14:44
 * Copyright 2021 www.meikuangrm.com Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Component
public class GiantsSwaggerBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Resource
    private GiantsSwaggerProperties giantsSwaggerProperties;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        registry.removeBeanDefinition("serviceModelToSwagger2MapperImpl");
        BeanDefinitionBuilder serviceModelToSwagger2MapperImplBuilder =
                BeanDefinitionBuilder.rootBeanDefinition(ServiceModelToSwagger2MapperImpl.class);
        registry.registerBeanDefinition("serviceModelToSwagger2MapperImpl",
                serviceModelToSwagger2MapperImplBuilder.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        /*GiantsSwaggerProperties giantsSwaggerProperties = (GiantsSwaggerProperties)beanFactory.getBean(
                "giantsSwaggerProperties");
        if (CollectionUtils.isNotEmpty(giantsSwaggerProperties.getIgnorableParameterTypes())) {
            Defaults defaults = beanFactory.getBean(Defaults.class);
            defaults.defaultIgnorableParameterTypes().addAll(giantsSwaggerProperties.getIgnorableParameterTypes());
        }*/
    }
}
