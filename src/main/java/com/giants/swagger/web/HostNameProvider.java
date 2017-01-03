/**
 * 
 */
package com.giants.swagger.web;

import static org.springframework.util.StringUtils.commaDelimitedListToStringArray;
import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.StringUtils.split;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

/**
 * @author vencent.lu
 *
 */
public class HostNameProvider {

	  public HostNameProvider() {
	    throw new UnsupportedOperationException();
	  }

	  static UriComponents componentsFrom(HttpServletRequest request) {
	    ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromServletMapping(request);

	    ForwardedHeader forwarded = ForwardedHeader.of(request.getHeader(ForwardedHeader.NAME));
	    String proto = hasText(forwarded.getProto()) ? forwarded.getProto() : request.getHeader("X-Forwarded-Proto");
	    String forwardedSsl = request.getHeader("X-Forwarded-Ssl");

	    if (hasText(proto)) {
	      builder.scheme(proto);
	    } else if (hasText(forwardedSsl) && forwardedSsl.equalsIgnoreCase("on")) {
	      builder.scheme("https");
	    }

	    String host = forwarded.getHost();
	    host = hasText(host) ? host : request.getHeader("X-Forwarded-Host");

	    if (!hasText(host)) {
	      return builder.build();
	    }

	    String[] hosts = commaDelimitedListToStringArray(host);
	    String hostToUse = hosts[0];

	    if (hostToUse.contains(":")) {

	      String[] hostAndPort = split(hostToUse, ":");

	      builder.host(hostAndPort[0]);
	      builder.port(Integer.parseInt(hostAndPort[1]));

	    } else {
	      builder.host(hostToUse);
	      builder.port(-1); // reset port if it was forwarded from default port
	    }

	    String port = request.getHeader("X-Forwarded-Port");

	    if (hasText(port)) {
	      builder.port(Integer.parseInt(port));
	    }

	    return builder.build();
	  }
	}
