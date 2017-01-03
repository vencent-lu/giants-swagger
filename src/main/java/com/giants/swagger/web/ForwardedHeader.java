/**
 * 
 */
package com.giants.swagger.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author vencent.lu
 *
 */
class ForwardedHeader {

	  public static String NAME = "Forwarded";
	  private static final ForwardedHeader NO_HEADER = new ForwardedHeader(Collections.<String, String> emptyMap());

	  private final Map<String, String> elements;

	  private ForwardedHeader(Map<String, String> elements) {
	    this.elements = elements;
	  }

	  /**
	   * Creates a new {@link ForwardedHeader} from the given source.
	   *
	   * @param source can be {@literal null}.
	   * @return
	   */
	  public static ForwardedHeader of(String source) {

	    if (!StringUtils.hasText(source)) {
	      return NO_HEADER;
	    }

	    Map<String, String> elements = new HashMap<String, String>();

	    for (String part : source.split(";")) {

	      String[] keyValue = part.split("=");

	      if (keyValue.length != 2) {
	        continue;
	      }

	      elements.put(keyValue[0].trim(), keyValue[1].trim());
	    }

	    Assert.notNull(elements, "Forwarded elements must not be null!");
	    Assert.isTrue(!elements.isEmpty(), "At least one forwarded element needs to be present!");

	    return new ForwardedHeader(elements);
	  }

	  /**
	   * Returns the value defined for the {@code proto} parameter of the header.
	   *
	   * @return
	   */
	  public String getProto() {
	    return elements.get("proto");
	  }

	  /**
	   * Returns the value defined for the {@code host} parameter of the header.
	   *
	   * @return
	   */
	  public String getHost() {
	    return elements.get("host");
	  }
	}
