package com.giants.swagger.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc  
@EnableSwagger2
@ComponentScan(basePackages={"com.giants.swagger.web"})
@Configuration
public class GiantsSwaggerConfig extends WebMvcConfigurerAdapter {
	
	private List<Parameter> excludeParameters;
	
	@Bean  
    public Docket createRestApi() {  
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .build();
    }  
 
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")  
        			.addResourceLocations("classpath:/META-INF/resources/");  
		registry.addResourceHandler("swagger/**")  
        			.addResourceLocations("classpath:/META-INF/resources/swagger/");
		super.addResourceHandlers(registry);
	}

	public List<Parameter> getExcludeParameters() {
		return excludeParameters;
	}

	public void setExcludeParameters(List<Parameter> excludeParameters) {
		this.excludeParameters = excludeParameters;
	}
	
	public void setExcludeParameter(Parameter excludeParameter) {
		this.excludeParameters = new ArrayList<Parameter>();
		this.excludeParameters.add(excludeParameter);
	}    
	
}
