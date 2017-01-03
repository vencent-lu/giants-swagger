/**
 * 
 */
package springfox.documentation.spring.web.readers.operation;

import com.fasterxml.classmate.ResolvedType;
import com.giants.swagger.configuration.GiantsSwaggerConfig;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Predicates.*;
import static com.google.common.collect.Lists.*;
import static springfox.documentation.schema.Collections.*;
import static springfox.documentation.schema.Maps.*;
import static springfox.documentation.schema.Types.*;

/**
 * @author vencent.lu
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OperationParameterReader implements OperationBuilderPlugin {
  private final ModelAttributeParameterExpander expander;

  @Autowired
  private DocumentationPluginsManager pluginsManager;
  
  @Autowired
  private GiantsSwaggerConfig giantsSwaggerConfig;

  @Autowired
  public OperationParameterReader(ModelAttributeParameterExpander expander) {
    this.expander = expander;
  }

  @Override
  public void apply(OperationContext context) {
    context.operationBuilder().parameters(context.getGlobalOperationParameters());
    context.operationBuilder().parameters(readParameters(context));
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return true;
  }

  private List<Parameter> readParameters(final OperationContext context) {

    List<ResolvedMethodParameter> methodParameters = context.getParameters();
    List<Parameter> parameters = newArrayList();

    for (ResolvedMethodParameter methodParameter : methodParameters) {
      ResolvedType alternate = context.alternateFor(methodParameter.getParameterType());
      if (!shouldIgnore(methodParameter, alternate, context.getIgnorableParameterTypes())) {

        ParameterContext parameterContext = new ParameterContext(methodParameter,
            new ParameterBuilder(),
            context.getDocumentationContext(),
            context.getGenericsNamingStrategy(),
            context);
 
        if (shouldExpand(methodParameter, alternate)) {
        	boolean isAdd = true;
			if (this.giantsSwaggerConfig.getExcludeParameters() != null) {
				String defaultName = methodParameter.defaultName().get();
				for (com.giants.swagger.configuration.Parameter parameter : this.giantsSwaggerConfig
								.getExcludeParameters()) {
					if (methodParameter.getParameterType().getErasedType() == parameter.getType() && defaultName.equals(parameter.getName())) {
						isAdd = false;
					}
				}
			}
			if (isAdd) {
				parameters.addAll(
			              expander.expand(
			                  "",
			                  methodParameter.getParameterType(),
			                  context.getDocumentationContext()));
			}          
        } else {
          parameters.add(pluginsManager.parameter(parameterContext));
        }
      }
    }
    return FluentIterable.from(parameters).filter(not(hiddenParams())).toList();
  }

  private Predicate<Parameter> hiddenParams() {
    return new Predicate<Parameter>() {
      @Override
      public boolean apply(Parameter input) {
        return input.isHidden();
      }
    };
  }

  private boolean shouldIgnore(
      final ResolvedMethodParameter parameter,
      ResolvedType resolvedParameterType,
      final Set<Class> ignorableParamTypes) {

    if (ignorableParamTypes.contains(resolvedParameterType.getErasedType())) {
      return true;
    }
    return FluentIterable.from(ignorableParamTypes)
        .filter(isAnnotation())
        .filter(parameterIsAnnotatedWithIt(parameter)).size() > 0;

  }

  private Predicate<Class> parameterIsAnnotatedWithIt(final ResolvedMethodParameter parameter) {
    return new Predicate<Class>() {
      @Override
      public boolean apply(Class input) {
        return parameter.hasParameterAnnotation(input);
      }
    };
  }

  private Predicate<Class> isAnnotation() {
    return new Predicate<Class>() {
      @Override
      public boolean apply(Class input) {
        return Annotation.class.isAssignableFrom(input);
      }
    };
  }

  private boolean shouldExpand(final ResolvedMethodParameter parameter, ResolvedType resolvedParamType) {
    return (!parameter.hasParameterAnnotations() || parameter.hasParameterAnnotation(ModelAttribute.class))
        && !isBaseType(typeNameFor(resolvedParamType.getErasedType()))
        && !resolvedParamType.getErasedType().isEnum()
        && !isContainerType(resolvedParamType)
        && !isMapType(resolvedParamType);

  }

}

