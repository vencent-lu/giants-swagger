package com.giants.swagger.configuration;

import com.giants.common.collections.CollectionUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.service.contexts.Defaults;

/**
 * GiantsSwaggerConfiguration TODO
 * date time: 2021/6/2 9:50
 * Copyright 2021 www.meikuangrm.com Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(GiantsSwaggerProperties.class)
@ComponentScan(basePackages = "com.giants.swagger")
public class GiantsSwaggerConfiguration {

    @Bean
    public Object addIgnorableParameterTypes(BeanFactory beanFactory,
                                                GiantsSwaggerProperties giantsSwaggerProperties) {
        if (CollectionUtils.isNotEmpty(giantsSwaggerProperties.getIgnoreParameterTypes())) {
            Defaults defaults = beanFactory.getBean(Defaults.class);
            defaults.defaultIgnorableParameterTypes().addAll(giantsSwaggerProperties.getIgnoreParameterTypes());
        }
        return null;
    }

}
