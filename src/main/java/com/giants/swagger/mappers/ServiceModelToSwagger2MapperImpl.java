package com.giants.swagger.mappers;

import com.giants.common.collections.CollectionUtils;
import com.giants.swagger.configuration.GiantsSwaggerProperties;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.utils.PropertyModelConverter;
import springfox.documentation.service.Documentation;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

/**
 * ServiceModelToSwagger2MapperImpl TODO
 * date time: 2021/5/28 14:16
 * Copyright 2021 github.com/vencent-lu/giants-swagger Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
public class ServiceModelToSwagger2MapperImpl extends springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl {

    private Model resultModel;
    private final PropertyModelConverter propertyModelConverter = new PropertyModelConverter();
    @Resource
    private GiantsSwaggerProperties giantsSwaggerProperties;

    @Override
    public Swagger mapDocumentation(Documentation from) {
        Swagger swagger = super.mapDocumentation(from);
        Map<String, Path> paths = swagger.getPaths();
        Map<String, Model> definitions = swagger.getDefinitions();

        if (CollectionUtils.isNotEmpty(this.giantsSwaggerProperties.getIgnoreModelPropertyNames())) {
            Iterator<Map.Entry<String, Model>> modelIterator = definitions.entrySet().iterator();
            while (modelIterator.hasNext()) {
                Model model = modelIterator.next().getValue();
                Map<String, Property> properties = model.getProperties();
                for (String ignorePropertyName : this.giantsSwaggerProperties.getIgnoreModelPropertyNames()) {
                    properties.remove(ignorePropertyName);
                }
            }
        }

        if (this.resultModel == null && this.giantsSwaggerProperties.getReturnResultClass() != null) {
            this.resultModel =
                    definitions.get(this.giantsSwaggerProperties.getReturnResultClass().getType().getSimpleName());
        }
        if (this.resultModel != null) {
            Iterator<Map.Entry<String, Path>> pathIterator = paths.entrySet().iterator();
            while (pathIterator.hasNext()) {
                Path path = pathIterator.next().getValue();
                this.operationProcess(path.getGet());
                this.operationProcess(path.getPut());
                this.operationProcess(path.getPost());
                this.operationProcess(path.getDelete());
            }
        }
        return swagger;
    }

    private void operationProcess(Operation operation) {
        if (operation != null) {
            Map<String, Response> responses = operation.getResponses();
            Iterator<Map.Entry<String, Response>> iterator = responses.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Response> entry = iterator.next();
                Response response = entry.getValue();
                ObjectProperty resultProperty =
                        (ObjectProperty)this.propertyModelConverter.modelToProperty((Model)this.resultModel.clone());
                resultProperty.property(this.giantsSwaggerProperties.getReturnResultClass().getDataProperty(), response.getSchema());
                response.setSchema(resultProperty);
            }
        }
    }
}
