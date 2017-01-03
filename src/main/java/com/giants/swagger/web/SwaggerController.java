/**
 * 
 */
package com.giants.swagger.web;

import static com.google.common.base.Strings.isNullOrEmpty;

import io.swagger.models.Swagger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

/**
 * @author vencent.lu
 *
 */
@Controller
@ApiIgnore
public class SwaggerController {
	
	public static final String DEFAULT_URL = "/v2/api-docs";

	  @Value("${springfox.documentation.swagger.v2.host:DEFAULT}")
	  private String hostNameOverride;

	  @Autowired
	  private DocumentationCache documentationCache;

	  @Autowired
	  private ServiceModelToSwagger2Mapper mapper;

	  @Autowired
	  private JsonSerializer jsonSerializer;

	  @ApiIgnore
	  @RequestMapping(value = "${springfox.documentation.swagger.v2.path:" + DEFAULT_URL + "}",
	      method = RequestMethod.GET)
	  public
	  @ResponseBody
	  ResponseEntity<Json> getDocumentation(
	      @RequestParam(value = "group", required = false) String swaggerGroup,
	      HttpServletRequest servletRequest) {

	    String groupName = Optional.fromNullable(swaggerGroup).or(Docket.DEFAULT_GROUP_NAME);
	    Documentation documentation = documentationCache.documentationByGroup(groupName);
	    if (documentation == null) {
	    	return new ResponseEntity<Json>(HttpStatus.NOT_FOUND);
	    }
	    Swagger swagger = mapper.mapDocumentation(documentation);
	    if (isNullOrEmpty(swagger.getHost())) {
	      final UriComponents uriComponents = HostNameProvider.componentsFrom(servletRequest);
	      swagger.basePath(Strings.isNullOrEmpty(uriComponents.getPath()) ? "/" : uriComponents.getPath());
	      swagger.host(hostName(uriComponents));
	    }
	    return new ResponseEntity<Json>(jsonSerializer.toJson(swagger), HttpStatus.OK);
	  }
	  
	  private String hostName(UriComponents uriComponents) {
	    if ("DEFAULT".equals(hostNameOverride)) {
	      String host = uriComponents.getHost();
	      int port = uriComponents.getPort();
	      if (port > -1) {
	        return String.format("%s:%d", host, port);
	      }
	      return host;
	    }
	    return hostNameOverride;
	  }

}
